package de.marcdoderer.shop_keeper.shop;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import de.marcdoderer.shop_keeper.manager.EntityData;
import de.marcdoderer.shop_keeper.screen.state.GameState;
import de.marcdoderer.shop_keeper.shop.loader.GardenLoader;
import de.marcdoderer.shop_keeper.shop.time.DayNightCircle;

public class Garden extends Place{

    private TextureRegion day;
    private TextureRegion night;

    public Garden(GameState gameState, DayNightCircle dayNightCircle, EntityData[] entityData) {
        super(gameState, dayNightCircle, new GardenLoader(gameState, new Vector2(GameState.WIDTH, 0), entityData));
    }

    @Override
    public int getID() {
        return Place.GARDEN_ID;
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
    public void update(float delta) {
        super.update(delta);
        this.rayHandler.setAmbientLight(dayNightCircle.getAmbientLightIntensity());
    }

    @Override
    protected void init() {
        this.position = new Vector2(GameState.WIDTH, 0);
        TextureAtlas atlas = gameState.screen.assetManager.get("shop/background/atlas/shop.atlas");
        this.day = atlas.findRegion("day");
        this.night = atlas.findRegion("night");
    }
}
