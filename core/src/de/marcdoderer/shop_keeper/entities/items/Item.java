package de.marcdoderer.shop_keeper.entities.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import de.marcdoderer.shop_keeper.animation.SpriteAnimator;
import de.marcdoderer.shop_keeper.entities.MovableEntity;

public class Item extends MovableEntity {

    public final ItemFactory.ItemType type;

    public Item(Sprite sprite, Body body, SpriteAnimator moveAnimation, SpriteAnimator idleAnimation, ItemFactory.ItemType type) {
        super(sprite, body, moveAnimation, idleAnimation);
        this.type = type;
    }
}
