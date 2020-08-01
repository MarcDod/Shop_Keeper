package de.marcdoderer.shop_keeper.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.marcdoderer.shop_keeper.movement.Zone;

import java.util.*;

public class EntityManager {

    public enum Layer{
        ENTITY_LAYER,
        ITEM_LAYER,
        TOP_LAYER
    }

    private final List<Entity> entityLayerList;
    private final List<Entity> itemLayerList;
    private final List<Entity> topLayerList;

    public EntityManager(){
        this.entityLayerList = new LinkedList<>();
        this.itemLayerList = new LinkedList<>();
        this.topLayerList = new LinkedList<>();
    }


    private void sortList(){
        sort(this.entityLayerList);
    }

    protected void sort(List<Entity> list){
        boolean noChange = false;
        while (!noChange) {
            noChange = true;
            for (int i = 0; i < list.size() - 1; i++) {
                if (list.get(i).getPosition().y < list.get(i + 1).getPosition().y) {
                    final Entity temp = list.get(i);
                    list.remove(list.get(i));
                    list.add(i + 1, temp);
                    noChange = false;
                }
            }
        }
    }

    public List<Entity> getEntities(){
        return this.entityLayerList;
    }

    public void update(float delta){
        for(Entity entity : entityLayerList){
            entity.update(delta);
        }
        for(Entity entity : topLayerList){
            entity.update(delta);
        }
        for(Entity entity : itemLayerList){
            entity.update(delta);
        }
        sortList();
    }

    public final void renderEntities(SpriteBatch batch){
        for(Entity entity : entityLayerList){
            entity.render(batch);
        }
        for(Entity entity : topLayerList){
            entity.render(batch);
        }
    }


    public boolean hasEntity(final Entity entity){
        return entityLayerList.contains(entity) || itemLayerList.contains(entity) || topLayerList.contains(entity);
    }

    public void addEntity(final Layer layer, final Entity entity){
        switch(layer){
            case ENTITY_LAYER:
                this.entityLayerList.add(entity);
                break;
            case ITEM_LAYER:
                this.itemLayerList.add(entity);
                break;
            case TOP_LAYER:
                this.topLayerList.add(entity);
                break;
        }
    }

    public void addEntity(final Layer layer, final Collection<Entity> entity){
        switch(layer){
            case ENTITY_LAYER:
                this.entityLayerList.addAll(entity);
                break;
            case ITEM_LAYER:
                this.itemLayerList.addAll(entity);
                break;
            case TOP_LAYER:
                this.topLayerList.addAll(entity);
                break;
        }
    }

    public void removeEntity(final Layer layer, final Entity entity){
        switch(layer){
            case ENTITY_LAYER:
                this.entityLayerList.remove(entity);
                break;
            case ITEM_LAYER:
                this.itemLayerList.remove(entity);
                break;
            case TOP_LAYER:
                this.topLayerList.remove(entity);
                break;
        }
    }

}
