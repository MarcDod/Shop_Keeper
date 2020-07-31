package de.marcdoderer.shop_keeper.shop.loader;

import com.badlogic.gdx.math.Vector2;
import de.marcdoderer.shop_keeper.exceptions.CollisionMapOutOfBoundsException;
import de.marcdoderer.shop_keeper.manager.EntityData;
import de.marcdoderer.shop_keeper.movement.Zone;
import de.marcdoderer.shop_keeper.screen.state.GameState;
import de.marcdoderer.shop_keeper.shop.Garden;
import de.marcdoderer.shop_keeper.shop.Shop;

import java.util.LinkedList;
import java.util.List;

public class GardenLoader extends PlaceLoader{

    public final static int SHOP_EXIT = 2 * 14;

    public GardenLoader(GameState gameState, Vector2 position, EntityData[] entityData) {
        super(14, 8, 0, 0, position, Garden.GARDEN_ID, gameState, entityData);

        try {
            this.graph = loadGraph(gameState);
        } catch (CollisionMapOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected List<Zone> loadZones(GameState gameState) {
        final List<Zone> zones = new LinkedList<Zone>();

        for(int col = 0; col < gridSizeY; col++){
            for(int row = 0; row < gridSizeX; row++){
                zones.add(new Zone(row * zoneSize + startX, col * zoneSize + startY, zoneSize, zoneSize, row + col * gridSizeX));
            }
        }

        Zone shopZone = zones.remove(SHOP_EXIT);
        zones.add(SHOP_EXIT, shopZone.makeExitZoneCopy(ShopLoader.GARDEN_EXIT, Shop.SHOP_ID, gameState.exitZoneListener));

        return zones;
    }
}
