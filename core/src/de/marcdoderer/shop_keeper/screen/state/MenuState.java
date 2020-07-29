package de.marcdoderer.shop_keeper.screen.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import de.marcdoderer.shop_keeper.screen.GameScreen;

public class MenuState extends State{

    public final TextureRegion background;
    public final GameScreen screen;

    private final OrthographicCamera camera;

    public MenuState(final GameScreen screen){
        background = ScreenUtils.getFrameBufferTexture();
        this.screen = screen;
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera.translate(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        this.camera.update();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.end();

    }

    @Override
    public void renderShapes(ShapeRenderer shapeRenderer) {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void resize(float width, float height) {

    }

    @Override
    public void dispose() {
        background.getTexture().dispose();
    }

    @Override
    public void keyPressed(int keyCode) {
        if(keyCode == Input.Keys.TAB){
            this.dispose();
            this.screen.stateManager.pop();
        }
    }

    @Override
    public void mouseClicked(float x, float y) {

    }

    @Override
    public OrthographicCamera getCamera() {
        return null;
    }
}
