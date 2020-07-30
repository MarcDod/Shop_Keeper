package de.marcdoderer.shop_keeper.manager;

public class GameData {

    private PlayerData playerData;
    private float timeInSeconds;
    private PlaceData[] placeDatas;
    private boolean vSync;


    public PlayerData getPlayerData() {
        return playerData;
    }

    public void setPlayerData(final PlayerData playerData) {this.playerData = playerData;}

    public float getTimeInSeconds() {
        return timeInSeconds;
    }

    public void setTimeInSeconds(float timeInSeconds) {
        this.timeInSeconds = timeInSeconds;
    }

    public PlaceData[] getPlaceDatas(){
        return this.placeDatas;
    }

    public void setPlaceDatas(PlaceData[] placeDatas){
        this.placeDatas = placeDatas;
    }

    public void setVsync(boolean vSync){
        this.vSync = vSync;
    }

    public boolean getVSync(){
        return this.vSync;
    }

}
