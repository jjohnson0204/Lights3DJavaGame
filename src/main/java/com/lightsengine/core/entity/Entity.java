package com.lightsengine.core.entity;

import org.joml.Vector3f;

import java.util.Objects;

public class Entity {
    private final Model model;
    private final Vector3f position, rotation;
    private final float scale;

    public Entity(Model model, Vector3f position, Vector3f rotation, float scale) {
        this.model = model;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public void incrementPosition(float x, float y, float z) {
        position.x += x;
        position.y += y;
        position.z += z;
    }

    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }

    public void incrementRotation(float x, float y, float z) {
        rotation.x += x;
        rotation.y += y;
        rotation.z += z;
    }

    public void setRotation(float x, float y, float z) {
        rotation.x = x;
        rotation.y = y;
        rotation.z = z;
    }

    public Model getModel() {
        return model;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Entity) obj;
        return Objects.equals(this.model, that.model) &&
                Objects.equals(this.position, that.position) &&
                Objects.equals(this.rotation, that.rotation) &&
                Float.floatToIntBits(this.scale) == Float.floatToIntBits(that.scale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, position, rotation, scale);
    }

    @Override
    public String toString() {
        return "Entity[" +
                "model=" + model + ", " +
                "pos=" + position + ", " +
                "rotation=" + rotation + ", " +
                "scale=" + scale + ']';
    }

}
