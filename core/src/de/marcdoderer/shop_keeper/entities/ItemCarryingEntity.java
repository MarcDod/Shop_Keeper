package de.marcdoderer.shop_keeper.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import de.marcdoderer.shop_keeper.entities.items.Item;
import de.marcdoderer.shop_keeper.entities.items.ItemCarry;
import de.marcdoderer.shop_keeper.manager.EntityData;

public class ItemCarryingEntity extends Entity implements ItemCarry {

    protected Item carriedItem;
    protected Vector2 itemOffset;

    /**
     * Requires body.getUserData() instance Of BodyDef
     * Requires body.getFixtureList().first().getUserData instance Of FixtureDef
     *
     * @param sprite sprite of the Entity
     * @param body  body of the Entity
     */
    public ItemCarryingEntity(Sprite sprite, Body body, String entityType, Vector2 itemOffset) {
        super(sprite, body, entityType);
        this.itemOffset = itemOffset;
        this.type = EntityFactory.EntityType.ITEM_CARRYING;
    }

    @Override
    public void carryItem(Item item) {
        this.carriedItem = item;
        item.stopIdleAnimation();
        carriedItem.teleportTo(sprite.getX() + (sprite.getWidth() + item.getWidth()) * itemOffset.x, sprite.getY() +(sprite.getHeight() + item.getHeight()) * itemOffset.y);
    }

    @Override
    public Item getCarriedItem() {
        return carriedItem;
    }

    @Override
    public void removeCarriedItem() {
        this.carriedItem = null;
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        if(carriedItem != null)
            carriedItem.render(batch);
    }

    protected void renderWithoutItem(final SpriteBatch batch){
        super.render(batch);
    }

    @Override
    public EntityData getEntityData() {
        EntityData data = super.getEntityData();
        if(carriedItem == null) {
            data.setCarriedItem(null);
            return data;
        }
        data.setCarriedItem(carriedItem.id);
        return data;
    }
}
