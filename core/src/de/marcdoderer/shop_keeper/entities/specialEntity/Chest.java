package de.marcdoderer.shop_keeper.entities.specialEntity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import de.marcdoderer.shop_keeper.entities.ItemCarryingEntity;
import de.marcdoderer.shop_keeper.entities.items.Item;

import java.util.LinkedList;
import java.util.List;

public class Chest extends ItemCarryingEntity {

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
    }

    @Override
    public void render(SpriteBatch batch) {
        renderWithoutItem(batch);
    }

    @Override
    public void carryItem(Item item) {
        super.carryItem(item);
    }


}
