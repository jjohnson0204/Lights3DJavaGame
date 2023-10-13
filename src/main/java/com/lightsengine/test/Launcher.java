package com.lightsengine.test;

import com.lightsengine.core.EngineManager;
import com.lightsengine.core.WindowManager;
import com.lightsengine.core.utils.Consts;

public class Launcher {

    private static WindowManager window;
    private static TestGame game;


    public static void main(String[] args) {
        window = new WindowManager(Consts.TITLE, 1600, 900, false);
        game = new TestGame();
        EngineManager engine = new EngineManager();
        try {
            engine.start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WindowManager getWindow() {
        return window;
    }

    public static TestGame getGame() {
        return game;
    }
}