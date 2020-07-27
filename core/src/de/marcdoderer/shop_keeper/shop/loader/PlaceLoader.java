package de.marcdoderer.shop_keeper.shop.loader;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import de.marcdoderer.shop_keeper.astar.WeightedGraph;
import de.marcdoderer.shop_keeper.entities.Blocker;
import de.marcdoderer.shop_keeper.entities.Entity;
import de.marcdoderer.shop_keeper.entities.EntityFactory;
import de.marcdoderer.shop_keeper.entities.ItemCarryingEntity;
import de.marcdoderer.shop_keeper.entities.items.ItemFactory;
import de.marcdoderer.shop_keeper.exceptions.CollisionMapOutOfBoundsException;
import de.marcdoderer.shop_keeper.listener.ExitZoneListener;
import de.marcdoderer.shop_keeper.manager.EntityData;
import de.marcdoderer.shop_keeper.movement.ExitZone;
import de.marcdoderer.shop_keeper.movement.Zone;
import de.marcdoderer.shop_keeper.movement.collision.CollisionMap;
import de.marcdoderer.shop_keeper.screen.state.GameState;
import de.marcdoderer.shop_keeper.util.Util;

import java.util.*;

public abstract class PlaceLoader {

    public final static float zoneSize = 3.33f;

    public WeightedGraph<Zone, Integer> graph;
    public HashMap<String, Entity> entityList;
    public HashMap<String, Entity> itemLayerList;
    public Set<Sprite> topLayerTexture;
    public Set<Blocker> blockerList;

    protected final int gridSizeX;
    protected final int gridSizeY;
    protected final float startX;
    protected final float startY;
    protected Vector2 position;

    public final WeightedGraph<Zone,Integer> getGraph(){
        return this.graph;
    }

    public final Collection<Entity> getEntityList(){
        return this.entityList.values();
    }

    public final Collection<Entity> getItemLayerList(){
        return this.itemLayerList.values();
    }


    protected PlaceLoader(int gridSizeX, int gridSizeY, float startX, float startY, Vector2 position, int placeID, GameState gameState, EntityData[] entityData){
        this.entityList = new HashMap<String, Entity>();
        this.itemLayerList = new HashMap<String, Entity>();
        this.topLayerTexture = new HashSet<Sprite>();
        this.blockerList = new HashSet<Blocker>();
        this.gridSizeX = gridSizeX;
        this.gridSizeY = gridSizeY;
        this.startX = startX + position.x;
        this.startY = startY + position.y;
        this.position = position;

        if(entityData == null) return;
        EntityFactory factory = new EntityFactory(gameState);
        ItemFactory itemFactory = new ItemFactory(gameState);
        for(EntityData data : entityData) {
            if(data.getName().equals("")) continue;
            Entity e = factory.createEntity(data.getType(), new Vector2(data.getPosX(), data.getPosY()), data.getWidth(), data.getHeight(), data.getName(), gameState.world);
            if(data.getCarriedItem() != null){
                ((ItemCarryingEntity) e).carryItem(itemFactory.createItem(data.getCarriedItem().getType(), new Vector2(0, 0), gameState.world));
                itemLayerList.put(data.getCarriedItem().getType().name(), ((ItemCarryingEntity) e).getCarriedItem());
            }

            entityList.put(data.getName(), e);
        }
    }

    protected Body createBody(float width, float height, float x, float y, World world){
        return Util.createBody(width, height, position.x + x, position.y + y , world);
    }

    protected Body createNoShadowBody(float width, float height, float x, float y, World world){
        return Util.createNoShadowBody(width, height, position.x + x, position.y + y , world);
    }

    protected Zone createZone(float x, float y, float width, float height, int zoneID){
        return new Zone(x / GameState.SCALE, y / GameState.SCALE, width / GameState.SCALE, height / GameState.SCALE, zoneID);
    }

    protected ExitZone createExitZone(float x, float y, float width, float height, int zoneID, int nextZone, int nextPlace, ExitZoneListener listener){
        return new ExitZone(x / GameState.SCALE, y / GameState.SCALE, width / GameState.SCALE, height / GameState.SCALE, zoneID, nextZone, nextPlace, listener);
    }

    protected abstract List<Zone> loadZones(GameState gameState);

    protected void sort(List<Zone> list){
        for(int x= 0; x< list.size(); x++) {
            for (int i = 0; i < list.size() - 1; i++) {
                if (list.get(i).getZoneID() > list.get(i + 1).getZoneID()) {
                    final Zone temp = list.get(i);
                    list.remove(list.get(i));
                    list.add(i + 1, temp);
                }
            }
        }
    }

