package de.marcdoderer.shop_keeper.screen.state;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class State {

    public abstract void render(final SpriteBatch batch);
    public abstract void renderShapes(final ShapeRenderer shapeRenderer);
    public abstract void update(final float delta);
    public abstract void resize(final float width, final float height);
    public abstract void dispose();
    public abstract void keyPressed(final int keyCode);
    public abstract void mouseClicked(final float x, final float y);
    public abstract void resume();
    public abstract OrthographicCamera getCamera();
}
