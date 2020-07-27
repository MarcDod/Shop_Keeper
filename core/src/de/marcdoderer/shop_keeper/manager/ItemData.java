package de.marcdoderer.shop_keeper.manager;

import de.marcdoderer.shop_keeper.entities.items.ItemFactory;

public class ItemData {
    private ItemFactory.ItemType type;

    public ItemFactory.ItemType getType(){
        return this.type;
    }

    public void setType(ItemFactory.ItemType type){
        this.type = type;
    }
}
