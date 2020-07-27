package de.marcdoderer.shop_keeper.shop.loader;

import com.badlogic.gdx.math.Vector2;
import de.marcdoderer.shop_keeper.astar.WeightedGraph;
import de.marcdoderer.shop_keeper.entities.Blocker;
import de.marcdoderer.shop_keeper.exceptions.CollisionMapOutOfBoundsException;
import de.marcdoderer.shop_keeper.manager.EntityData;
import de.marcdoderer.shop_keeper.movement.Zone;
import de.marcdoderer.shop_keeper.screen.GameScreen;
import de.marcdoderer.shop_keeper.screen.state.GameState;
import de.marcdoderer.shop_keeper.shop.Basement;
import de.marcdoderer.shop_keeper.shop.Basement2;
import de.marcdoderer.shop_keeper.shop.Place;
import de.marcdoderer.shop_keeper.shop.Shop;
import de.marcdoderer.shop_keeper.util.Util;

import java.util.LinkedList;
import java.util.List;

public class BasementLoader extends PlaceLoader{

    public static final int BASEMENT2_EXIT = 0;
    public static final int SHOP_EXIT = 66;

    public BasementLoader(GameState gameState, Vector2 position, EntityData[] entityData){
        super(11, 7, 6.5f, -1, position, Basement.BASEMENT_ID, gameState, entityData);

        blockerList.add(new Blocker(createNoShadowBody(0.6f, 10, 10, 16f, gameState.world)));
        blockerList.add(new Blocker(createNoShadowBody(35f, 0.6f, 28, 20f, gameState.world)));

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

        Zone z = zones.remove(66);
        zones.add(66, z.makeExitZoneCopy(ShopLoader.BASEMENT_EXIT, Shop.SHOP_ID, gameState.exitZoneListener));

        for(int i = 0; i < gridSizeX; i++) {
            z = zones.remove(i);
            zones.add(i, z.makeExitZoneCopy(Basement2Loader.BASEMENT_EXIT + i, Basement2.BASEMENT2_ID, gameState.exitZoneListener));
        }

        return zones;
    }

}
