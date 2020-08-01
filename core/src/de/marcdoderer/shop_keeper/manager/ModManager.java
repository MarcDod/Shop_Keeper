package de.marcdoderer.shop_keeper.manager;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Json;
import de.marcdoderer.shop_keeper.entities.items.ModItemData;
import de.marcdoderer.shop_keeper.entities.items.ItemFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class ModManager {
    private static ModManager modManager;
    private final AssetManager assetManager;
    private final Json json;

    private final HashMap<String, TextureAtlas> atlasList;

    private ModManager(AssetManager assetManager) {
        this.assetManager = assetManager;
        this.atlasList = new HashMap<>();
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
        ModItemData modItemData;
        ItemFactory itemRegistry = ItemFactory.getItemRegistry();
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            FileHandle itemDir = assetManager.getFileHandleResolver().resolve(path + "items");
            for (FileHandle itemFile : itemDir.list()) {
                modItemData = json.fromJson(ModItemData.class, itemFile.read());
                itemRegistry.registerItemData(modItemData.getFullID(), modItemData);
            }
        } else {
            File itemDir = assetManager.getFileHandleResolver().resolve(path + "items").file();
            for (File itemFile : itemDir.listFiles()) {
                try {
                    modItemData = json.fromJson(ModItemData.class, new FileInputStream(itemFile));
                    itemRegistry.registerItemData(modItemData.getFullID(), modItemData);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void loadModData() {
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            final FileHandle modsDir = Gdx.files.internal("mods/");
            for (FileHandle modDir : modsDir.list()) {
                registerMod(modDir.name());
            }
        } else {
            File modsDir = assetManager.getFileHandleResolver().resolve("mods").file();
            for (File modDir : modsDir.listFiles()) {
                registerMod(modDir.getName());
            }
        }
    }

    public TextureAtlas getAtlas(String modID) {
        return atlasList.get(modID);
    }
}
