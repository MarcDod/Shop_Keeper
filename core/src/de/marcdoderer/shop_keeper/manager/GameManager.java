package de.marcdoderer.shop_keeper.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
import de.marcdoderer.shop_keeper.entities.EntityFactory;
import de.marcdoderer.shop_keeper.entities.specialEntity.Chest;
import de.marcdoderer.shop_keeper.shop.Basement;
import de.marcdoderer.shop_keeper.shop.Basement2;
import de.marcdoderer.shop_keeper.shop.Garden;
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
            gameData.getPlayerData().setCarriedItemData(new ItemData("shopKeeper:gold", 1));

            PlaceData[] placeData = new PlaceData[4];
            placeData[Shop.SHOP_ID] = new PlaceData();
            EntityData[] entityData = new EntityData[2];
            EntityData ed = new EntityData();
            ed.setType(EntityFactory.EntityType.ITEM_CARRYING);
            ed.setName("table");
            ed.setPosY(19.6f);
            ed.setPosX(34.6f);
            ed.setWidth(11.3f);
            ed.setHeight(4f);
            entityData[0] = ed;

            EntityData ed1 = new Chest.ChestData();
            ed1.setType(EntityFactory.EntityType.ITEM_CARRYING);
            ed1.setName("chest");
            ed1.setPosY(19.6f);
            ed1.setPosX(28.3f);
            ed1.setWidth(3f);
            ed1.setHeight(3f);
            entityData[1] = ed1;


            placeData[Shop.SHOP_ID].setEntity(entityData);
            placeData[Basement.BASEMENT_ID] = new PlaceData();
            placeData[Basement2.BASEMENT2_ID] = new PlaceData();
            placeData[Garden.GARDEN_ID] = new PlaceData();
            EntityData[] entityData1 = new EntityData[1];

            EntityData tree = new EntityData();
            tree.setName("tree");
            tree.setPosY(15f);
            tree.setPosX(70.6f);
            tree.setWidth(11.4f);
            tree.setHeight(11.4f);
            tree.setType(EntityFactory.EntityType.ITEM_CARRYING);
            entityData1[0] = tree;
            placeData[Garden.GARDEN_ID].setEntity(entityData1);

            gameData.setPlaceDatas(placeData);
            gameData.setVsync(true);
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
