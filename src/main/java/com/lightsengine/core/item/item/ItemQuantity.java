package com.lightsengine.core.item.item;

public class ItemQuantity {
    private int ItemID;
    public final int getItemID()
    {
        return ItemID;
    }
    public final void setItemID(int value)
    {
        ItemID = value;
    }
    private int Quantity;
    public final int getQuantity()
    {
        return Quantity;
    }
    public final void setQuantity(int value)
    {
        Quantity = value;
    }
    public ItemQuantity(int itemID, int quantity)
    {
        setItemID(itemID);
        setQuantity(quantity);
    }
}
