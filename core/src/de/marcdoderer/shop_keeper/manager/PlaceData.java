package de.marcdoderer.shop_keeper.manager;

import de.marcdoderer.shop_keeper.entities.Entity;
import de.marcdoderer.shop_keeper.shop.Place;

import java.util.Collection;

public class PlaceData {
    private EntityData[] entityDatas;


    public void setEntity(EntityData[] entitys){
        entityDatas = entitys;
    }

    public EntityData[] getEntityDatas(){
        return this.entityDatas;
    }
}
