package com.lightsengine.core.quest;

import com.lightsengine.core.item.item.ItemQuantity;

import java.util.ArrayList;

public class Quest {
    private int ID;

    public Quest(int id, String name, String description, ArrayList<ItemQuantity> itemsToComplete, int rewardExperiencePoints, int rewardGold, ArrayList<ItemQuantity> rewardItems)
    {
        setID(id);
        setName(name);
        setDescription(description);
        setItemsToComplete(itemsToComplete);
        setRewardExperiencePoints(rewardExperiencePoints);
        setRewardGold(rewardGold);
        setRewardItems(rewardItems);
    }

    public final int getID()
    {
        return ID;
    }
    public final void setID(int value)
    {
        ID = value;
    }
    private String Name;
    public final String getName()
    {
        return Name;
    }
    public final void setName(String value)
    {
        Name = value;
    }
    private String Description;
    public final String getDescription()
    {
        return Description;
    }
    public final void setDescription(String value)
    {
        Description = value;
    }
    private ArrayList<ItemQuantity> ItemsToComplete;
    public final ArrayList<ItemQuantity> getItemsToComplete()
    {
        return ItemsToComplete;
    }
    public final void setItemsToComplete(ArrayList<ItemQuantity> value)
    {
        ItemsToComplete = value;
    }
    private int RewardExperiencePoints;
    public final int getRewardExperiencePoints()
    {
        return RewardExperiencePoints;
    }
    public final void setRewardExperiencePoints(int value)
    {
        RewardExperiencePoints = value;
    }
    private int RewardGold;
    public final int getRewardGold()
    {
        return RewardGold;
    }
    public final void setRewardGold(int value)
    {
        RewardGold = value;
    }
    private ArrayList<ItemQuantity> RewardItems;
    public final ArrayList<ItemQuantity> getRewardItems()
    {
        return RewardItems;
    }
    public final void setRewardItems(ArrayList<ItemQuantity> value)
    {
        RewardItems = value;
    }

}
