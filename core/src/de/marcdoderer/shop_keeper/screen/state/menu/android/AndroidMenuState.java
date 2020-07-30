package de.marcdoderer.shop_keeper.screen.state.menu.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import de.marcdoderer.shop_keeper.screen.GameScreen;
import de.marcdoderer.shop_keeper.screen.state.GameState;
import de.marcdoderer.shop_keeper.screen.state.MenuManager;
import de.marcdoderer.shop_keeper.screen.state.menu.MenuState;
import de.marcdoderer.shop_keeper.screen.state.menu.desktop.DesktopMenuPage;

import static de.marcdoderer.shop_keeper.screen.state.MenuManager.MENU_KEY;

public class AndroidMenuState extends MenuState {

    public AndroidMenuState(final GameScreen screen){
        super(screen);

        Gdx.input.setInputProcessor(getInputProcessor());

        menuPages.add(new AndroidMenuPage(MenuState.MENU_X, MenuState.MENU_Y, MenuState.WIDTH, MenuState.HEIGHT, this));
        stage.addActor(menuPages.get(currentPage).getGroup());
        stage.getCamera().project(new Vector3(GameState.WIDTH, GameState.HEIGHT, 0));
        stage.getCamera().update();
    }


    @Override
    public void renderShapes(ShapeRenderer shapeRenderer) {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void mouseClicked(float x, float y) {

    }


    @Override
    public OrthographicCamera getCamera() {
        return this.camera;
    }

}