    protected CollisionMap createCollisionMap(){
        Set<Rectangle> collisionRectangles = new HashSet<Rectangle>();
        for(Entity entity : entityList.values()){
            final float x = entity.getPosition().x - entity.getWidth() / 2;
            final float y = entity.getPosition().y - entity.getHeight() / 2;
            collisionRectangles.add(new Rectangle(x, y, entity.getWidth(), entity.getHeight()));
        }

        for(Blocker blocker : blockerList){
            final float x = blocker.getPosition().x - blocker.getWidth() / 2;
            final float y = blocker.getPosition().y - blocker.getHeight() / 2;
            collisionRectangles.add(new Rectangle(x, y, blocker.getWidth(), blocker.getHeight()));
        }
        return new CollisionMap(collisionRectangles, 5, 10);
    }

    protected Rectangle createCheckRectangle(final Vector2 source, final Vector2 destination){
        Set<Rectangle> rectangleSet = new HashSet<Rectangle>();
        rectangleSet.add(new Rectangle(source.x, source.y, 0.1f, 0.1f));
        rectangleSet.add(new Rectangle(destination.x, destination.y, 0.1f, 0.1f));
        return Util.getBoundingBox(rectangleSet);
    }

    public void draw(ShapeRenderer renderer){

    }

    protected WeightedGraph<Zone, Integer> loadGraph(GameState gameState) throws CollisionMapOutOfBoundsException {
        final List<Zone> zones = loadZones(gameState);
        final WeightedGraph<Zone, Integer> graph = new WeightedGraph<>(zones.size());
        int i = 0;
        for(Zone zone : zones){
            graph.setNodeMetaData(i, zone);
            i++;
        }
        final CollisionMap collisionMap = createCollisionMap();
        collisionMap.gridRectangle = new Rectangle(zones.get(0).getX(),zones.get(0).getY(), gridSizeX * zoneSize, gridSizeY * zoneSize);

        for(int col = 0; col < gridSizeY; col++){
            for(int row = 0; row < gridSizeX; row++) {
                final Zone source = graph.getNodeMetaData(row + col * gridSizeX);
                boolean left = false;
                boolean right = false;
                //right
                if(row + 1 < gridSizeX && !collisionMap.collided(createCheckRectangle(source.getCenter(), graph.getNodeMetaData(row + 1 + col * gridSizeX).getCenter()))){
                    graph.addEdge(source.getZoneID(), row + 1 + col * gridSizeX, 10);
                    right = true;
                }
                if(row - 1 >= 0 && !collisionMap.collided(createCheckRectangle(source.getCenter(), graph.getNodeMetaData(row - 1 + col * gridSizeX).getCenter()))){
                    graph.addEdge(source.getZoneID(), row - 1 + col * gridSizeX, 10);
                    left = true;
                }
                if(col + 1 < gridSizeY && !collisionMap.collided(createCheckRectangle(source.getCenter(), graph.getNodeMetaData(row + (col + 1) * gridSizeX).getCenter()))){
                    graph.addEdge(source.getZoneID(), row + (col + 1) * gridSizeX, 10);
                    if(right && !collisionMap.collided(createCheckRectangle(source.getCenter(), graph.getNodeMetaData(row + 1 + (col + 1) * gridSizeX).getCenter()))){
                        graph.addEdge(source.getZoneID(), row + 1 + (col + 1) * gridSizeX, 14);
                    }
                    if(left && !collisionMap.collided(createCheckRectangle(source.getCenter(), graph.getNodeMetaData(row - 1 + (col + 1) * gridSizeX).getCenter()))){
                        graph.addEdge(source.getZoneID(), row - 1 + (col + 1) * gridSizeX, 14);
                    }
                }
                if(col - 1 >= 0 && !collisionMap.collided(createCheckRectangle(source.getCenter(), graph.getNodeMetaData(row + (col - 1) * gridSizeX).getCenter()))){
                    graph.addEdge(source.getZoneID(), row + (col - 1) * gridSizeX, 10);
                    if(right && !collisionMap.collided(createCheckRectangle(source.getCenter(), graph.getNodeMetaData(row + 1 +(col - 1) * gridSizeX).getCenter()))){
                        graph.addEdge(source.getZoneID(), row + 1 +(col - 1) * gridSizeX, 14);
                    }
                    if(left && !collisionMap.collided(createCheckRectangle(source.getCenter(), graph.getNodeMetaData(row - 1 + (col - 1) * gridSizeX).getCenter()))){
                        graph.addEdge(source.getZoneID(), row - 1 + (col - 1) * gridSizeX, 14);
                    }
                }
            }
        }

        return graph;
    }

}
