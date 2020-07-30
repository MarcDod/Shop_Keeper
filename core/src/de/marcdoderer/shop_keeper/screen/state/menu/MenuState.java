package de.marcdoderer.shop_keeper.screen.state.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import de.marcdoderer.shop_keeper.screen.GameScreen;
import de.marcdoderer.shop_keeper.screen.state.GameState;
import de.marcdoderer.shop_keeper.screen.state.MenuManager;
import de.marcdoderer.shop_keeper.screen.state.State;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public abstract class MenuState extends State {

    public final static float GRAY_VALUE = 0.5f;
    public final static float WIDTH = GameState.WIDTH * 0.3f;
    public final static float HEIGHT = GameState.HEIGHT * 0.8f;

    public final static float MENU_X = GameState.WIDTH / 2f - WIDTH / 2f;
    public final static float MENU_Y = GameState.HEIGHT / 2f - HEIGHT / 2f;

    public final TextureRegion background;
    public final Texture menuForeground;
    public final GameScreen screen;

    protected final OrthographicCamera camera;
    protected final ShaderProgram grayShader;

    protected final List<MenuPage> menuPages;

    protected final Stage stage;

    protected InputProcessor originInputProcessor;
    protected int currentPage;

    private MenuState(final GameScreen screen, final int page){
        this.currentPage = page;
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

        this.menuPages = new LinkedList<MenuPage>();

        this.stage = new Stage();
        originInputProcessor = Gdx.input.getInputProcessor();
    }

    public MenuState(GameScreen screen){
        this(screen, 0);
    }

    public void loadLastPage(){
        stage.getRoot().removeActor(menuPages.get(currentPage).getGroup());
        this.currentPage--;
        stage.addActor(menuPages.get(currentPage).getGroup());
    }

    public void loadNextPage(){
        stage.getRoot().removeActor(menuPages.get(currentPage).getGroup());
        this.currentPage++;
        stage.addActor(menuPages.get(currentPage).getGroup());
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.setShader(grayShader);
        batch.begin();
        batch.getColor();

        grayShader.setUniformf("u_percent", GRAY_VALUE);
        batch.draw(background, 0, 0, GameState.WIDTH, GameState.HEIGHT);

        batch.end();
        batch.setShader(SpriteBatch.createDefaultShader());

        batch.begin();
        Color c = batch.getColor();
        batch.setColor(c.r, c.g, c.b, .3f);
        batch.draw(menuForeground, MENU_X, MENU_Y, WIDTH, HEIGHT);
        batch.setColor(c.r, c.g, c.b, 1f);

        batch.end();

        this.stage.draw();
    }

    @Override
    public void dispose() {
        background.getTexture().dispose();
        grayShader.dispose();
        this.stage.dispose();
        Gdx.input.setInputProcessor(originInputProcessor);
        for(MenuPage page : menuPages){
            page.dispose();
        }
    }

    @Override
    public OrthographicCamera getCamera() {
        return camera;
    }

    public InputProcessor getInputProcessor(){
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(originInputProcessor);
        return inputMultiplexer;
    }

    @Override
    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height);
    }

    @Override
    public void resume() {
        originInputProcessor = Gdx.input.getInputProcessor();
        Gdx.input.setInputProcessor(getInputProcessor());
    }

    @Override
    public void keyPressed(int keyCode) {
        if(keyCode == MenuManager.MENU_KEY){
            this.screen.stateManager.pop();
        }
    }

}
