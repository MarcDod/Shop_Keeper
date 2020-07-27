package de.marcdoderer.shop_keeper.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import de.marcdoderer.shop_keeper.animation.SpriteAnimator;
import de.marcdoderer.shop_keeper.entities.items.Item;
import de.marcdoderer.shop_keeper.entities.items.ItemCarry;

public class Character extends MovableEntity implements ItemCarry {

    // the id of the zone the character is standing atm
    private int currentZoneID;
    protected String name;
    protected Item carriedItem;

    public Character(final Sprite sprite, final Body body, final SpriteAnimator moveAnimation, final SpriteAnimator idleAnimation, final String name) {
        super(sprite, body, moveAnimation, idleAnimation);
        this.name = name;
        this.carriedItem = null;
        this.type = EntityFactory.EntityType.MOVABLE_ITEM_CARRYING;
    }

    public String getName(){
        return this.name;
    }

    public void setCurrentZoneID(final int currentZoneID){
        this.currentZoneID = currentZoneID;
    }

    public int getCurrentZoneID(){
        return this.currentZoneID;
    }

    @Override
    public boolean moveTo(Vector2 destination) {
        if(this.carriedItem != null)
            this.carriedItem.moveTo(new Vector2(destination.x, destination.y + sprite.getHeight() / 8));
        return super.moveTo(destination);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        if(carriedItem != null)
            carriedItem.render(batch);
    }

    @Override
    public void carryItem(Item item) {
        stopIdleAnimation();
        startIdleAnimation();
        item.startIdleAnimation();
        this.carriedItem = item;
        carriedItem.teleportTo(getPosition().add(new Vector2(0, sprite.getHeight() / 8)));
    }

    @Override
    public Item getCarriedItem() {
        return this.carriedItem;
    }

    @Override
    public void removeCarriedItem() {
        this.carriedItem = null;
    }

    @Override
    public void startMoveAnimation() {
        super.startMoveAnimation();
        if(this.carriedItem != null)
            this.carriedItem.startMoveAnimation();
    }

    @Override
    public void stopMoveAnimation() {
        super.stopMoveAnimation();
        if(this.carriedItem != null)
            this.carriedItem.stopMoveAnimation();
    }

    @Override
    public void startIdleAnimation() {
        super.startIdleAnimation();
        if(this.carriedItem != null)
            this.carriedItem.startIdleAnimation();
    }

    @Override
    public void stopIdleAnimation() {
        super.stopIdleAnimation();
        if(this.carriedItem != null)
            this.carriedItem.stopIdleAnimation();
    }
}
