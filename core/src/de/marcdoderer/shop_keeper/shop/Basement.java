package de.marcdoderer.shop_keeper.shop;

import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import de.marcdoderer.shop_keeper.manager.EntityData;
import de.marcdoderer.shop_keeper.movement.collision.Collision;
import de.marcdoderer.shop_keeper.screen.GameScreen;
import de.marcdoderer.shop_keeper.screen.state.GameState;
import de.marcdoderer.shop_keeper.shop.loader.BasementLoader;
import de.marcdoderer.shop_keeper.shop.time.DayNightCircle;

public class Basement extends Place{


    public Basement(GameState gameState, DayNightCircle dayNightCircle, EntityData[] entityData) {
        super(gameState, dayNightCircle,new BasementLoader(gameState, new Vector2(0, GameState.HEIGHT), entityData));
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
    }

    @Override
    public int getID() {
        return Place.BASEMENT_ID;
    }

    @Override
    public void init() {
        this.position = new Vector2(0, GameState.HEIGHT);

        this.background = new Sprite((Texture) gameState.screen.assetManager.get("shop/background/basement.png"));

        rayHandler.setBlur(true);
        rayHandler.setBlurNum(1);

        rayHandler.setAmbientLight(0.3f);

        PointLight test = new PointLight(rayHandler, 500, new Color(1, 1, 1, 0.7f), 900f / GameState.SCALE,  position.x + 560f / GameState.SCALE, position.y + 440f / GameState.SCALE);
        test.setContactFilter(Collision.LIGHT, Collision.LIGHT_GROUP, Collision.MASK_LIGHTS);
    }

}
