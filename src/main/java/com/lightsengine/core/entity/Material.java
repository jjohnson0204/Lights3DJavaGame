package com.lightsengine.core.entity;

import com.lightsengine.core.utils.Consts;
import org.joml.Vector4f;

public class Material {
    private Vector4f ambientColor, diffuserColor, specularColor;
    private float reflectance;
    private Texture texture;

    public Material() {
        this.ambientColor = Consts.DEFAULT_COLOR;
        this.diffuserColor = Consts.DEFAULT_COLOR;
        this.specularColor = Consts.DEFAULT_COLOR;
        this.texture = null;
        this.reflectance = 0;
    }
    public Material(Vector4f color, float reflectance) {
        this(color, color, color, reflectance, null);
    }
    public Material(Vector4f color, float reflectance, Texture texture) {
        this(color, color, color, reflectance, texture);
    }
    public Material(Texture texture) {
        this(Consts.DEFAULT_COLOR, Consts.DEFAULT_COLOR, Consts.DEFAULT_COLOR, 0, texture);
    }
    public Material(Vector4f ambientColor, Vector4f diffuserColor, Vector4f specularColor, float reflectance, Texture texture) {
        this.ambientColor = ambientColor;
        this.diffuserColor = diffuserColor;
        this.specularColor = specularColor;
        this.reflectance = reflectance;
        this.texture = texture;
    }

    public Vector4f getAmbientColor() {
        return ambientColor;
    }

    public void setAmbientColor(Vector4f ambientColor) {
        this.ambientColor = ambientColor;
    }

    public Vector4f getDiffuserColor() {
        return diffuserColor;
    }

    public void setDiffuserColor(Vector4f diffuserColor) {
        this.diffuserColor = diffuserColor;
    }

    public Vector4f getSpecularColor() {
        return specularColor;
    }

    public void setSpecularColor(Vector4f specularColor) {
        this.specularColor = specularColor;
    }

    public float getReflectance() {
        return reflectance;
    }

    public void setReflectance(float reflectance) {
        this.reflectance = reflectance;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public boolean hasTexture() {
        return texture != null;
    }
}
