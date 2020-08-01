package de.marcdoderer.shop_keeper.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.strongjoshua.console.Console;
import com.strongjoshua.console.GUIConsole;
import de.marcdoderer.shop_keeper.Shop_Keeper;
import de.marcdoderer.shop_keeper.manager.GameData;
import de.marcdoderer.shop_keeper.manager.GameManager;
import de.marcdoderer.shop_keeper.screen.state.GameState;
import de.marcdoderer.shop_keeper.screen.state.StateManager;
import de.marcdoderer.shop_keeper.util.GameCommandExecutor;

public class GameScreen extends ScreenAdapter {


    public final SpriteBatch batch;
    public final ShapeRenderer shapeRenderer;

    public final StretchViewport viewport;

    public final AssetManager assetManager;
    public final GameManager gameManager;

    public final StateManager stateManager;
    private final GameState gameState;

    private final Console console;

    public GameScreen(final AssetManager assetManager){
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.gameManager = new GameManager();
        Shop_Keeper.vSync = gameManager.gameData.getVSync();
        Gdx.graphics.setVSync(Shop_Keeper.vSync);
        this.assetManager = assetManager;
        this.gameState = new GameState(this);
        this.stateManager = new StateManager(gameState);
        this.viewport = new StretchViewport(GameState.WIDTH, GameState.HEIGHT, stateManager.peek().getCamera());
        this.console = new GUIConsole();
        this.console.setSize(600, 500);
        this.console.setPosition(50, 50);
        this.console.setVisible(false);
        this.console.setDisplayKeyID(Input.Keys.UP);
        this.console.setCommandExecutor(new GameCommandExecutor(console, gameState));
        this.console.setDisabled(false);

        Gdx.input.setInputProcessor(new InputAdapter(){

            @Override
            public boolean keyDown(int keycode) {
                keyClicked(keycode);
                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                mouseDragged(screenX, screenY);
                return true;
            }

            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                //if (button == Input.Buttons.LEFT) {
                    mouseClicked(screenX, screenY);
                    return true;
                //}
                //return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                //if (button == Input.Buttons.LEFT) {
                    mouseReleased(screenX, screenY);
                    return true;
                //}
                //return false;
            }
        });

        this.console.resetInputProcessing();
    }

    private void mouseReleased(final int screenX, final int screenY){
        final Vector2 mouseClick = viewport.unproject(new Vector2(screenX, screenY));
        this.stateManager.peek().mouseReleased(mouseClick.x, mouseClick.y);
    }

    private void mouseDragged(final int screenX, final int screenY){
        final Vector2 mouseClick = viewport.unproject(new Vector2(screenX, screenY));
        this.stateManager.peek().mouseDragged(mouseClick.x, mouseClick.y);
    }

    private void mouseClicked(final int screenX, final int screenY){
        final Vector2 mouseClick = viewport.unproject(new Vector2(screenX, screenY));
        this.stateManager.peek().mouseClicked(mouseClick.x, mouseClick.y);
    }

    private void keyClicked(final int keyCode){
        this.stateManager.peek().keyPressed(keyCode);
    }

    public void save(){

        gameManager.gameData = new GameData();
        gameManager.gameData.setTimeInSeconds(gameState.dayNightCircle.getSeconds());
        gameManager.gameData.setPlayerData(gameState.getPlayerData());
        gameManager.gameData.setPlaceDatas(gameState.getPlaceData());
        gameManager.gameData.setVsync(Shop_Keeper.vSync);

        gameManager.saveData();
    }

    public void saveOptions(){
        gameManager.gameData.setVsync(Shop_Keeper.vSync);

        gameManager.saveData();
    }

    @Override
    public void render (float delta) {

        //int renderCalls = batch.totalRenderCalls;
        update(delta);
        render();

        console.draw();
        //System.out.println(batch.totalRenderCalls - renderCalls);

    }

    @Override
    public void dispose () {
        batch.dispose();
        shapeRenderer.dispose();
        stateManager.peek().dispose();
        console.dispose();
    }

    @Override
    public void resize(final int width, final int height){
        viewport.update(width, height, false);
        stateManager.peek().resize(width, height);
    }

    private void update(float delta){
        if(Gdx.input.isKeyJustPressed(Input.Keys.M)){
            save();
        }

        stateManager.peek().update(delta);
    }

    private void render(){
        clear();

        this.stateManager.peek().render(batch);

        this.stateManager.peek().renderShapes(shapeRenderer);
    }


    private void clear(){
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

}
