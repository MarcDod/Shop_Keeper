package de.marcdoderer.shop_keeper.entities.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import de.marcdoderer.shop_keeper.animation.IdleAnimation;
import de.marcdoderer.shop_keeper.animation.MoveAnimation;
import de.marcdoderer.shop_keeper.manager.ItemData;
import de.marcdoderer.shop_keeper.screen.state.GameState;
import de.marcdoderer.shop_keeper.util.Util;

public class ItemFactory {
    private final GameState gameState;

    public ItemFactory(GameState gameState){
        this.gameState = gameState;
    }

    /**
     * creates an Item
     *
     * @param data     the data of the item that should be produced
     * @param position the position of the item
     * @param world    the world in witch the item is.
     * @return the item
     */
    public Item createItem(final ItemData data, final Vector2 position, final World world) {
        Item itemEntity = createEntity(data, "items/atlas/items.atlas"/*@TODO ModID */, data.name,
                position, world, data.width, data.heigth);
        return itemEntity;
    }

    private Item createEntity(ItemData data, String atlasUrl, String itemName, Vector2 position, World world, float width, float height) {
        TextureAtlas atlas = gameState.screen.assetManager.get(atlasUrl);
        TextureRegion texture = atlas.findRegion(itemName);
        Sprite sprite = new Sprite(texture);
        sprite.setSize(width, height);
        sprite.setOriginCenter();

        Body entityBody = Util.crateKinematicBody(width / 2, height / 2, position.x, position.y, world);

        return new Item(sprite, entityBody, new MoveAnimation(sprite), new IdleAnimation(sprite), data);
    }

}
