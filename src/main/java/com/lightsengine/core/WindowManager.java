package com.lightsengine.core;

// Class Imports
import com.lightsengine.core.utils.Consts;

// LWJGL imports
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

// JOML imports
import org.joml.Matrix4f;

public class WindowManager {
    // WindowManager Variables
    private final Matrix4f projectionMatrix;
    private final String title;
    private boolean resize, vSync;
    private int width, height;
    private long window;


    // Main
    public WindowManager(String title, int width, int height, boolean vSync) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.vSync = vSync;
        projectionMatrix = new Matrix4f();
    }


    public void init() {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!GLFW.glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL11.GL_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 4);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 6);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);
        var maximized = false;
        if (width == 0 || height == 0) {
            width = 100;
            height = 100;
            GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_TRUE);
            maximized = true;
        }
        window = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
        if (window == MemoryUtil.NULL)
            throw new RuntimeException("Failed to create GLFW window");
        GLFW.glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
            this.width = width;
            this.height = height;
            this.setResize(true);
        });
        GLFW.glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE)
                GLFW.glfwSetWindowShouldClose(window, true);
        });
        if (maximized)
            GLFW.glfwMaximizeWindow(window);
        else {
            var vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
            assert vidMode != null;
            GLFW.glfwSetWindowPos(window, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);
        }
        GLFW.glfwMakeContextCurrent(window);
        if (isVSync())
            GLFW.glfwSwapInterval(1);
        GLFW.glfwShowWindow(window);
        GL.createCapabilities();
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_STENCIL_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }
    public void update() {
        GLFW.glfwSwapBuffers(window);
        GLFW.glfwPollEvents();
    }
    public void cleanup() {
        GLFW.glfwDestroyWindow(window);
    }
    public Matrix4f updateProjectionMatrix() {
        var aspectRatio = (float) width / height;
        return projectionMatrix.setPerspective(Consts.FOV, aspectRatio, Consts.Z_NEAR, Consts.Z_FAR);
    }
    public Matrix4f updateProjectionMatrix(Matrix4f matrix, int width, int height) {
        var aspectRatio = (float) width / height;
        return matrix.setPerspective(Consts.FOV, aspectRatio, Consts.Z_NEAR, Consts.Z_FAR);
    }
    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
    public boolean windowShouldClose() {
        return GLFW.glfwWindowShouldClose(window);
    }

    // Getters and Setters
    public boolean isResize() {
        return resize;
    }
    public boolean isVSync() {
        return vSync;
    }
    public boolean isKeyPressed(int keycode) {
        return GLFW.glfwGetKey(window, keycode) == GLFW.GLFW_PRESS;
    }

    public void setClearColor(float r, float g, float b, float a) {
        GL11.glClearColor(r, g, b, a);
    }
    public void setResize(boolean resize) {
        this.resize = resize;
    }
    public void setTitle(String title) {
        GLFW.glfwSetWindowTitle(window, title);
    }
    public void setVSync(boolean vSync) {
        this.vSync = vSync;
    }

    public String getTitle() {
        return title;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public long getWindowHandle() {
        return window;
    }

}
