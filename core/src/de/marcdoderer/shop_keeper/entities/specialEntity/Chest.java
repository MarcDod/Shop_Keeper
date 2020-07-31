package de.marcdoderer.shop_keeper.entities.specialEntity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import de.marcdoderer.shop_keeper.entities.ItemCarryingEntity;
import de.marcdoderer.shop_keeper.entities.items.Item;
import de.marcdoderer.shop_keeper.entities.items.ItemFactory;
import de.marcdoderer.shop_keeper.manager.EntityData;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Chest extends ItemCarryingEntity {

    private final List<String> itemIDs;


    /**
     * Requires body.getUserData() instance Of BodyDef
     * Requires body.getFixtureList().first().getUserData instance Of FixtureDef
     *
     * @param sprite     sprite of the Entity
     * @param body       body of the Entity
     * @param entityType
     */
    public Chest(Sprite sprite, Body body, String entityType) {
        super(sprite, body, entityType);
        this.itemIDs = new ArrayList<>();
    }

    @Override
    public void render(SpriteBatch batch) {
        renderWithoutItem(batch);
    }

    @Override
    public void carryItem(Item item) {
        this.itemIDs.add(item.id);
    }

    public void selectItem(final int index){
        this.carriedItem = ItemFactory.getItemRegistry().createItemByID(itemIDs.get(index));
    }

    @Override
    public void removeCarriedItem() {
        this.itemIDs.remove(getCarriedItem().id);
        super.removeCarriedItem();
    }

    @Override
    public EntityData getEntityData() {
        EntityData eData = super.getEntityData();

        return eData;
    }
}
