package de.marcdoderer.shop_keeper.manager;

import de.marcdoderer.shop_keeper.entities.EntityFactory;

public class EntityData {
    private float width;
    private float height;
    private float posX;
    private float posY;
    private String name;

    private String carriedItem;
    private EntityFactory.EntityType type;

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public String getCarriedItem() {
        return carriedItem;
    }

    public void setType(EntityFactory.EntityType type){
        this.type = type;
    }

    public EntityFactory.EntityType getType(){
        return this.type;
    }

    public void setCarriedItem(String carriedItem){
        this.carriedItem = carriedItem;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

}
