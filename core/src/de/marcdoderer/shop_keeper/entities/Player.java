package de.marcdoderer.shop_keeper.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.marcdoderer.shop_keeper.animation.SpriteAnimator;
import de.marcdoderer.shop_keeper.screen.GameScreen;

public class Player extends Character{

    public Player(Sprite sprite, Body body, SpriteAnimator moveAnimation, final SpriteAnimator idleAnimation, String name) {
        super(sprite, body, moveAnimation, idleAnimation, name);
    }


}
