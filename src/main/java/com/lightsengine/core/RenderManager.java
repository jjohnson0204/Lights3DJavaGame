package com.lightsengine.core;

import com.lightsengine.core.entity.Entity;
import com.lightsengine.core.lighting.DirectionalLight;
import com.lightsengine.core.utils.Consts;
import com.lightsengine.core.utils.Transformation;
import com.lightsengine.core.utils.Utils;
import com.lightsengine.test.Main;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class RenderManager {

    private final WindowManager windowManager;
    private final String textureSamplerUniformName = "textureSampler";
    private final String transformationMatrixUniformName = "transformationMatrix";
    private final String projectionMatrixUniformName = "projectionMatrix";
    private final String viewMatrixUniformName = "viewMatrix";
    private final String ambientLightUniformName = "ambientLight";
    private final String materialUniformName = "material";
    private final String specularPowerUniformName = "specularPower";
    private final String directionalLightUniformName = "directionalLight";
    private ShaderManager shaderManager;
    public RenderManager() {
        windowManager = Main.getWindowManager();
    }
    public void init() throws Exception {
        shaderManager = new ShaderManager();
        shaderManager.createVertexShader(Utils.loadResource("/shaders/vertex.vs"));
        shaderManager.createFragmentShader(Utils.loadResource("/shaders/fragment.fs"));
        shaderManager.link();
        shaderManager.createUniform(textureSamplerUniformName);
        shaderManager.createUniform(transformationMatrixUniformName);
        shaderManager.createUniform(projectionMatrixUniformName);
        shaderManager.createUniform(viewMatrixUniformName);
        shaderManager.createUniform(ambientLightUniformName);
        shaderManager.createMaterialUniform(materialUniformName);
        shaderManager.createUniform(specularPowerUniformName);
        shaderManager.createDirectionalLightUniform(directionalLightUniformName);
    }
    public void render(Entity entity, Camera camera, DirectionalLight directionalLight) {
        clear();

        if (windowManager.isResize()) {
            GL11.glViewport(0,0,windowManager.getWidth(), windowManager.getHeight());
            windowManager.setResize(true);
        }

        shaderManager.bind();
        shaderManager.setUniform(textureSamplerUniformName, 0);
        shaderManager.setUniform(transformationMatrixUniformName, Transformation.createTransformationMatrix(entity));
        shaderManager.setUniform(projectionMatrixUniformName, windowManager.updateProjectionMatrix());
        shaderManager.setUniform(viewMatrixUniformName, Transformation.getViewMatrix(camera));
        shaderManager.setUniform(ambientLightUniformName, Consts.AMBIENT_LIGHT);
        shaderManager.setUniform(materialUniformName, entity.getModel().getMaterial());
        shaderManager.setUniform(specularPowerUniformName, Consts.SPECULAR_POWER);
        shaderManager.setUniform(directionalLightUniformName, directionalLight);

        GL30.glBindVertexArray(entity.getModel().getId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, entity.getModel().getTexture().getId());

        // GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
        GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);

        shaderManager.unbind();
    }
    public void clear() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }
    public void cleanup() {
        shaderManager.cleanup();
    }
}
