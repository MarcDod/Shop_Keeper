package de.marcdoderer.shop_keeper.manager;

import de.marcdoderer.shop_keeper.entities.items.Item;

public class ItemData {
    public String itemID;
    public int stackCount;

    private ItemData(){}

    public ItemData(String itemID, int stackCount){
        this.itemID = itemID;
        this.stackCount = stackCount;
    }

    public String getItemID() {
        return itemID;
    }

    public int getStackCount() {
        return stackCount;
    }
}
