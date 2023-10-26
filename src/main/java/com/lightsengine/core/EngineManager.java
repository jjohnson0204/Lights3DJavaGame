package com.lightsengine.core;

import com.lightsengine.core.inputs.MouseInput;
import com.lightsengine.test.Main;
import com.lightsengine.core.utils.Consts;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

public class EngineManager {
    public static final long NANOSECOND = 1000000000L;
    public static final float FRAMERATE = 1000.0f;
    private static int fps;
    private static final float frameTime = 1.0f / FRAMERATE;
    private boolean isRunning;
    private WindowManager windowManager;
    private MouseInput mouseInput;
    private GLFWErrorCallback errorCallback;
    private ILogic gameLogic;

    private  void init() throws Exception {
        GLFW.glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
        windowManager = Main.getWindowManager();
        mouseInput = new MouseInput();
        gameLogic = Main.getGame();

        windowManager.init();
        gameLogic.init();
        mouseInput.init();
    }
    public void start() throws Exception {
        init();
        if (isRunning) {
            return;
        }
        run();
    }
    public void run() {
        this.isRunning = true;
        int frames = 0;
        long frameCounter = 0;
        long lastTime = System.nanoTime();
        double unprocessedTime = 0;

        while (isRunning) {
            boolean render = false;
            long startTime = System.nanoTime();
            long passedTime = startTime - lastTime;
            lastTime = startTime;

            unprocessedTime += passedTime / (double) NANOSECOND;
            frameCounter += passedTime;

            input();

            while (unprocessedTime > frameTime) {
                render = true;
                unprocessedTime -= frameTime;

                if (windowManager.windowShouldClose()) {
                    stop();
                }

                if (frameCounter >= NANOSECOND) {
                    setFps(frames);
                    windowManager.setTitle(Consts.TITLE + " - FPS: " + getFps());
                    frames = 0;
                    frameCounter = 0;
                }
            }

            if (render) {
                update(frameTime);
                render();
                frames++;
            }
        }
        cleanup();
     }
    private void stop() {
        if (!isRunning) {
            return;
        }
        isRunning = false;
     }
    private void input() {
        mouseInput.input();
        gameLogic.input();
     }
    private void render() {
        gameLogic.render();
        windowManager.update();
    }
    private void update(float interval) {
        gameLogic.update(interval, mouseInput);
     }
    private void cleanup() {
        gameLogic.cleanup();
        windowManager.cleanup();
        errorCallback.free();
        GLFW.glfwTerminate();
    }

    public static int getFps() {
        return fps;
    }

    public static void setFps(int fps) {
        EngineManager.fps = fps;
    }
}
