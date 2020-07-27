package de.marcdoderer.shop_keeper.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import de.marcdoderer.shop_keeper.screen.GameScreen;

public class IdleAnimation extends SpriteAnimator{

    private float progress;
    private float height;


    public IdleAnimation(Sprite sprite) {
        super(sprite);
    }

    @Override
    public void reset() {
        sprite.setSize(sprite.getWidth(), height);
        this.progress = 0.0f;
    }

    @Override
    public void update(float delta) {
        if(isAnimated()){
            sprite.setSize(sprite.getWidth(), height+ (float) (Math.sin(progress) * 0.3f));
            progress += 5f * delta;
        }
    }

    @Override
    public void load() {
        this.height = sprite.getHeight();
    }

}
