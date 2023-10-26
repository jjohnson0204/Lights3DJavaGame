package com.lightsengine.core;

import com.lightsengine.core.inputs.MouseInput;

public interface ILogic {

    void init() throws Exception;

    void input();

    void update(float interval, MouseInput mouseInput);

    void render();

    void cleanup();
}
