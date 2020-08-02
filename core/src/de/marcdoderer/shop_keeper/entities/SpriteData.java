package de.marcdoderer.shop_keeper.entities;

public class SpriteData {
    public static final SpriteData DEFAULT_SPRITE_DATA = new SpriteData(0, 0, 1, 1, 0.5f, 0.5f);
    private float posScalarX;
    private float posScalarY;
    private float scalarX;
    private float scalarY;
    private float itemX;
    private float itemY;

    private SpriteData() {
    }

    private SpriteData(float posX, float posY, float scalarX, float scalarY, float itemX, float itemY){
        this.posScalarX = posX;
        this.posScalarY = posY;
        this.scalarX = scalarX;
        this.scalarY = scalarY;
        this.itemX = itemX;
        this.itemY = itemY;
    }

    public float getScalarX() {
        return scalarX;
    }

    public float getScalarY() {
        return scalarY;
    }

    public float getPosScalarX() { return posScalarX; }

    public float getPosScalarY() { return posScalarY; }

    public float getItemX() { return itemX; }

    public float getItemY() { return itemY; }
}
