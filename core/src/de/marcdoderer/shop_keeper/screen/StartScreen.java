package de.marcdoderer.shop_keeper.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import de.marcdoderer.shop_keeper.Shop_Keeper;

public class StartScreen extends ScreenAdapter {

    private Shop_Keeper shop_keeper;

    public StartScreen(Shop_Keeper shop_keeper){
        this.shop_keeper = shop_keeper;
        shop_keeper.assetManager.load("shop/background/basement.png" ,Texture.class);
        shop_keeper.assetManager.load("shop/background/basement2.png", Texture.class);
        shop_keeper.assetManager.load("shop/background/atlas/shop.atlas", TextureAtlas.class);
        shop_keeper.assetManager.load("npc/atlas/npc.atlas", TextureAtlas.class);
        shop_keeper.assetManager.load("shop/entity/atlas/shopEntity.atlas", TextureAtlas.class);
        shop_keeper.assetManager.load("items/atlas/items.atlas", TextureAtlas.class);
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(shop_keeper.assetManager.update()){
            shop_keeper.setScreen(new GameScreen(shop_keeper.assetManager));
            this.dispose();
        }
    }

}
