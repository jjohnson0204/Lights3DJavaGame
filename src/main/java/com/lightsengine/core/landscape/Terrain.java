package com.lightsengine.core.landscape;

import com.lightsengine.core.ObjectLoader;
import com.lightsengine.core.entity.Material;
import com.lightsengine.core.entity.Model;
import com.lightsengine.core.entity.Texture;
import org.joml.Vector3f;

public class Terrain {
    private static final float SIZE = 800;
    private static final int VERTEX_COUNT = 128;

    private Vector3f position;
    private Model model;

    public Terrain(Vector3f position, ObjectLoader objectLoader, Material material)  {
        this.position = position;
        this.model = generateTerrain(objectLoader);
        this.model.setMaterial(material);
    }

    public Model generateTerrain(ObjectLoader objectLoader) {
        int count = VERTEX_COUNT * VERTEX_COUNT;
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textureCoords = new float[count * 2];
        int[] indices = new int[6 * (VERTEX_COUNT -1) * (VERTEX_COUNT - 1)];
        int vertexPointer = 0;

        for (int i = 0; i < VERTEX_COUNT; i++) {
            for (int j = 0; j < VERTEX_COUNT; j++) {
                vertices[vertexPointer * 3] = j / (VERTEX_COUNT - 1.0f) * SIZE;
                vertices[vertexPointer * 3 + 1] = 0;  // height map
                vertices[vertexPointer * 3 + 2] = i / (VERTEX_COUNT - 1.0f) * SIZE;

                normals[vertexPointer * 3] = 0;
                normals[vertexPointer * 3 +1] = 1;
                normals[vertexPointer * 3 +2] = 0;

                textureCoords[vertexPointer * 2] = j / (VERTEX_COUNT - 1.0f) * SIZE;
                textureCoords[vertexPointer * 2 + 1] = i / (VERTEX_COUNT - 1.0f) * SIZE;
                vertexPointer++;
            }
        }
        int pointer = 0;
        for (int i = 0; i < VERTEX_COUNT - 1.0f; i++) {
            for (int j = 0; j < VERTEX_COUNT - 1.0f; j++) {
                int topLeft = (i * VERTEX_COUNT) + j;
                int topRight = topLeft + 1;
                int bottomLeft = ((i + 1) * VERTEX_COUNT) + j;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }
        return objectLoader.loadModel(vertices, textureCoords, normals, indices);
    }

    public Vector3f getPosition() {
        return position;
    }

    public Model getModel() {
        return model;
    }
    public Material getMaterial() {
        return model.getMaterial();
    }
    public Texture getTexture() {
        return model.getTexture();
    }
}
