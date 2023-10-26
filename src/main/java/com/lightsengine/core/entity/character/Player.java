package com.lightsengine.core.entity.character;

import com.lightsengine.core.entity.Model;
import com.lightsengine.core.item.GameItem;
import com.lightsengine.core.utils.BaseNotificationClass;
import org.joml.Vector3f;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Vector;

public class Player extends BaseNotificationClass {
    // Player
    private Model model;
    private Vector3f pos, rotation;
    private float scale;

    // Variables
    private String name;
    private String characterClass;
    private int hitPoints;
    private int experiencePoints;
    private int level;
    private int gold;
    private final PropertyChangeSupport propertyChangeSupport;
    private ArrayList<GameItem> Inventory;

    public Player(Model model, Vector3f pos, Vector3f rotation, float scale) {
        this.model = model;
        this.pos = pos;
        this.rotation = rotation;
        this.scale = scale;

        propertyChangeSupport = new PropertyChangeSupport(this);
        setInventory(new ArrayList<>());
    }

    public void incPos(float x, float y, float z) {
        this.pos.x += x;
        this.pos.y += y;
        this.pos.z += z;
    }
    public void setPos(float x, float y, float z) {
        this.pos.x = x;
        this.pos.y = y;
        this.pos.z = z;
    }
    public void incRotation(float x, float y, float z) {
        this.rotation.x += x;
        this.rotation.y += y;
        this.rotation.z += z;
    }
    public void setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }
    public Model getModel() {
        return model;
    }
    public Vector3f getPos() {
        return pos;
    }
    public Vector3f getRotation() {
        return rotation;
    }
    public float getScale() {
        return scale;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        String oldValue = this.name;
        this.name = name;
        propertyChangeSupport.firePropertyChange("Name", oldValue, name);
    }
    public String getCharacterClass() {
        return characterClass;
    }
    public void setCharacterClass(String characterClass) {
        String oldValue = this.characterClass;
        this.characterClass = characterClass;
        propertyChangeSupport.firePropertyChange("CharacterClass", oldValue, characterClass);
    }
    public int getHitPoints() {
        return hitPoints;
    }
    public void setHitPoints(int hitPoints) {
        int oldValue = this.hitPoints;
        this.hitPoints = hitPoints;
        propertyChangeSupport.firePropertyChange("HitPoints", oldValue, hitPoints);
    }
    public int getExperiencePoints() {
        return experiencePoints;
    }
    public void setExperiencePoints(int experiencePoints) {
        int oldValue = this.experiencePoints;
        this.experiencePoints = experiencePoints;
        propertyChangeSupport.firePropertyChange("ExperiencePoints", oldValue, experiencePoints);
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        int oldValue = this.level;
        this.level = level;
        propertyChangeSupport.firePropertyChange("Level", oldValue, level);
    }
    public int getGold() {
        return gold;
    }
    public void setGold(int gold) {
        this.gold = gold;
    }
    public final ArrayList<GameItem> getInventory()
    {
        return Inventory;
    }
    public final void setInventory(ArrayList<GameItem> value) {
        Inventory = value;
    }
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
