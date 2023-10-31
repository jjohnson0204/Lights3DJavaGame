package com.lightsengine.test;

// Class Imports
import com.lightsengine.core.*;
import com.lightsengine.core.entity.Entity;

import com.lightsengine.core.entity.Material;
import com.lightsengine.core.entity.Texture;
import com.lightsengine.core.inputs.MouseInput;
import com.lightsengine.core.landscape.Terrain;
import com.lightsengine.core.lighting.DirectionalLight;
import com.lightsengine.core.lighting.PointLight;
import com.lightsengine.core.lighting.SpotLight;
import com.lightsengine.core.rendering.RenderManager;
import com.lightsengine.core.utils.Consts;

// JOML Imports
import org.joml.Vector3f;

// LWJGL Imports
import org.lwjgl.glfw.GLFW;

// Java Imports
import java.util.Random;


//Main
public class TestGame implements ILogic {
    private final RenderManager renderManager;
    private final WindowManager windowManager;
    private final ObjectLoader objectLoader;
    private final Camera camera;
    private final SceneManager sceneManager;

    private final Vector3f cameraInc;


    public TestGame(WindowManager windowManager) {
        this.windowManager = windowManager;
        renderManager = new RenderManager(windowManager);
        objectLoader = new ObjectLoader();
        camera = new Camera();
        cameraInc = new Vector3f(0, 0, 0);
        sceneManager = new SceneManager(-90);
    }

    @Override
    public void init() throws Exception {
        renderManager.init();

        // Model and Model Texture
        var model = objectLoader.loadObjModel("/models/cube.obj");
        model.setTexture(new Texture(objectLoader.loadTexture("D:\\JavaGames\\Java3DGame\\Java3DGame\\textures\\grassblock.png")), 1f);

        // Terrains
        Terrain terrain = new Terrain(new Vector3f(0, -1, -800), objectLoader,new Material(new Texture(objectLoader.loadTexture("D:\\JavaGames\\Java3DGame\\Java3DGame\\textures\\terrain.png")), 0.1f));
        Terrain terrain2 = new Terrain(new Vector3f(-800, -1, -800), objectLoader,new Material(new Texture(objectLoader.loadTexture("D:\\JavaGames\\Java3DGame\\Java3DGame\\textures\\flowers.png")), 0.1f));
        sceneManager.addTerrain(terrain);
        sceneManager.addTerrain(terrain2);

        // Entities
        var rnd = new Random();

        for (var i = 0; i < 2000; i++) {
            // renders 200 entities of the same model and texture
            float x = rnd.nextFloat() * 800;
//            var y = rnd.nextFloat() * 100 - 50;
            float z = rnd.nextFloat() * -800;

//            var entityPosition = new Vector3f(x, y, z);
//            var entityRotation = new Vector3f(rnd.nextFloat() * 180, rnd.nextFloat() * 180, 0);
//            var entityScale = 1;

            sceneManager.addEntity(new Entity(model, new Vector3f(x,2,z),
                    new Vector3f(0,0,0),2));
        }

        sceneManager.addEntity(new Entity(model, new Vector3f(0, 2, -5f),
                new Vector3f(0, 0, 0), 2));

        // light intensities
        float directionalLightIntensity = 1.0f;
        float pointLightIntensity = 1.0f;
        float spotLightIntensity = 1.0f;
        float spotLight2Intensity = 1.0f;

        // point lighting
        Vector3f lightPosition = new Vector3f(-0.5f, -0.5f, -3.2f);
        Vector3f lightColor = new Vector3f(1, 1, 1);
        PointLight pointLight = new PointLight(lightColor, lightPosition, pointLightIntensity, 0, 0, 1);

        // spotlight
        Vector3f coneDirection = new Vector3f(0, -50, 0);
        float cutoff = (float) Math.cos(Math.toRadians(140));
        SpotLight spotLight = new SpotLight(new PointLight(new Vector3f(0.25f, 0, 0f), new Vector3f(1f, 50f,-5f),
                spotLightIntensity, 0f, 0f, 0.2f), coneDirection, cutoff);

        // spot light2
        coneDirection = new Vector3f(0, -50, 0);
        cutoff = (float) Math.cos(Math.toRadians(140));
        SpotLight spotLight2 = new SpotLight(new PointLight(new Vector3f(0,0.25f,0f), new Vector3f(1f, 50f, -5f),
                spotLight2Intensity, 0f, 0f, 0.02f), coneDirection, cutoff);
//        spotLight2.getPointLight().setPosition(new Vector3f(0.5f, 0.5f, -3.6f));

        // directional lighting
        lightPosition = new Vector3f(-1, 0, 0);
        lightColor = new Vector3f(1, 1, 1);
        sceneManager.setDirectionalLight(new DirectionalLight(lightColor, lightPosition, directionalLightIntensity));

        sceneManager.setPointLights(new PointLight[]{pointLight});
        sceneManager.setSpotLights(new SpotLight[]{spotLight, spotLight2});
    }

