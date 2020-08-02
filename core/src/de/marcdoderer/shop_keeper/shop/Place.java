package de.marcdoderer.shop_keeper.shop;

import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.marcdoderer.shop_keeper.astar.WeightedGraph;
import de.marcdoderer.shop_keeper.entities.Entity;
import de.marcdoderer.shop_keeper.entities.EntityManager;
import de.marcdoderer.shop_keeper.manager.EntityData;
import de.marcdoderer.shop_keeper.manager.PlaceData;
import de.marcdoderer.shop_keeper.movement.Zone;
import de.marcdoderer.shop_keeper.screen.state.GameState;
import de.marcdoderer.shop_keeper.shop.loader.PlaceLoader;
import de.marcdoderer.shop_keeper.shop.time.DayNightCircle;

import java.util.Collection;
import java.util.List;

public abstract class Place {

    public static int SHOP_ID = 0;
    public static int BASEMENT_ID = 1;
    public static int BASEMENT2_ID = 2;
    public static int GARDEN_ID = 3;

    protected Sprite background;
    protected List<Sprite> topLayerTexture;
    protected WeightedGraph<Zone, Integer> graph;
    protected EntityManager manager;
    protected Vector2 position;

    protected final World world;
    public final RayHandler rayHandler;
    protected final OrthographicCamera camera;

    protected final DayNightCircle dayNightCircle;
    protected GameState gameState;

    protected final PlaceLoader loader;

    public Place(GameState gameState, DayNightCircle dayNightCircle ,PlaceLoader placeLoader) {
        this.manager = new EntityManager();
        this.world = gameState.world;
        this.camera = gameState.gameCamera;
        this.rayHandler = new RayHandler(world);
        this.graph = placeLoader.graph;
        this.gameState = gameState;
        this.loader = placeLoader;

        this.addEntity(EntityManager.Layer.ENTITY_LAYER, placeLoader.getEntityList());
        this.addEntity(EntityManager.Layer.ITEM_LAYER, placeLoader.getItemLayerList());
        this.topLayerTexture = placeLoader.topLayerTexture;
        this.dayNightCircle =  dayNightCircle;
        init();
    }

    public WeightedGraph<Zone, Integer> getGraph(){
        return this.graph;
    }

    public void drawShape(final ShapeRenderer shapeRenderer, boolean drawGrid, boolean drawPaths){
        if(drawGrid) {
            for (int i = 0; i < graph.numberOfNodes(); i++) {
                graph.getNodeMetaData(i).drawShape(shapeRenderer);
            }
        }
        if(drawPaths) loader.draw(shapeRenderer);
    }

    public Vector2 getPosition(){
        return this.position;
    }

    public void render(SpriteBatch batch){
        if(background != null)
            batch.draw(background, position.x,position.y, GameState.WIDTH, GameState.HEIGHT);
        manager.renderEntities(batch);
        for(Sprite sprite  : topLayerTexture){
            sprite.draw(batch);
        }
    }

    public void renderLight(){
        rayHandler.setCombinedMatrix(camera);
        rayHandler.updateAndRender();
    }

    public void addEntity(final EntityManager.Layer layer, final Entity entity){
        this.manager.addEntity(layer, entity);
    }

    public void addEntity(final EntityManager.Layer layer,final Collection<Entity> entity){
        this.manager.addEntity(layer, entity);
    }

    public void removeEntity(final EntityManager.Layer layer,final Entity entity){
        this.manager.removeEntity(layer, entity);
    }

    public void update(float delta){
        this.manager.update(delta);
    }

    public void dispose(){
        background.getTexture().dispose();
        rayHandler.dispose();
        for(Sprite sprite : topLayerTexture){
            sprite.getTexture().dispose();
        }
    }

    public abstract int getID();

    protected abstract void init();

    public PlaceData getPlaceData(){
        PlaceData placeData = new PlaceData();
        List<Entity> entitiesList = manager.getEntities();
        boolean removed = entitiesList.remove(gameState.player);
        EntityData[] entityDatas = new EntityData[entitiesList.size()];

        for(int i = 0; i < entityDatas.length; i++){
            entityDatas[i] = entitiesList.get(i).getEntityData();
        }

        placeData.setEntity(entityDatas);
        if(removed){
            entitiesList.add(gameState.player);
        }
        return placeData;
    }

    public boolean hasEntity(final Entity e){
        return manager.hasEntity(e);
    }

}
