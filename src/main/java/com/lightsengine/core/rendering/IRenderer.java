package com.lightsengine.core.rendering;

import com.lightsengine.core.Camera;
import com.lightsengine.core.entity.Model;
import com.lightsengine.core.lighting.DirectionalLight;
import com.lightsengine.core.lighting.PointLight;
import com.lightsengine.core.lighting.SpotLight;

public interface IRenderer<T> {
    void init() throws Exception;

    void render(Camera camera, PointLight[] pointLights, SpotLight[] spotLights, DirectionalLight directionalLight);

    void bind(Model model);

    void unbind();

    void prepare(T t, Camera camera);

    void cleanup();
}
