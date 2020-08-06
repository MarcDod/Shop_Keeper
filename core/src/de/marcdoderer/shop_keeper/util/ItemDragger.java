package de.marcdoderer.shop_keeper.util;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class ItemDragger extends SpriteDragger {

    private final String itemID;

    /**
     * initialises the spriteDragger
     *
     * @param sprite the sprite that should be dragged over the screen
     * @param posX   the starting mouse X position;
     * @param posY   the starting mouse Y position;
     */
    public ItemDragger(Sprite sprite, float posX, float posY, final String itemID) {
        super(sprite, posX, posY);
        this.itemID = itemID;
    }

    public final String getItemID(){
        return this.itemID;
    }
}
