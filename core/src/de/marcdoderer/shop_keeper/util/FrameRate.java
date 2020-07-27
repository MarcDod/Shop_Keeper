package de.marcdoderer.shop_keeper.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import de.marcdoderer.shop_keeper.screen.hud.HudElement;

public class FrameRate implements Disposable, HudElement {
    long lastTimeCounted;
    private float sinceChange;
    private float frameRate;
    private final BitmapFont font;


    public FrameRate() {
        lastTimeCounted = TimeUtils.millis();
        sinceChange = 0;
        frameRate = Gdx.graphics.getFramesPerSecond();
        font = new BitmapFont();
    }

    public void update(float delta) {
        lastTimeCounted = TimeUtils.millis();

        sinceChange += delta;
        if(sinceChange >= 1) {
            sinceChange = 0;
            frameRate = Gdx.graphics.getFramesPerSecond();
        }
    }

    public void render(SpriteBatch batch) {
        font.draw(batch, (int)frameRate + " fps", 60, Gdx.graphics.getHeight() - 3);
    }

    public void dispose() {
        font.dispose();
    }
}
