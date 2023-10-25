package com.lightsengine.core.item;

import java.util.Objects;

public class GameItem {
    private int itemTypeID;
    private String name;
    private int price;

    public GameItem(int itemTypeID, String name, int price) {
        this.itemTypeID = itemTypeID;
        this.name = name;
        this.price = price;
    }

    public int getItemTypeID() {
        return itemTypeID;
    }

    public void setItemTypeID(int itemTypeID) {
        this.itemTypeID = itemTypeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public GameItem Clone() {
        return new GameItem(itemTypeID, name, price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameItem gameItem = (GameItem) o;
        return itemTypeID == gameItem.itemTypeID &&
                price == gameItem.price &&
                Objects.equals(name, gameItem.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemTypeID, name, price);
    }
}