    @Override
    public void input() {
        cameraInc.set(0, 0, 0);

        if (windowManager.isKeyPressed(GLFW.GLFW_KEY_W))
            cameraInc.z = -1;

        if (windowManager.isKeyPressed(GLFW.GLFW_KEY_S))
            cameraInc.z = 1;

        if (windowManager.isKeyPressed(GLFW.GLFW_KEY_A))
            cameraInc.x = -1;

        if (windowManager.isKeyPressed(GLFW.GLFW_KEY_D))
            cameraInc.x = 1;

        if (windowManager.isKeyPressed(GLFW.GLFW_KEY_Z))
            cameraInc.y = -1;

        if (windowManager.isKeyPressed(GLFW.GLFW_KEY_X))
            cameraInc.y = 1;

        var lightPos = sceneManager.getSpotLights()[0].getPointLight().getPosition().z;

        if (windowManager.isKeyPressed(GLFW.GLFW_KEY_N))
            sceneManager.getSpotLights()[0].getPointLight().getColor().z = lightPos + 0.1f;

        if (windowManager.isKeyPressed(GLFW.GLFW_KEY_M))
            sceneManager.getSpotLights()[0].getPointLight().getColor().z = lightPos - 0.1f;

        if (windowManager.isKeyPressed(GLFW.GLFW_KEY_C)) {
            camera.setPosition(0, 0, 0);
            camera.setRotation(0, 0, 0);
        }

        if (windowManager.isKeyPressed(GLFW.GLFW_KEY_O))
            sceneManager.getPointLights()[0].getPosition().x += 0.1f;

        if (windowManager.isKeyPressed(GLFW.GLFW_KEY_P))
            sceneManager.getPointLights()[0].getPosition().x -= 0.1f;
    }

    @Override
    public void update(float interval, MouseInput mouseInput) {
        camera.movePosition(cameraInc.x * Consts.CAMERA_MOVE_SPEED, cameraInc.y * Consts.CAMERA_MOVE_SPEED, cameraInc.z * Consts.CAMERA_MOVE_SPEED);

        if (mouseInput.isRightButtonPress()) {
            var rotateVector = mouseInput.getDisplayVector();
            camera.moveRotation(rotateVector.x * Consts.MOUSE_SENSITIVITY, rotateVector.y * Consts.MOUSE_SENSITIVITY, 0);
        }

        // entity.incrementRotation(0.0f, 0.25f, 0.0f);
        sceneManager.incSpotAngle(0.15f);
        if (sceneManager.getSpotAngle() > 9600)
            sceneManager.setSpotInc(-1);
        else if (sceneManager.getSpotAngle() <= -9600)
            sceneManager.setSpotInc(1);

        var spotAngleRad = Math.toRadians(sceneManager.getSpotAngle());
        var coneDirection = sceneManager.getSpotLights()[0].getPointLight().getPosition();
        coneDirection.x = (float) Math.sin(spotAngleRad);

        coneDirection = sceneManager.getSpotLights()[1].getPointLight().getPosition();
        coneDirection.z = (float) Math.cos(spotAngleRad * 0.15);

        // a basic day-night environment light cycle
        sceneManager.incLightAngle(1.1f);
        if (sceneManager.getLightAngle() > 90) {
            sceneManager.getDirectionalLight().setIntensity(0);
            if (sceneManager.getLightAngle() >= 360)
                sceneManager.setLightAngle(-90);
        } else if (sceneManager.getLightAngle() <= -80 || sceneManager.getLightAngle() >= 80) {
            float factor = 1 - (Math.abs(sceneManager.getLightAngle()) - 80) / 10.0f;
            sceneManager.getDirectionalLight().setIntensity(factor);
            sceneManager.getDirectionalLight().getColor().y = Math.max(factor, 0.9f);
            sceneManager.getDirectionalLight().getColor().z = Math.max(factor, 0.5f);
        } else {
            sceneManager.getDirectionalLight().setIntensity(1);
            sceneManager.getDirectionalLight().getColor().x = 1;
            sceneManager.getDirectionalLight().getColor().y = 1;
            sceneManager.getDirectionalLight().getColor().z = 1;
        }

        double angRad = Math.toRadians(sceneManager.getLightAngle());
        sceneManager.getDirectionalLight().getDirection().x = (float) Math.sin(angRad);
        sceneManager.getDirectionalLight().getDirection().y = (float) Math.cos(angRad);

        for (Entity entity : sceneManager.getEntities()) {
            renderManager.processEntity(entity);
        }
        for (Terrain terrain : sceneManager.getTerrains()) {
            renderManager.processTerrain(terrain);
        }
    }

    @Override
    public void render() {
        renderManager.render(camera, sceneManager);
    }

    @Override
    public void cleanup() {
        renderManager.cleanup();
        objectLoader.cleanup();
    }
}
