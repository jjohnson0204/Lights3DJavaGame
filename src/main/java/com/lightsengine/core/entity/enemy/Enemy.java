package com.lightsengine.core.entity.enemy;

import com.lightsengine.core.item.item.ItemQuantity;
import com.lightsengine.core.utils.BaseNotificationClass;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Enemy extends BaseNotificationClass
{
    // Variables
    private String Name;
    private int hitPoints;
    private int MaximumHitPoints;
    private String ImageName;
    private int RewardExperiencePoints;
    private int RewardGold;

    // List
    private ArrayList<ItemQuantity> Inventory;
    private final PropertyChangeSupport propertyChangeSupport;

    // Main Constructor
    public Enemy(String name, String imageName, int maximumHitPoints, int hitPoints, int rewardExperiencePoints, int rewardGold) {
        propertyChangeSupport = new PropertyChangeSupport(this);
        setName(name);
        setImageName(String.format("/Engine;component/Images/Monsters/%1$s", imageName));
        setMaximumHitPoints(maximumHitPoints);
        setHitPoints(hitPoints);
        setRewardExperiencePoints(rewardExperiencePoints);
        setRewardGold(rewardGold);
        setInventory(new ArrayList<>());
    }

    // Getters and Setters
    public final String getName() {
        return Name;
    }
    private void setName(String value) {
        Name = value;
    }
    public final String getImageName()
    {
        return ImageName;
    }
    public final void setImageName(String value) {
        ImageName = value;
    }
    public final int getMaximumHitPoints() {
        return MaximumHitPoints;
    }
    private void setMaximumHitPoints(int value) {
        MaximumHitPoints = value;
    }
    public final int getHitPoints() {
        return hitPoints;
    }
    private void setHitPoints(int hitPoints) {
        int oldValue = this.hitPoints;
        this.hitPoints = hitPoints;
        propertyChangeSupport.firePropertyChange("HitPoints", oldValue, hitPoints);
    }

    public final int getRewardExperiencePoints() {
        return RewardExperiencePoints;
    }
    private void setRewardExperiencePoints(int value) {
        RewardExperiencePoints = value;
    }
    public final int getRewardGold() {
        return RewardGold;
    }
    private void setRewardGold(int value) {
        RewardGold = value;
    }
    public final ArrayList<ItemQuantity> getInventory() {
        return Inventory;
    }
    public final void setInventory(ArrayList<ItemQuantity> value) {
        Inventory = value;
    }
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}

