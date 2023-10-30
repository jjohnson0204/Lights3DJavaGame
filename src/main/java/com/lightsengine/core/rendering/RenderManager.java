package com.lightsengine.core.rendering;

// Class Imports
import com.lightsengine.core.Camera;
import com.lightsengine.core.ShaderManager;
import com.lightsengine.core.WindowManager;
import com.lightsengine.core.entity.Entity;
import com.lightsengine.core.landscape.Terrain;
import com.lightsengine.core.lighting.DirectionalLight;
import com.lightsengine.core.lighting.PointLight;
import com.lightsengine.core.lighting.SpotLight;
import com.lightsengine.core.utils.Consts;

// LWJGL Imports
import org.lwjgl.opengl.GL11;

// Java Imports
import java.util.ArrayList;
import java.util.List;


//Main
public class RenderManager {
    private final WindowManager windowManager;
    private EntityRenderer entityRenderer;
    private TerrainRenderer terrainRenderer;

    public RenderManager(WindowManager windowManager) {
        this.windowManager = windowManager;
    }

    public void init() throws Exception {
        entityRenderer = new EntityRenderer(windowManager);
        terrainRenderer = new TerrainRenderer(windowManager);
        entityRenderer.init();
        terrainRenderer.init();
    }

    public static void renderLights(PointLight[] pointLights, SpotLight[] spotLights, DirectionalLight directionalLight, ShaderManager shaderManager) {
        var ambientLightUniformName = "ambientLight";
        shaderManager.setUniform(ambientLightUniformName, Consts.AMBIENT_LIGHT);

        var specularPowerUniformName = "specularPower";
        shaderManager.setUniform(specularPowerUniformName, Consts.SPECULAR_POWER);

        var numLights = spotLights != null ? spotLights.length : 0;

        for (var i = 0; i < numLights; i++) {
            shaderManager.setUniform("spotLights", spotLights[i], i);
        }

        numLights = pointLights != null ? pointLights.length : 0;

        for (var i = 0; i < numLights; i++) {
            shaderManager.setUniform("pointLights", pointLights[i], i);
        }

        var directionalLightUniformName = "directionalLight";
        shaderManager.setUniform(directionalLightUniformName, directionalLight);
    }

    public void render(Camera camera, DirectionalLight directionalLight, PointLight[] pointLights, SpotLight[] spotLights) {
        clear();

        if (windowManager.isResize()) {
            GL11.glViewport(0, 0, windowManager.getWidth(), windowManager.getHeight());
            windowManager.setResize(true);
        }

        entityRenderer.render(camera, pointLights, spotLights, directionalLight);
        terrainRenderer.render(camera, pointLights, spotLights, directionalLight);
    }

    public void processEntity(Entity entity) {
        var entities = entityRenderer.getEntities();

        List<Entity> entityList = entities.get(entity.getModel());

        if (entityList != null) {
            entityList.add(entity);
        } else {
            List<Entity> newEntityList = new ArrayList<>();
            newEntityList.add(entity);
            entities.put(entity.getModel(), newEntityList);
        }
    }
    public void processTerrain(Terrain terrain) {
        terrainRenderer.getTerrain().add(terrain);
    }
    public void clear() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void cleanup() {
        entityRenderer.cleanup();
        terrainRenderer.cleanup();
    }
}
