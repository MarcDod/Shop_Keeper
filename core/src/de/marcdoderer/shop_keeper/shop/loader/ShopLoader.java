package de.marcdoderer.shop_keeper.shop.loader;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import de.marcdoderer.shop_keeper.Shop_Keeper;
import de.marcdoderer.shop_keeper.entities.Blocker;
import de.marcdoderer.shop_keeper.entities.Entity;
import de.marcdoderer.shop_keeper.entities.EntityFactory;
import de.marcdoderer.shop_keeper.entities.ItemCarryingEntity;
import de.marcdoderer.shop_keeper.entities.items.ItemFactory;
import de.marcdoderer.shop_keeper.exceptions.CollisionMapOutOfBoundsException;
import de.marcdoderer.shop_keeper.manager.EntityData;
import de.marcdoderer.shop_keeper.movement.EntityZone;
import de.marcdoderer.shop_keeper.movement.ExitZone;
import de.marcdoderer.shop_keeper.movement.Zone;
import de.marcdoderer.shop_keeper.screen.GameScreen;
import de.marcdoderer.shop_keeper.screen.state.GameState;
import de.marcdoderer.shop_keeper.shop.Place;
import de.marcdoderer.shop_keeper.shop.Shop;
import de.marcdoderer.shop_keeper.util.Util;

import java.util.LinkedList;
import java.util.List;

public class ShopLoader extends PlaceLoader {

    public static final int START = 48;
    public static final int BASEMENT_EXIT = 31;

    public ShopLoader(GameState gameState, Vector2 position, EntityData[] entityData){
        super(10, 5, 6.5f, 5.8f, position, Shop.SHOP_ID, gameState, entityData);
        final TextureAtlas atlas = gameState.screen.assetManager.get("shop/entity/atlas/shopEntity.atlas");
        Sprite barSprite = new Sprite(atlas.findRegion("bar"));
        barSprite.setSize(1.2f, 19f);
        barSprite.setOriginCenter();

        barSprite.setPosition(14f, 6.4f);
        topLayerTexture.add(barSprite);

        blockerList.add(new Blocker(Util.createBody(27, 2, 27, 24, gameState.world)));
        blockerList.add(new Blocker(Util.createBody(0.6f, 20, 40, 16f, gameState.world)));
        blockerList.add(new Blocker(Util.createBody(0.6f, 20, 6, 16f, gameState.world)));
        blockerList.add(new Blocker(Util.createBody(1.2f, 3.3f, 14.6f, 7.3f, gameState.world)));
        blockerList.add(new Blocker(Util.createBody(1.2f, 12.3f, 14.6f, 18.5f, gameState.world)));
        try {
            this.graph = loadGraph(gameState);
        } catch (CollisionMapOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected List<Zone> loadZones(GameState gameState){
        final List<Zone> zones = new LinkedList<Zone>();

        for(int col = 0; col < gridSizeY; col++){
            for(int row = 0; row < gridSizeX; row++){
                zones.add(new Zone(row * zoneSize + startX, col * zoneSize + startY, zoneSize, zoneSize, row + col * gridSizeX));
            }
        }
        // ExitZone
        for(int i = 0; i < 2; i++) {
            Zone z = zones.remove(30 + i);
            zones.add(30 + i, z.makeExitZoneCopy(BasementLoader.SHOP_EXIT, Place.BASEMENT_ID, gameState.exitZoneListener));
        }
        Zone tableZone = zones.remove(38);
        zones.add(38, tableZone.makeEntityZoneCopy ( entityList.get("table"), gameState.tradeItemListener));
        Zone chestZone = zones.remove(46);
        zones.add(46, chestZone.makeEntityZoneCopy ( entityList.get("chest"), gameState.tradeItemListener));


        //sort(zones);

        return zones;
    }


}
