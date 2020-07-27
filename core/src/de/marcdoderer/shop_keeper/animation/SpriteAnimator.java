package de.marcdoderer.shop_keeper.animation;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class SpriteAnimator {

    protected final Sprite sprite;
    protected boolean animate;

    public SpriteAnimator(final Sprite sprite){
        this.sprite = sprite;
        this.animate = false;
        load();
    }

    public boolean isAnimated(){
        return this.animate;
    }

    public SpriteAnimator start(){
        this.animate = true;
        return this;
    }

    public SpriteAnimator stop(){
        this.animate = false;
        return this;
    }

    public abstract void reset();

    public abstract void update(float delta);

    public abstract void load();
}
