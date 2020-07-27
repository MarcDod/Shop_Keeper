package de.marcdoderer.shop_keeper.shop.loader;

import com.badlogic.gdx.math.Vector2;
import de.marcdoderer.shop_keeper.astar.WeightedGraph;
import de.marcdoderer.shop_keeper.exceptions.CollisionMapOutOfBoundsException;
import de.marcdoderer.shop_keeper.manager.EntityData;
import de.marcdoderer.shop_keeper.movement.Zone;
import de.marcdoderer.shop_keeper.screen.GameScreen;
import de.marcdoderer.shop_keeper.screen.state.GameState;
import de.marcdoderer.shop_keeper.shop.Basement;
import de.marcdoderer.shop_keeper.shop.Basement2;
import de.marcdoderer.shop_keeper.shop.Place;

import java.util.LinkedList;
import java.util.List;

public class Basement2Loader extends PlaceLoader{

    public static final int BASEMENT_EXIT = 44;

    public Basement2Loader(GameState gameState, Vector2 position, EntityData[] entityData){
        super(11, 5, 6.2f, 9f, position, Basement2.BASEMENT2_ID, gameState, entityData);

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

        for(int i = 0; i < gridSizeX; i++) {
            Zone z = zones.remove(44 + i);
            zones.add(44 + i, z.makeExitZoneCopy(BasementLoader.BASEMENT2_EXIT + i, Basement.BASEMENT_ID, gameState.exitZoneListener));
        }

        sort(zones);
        return zones;
    }

}
