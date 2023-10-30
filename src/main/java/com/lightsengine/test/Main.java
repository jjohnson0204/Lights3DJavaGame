package com.lightsengine.test;

import com.lightsengine.core.EngineManager;
import com.lightsengine.core.WindowManager;
import com.lightsengine.core.utils.Consts;

public class Main {
    public static void main(String[] args) {
        var windowManager = new WindowManager(Consts.TITLE, 1920, 1080, false);
        var testGame = new TestGame(windowManager);
        var engineManager = new EngineManager(windowManager, testGame);

        try {
            engineManager.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}