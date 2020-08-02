package de.marcdoderer.shop_keeper.entities.items;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import de.marcdoderer.shop_keeper.animation.SpriteAnimator;
import de.marcdoderer.shop_keeper.entities.MovableEntity;
import de.marcdoderer.shop_keeper.manager.ItemData;

public class Item extends MovableEntity {

    public final String id;
    private int stackCount;

    public Item(Sprite sprite, Body body, SpriteAnimator moveAnimation, SpriteAnimator idleAnimation, ModItemData data) {
        super(sprite, body, moveAnimation, idleAnimation);
        this.id = data.getFullID();
        this.stackCount = 0;
    }

    public Item(Sprite sprite, Body body, SpriteAnimator moveAnimation, SpriteAnimator idleAnimation, ModItemData data, int itemCount) {
        this(sprite, body, moveAnimation, idleAnimation, data);
        this.stackCount = itemCount;
    }

    public final int getStackCount(){
        return this.stackCount;
    }

    /**
     * Requires count >= 1;
     *
     * @param count the amount of items;
     */
    public final void setStackCount(final int count){
        if(count < 1) throw  new IllegalArgumentException("stackCount cant be smaller than one");
        this.stackCount = count;
    }

    public ItemData createItemData(){
        return new ItemData(id, stackCount);
    }
}
