package de.marcdoderer.shop_keeper.shop.loader;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import de.marcdoderer.shop_keeper.astar.IEdge;
import de.marcdoderer.shop_keeper.astar.WeightedGraph;
import de.marcdoderer.shop_keeper.entities.Blocker;
import de.marcdoderer.shop_keeper.entities.Entity;
import de.marcdoderer.shop_keeper.entities.EntityFactory;
import de.marcdoderer.shop_keeper.entities.ItemCarryingEntity;
import de.marcdoderer.shop_keeper.entities.items.Item;
import de.marcdoderer.shop_keeper.entities.items.ItemFactory;
import de.marcdoderer.shop_keeper.exceptions.CollisionMapOutOfBoundsException;
import de.marcdoderer.shop_keeper.listener.ExitZoneListener;
import de.marcdoderer.shop_keeper.manager.EntityData;
import de.marcdoderer.shop_keeper.movement.ExitZone;
import de.marcdoderer.shop_keeper.movement.Zone;
import de.marcdoderer.shop_keeper.movement.collision.Collision;
import de.marcdoderer.shop_keeper.movement.collision.CollisionMap;
import de.marcdoderer.shop_keeper.screen.state.GameState;
import de.marcdoderer.shop_keeper.util.Util;
import org.w3c.dom.css.Rect;

import java.util.*;

public abstract class PlaceLoader {

    public final static float zoneSize = 3.33f;

    public WeightedGraph<Zone, Integer> graph;
    public HashMap<String, Entity> entityList;
    public Set<Entity> itemLayerList;
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
        return this.itemLayerList;
    }


    protected PlaceLoader(int gridSizeX, int gridSizeY, float startX, float startY, Vector2 position, int placeID, GameState gameState, EntityData[] entityData){
        this.entityList = new HashMap<String, Entity>();
        this.itemLayerList = new HashSet<Entity>();
        this.topLayerTexture = new HashSet<Sprite>();
        this.blockerList = new HashSet<Blocker>();
        this.gridSizeX = gridSizeX;
        this.gridSizeY = gridSizeY;
        this.startX = startX + position.x;
        this.startY = startY + position.y;
        this.position = position;

        if(entityData == null) return;
        EntityFactory factory = new EntityFactory(gameState);
        ItemFactory itemFactory = ItemFactory.getItemRegistry(gameState);
        for(EntityData data : entityData) {
            Entity e = factory.createEntity(data, gameState.world);
            if(data.getCarriedItem() != null){
                ((ItemCarryingEntity) e).carryItem(itemFactory.createItem(data.getCarriedItem(), position, gameState.world));
                itemLayerList.add(((ItemCarryingEntity) e).getCarriedItem());
            }

            entityList.put(data.getName(), e);
        }
    }

    protected Body createNoShadowBody(float width, float height, float x, float y, World world){
        return Util.createNoShadowBody(width, height, position.x + x, position.y + y , world);
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

    protected CollisionMap createCollisionMap(Rectangle gridRectangle){
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
        Set<Rectangle> boundingBox = new HashSet<Rectangle>();
        boundingBox.addAll(collisionRectangles);
        boundingBox.add(gridRectangle);
        return new CollisionMap(collisionRectangles, gridSizeX, gridSizeY, Util.getBoundingBox(boundingBox));
    }

    protected Rectangle createCheckRectangle(final Vector2 source, final Vector2 destination){
        Set<Rectangle> rectangleSet = new HashSet<Rectangle>();
        rectangleSet.add(new Rectangle(source.x, source.y, 0.1f, 0.1f));
        rectangleSet.add(new Rectangle(destination.x, destination.y, 0.1f, 0.1f));
        return Util.getBoundingBox(rectangleSet);
    }

    public void draw(ShapeRenderer renderer){
        Iterator<IEdge<Integer>> it = graph.edgeIterator();
        while(it.hasNext()){
            IEdge<Integer> edge = it.next();
            Zone source = graph.getNodeMetaData(edge.getSource());
            Zone destination = graph.getNodeMetaData(edge.getDestination());
            renderer.line(source.getCenter(), destination.getCenter());
        }
        //final CollisionMap collisionMap = createCollisionMap(new Rectangle(new Rectangle(position.x, position.y, GameState.WIDTH, GameState.HEIGHT)));
        //Rectangle r = collisionMap.gridRectangle;

        //renderer.rect(r.x, r.y, r.width, r.height);
    }

    protected WeightedGraph<Zone, Integer> loadGraph(GameState gameState) throws CollisionMapOutOfBoundsException {
        final List<Zone> zones = loadZones(gameState);
        final WeightedGraph<Zone, Integer> graph = new WeightedGraph<>(zones.size());
        int i = 0;
        for(Zone zone : zones){
            graph.setNodeMetaData(i, zone);
            i++;
        }
        final CollisionMap collisionMap = createCollisionMap(new Rectangle(zones.get(0).getPosition().x, zones.get(0).getPosition().y, gridSizeX * zoneSize, gridSizeY  * zoneSize));

        for(int col = 0; col < gridSizeY; col++){
            for(int row = 0; row < gridSizeX; row++) {
                final Zone source = graph.getNodeMetaData(row + col * gridSizeX);
                boolean left = false;
                boolean right = false;
                //right
                if(row + 1 < gridSizeX && !collisionMap.collided(createCheckRectangle(source.getCenter(), graph.getNodeMetaData(getGridID(row + 1, col)).getCenter()))){
                    graph.addEdge(source.getZoneID(), getGridID(row + 1, col), 10);
                    right = true;
                }
                if(row - 1 >= 0 && !collisionMap.collided(createCheckRectangle(source.getCenter(), graph.getNodeMetaData(getGridID(row - 1, col)).getCenter()))){
                    graph.addEdge(source.getZoneID(), getGridID(row - 1, col), 10);
                    left = true;
                }
                if(col + 1 < gridSizeY && !collisionMap.collided(createCheckRectangle(source.getCenter(), graph.getNodeMetaData(getGridID(row, col + 1)).getCenter()))){
                    graph.addEdge(source.getZoneID(), getGridID(row, col + 1), 10);
                    if(right && !collisionMap.collided(createCheckRectangle(source.getCenter(), graph.getNodeMetaData(getGridID(row + 1, col + 1)).getCenter()))){
                        graph.addEdge(source.getZoneID(), getGridID(row + 1, col + 1), 14);
                    }
                    if(left && !collisionMap.collided(createCheckRectangle(source.getCenter(), graph.getNodeMetaData(getGridID(row - 1, col + 1)).getCenter()))){
                        graph.addEdge(source.getZoneID(), getGridID(row - 1, col + 1), 14);
                    }
                }
                if(col - 1 >= 0 && !collisionMap.collided(createCheckRectangle(source.getCenter(), graph.getNodeMetaData(getGridID(row, col - 1)).getCenter()))){
                    graph.addEdge(source.getZoneID(), getGridID(row, col - 1), 10);
                    if(right && !collisionMap.collided(createCheckRectangle(source.getCenter(), graph.getNodeMetaData(getGridID(row + 1, col - 1)).getCenter()))){
                        graph.addEdge(source.getZoneID(), getGridID(row + 1, col - 1), 14);
                    }
                    if(left && !collisionMap.collided(createCheckRectangle(source.getCenter(), graph.getNodeMetaData(getGridID(row - 1, col - 1)).getCenter()))){
                        graph.addEdge(source.getZoneID(), getGridID(row - 1, col - 1), 14);
                    }
                }
            }
        }

        return graph;
    }

    protected int getGridID(int row, int col){
        return row + col * gridSizeX;
    }
}
