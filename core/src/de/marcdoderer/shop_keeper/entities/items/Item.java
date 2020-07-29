package de.marcdoderer.shop_keeper.entities.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import de.marcdoderer.shop_keeper.animation.SpriteAnimator;
import de.marcdoderer.shop_keeper.entities.MovableEntity;
import de.marcdoderer.shop_keeper.manager.ItemData;

public class Item extends MovableEntity {

    public final String id;

    public Item(Sprite sprite, Body body, SpriteAnimator moveAnimation, SpriteAnimator idleAnimation, ItemData data) {
        super(sprite, body, moveAnimation, idleAnimation);
        this.id = data.getFullID();
    }
}
