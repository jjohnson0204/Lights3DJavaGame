package com.lightsengine.test;

import com.lightsengine.core.*;
import com.lightsengine.core.entity.Entity;
import com.lightsengine.core.entity.Model;
import com.lightsengine.core.entity.Texture;
import com.lightsengine.core.inputs.MouseInput;
import com.lightsengine.core.utils.Consts;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class TestGame implements ILogic {

    private static final float CAMERA_MOVE_SPEED = 0.05f;
    private final RenderManager renderManager;
    private final ObjectLoader objectLoader;
    private final WindowManager windowManager;

    private Entity entity;
    private Camera camera;

    Vector3f cameraInc;

    public TestGame() {
        renderManager = new RenderManager();
        windowManager = Main.getWindowManager();
        objectLoader = new ObjectLoader();
        camera = new Camera();
        cameraInc = new Vector3f(0, 0,0);
    }
    @Override
    public void init() throws Exception {
        renderManager.init();

        Model model = objectLoader.loadOBJModel("/models/bunny.obj");
        model.setTexture(new Texture(objectLoader.loadTexture("D:\\JavaGames\\Java3DGame\\Java3DGame\\textures\\grassblock.png")), 3f);
//        model.setTexture(new Texture(objectLoader.loadTexture("D:\\JavaGames\\Java3DGame\\Java3DGame\\textures\\2.jpg")));
        entity = new Entity(model, new Vector3f(0, 0, -1), new Vector3f(0, 0, 0), 1);
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

        entity.incrementRotation(0.0f, 0.25f, 0.0f);
    }

    @Override
    public void render() {
        renderManager.render(entity, camera);
    }

    @Override
    public void cleanup() {
        renderManager.cleanup();
        objectLoader.cleanup();
    }
}
