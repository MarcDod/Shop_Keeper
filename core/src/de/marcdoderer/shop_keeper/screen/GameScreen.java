package de.marcdoderer.shop_keeper.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.strongjoshua.console.Console;
import com.strongjoshua.console.GUIConsole;
import de.marcdoderer.shop_keeper.animation.IdleAnimation;
import de.marcdoderer.shop_keeper.animation.MoveAnimation;
import de.marcdoderer.shop_keeper.entities.EntityManager;
import de.marcdoderer.shop_keeper.entities.Player;
import de.marcdoderer.shop_keeper.entities.items.Item;
import de.marcdoderer.shop_keeper.entities.items.ItemFactory;
import de.marcdoderer.shop_keeper.listener.ExitZoneListener;
import de.marcdoderer.shop_keeper.listener.TradeItemListener;
import de.marcdoderer.shop_keeper.manager.GameData;
import de.marcdoderer.shop_keeper.manager.GameManager;
import de.marcdoderer.shop_keeper.manager.ItemData;
import de.marcdoderer.shop_keeper.manager.PlaceData;
import de.marcdoderer.shop_keeper.movement.PlayerController;
import de.marcdoderer.shop_keeper.screen.state.GameState;
import de.marcdoderer.shop_keeper.screen.state.StateManager;
import de.marcdoderer.shop_keeper.shop.Basement;
import de.marcdoderer.shop_keeper.shop.Basement2;
import de.marcdoderer.shop_keeper.shop.Place;
import de.marcdoderer.shop_keeper.shop.Shop;
import de.marcdoderer.shop_keeper.shop.time.DayNightCircle;
import de.marcdoderer.shop_keeper.util.FrameRate;

import java.util.LinkedList;

public class GameScreen extends ScreenAdapter {


    private final SpriteBatch batch;
    private final ShapeRenderer shapeRenderer;

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
        this.assetManager = assetManager;
        this.gameState = new GameState(this);
        this.stateManager = new StateManager(gameState);
        this.viewport = new StretchViewport(GameState.WIDTH, GameState.HEIGHT, stateManager.peek().getCamera());
        this.console = new GUIConsole();
        this.console.setSize(600, 500);
        this.console.setPosition(50, 50);
        this.console.setVisible(false);
        this.console.setDisplayKeyID(Input.Keys.UP);
        this.console.setCommandExecutor(new GameCommandExecutor(console));
        this.console.setDisabled(false);

        Gdx.input.setInputProcessor(new InputAdapter(){

            @Override
            public boolean keyDown(int keycode) {
                keyClicked(keycode);
                return true;
            }

            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (button == Input.Buttons.LEFT) {
                    mouseClicked(screenX, screenY);
                    return true;
                }
                return false;
            }
         });

        this.console.resetInputProcessing();
    }


    private void mouseClicked(final int screenX, final int screenY){
        final Vector2 mouseClick = viewport.unproject(new Vector2(screenX, screenY));
        this.stateManager.peek().mouseClicked(mouseClick.x, mouseClick.y);

        //TEST
        if(mouseClick.x < 1 && mouseClick.y < 1){
            save();
        }
    }

    private void keyClicked(final int keyCode){
        this.stateManager.peek().keyPressed(keyCode);
    }

    public void save(){

        gameManager.gameData = new GameData();
        gameManager.gameData.setTimeInSeconds(gameState.dayNightCircle.getSeconds());
        gameManager.gameData.setPlayerData(gameState.getPlayerData());
        gameManager.gameData.setPlaceDatas(gameState.getPlaceData());

        gameManager.saveData();
    }

    @Override
    public void render (float delta) {

        update(delta);
        render();

        console.draw();
        //System.out.println(batch.renderCalls);

    }

    @Override
    public void dispose () {
        batch.dispose();
        shapeRenderer.dispose();
    }

    @Override
    public void resize(final int width, final int height){
        viewport.update(width, height, false);
        stateManager.peek().resize(width, height);
    }

    private void update(float delta){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            if(Gdx.graphics.isFullscreen()){
                Gdx.graphics.setVSync(false);
                Gdx.graphics.setWindowedMode(GameState.WIDTH * GameState.SCALE, GameState.HEIGHT * GameState.SCALE);
            }else{
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
            Gdx.graphics.setVSync(true);
        }else if(Gdx.input.isKeyJustPressed(Input.Keys.M)){
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
