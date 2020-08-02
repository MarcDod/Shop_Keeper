package de.marcdoderer.shop_keeper.entities;

public class HitboxScalarData {
    public static final HitboxScalarData DEFAULT_HITBOX_SCALAR_DATA = new HitboxScalarData(0, 0, 1, 1);
    private float posScalarX;
    private float posScalarY;
    private float scalarX;
    private float scalarY;

    private HitboxScalarData() {
    }

    private HitboxScalarData(float posX, float posY, float scalarX, float scalarY){
        this.posScalarX = posX;
        this.posScalarY = posY;
        this.scalarX = scalarX;
        this.scalarY = scalarY;
    }

    public float getScalarX() {
        return scalarX;
    }

    public float getScalarY() {
        return scalarY;
    }

    public float getPosScalarX(){return posScalarX;}
    public float getPosScalarY(){return posScalarY;}
}
