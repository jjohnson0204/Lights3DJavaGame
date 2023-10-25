package com.lightsengine.core.entity.character;

import com.lightsengine.core.item.GameItem;
import com.lightsengine.core.utils.BaseNotificationClass;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Player extends BaseNotificationClass {
    private String name;
    private String characterClass;
    private int hitPoints;
    private int experiencePoints;
    private int level;
    private int gold;
    private final PropertyChangeSupport propertyChangeSupport;

    public Player() {
        propertyChangeSupport = new PropertyChangeSupport(this);
        setInventory(new ArrayList<>());
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

    private ArrayList<GameItem> Inventory;
    public final ArrayList<GameItem> getInventory()
    {
        return Inventory;
    }
    public final void setInventory(ArrayList<GameItem> value)
    {
        Inventory = value;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
