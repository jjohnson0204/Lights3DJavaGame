package com.lightsengine.core;

import com.lightsengine.core.entity.Model;
import com.lightsengine.core.utils.Utils;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ObjectLoader {

    private final List<Integer> vaos = new ArrayList<>();
    private final List<Integer> vbos = new ArrayList<>();
    private final List<Integer> textures = new ArrayList<>();

    public Model loadOBJModel(String fileName) {
        List<String> lines = Utils.readAlllines(fileName);

        List<Vector3f> vertices = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Vector3i> faces = new ArrayList<>();

        for (String line : lines) {
            String[] tokens = line.split("\\s+");
            switch (tokens[0]) {
                case "v":
                    // Vertices
                    Vector3f verticesVec = new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3])
                    );
                    vertices.add(verticesVec);
                    break;
                case "vt":
                    // Vertex Textures
                    Vector2f textureVec = new Vector2f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2])
                    );
                    textures.add(textureVec);
                    break;
                case "vn":
                    // Vertex Normals
                    Vector3f normalsVec = new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3])
                    );
                    normals.add(normalsVec);
                    break;
                case "f":
                    // Faces
                    processFace(tokens[1], faces);
                    processFace(tokens[2], faces);
                    processFace(tokens[3], faces);
                    break;
                default:
                    break;
            }
        }
        List<Integer> indices = new ArrayList<>();
        float[] verticesArray = new float[vertices.size() * 3];
        int i = 0;
        for (Vector3f position: vertices) {
            verticesArray[i * 3] = position.x;
            verticesArray[i * 3 + 1] = position.y;
            verticesArray[i * 3 + 2] = position.z;
            i++;
        }
        float[] texCoordArray = new float[vertices.size() * 2];
        float[] normalArray = new float[vertices.size() * 3];

        for (Vector3i face : faces) {
            processVertex(face.x,
                    face.y,
                    face.z,
                    textures,
                    normals,
                    indices,
                    texCoordArray,
                    normalArray
            );
        }
        int[] indicesArray = indices.stream().mapToInt((Integer v) -> v).toArray();

        return loadModel(verticesArray, texCoordArray, indicesArray);
    }
    private static void processVertex(
        int position,
        int texCoord,
        int normal,
        List<Vector2f> texCoordList,
        List<Vector3f> normaList,
        List<Integer> indicesList,
        float[] texCoordArray, float[] normalArray) {

            indicesList.add(position);

            if (texCoord >= 0) {
                Vector2f texCoordVec = texCoordList.get(texCoord);
                texCoordArray[position *2] = texCoordVec.x;
                texCoordArray[position * 2 + 1] = 1 - texCoordVec.y;
            }
            if (normal >= 0) {
                Vector3f normalVec = normaList.get(normal);
                normalArray[position * 3] = normalVec.x;
                normalArray[position * 3 + 1] = normalVec.y;
                normalArray[position * 3 + 2] = normalVec.z;
            }
    }
    private static void processFace(String token, List<Vector3i> faces) {
        String[] lineToken = token.split("/");
        int length = lineToken.length;
        int position = -1, coords = -1, normal = -1;
        position = Integer.parseInt(lineToken[0]) - 1;
        if (length > 1) {
            String textCoord = lineToken[1];
            coords = textCoord.length() > 0 ? Integer.parseInt(textCoord) -1 : -1;
            if (length > 2) {
                normal = Integer.parseInt(lineToken[2]) - 1;
            }
        }
        Vector3i facesVec = new Vector3i(position, coords, normal);
        faces.add(facesVec);
    }
    public Model loadModel(float[] vertices, float[] texCoords, int[] indices) {
        int id = createVAO();
        storeIndicesBuffer(indices);
        storeDataInAttribList(0, 3, vertices);
        storeDataInAttribList(1, 2, texCoords);
        unbind();
        return new Model(id, indices.length);
    }
    public int loadTexture(String filename) throws Exception {
        int width, height;
        ByteBuffer byteBuffer;

        try(var stack = MemoryStack.stackPush()) {
            var w = stack.mallocInt(1);
            var h = stack.mallocInt(1);
            var c = stack.mallocInt(1);

            // 4 channels in file: red, green, blue and alpha
            final int desiredChannels = 4;

            byteBuffer = STBImage.stbi_load(filename, w, h, c, desiredChannels);

            if (byteBuffer == null) {
                var exceptionMessage = "Texture Image File " + filename + " not loaded " + STBImage.stbi_failure_reason();
                throw new Exception(exceptionMessage);
            }

            width = w.get();
            height = h.get();
        }

        var id = GL11.glGenTextures();

        textures.add(id);

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, byteBuffer);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
        STBImage.stbi_image_free(byteBuffer);

        return id;
    }
    private int createVAO() {
        var id = GL30.glGenVertexArrays();
        vaos.add(id);
        GL30.glBindVertexArray(id);
        return id;
    }
    private void storeIndicesBuffer(int[] indices) {
        var vbo = GL15.glGenBuffers();
        vbos.add(vbo);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vbo);
        var buffer = Utils.storeDataInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }
    private void storeDataInAttribList(int attribNo, int vertexCount, float[] data) {
        int vbo = GL15.glGenBuffers();
        vbos.add(vbo);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        var buffer = Utils.storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attribNo, vertexCount, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }
    private void unbind() {
        GL30.glBindVertexArray(0);
    }
    public void cleanup() {
        for(var vao: vaos)
            GL30.glDeleteVertexArrays(vao);

        for(var vbo: vbos)
            GL30.glDeleteBuffers(vbo);

        for(var texture : textures)
            GL11.glDeleteTextures(texture);
    }
}
