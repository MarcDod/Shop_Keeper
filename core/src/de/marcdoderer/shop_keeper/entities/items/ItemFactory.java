package de.marcdoderer.shop_keeper.entities.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import de.marcdoderer.shop_keeper.animation.IdleAnimation;
import de.marcdoderer.shop_keeper.animation.MoveAnimation;
import de.marcdoderer.shop_keeper.screen.GameScreen;
import de.marcdoderer.shop_keeper.screen.state.GameState;
import de.marcdoderer.shop_keeper.util.Util;

public class ItemFactory {
    private final GameState gameState;

    public enum ItemType {
        APPLE, GOLD, PICKAXE
    }

    public ItemFactory(GameState gameState){
        this.gameState = gameState;
    }

    /**
     * creates an Item
     * @param item the Item that should be produced
     * @param position the Position of the Item
     * @param world the world in witch the item is.
     * @return the Item
     */
    public Item createItem(final ItemType item, final Vector2 position, final World world) {
        Item itemEntity = null;
        switch(item){
            case APPLE:
                itemEntity = createEntity(item, "items/atlas/items.atlas", "apple",
                        position, world, 2f, 2f);
                break;
            case GOLD:
                itemEntity = createEntity(item, "items/atlas/items.atlas", "gold",
                        position, world, 1.6f, 1.6f);
                break;
            case PICKAXE:
                itemEntity = createEntity(item, "items/atlas/items.atlas", "pickaxe",
                        position, world, 3f, 3f);
                break;
        }
        return itemEntity;
    }

    private Item createEntity(ItemType type, String atlasUrl, String itemName, Vector2 position, World world, float width, float height){
        TextureAtlas atlas = gameState.screen.assetManager.get(atlasUrl);
        TextureRegion texture = atlas.findRegion(itemName);
        Sprite sprite = new Sprite(texture);
        sprite.setSize(width, height);
        sprite.setOriginCenter();

        Body entityBody = Util.crateKinematicBody(width / 2, height / 2, position.x, position.y, world);

        return new Item(sprite, entityBody, new MoveAnimation(sprite), new IdleAnimation(sprite), type);
    }

}
