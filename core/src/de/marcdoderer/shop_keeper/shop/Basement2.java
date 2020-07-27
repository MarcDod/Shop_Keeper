package de.marcdoderer.shop_keeper.shop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import de.marcdoderer.shop_keeper.manager.EntityData;
import de.marcdoderer.shop_keeper.screen.GameScreen;
import de.marcdoderer.shop_keeper.screen.state.GameState;
import de.marcdoderer.shop_keeper.shop.loader.Basement2Loader;
import de.marcdoderer.shop_keeper.shop.time.DayNightCircle;

public class Basement2 extends Place{

    public Basement2(GameState gameState, DayNightCircle dayNightCircle, EntityData[] entityData) {
        super(gameState, dayNightCircle ,new Basement2Loader(gameState, new Vector2(0, 2 * GameState.HEIGHT), entityData));
    }

    @Override
    public int getID() {
        return Place.BASEMENT2_ID;
    }

    @Override
    public void init() {
        this.position = new Vector2(0, 2 * GameState.HEIGHT);
        this.background = new Sprite((Texture) gameState.screen.assetManager.get("shop/background/basement2.png"));
        rayHandler.setBlur(true);
        rayHandler.setBlurNum(1);

        rayHandler.setAmbientLight(0.3f);
    }
}
