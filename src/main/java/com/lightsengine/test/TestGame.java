package com.lightsengine.test;

// Class Imports
import com.lightsengine.core.*;
import com.lightsengine.core.entity.Entity;
import com.lightsengine.core.entity.Model;
import com.lightsengine.core.entity.Texture;
import com.lightsengine.core.inputs.MouseInput;
import com.lightsengine.core.lighting.DirectionalLight;
import com.lightsengine.core.lighting.PointLight;
import com.lightsengine.core.lighting.SpotLight;
import com.lightsengine.core.utils.Consts;

// JOML Imports
import org.joml.Vector2f;
import org.joml.Vector3f;

// LWJGL Imports
import org.lwjgl.glfw.GLFW;

//Main
public class TestGame implements ILogic {
    // Variables
    // Import Class Variables
    private final RenderManager renderManager;
    private final ObjectLoader objectLoader;
    private final WindowManager windowManager;
    private Entity entity;
    private Camera camera;
    private DirectionalLight directionalLight;
    private PointLight[] pointLights;
    private SpotLight[] spotLights;

    // TestGame Variables
    Vector3f cameraInc;
    private float lightAngle, spotAngle = 0, spotInc = 1;

    // Main
    public TestGame() {
        renderManager = new RenderManager();
        windowManager = Main.getWindowManager();
        objectLoader = new ObjectLoader();
        camera = new Camera();
        cameraInc = new Vector3f(0, 0,0);
        lightAngle = -90;
    }


    @Override
    public void init() throws Exception {
        renderManager.init();

        Model model = objectLoader.loadOBJModel("/models/cube.obj");
        model.setTexture(new Texture(objectLoader.loadTexture("Java3DGame/textures/grassblock.png")), 1f);
//        model.setTexture(new Texture(objectLoader.loadTexture("D:\\JavaGames\\Java3DGame\\Java3DGame\\textures\\2.jpg")));
        entity = new Entity(model, new Vector3f(0, 0, -3), new Vector3f(0, 0, 0), 1);

        float lightIntensity = 1.0f;

        // Point Light
        Vector3f lightPosition = new Vector3f(-0.5f,-0.5f,-6f);
        Vector3f lightColor = new Vector3f(1,1,1);
        PointLight pointLight = new PointLight(lightColor, lightPosition, lightIntensity, 0,0,1);

        // Spotlight
        Vector3f coneDirection = new Vector3f(0, 0, -1);
        float cutoff = (float) Math.cos(Math.toRadians(180));
        SpotLight spotLight = new SpotLight(new PointLight(lightColor, new Vector3f(0, 0, -3.6f),
                lightIntensity, 0, 0, 0.2f), coneDirection, cutoff);

        SpotLight spotLight1 = new SpotLight(new PointLight(lightColor, new Vector3f(0, 0, -3.6f),
                lightIntensity, 0, 0, 1), coneDirection, cutoff);
        spotLight1.getPointLight().setPosition(new Vector3f(0.5f,0.5f,-6f));

        // Directional Light
        lightPosition = new Vector3f(-1,-10,0);
        lightColor = new Vector3f(1,1,1);
        directionalLight = new DirectionalLight(lightColor, lightPosition, lightIntensity);

        pointLights = new PointLight[] {pointLight};
        spotLights = new SpotLight[] {spotLight, spotLight1};
    }
    @Override
    public void input() {
        cameraInc.set(0, 0,0);

        if (windowManager.isKeyPressed(GLFW.GLFW_KEY_W)) {
            cameraInc.z = -1;
        }
        if (windowManager.isKeyPressed(GLFW.GLFW_KEY_S)) {
            cameraInc.z = 1;
        }

        if (windowManager.isKeyPressed(GLFW.GLFW_KEY_A)) {
            cameraInc.x = -1;
        }
        if (windowManager.isKeyPressed(GLFW.GLFW_KEY_D)) {
            cameraInc.x = 1;
        }

        if (windowManager.isKeyPressed(GLFW.GLFW_KEY_Z)) {
            cameraInc.y = -1;
        }
        if (windowManager.isKeyPressed(GLFW.GLFW_KEY_X)) {
            cameraInc.y = 1;
        }
        if (windowManager.isKeyPressed(GLFW.GLFW_KEY_C)) {
            camera.setPosition(0, 0, 0);
            camera.setRotation(0, 0, 0);
        }
        if (windowManager.isKeyPressed(GLFW.GLFW_KEY_O)) {
            pointLights[0].getPosition().x += 0.1f;
        }
        if (windowManager.isKeyPressed(GLFW.GLFW_KEY_P)) {
            pointLights[0].getPosition().x -= 0.1f;
        }
        var lightPos = spotLights[0].getPointLight().getPosition().z;

        if (windowManager.isKeyPressed(GLFW.GLFW_KEY_N))
            spotLights[0].getPointLight().getPosition().z = lightPos + 0.1f;

        if (windowManager.isKeyPressed(GLFW.GLFW_KEY_M))
            spotLights[0].getPointLight().getPosition().z = lightPos - 0.1f;
    }
    @Override
    public void update(float interval, MouseInput mouseInput) {
        camera.movePosition(
                cameraInc.x * Consts.CAMERA_STEP,
                cameraInc.y * Consts.CAMERA_STEP,
                cameraInc.z * Consts.CAMERA_STEP
        );

        if (mouseInput.isRightButtonPress()) {
            Vector2f rotVec = mouseInput.getDisplayVec();
            camera.moveRotation(rotVec.x * Consts.MOUSE_SENSITIVITY, rotVec.y * Consts.MOUSE_SENSITIVITY, 0);
        }

        //entity.incrementRotation(0.0f, 0.25f, 0.0f);

        spotAngle += spotInc * 0.05f;
        if (spotAngle > 4) {
            spotInc = -1;
        }
        else if (spotAngle <= -4) {
            spotInc = 1;
        }

        double spotAngleRad = Math.toRadians(spotAngle);
        Vector3f coneDir = spotLights[0].getPointLight().getPosition();
        coneDir.y = (float) Math.sin(spotAngleRad);

        lightAngle += 0.5f;
        if (lightAngle > 90) {
            directionalLight.setIntensity(0);

            if (lightAngle >= 360)
                lightAngle = -90;
        } else if (lightAngle <= -80 || lightAngle >= 80) {
            var factor = 1 - (Math.abs(lightAngle) - 80) / 10.0f;
            directionalLight.setIntensity(factor);
            directionalLight.getColor().y = Math.max(factor, 0.9f);
            directionalLight.getColor().z = Math.max(factor, 0.5f);
        } else {
            directionalLight.setIntensity(1);
            directionalLight.getColor().x = 1;
            directionalLight.getColor().y = 1;
            directionalLight.getColor().z = 1;
        }

        var angRad = Math.toRadians(lightAngle);
        directionalLight.getDirection().x = (float) Math.sin(angRad);
        directionalLight.getDirection().y = (float) Math.cos(angRad);
    }
    @Override
    public void render() {
        renderManager.render(entity, camera, directionalLight, pointLights, spotLights);
    }
    @Override
    public void cleanup() {
        renderManager.cleanup();
        objectLoader.cleanup();
    }
}
