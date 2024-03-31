package de.marcdoderer.shop_keeper.util;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * This class can be used to drag a sprite over the screen.
 */
public class SpriteDragger {

    private final Sprite draggedSprite;
    public final float originPosX;
    public final float originPosY;
    private float posX;
    private float posY;

    private float timeDraggedInSeconds;

    /**
     * initialises the spriteDragger
     * @param sprite the sprite that should be dragged over the screen
     * @param posX the starting mouse X position;
     * @param posY the starting mouse Y position;
     */
    public SpriteDragger(final Sprite sprite, final float posX, final float posY){
        this.draggedSprite = sprite;
        this.originPosX = posX;
        this.originPosY = posY;
        timeDraggedInSeconds = 0;
    }

    /**
     * counts the time the spriteDragger was active;
     * Ensures getTimeDraggedInSeconds() = Old(getTimeTraggedInSeconds()) + delta;
     * Ensures getTimeDraggedInSeconds() < Float.MAX_VALUE;
     * @param delta delta time since last update
     */
    public void update(final float delta){
        if(this.timeDraggedInSeconds + delta < Float.MAX_VALUE)
            this.timeDraggedInSeconds += delta;
    }

    /**
     * updates the x and y position of the SpriteDragger
     * @param x mouse x position;
     * @param y mouse y position;
     */
    public void mouseDragged(final float x, final float y){
        this.posX = x;
        this.posY = y;
    }

    public float getTimeDraggedInSeconds(){
        return this.timeDraggedInSeconds;
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
