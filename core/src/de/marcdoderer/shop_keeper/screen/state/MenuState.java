package de.marcdoderer.shop_keeper.screen.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import de.marcdoderer.shop_keeper.screen.GameScreen;

public class MenuState extends State{

    public final static float grayValue = 0.5f;
    public final static float WIDTH = GameState.WIDTH * 0.3f;
    public final static float HEIGHT = GameState.HEIGHT * 0.8f;

    public final TextureRegion background;
    public final Texture menuForeground;
    public final GameScreen screen;

    private final OrthographicCamera camera;
    private ShaderProgram grayShader;

    public MenuState(final GameScreen screen){
        background = ScreenUtils.getFrameBufferTexture();
        menuForeground = screen.assetManager.get("menu/menu.png");
        this.screen = screen;
        this.camera = new OrthographicCamera(GameState.WIDTH, GameState.HEIGHT);
        this.camera.translate(GameState.WIDTH / 2f, GameState.HEIGHT / 2f);
        this.camera.update();

        grayShader = new ShaderProgram(Gdx.files.internal("shader/vertex.vert"), Gdx.files.internal("shader/fragment.frag"));
        if(grayShader.getLog().length() > 0){
            System.out.println(grayShader.getLog());
        }

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.setShader(grayShader);
        batch.begin();
        batch.getColor();

        grayShader.setUniformf("u_percent", grayValue);
        batch.draw(background, 0, 0, GameState.WIDTH, GameState.HEIGHT);

        batch.end();
        batch.setShader(SpriteBatch.createDefaultShader());

        batch.begin();
        batch.draw(menuForeground, GameState.WIDTH / 2f - WIDTH / 2f, GameState.HEIGHT / 2f - HEIGHT / 2f, WIDTH, HEIGHT);
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
        grayShader.dispose();
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
    public void resume() {

    }

    @Override
    public OrthographicCamera getCamera() {
        return null;
    }
}
