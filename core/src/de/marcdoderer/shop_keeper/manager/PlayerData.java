package de.marcdoderer.shop_keeper.manager;

public class PlayerData {

    private int playerZoneID;
    private int playerPlaceID;
    private String carriedItemID;

    public void setPlayerZoneID(final int playerTileID){
        this.playerZoneID = playerTileID;
    }

    public void setPlayerPlaceID(final int playerPlaceID){
        this.playerPlaceID = playerPlaceID;
    }

    public int getPlayerZoneID() {
        return this.playerZoneID;
    }

    public int getPlayerPlaceID() {
        return this.playerPlaceID;
    }

    public String getCarriedItemID() {
        return this.carriedItemID;
    }

    public void setCarriedItemID(String id) {
        this.carriedItemID = id;
    }
}
