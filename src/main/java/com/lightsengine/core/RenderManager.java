package com.lightsengine.core;

import com.lightsengine.core.entity.Entity;
import com.lightsengine.core.utils.Transformation;
import com.lightsengine.core.utils.Utils;
import com.lightsengine.test.Main;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class RenderManager {

    private final WindowManager windowManager;
    private ShaderManager shaderManager;
    public RenderManager() {
        windowManager = Main.getWindowManager();
    }

    public void init() throws Exception {
        shaderManager = new ShaderManager();
        shaderManager.createVertexShader(Utils.loadResource("/shaders/vertex.vs"));
        shaderManager.createFragmentShader(Utils.loadResource("/shaders/fragment.fs"));
        shaderManager.link();
        shaderManager.createUniform("textureSampler");
        shaderManager.createUniform("transformationMatrix");
        shaderManager.createUniform("projectionMatrix");
        shaderManager.createUniform("viewMatrix");
    }

    public void render(Entity entity, Camera camera) {
        var model = entity.getModel();

        clear();

        shaderManager.bind();
        shaderManager.setUniform("textureSampler", 0);
        shaderManager.setUniform("transformationMatrix", Transformation.createTransformationMatrix(entity));
        shaderManager.setUniform("projectionMatrix", windowManager.updateProjectionMatrix());
        shaderManager.setUniform("viewMatrix", Transformation.getViewMatrix(camera));

        GL30.glBindVertexArray(model.getId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getId());
        // GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
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
