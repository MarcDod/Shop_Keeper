package de.marcdoderer.shop_keeper.shop.loader;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
        final TextureAtlas atlas = gameState.screen.assetManager.get("shop/entity/atlas/shopEntity.atlas");
        for(int i = -1; i < 5; i++){
            for(int x = 0; x < 2; x++) {
                Sprite tree = new Sprite(atlas.findRegion("tree"));
                tree.setSize(11.4f, 11.4f);
                tree.setPosition(84.6f + x * 11.4f * 0.45f, GameState.HEIGHT - 9f - i * 11.4f * 0.6f - x * 3f);
                topLayerTexture.add(tree);
            }
        }


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
