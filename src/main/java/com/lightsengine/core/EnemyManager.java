package com.lightsengine.core;

import com.lightsengine.core.entity.enemy.Enemy;
import com.lightsengine.core.item.item.ItemQuantity;
import com.lightsengine.core.utils.RandomNumberGenerator;

public class EnemyManager {
    public static Enemy GetMonster(int monsterID)
    {
        switch (monsterID) {
            case 1 -> {
                Enemy snake = new Enemy("Snake", "Snake.png", 4, 4, 5, 1);
                AddLootItem(snake, 9001, 25);
                AddLootItem(snake, 9002, 75);
                return snake;
            }
            case 2 -> {
                Enemy rat = new Enemy("Rat", "Rat.png", 5, 5, 5, 1);
                AddLootItem(rat, 9003, 25);
                AddLootItem(rat, 9004, 75);
                return rat;
            }
            case 3 -> {
                Enemy giantSpider = new Enemy("Giant Spider", "GiantSpider.png", 10, 10, 10, 3);
                AddLootItem(giantSpider, 9005, 25);
                AddLootItem(giantSpider, 9006, 75);
                return giantSpider;
            }
            default ->
                    throw new IllegalArgumentException(String.format("MonsterType '%1$s' does not exist", monsterID));
        }
    }
    private static void AddLootItem(Enemy enemy, int itemID, int percentage)
    {
        if (RandomNumberGenerator.NumberBetween(1, 100) <= percentage)
        {
            enemy.getInventory().add(new ItemQuantity(itemID, 1));
        }
    }
}

