package de.marcdoderer.shop_keeper.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import de.marcdoderer.shop_keeper.entities.HitboxScalarData;

import java.util.HashMap;

public class HitboxScalarManager {
    private static HitboxScalarManager hitboxScalarManager;

    private final AssetManager assetManager;

    private final HashMap<String, HitboxScalarData> scalingData;

    private HitboxScalarManager(AssetManager assetManager) {
        this.assetManager = assetManager;
        scalingData = new HashMap<>();
    }

    public static HitboxScalarManager getHitboxScalarManager() {
        if (hitboxScalarManager == null) {
            throw new IllegalAccessError("HitboxScalarManager instance not yet created");
        } else {
            return hitboxScalarManager;
        }
    }

    public static HitboxScalarManager getHitboxScalarManager(AssetManager assetManager) {
        if (hitboxScalarManager == null) {
            hitboxScalarManager = new HitboxScalarManager(assetManager);
        }
        return hitboxScalarManager;
    }

    public HitboxScalarData getScalar(String spriteName) {
        return scalingData.get(spriteName);
    }

    public void loadScalars() {
        final FileHandle spriteScalars = Gdx.files.internal("SpriteScalars");
        for (FileHandle spriteScalarFile : spriteScalars.list()) {
            final HitboxScalarData scalarData = new Json().fromJson(HitboxScalarData.class, spriteScalarFile);
            scalingData.put(spriteScalarFile.nameWithoutExtension(), scalarData);
        }
    }
}
