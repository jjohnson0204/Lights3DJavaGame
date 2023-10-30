package com.lightsengine.core.rendering;


import com.lightsengine.core.Camera;
import com.lightsengine.core.ShaderManager;
import com.lightsengine.core.WindowManager;
import com.lightsengine.core.entity.Entity;
import com.lightsengine.core.entity.Model;
import com.lightsengine.core.landscape.Terrain;
import com.lightsengine.core.lighting.DirectionalLight;
import com.lightsengine.core.lighting.PointLight;
import com.lightsengine.core.lighting.SpotLight;
import com.lightsengine.core.utils.Consts;
import com.lightsengine.core.utils.Transformation;
import com.lightsengine.core.utils.Utils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TerrainRenderer implements IRenderer{
    ShaderManager shaderManager;
    WindowManager windowManager;

    private List<Terrain> terrains;

    private final String textureSamplerUniformName = "textureSampler";
    private final String transformationMatrixUniformName = "transformationMatrix";
    private final String projectionMatrixUniformName = "projectionMatrix";
    private final String viewMatrixUniformName = "viewMatrix";
    private final String materialUniformName = "material";
    private final String ambientLightUniformName = "ambientLight";
    private final String specularPowerUniformName = "specularPower";
    private final String directionalLightUniformName = "directionalLight";
    private final String pointLightListUniformName = "pointLights";
    private final String spotLightListUniformName = "spotLights";

    public TerrainRenderer(WindowManager windowManager) throws Exception {
        terrains = new ArrayList<>();
        shaderManager = new ShaderManager();
        this.windowManager = windowManager;
    }

    @Override
    public void init() throws Exception {
        shaderManager.createVertexShader(Utils.loadResource("/shaders/entity_vertex.glsl"));
        shaderManager.createFragmentShader(Utils.loadResource("/shaders/entity_fragment.glsl"));
        shaderManager.link();
        shaderManager.createUniform(textureSamplerUniformName);
        shaderManager.createUniform(transformationMatrixUniformName);
        shaderManager.createUniform(projectionMatrixUniformName);
        shaderManager.createUniform(viewMatrixUniformName);
        shaderManager.createUniform(ambientLightUniformName);
        shaderManager.createMaterialUniform(materialUniformName);
        shaderManager.createUniform(specularPowerUniformName);
        shaderManager.createDirectionalLightUniform(directionalLightUniformName);
        shaderManager.createPointLightListUniform(pointLightListUniformName, Consts.MAX_POINT_LIGHTS);
        shaderManager.createSpotLightListUniform(spotLightListUniformName, Consts.MAX_SPOT_LIGHTS);
    }

    @Override
    public void render(Camera camera, PointLight[] pointLights, SpotLight[] spotLights, DirectionalLight directionalLight) {
        shaderManager.bind();
        shaderManager.setUniform(projectionMatrixUniformName, windowManager.updateProjectionMatrix());
        RenderManager.renderLights(pointLights, spotLights, directionalLight, shaderManager);
        for (Terrain terrain : terrains) {
            bind(terrain.getModel());
            prepare(terrain, camera);
            GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            unbind();
        }
        terrains.clear();
        shaderManager.unbind();
    }

    @Override
    public void bind(Model model) {
        GL30.glBindVertexArray(model.getId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        shaderManager.setUniform(materialUniformName, model.getMaterial());

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getId());
    }

    @Override
    public void unbind() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    @Override
    public void prepare(Object terrain, Camera camera) {
        shaderManager.setUniform(textureSamplerUniformName, 0);
        shaderManager.setUniform(transformationMatrixUniformName, Transformation.createTransformationMatrix((Terrain) terrain));
        shaderManager.setUniform(viewMatrixUniformName, Transformation.getViewMatrix(camera));
    }

    @Override
    public void cleanup() {
        shaderManager.cleanup();
    }

    public List<Terrain> getTerrain() {
        return terrains;
    }
}
