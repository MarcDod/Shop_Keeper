package de.marcdoderer.shop_keeper.manager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Json;
import de.marcdoderer.shop_keeper.entities.items.ItemFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class ModManager {
    private static ModManager modManager;
    private final AssetManager assetManager;
    private final Json json;

    private HashMap<String, TextureAtlas> atlasList;

    private ModManager(AssetManager assetManager) {
        this.assetManager = assetManager;
        this.json = new Json();
    }

    public static ModManager getModManager() {
        if (modManager == null) {
            throw new IllegalAccessError("ModManager instance not yet created");
        } else {
            return modManager;
        }
    }

    public static ModManager getModManager(AssetManager assetManager) {
        if (modManager == null) {
            modManager = new ModManager(assetManager);
        }
        return modManager;
    }

    private void registerMod(String modID) {
        String path = "mods/" + modID + "/";
        assetManager.load(path + "items.atlas", TextureAtlas.class);
        assetManager.finishLoadingAsset(path + "items.atlas");
        TextureAtlas atlas = assetManager.get(path + "items.atlas");
        atlasList.put(modID, atlas);
        File itemDir = assetManager.getFileHandleResolver().resolve(path + "items").file();
        ItemData itemData;
        ItemFactory itemRegistry = ItemFactory.getItemRegistry();
        for (File itemFile : itemDir.listFiles()) {
            try {
                itemData = json.fromJson(ItemData.class, new FileInputStream(itemFile));
                itemRegistry.registerItemData(itemData.getFullID(), itemData);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public void loadModData() {
        File modsDir = assetManager.getFileHandleResolver().resolve("mods").file();
        for (File modDir : modsDir.listFiles()) {
            registerMod(modDir.getName());
        }
    }

    public TextureAtlas getAtlas(String modID) {
        return atlasList.get(modID);
    }
}
