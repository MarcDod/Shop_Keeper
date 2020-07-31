package de.marcdoderer.shop_keeper.util;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteDragger {

    private final Sprite draggedSprite;
    public final float originPosX;
    public final float originPosY;
    private float posX;
    private float posY;

    public SpriteDragger(final Sprite sprite, final float posX, final float posY){
        this.draggedSprite = sprite;
        this.originPosX = posX;
        this.originPosY = posY;
    }

    public void mouseDragged(final float x, final float y){
        this.posX = x;
        this.posY = y;
    }

    public Sprite getDraggedSprite(){
        return this.draggedSprite;
    }

    public float getPosX(){
        return this.posX;
    }

    public float getPosY(){
        return this.posY;
    }
}
