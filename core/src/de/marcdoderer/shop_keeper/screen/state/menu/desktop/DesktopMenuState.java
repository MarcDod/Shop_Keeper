package de.marcdoderer.shop_keeper.screen.state.menu.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import de.marcdoderer.shop_keeper.screen.GameScreen;
import de.marcdoderer.shop_keeper.screen.state.GameState;
import de.marcdoderer.shop_keeper.screen.state.MenuManager;
import de.marcdoderer.shop_keeper.screen.state.State;
import de.marcdoderer.shop_keeper.screen.state.menu.MenuPage;
import de.marcdoderer.shop_keeper.screen.state.menu.MenuState;

import java.util.LinkedList;
import java.util.List;

public class DesktopMenuState extends MenuState {

    public DesktopMenuState(final GameScreen screen){
        super(screen);

        Gdx.input.setInputProcessor(getInputProcessor());

        menuPages.add(new DesktopMenuPage(MenuState.MENU_X, MenuState.MENU_Y, MenuState.WIDTH, MenuState.HEIGHT, this));
        menuPages.add(new DesktopOptionMenuPage(MenuState.MENU_X, MenuState.MENU_Y, MenuState.WIDTH, MenuState.HEIGHT, this));
        stage.addActor(menuPages.get(currentPage).getGroup());
        stage.getCamera().project(new Vector3(GameState.WIDTH, GameState.HEIGHT, 0));
        stage.getCamera().update();
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
    public void renderShapes(ShapeRenderer shapeRenderer) {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void keyPressed(int keyCode) {
        if(keyCode == MenuManager.MENU_KEY){
            this.screen.stateManager.pop();
        }
    }

    @Override
    public void mouseClicked(float x, float y) {

    }

    @Override
    public void resume() {
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        originInputProcessor = Gdx.input.getInputProcessor();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(originInputProcessor);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }
}
