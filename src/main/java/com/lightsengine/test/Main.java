package com.lightsengine.test;

import com.lightsengine.core.EngineManager;
import com.lightsengine.core.WindowManager;
import com.lightsengine.core.utils.Consts;

public class Main {

    private static WindowManager windowManager;
    private static TestGame game;


    public static void main(String[] args) {
        windowManager = new WindowManager(Consts.TITLE, 1600, 900, true);
        game = new TestGame();
        EngineManager engine = new EngineManager();
        try {
            engine.start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WindowManager getWindowManager() {
        return windowManager;
    }

    public static TestGame getGame() {
        return game;
    }
}