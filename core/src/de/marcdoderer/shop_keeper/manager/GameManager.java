package de.marcdoderer.shop_keeper.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import de.marcdoderer.shop_keeper.entities.Entity;
import de.marcdoderer.shop_keeper.entities.EntityFactory;
import de.marcdoderer.shop_keeper.entities.items.ItemFactory;
import de.marcdoderer.shop_keeper.shop.Basement;
import de.marcdoderer.shop_keeper.shop.Place;
import de.marcdoderer.shop_keeper.shop.Shop;
import de.marcdoderer.shop_keeper.shop.loader.ShopLoader;


public class GameManager {

    public GameData gameData;
    private final Json json = new Json();

    public GameManager() {
        Preferences pref = Gdx.app.getPreferences("de.marcdoderer.shop_keeper.preferences");
        if (!pref.contains("GameData")) {
            gameData = new GameData();

            gameData.setPlayerData(new PlayerData());
            gameData.getPlayerData().setPlayerPlaceID(Shop.SHOP_ID);
            gameData.getPlayerData().setPlayerZoneID(ShopLoader.START);
            gameData.getPlayerData().setItemData(new ItemData("shopKeeper", "gold", 1.6f, 1.6f));

            PlaceData[] placeData = new PlaceData[3];
            placeData[Shop.SHOP_ID] = new PlaceData();
            EntityData[] entityData = new EntityData[1];
            EntityData ed = new EntityData();
            ed.setType(EntityFactory.EntityType.ITEM_CARRYING);
            ed.setName("table");
            ed.setPosY(19.6f);
            ed.setPosX(34.6f);
            ed.setWidth(11.3f);
            ed.setHeight(4f);
            entityData[0] = ed;
            placeData[Shop.SHOP_ID].setEntity(entityData);
            placeData[Basement.BASEMENT_ID] = new PlaceData();
            placeData[Basement.BASEMENT2_ID] = new PlaceData();

            gameData.setPlaceDatas(placeData);

            gameData.setTimeInSeconds(0f);

            saveData();
        } else {
            loadData();
        }
    }

    public void saveData() {
        if (gameData != null) {
            Preferences pref = Gdx.app.getPreferences("de.marcdoderer.shop_keeper.preferences");
            pref.putString("GameData", /*Base64Coder.encodeString(*/json.prettyPrint(gameData));//);
            pref.flush();
        }
    }

    public void loadData() {
        Preferences pref = Gdx.app.getPreferences("de.marcdoderer.shop_keeper.preferences");
        String gameDataJsonString = pref.getString("GameData");
        gameData = json.fromJson(GameData.class, /*Base64Coder.decodeString(*/gameDataJsonString);//);
    }
}
