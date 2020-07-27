package de.marcdoderer.shop_keeper.manager;

import de.marcdoderer.shop_keeper.entities.items.ItemFactory;

public class PlayerData {

    private int playerZoneID;
    private int playerPlaceID;
    private ItemData carriedItem;

    public void setPlayerZoneID(final int playerTileID){
        this.playerZoneID = playerTileID;
    }

    public void setPlayerPlaceID(final int playerPlaceID){
        this.playerPlaceID = playerPlaceID;
    }

    public int getPlayerZoneID(){
        return this.playerZoneID;
    }

    public int getPlayerPlaceID(){
        return this.playerPlaceID;
    }

    public ItemData getCarriedItem(){
        return this.carriedItem;
    }

    public void setItemData(ItemData data){
        this.carriedItem = data;
    }
}
