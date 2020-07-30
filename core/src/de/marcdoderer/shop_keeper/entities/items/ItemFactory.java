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

import java.util.HashMap;

public class ItemFactory {
    private final GameState gameState;
    private static ItemFactory itemRegistry;
    private final HashMap<String, ItemData> itemDatas;

    private ItemFactory(GameState gameState) {
        this.gameState = gameState;
        this.itemDatas = new HashMap<>(3);//@TODO initial Capacity for performance
        itemRegistry = this;
    }

    public static ItemFactory getItemRegistry(GameState gameState) {
        if (itemRegistry != null) {
            return itemRegistry;
        } else {
            final ItemFactory itemFactory = new ItemFactory(gameState);
            ItemData[] defaultItems = {new ItemData("shopKeeper", "apple", 2f, 2f),
                    new ItemData("shopKeeper", "gold", 1.6f, 1.6f),
                    new ItemData("shopKeeper", "pickaxe", 3f, 3f),
                    new ItemData("shopKeeper", "pickaxe2", 3f, 3f)};
            for (ItemData defaultItem : defaultItems) {
                itemFactory.registerItemData(defaultItem.getFullID(), defaultItem);
            }
            return itemFactory;
        }
    }

    public static ItemFactory getItemRegistry() {
        if (itemRegistry == null) {
            throw new IllegalAccessError("Instance not yet created");
        }
        return itemRegistry;
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
        Item itemEntity = createEntity(data, "items/atlas/items.atlas"/*@TODO item /ModID/name.atlas */, data.getName(),
                position, world, data.getWidth(), data.getHeigth());
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

    public ItemData getItemData(Item item) {
        return itemDatas.get(item.id);
    }

    public Item createItemByID(String id) {
        return this.createItem(itemDatas.get(id), new Vector2(0, 0), gameState.world);
    }

    private void registerItemData(String id, ItemData data) {
        this.itemDatas.put(id, data);
    }
}
