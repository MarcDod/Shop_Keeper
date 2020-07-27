package de.marcdoderer.shop_keeper.movement;

import de.marcdoderer.shop_keeper.entities.Entity;
import de.marcdoderer.shop_keeper.listener.ZoneListener;

public class EntityZone extends InteractiveZone{

    private final Entity entity;

    public EntityZone(float x, float y, float width, float height, int zoneID, Entity entity,ZoneListener listener) {
        super(x, y, width, height, zoneID, listener);
        this.entity = entity;
    }

    public Entity getEntity(){
        return this.entity;
    }
}
