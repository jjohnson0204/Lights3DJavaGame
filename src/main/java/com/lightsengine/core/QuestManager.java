package com.lightsengine.core;

import com.lightsengine.core.item.item.ItemQuantity;
import com.lightsengine.core.quest.Quest;

import java.util.ArrayList;

public class QuestManager {
    private static final ArrayList<Quest> _quests = new ArrayList<>();
    static
    {
        // Declare the items need to complete the quest, and its reward items
        ArrayList<ItemQuantity> itemsToComplete = new ArrayList<>();
        ArrayList<ItemQuantity> rewardItems = new ArrayList<>();
        itemsToComplete.add(new ItemQuantity(9001, 5));
        rewardItems.add(new ItemQuantity(1002, 1));
        // Create the quest
        _quests.add(new Quest(1, "Clear the herb garden", "Defeat the snakes in the Herbalist's garden", itemsToComplete, 25, 10, rewardItems));
    }
    public static Quest GetQuestByID(int id)
    {
        return _quests.stream()
                .filter(quest -> quest.getID() == id)
                .findFirst()
                .orElse(null);
    }

}
