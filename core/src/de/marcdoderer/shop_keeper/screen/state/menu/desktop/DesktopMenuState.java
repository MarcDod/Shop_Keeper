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
    public void renderShapes(ShapeRenderer shapeRenderer) {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void mouseClicked(float x, float y) {

    }
}
