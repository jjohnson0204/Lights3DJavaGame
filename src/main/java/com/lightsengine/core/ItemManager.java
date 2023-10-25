package com.lightsengine.core;

import com.lightsengine.core.item.GameItem;
import com.lightsengine.core.item.Weapon;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {
    private static List<GameItem> _standardGameItems;
    static {
        _standardGameItems = new ArrayList<>();
        _standardGameItems.add(new Weapon(1001, "Pointy Stick", 1, 1, 2));
        _standardGameItems.add(new Weapon(1002, "Rusty Sword", 5, 1, 3));
        _standardGameItems.add(new GameItem(9001, "Snake fang", 1));
        _standardGameItems.add(new GameItem(9002, "Snakeskin", 2));
    }
    public static GameItem CreateGameItem(int itemTypeID) {
        GameItem standardItem = _standardGameItems.stream()
                .filter(item -> item.getItemTypeID() == itemTypeID)
                .findFirst()
                .orElse(null);
        if (standardItem != null) {
            return standardItem.Clone();
        }
        return null;
    }
}
