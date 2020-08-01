package de.marcdoderer.shop_keeper.entities.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import de.marcdoderer.shop_keeper.animation.IdleAnimation;
import de.marcdoderer.shop_keeper.animation.MoveAnimation;
import de.marcdoderer.shop_keeper.manager.ModManager;
import de.marcdoderer.shop_keeper.screen.state.GameState;
import de.marcdoderer.shop_keeper.util.Util;

import java.util.HashMap;

public class ItemFactory {
    private final GameState gameState;
    private static ItemFactory itemRegistry;
    private final HashMap<String, ModItemData> itemDatas;

    private ItemFactory(GameState gameState) {
        this.gameState = gameState;
        this.itemDatas = new HashMap<>(3);//@TODO initial Capacity for performance
        itemRegistry = this;
        ModManager.getModManager().loadModData();
    }

    public static ItemFactory getItemRegistry(GameState gameState) {
        if (itemRegistry != null) {
            return itemRegistry;
        } else {
            return new ItemFactory(gameState);
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
    public Item createItem(final ModItemData data, final Vector2 position, final World world) {
        return createEntity(data, ModManager.getModManager().getAtlas(data.getModID()), data.getName(),
                position, world, data.getWidth(), data.getHeigth());
    }

    private Item createEntity(ModItemData data, TextureAtlas atlas, String itemName, Vector2 position, World world, float width, float height) {
        Sprite sprite = getSprite(atlas, itemName, width, height);

        Body entityBody = Util.crateKinematicBody(width / 2, height / 2, position.x, position.y, world);

        return new Item(sprite, entityBody, new MoveAnimation(sprite), new IdleAnimation(sprite), data);
    }

    private Sprite getSprite(TextureAtlas atlas, String itemName, float width, float height) {
        TextureRegion texture = atlas.findRegion(itemName);
        Sprite sprite = new Sprite(texture);
        sprite.setSize(width, height);
        sprite.setOriginCenter();
        return sprite;
    }

    public ModItemData getItemData(Item item) {
        return itemDatas.get(item.id);
    }

    public Item createItemByID(String id) {
        return this.createItem(itemDatas.get(id), new Vector2(0, 0), gameState.world);
    }

    public void registerItemData(String id, ModItemData data) {
        this.itemDatas.put(id, data);
    }

    public Sprite getSpriteByItemID(String itemID) {
        final ModItemData modItemData = itemDatas.get(itemID);
        return getSprite(ModManager.getModManager().getAtlas(modItemData.getModID()),
                modItemData.getName(),
                modItemData.getWidth(),
                modItemData.getHeigth());
    }
}
