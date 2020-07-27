package de.marcdoderer.shop_keeper.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import de.marcdoderer.shop_keeper.animation.SpriteAnimator;
import de.marcdoderer.shop_keeper.screen.GameScreen;

public class MovableEntity extends Entity{

    public static final int MOVE_ANIMATION = 0;
    public static final int IDLE_ANIMATION = 1;

    protected float speed;
    protected SpriteAnimator moveAnimation;
    protected SpriteAnimator idleAnimation;

    public MovableEntity(Sprite sprite, Body body, SpriteAnimator moveAnimation, SpriteAnimator idleAnimation, String entityType) {
        super(sprite, body, entityType);
        this.moveAnimation = moveAnimation;
        this.idleAnimation = idleAnimation;
        this.speed = 7;
        this.type = EntityFactory.EntityType.MOVABLE;
    }

    public MovableEntity(Sprite sprite, Body body, SpriteAnimator moveAnimation, SpriteAnimator idleAnimation) {
        super(sprite, body, "");
        this.moveAnimation = moveAnimation;
        this.idleAnimation = idleAnimation;
        this.speed = 7;
        this.type = EntityFactory.EntityType.MOVABLE;
    }

    private SpriteAnimator getMoveAnimation(){
        return this.moveAnimation;
    }
    private SpriteAnimator getIdleAnimation() { return this.idleAnimation; }

    public void startAnimation(int animationID){
        switch (animationID){
            case MOVE_ANIMATION:
                startMoveAnimation();
                break;
            case IDLE_ANIMATION:
                startIdleAnimation();
                break;
        }
    }

    public void stopAnimation(int animationID){
        switch (animationID){
            case MOVE_ANIMATION:
                stopMoveAnimation();
                break;
            case IDLE_ANIMATION:
                stopIdleAnimation();
                break;
        }
    }

    protected void startMoveAnimation(){
        this.moveAnimation.start().load();
    }

    protected void stopMoveAnimation(){
        this.moveAnimation.stop().reset();
    }

    protected void startIdleAnimation(){
        this.idleAnimation.start().load();
    }

    protected void stopIdleAnimation(){
        this.idleAnimation.stop().reset();
    }

    @Override
    public void update(float delta){
        this.moveAnimation.update(delta);
        this.idleAnimation.update(delta);
    }

    /**
     * move the entity to the given destination
     *
     * @return true if the entity is at its destination
     */
    public boolean moveTo(final Vector2 destination){
        final Vector2 position = this.getPosition();
        final float distance = position.dst(destination);
        if(speed * 0.0166f >= distance ){
            this.body.setLinearVelocity(0, 0);
            return true;
        }
        final float dx = ((destination.x - position.x) / distance) * speed;
        final float dy = ((destination.y - position.y) / distance) * speed;


        this.body.setLinearVelocity(dx, dy);

        return false;
    }
}
