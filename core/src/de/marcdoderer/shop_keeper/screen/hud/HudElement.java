package de.marcdoderer.shop_keeper.screen.hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface HudElement {
    void render(SpriteBatch batch);
    void update(float delta);
    void dispose();
    void setVisible(final boolean visible);
}
