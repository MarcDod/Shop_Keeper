package de.marcdoderer.shop_keeper.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import de.marcdoderer.shop_keeper.entities.SpriteData;

import java.util.HashMap;

public class SpriteDataManager {
    private static SpriteDataManager spriteDataManager;

    private final AssetManager assetManager;

    private final HashMap<String, SpriteData> scalingData;

    private SpriteDataManager(AssetManager assetManager) {
        this.assetManager = assetManager;
        scalingData = new HashMap<>();
    }

    public static SpriteDataManager getSpriteDataManager() {
        if (spriteDataManager == null) {
            throw new IllegalAccessError("HitboxScalarManager instance not yet created");
        } else {
            return spriteDataManager;
        }
    }

    public static SpriteDataManager getSpriteManager(AssetManager assetManager) {
        if (spriteDataManager == null) {
            spriteDataManager = new SpriteDataManager(assetManager);
        }
        return spriteDataManager;
    }

    public SpriteData getSpriteData(String spriteName) {
        if(scalingData.containsKey(spriteName))
            return scalingData.get(spriteName);

        return SpriteData.DEFAULT_SPRITE_DATA;
    }

    public void loadSpriteDatas() {
        final FileHandle spriteDatas = Gdx.files.internal("spriteDatas");
        for (FileHandle spriteDataFile : spriteDatas.list()) {
            final SpriteData scalarData = new Json().fromJson(SpriteData.class, spriteDataFile);
            scalingData.put(spriteDataFile.nameWithoutExtension(), scalarData);
        }
    }
}
