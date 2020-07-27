package de.marcdoderer.shop_keeper.shop;

import box2dLight.ConeLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import de.marcdoderer.shop_keeper.manager.EntityData;
import de.marcdoderer.shop_keeper.screen.GameScreen;
import de.marcdoderer.shop_keeper.screen.state.GameState;
import de.marcdoderer.shop_keeper.shop.loader.ShopLoader;
import de.marcdoderer.shop_keeper.shop.time.DayNightCircle;


public class Shop extends Place{

    private ConeLight lamp;
    private ConeLight sunTop;
    private ConeLight sunLeft;

    private TextureRegion day;
    private TextureRegion night;

    public Shop(GameState gameState, DayNightCircle dayNightCircle, EntityData[] entityData) {
        super(gameState, dayNightCircle,new ShopLoader(gameState, new Vector2(0, 0), entityData));
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        lamp.setActive(dayNightCircle.isNight());
        sunTop.setColor(dayNightCircle.getSunColor());
        sunLeft.setColor(dayNightCircle.getSunColor());
        rayHandler.setAmbientLight(dayNightCircle.getAmbientLight());
    }

    @Override
    public void render(SpriteBatch batch) {
        if(dayNightCircle.isNight()){
            batch.draw(night, position.x, position.y, GameState.WIDTH, GameState.HEIGHT);
        }else{
            batch.draw(day, position.x, position.y, GameState.WIDTH, GameState.HEIGHT);
        }
        super.render(batch);
    }

    @Override
    public int getID() {
        return Place.SHOP_ID;
    }

    public void init(){
        this.position = new Vector2();

        TextureAtlas atlas = gameState.screen.assetManager.get("shop/background/atlas/shop.atlas");
        TextureRegion region = atlas.findRegion("house");
        this.background = new Sprite(region);
        TextureRegion regionDay = atlas.findRegion("day");
        this.day = regionDay;
        TextureRegion regionNight = atlas.findRegion("night");
        this.night = regionNight;

        rayHandler.setBlur(true);
        rayHandler.setBlurNum(1);
        rayHandler.setCulling(false);

        lamp = new ConeLight(rayHandler, 100, new Color(1f, 0.66f, 0f, 0.7f), 40f,  25, 6.4f, 90, 90);
        sunTop = new ConeLight(rayHandler, 100, new Color(1, 1, 1, 0.7f), 20f,  560f/ GameState.SCALE, 740f/ GameState.SCALE, -90, 60);
        sunLeft = new ConeLight(rayHandler, 100, new Color(1, 1, 1, 0.7f), 10f,  190f/ GameState.SCALE, 400f/ GameState.SCALE, 0, 90);

        lamp.setStaticLight(true);
        rayHandler.update();
    }

    @Override
    public void dispose() {
        super.dispose();
        day.getTexture().dispose();
        night.getTexture().dispose();
    }
}
