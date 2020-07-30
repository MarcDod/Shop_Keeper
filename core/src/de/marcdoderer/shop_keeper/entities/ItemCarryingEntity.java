package de.marcdoderer.shop_keeper.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import de.marcdoderer.shop_keeper.entities.items.Item;
import de.marcdoderer.shop_keeper.entities.items.ItemCarry;
import de.marcdoderer.shop_keeper.entities.items.ItemFactory;
import de.marcdoderer.shop_keeper.manager.EntityData;

public class ItemCarryingEntity extends Entity implements ItemCarry {

    private Item carriedItem;

    /**
     * Requires body.getUserData() instance Of BodyDef
     * Requires body.getFixtureList().first().getUserData instance Of FixtureDef
     *
     * @param sprite sprite of the Entity
     * @param body  body of the Entity
     */
    public ItemCarryingEntity(Sprite sprite, Body body, String entityType) {
        super(sprite, body, entityType);
        this.type = EntityFactory.EntityType.ITEM_CARRYING;
    }

    @Override
    public void carryItem(Item item) {
        this.carriedItem = item;
        item.stopIdleAnimation();
        carriedItem.teleportTo(getPosition().add(new Vector2(0, sprite.getHeight() / 2 - item.getSprite().getHeight() / 2)));
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

    @Override
    public EntityData getEntityData() {
        EntityData data = super.getEntityData();
        if(carriedItem == null) {
            data.setItemData(null);
            return data;
        }
        data.setItemData(ItemFactory.getItemRegistry().getItemData(carriedItem));
        return data;
    }
}
