package de.marcdoderer.shop_keeper.animation;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import de.marcdoderer.shop_keeper.screen.GameScreen;
import de.marcdoderer.shop_keeper.screen.state.GameState;

public class MoveAnimation extends SpriteAnimator{

    private float progress;
    private float originX;
    private float originY;

    public MoveAnimation(Sprite sprite) {
        super(sprite);
    }

    @Override
    public void reset() {
        this.sprite.setOrigin(originX, originY);
        this.progress = 0.0f;
    }



    @Override
    public void update(float delta) {
        if(isAnimated()){
            this.sprite.setOrigin(originX, originY + (float) Math.sin(progress) * 7.0f/ GameState.SCALE);
            this.progress += 14f * delta;
        }
    }

    @Override
    public void load() {
        this.originX = sprite.getOriginX();
        this.originY = sprite.getOriginY();
    }
}
