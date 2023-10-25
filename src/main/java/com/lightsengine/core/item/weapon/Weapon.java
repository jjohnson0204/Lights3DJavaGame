package com.lightsengine.core.item.weapon;

public class Weapon extends GameItem {

    private int minimumDamage;
    private int maximumDamage;

    public Weapon(int itemTypeID, String name, int price, int minDamage, int maxDamage) {
        super(itemTypeID, name, price);
        minimumDamage = minDamage;
        maximumDamage = maxDamage;
    }

    public Weapon clone() {
        return new Weapon(getItemTypeID(), getName(), getPrice(), minimumDamage, maximumDamage);
    }

    public int getMinimumDamage() {
        return minimumDamage;
    }

    public void setMinimumDamage(int minDamage) {
        minimumDamage = minDamage;
    }

    public int getMaximumDamage() {
        return maximumDamage;
    }

    public void setMaximumDamage(int maxDamage) {
        maximumDamage = maxDamage;
    }
}
