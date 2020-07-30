package de.marcdoderer.shop_keeper.screen.state;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import de.marcdoderer.shop_keeper.screen.GameScreen;
import de.marcdoderer.shop_keeper.screen.state.menu.android.AndroidMenuState;
import de.marcdoderer.shop_keeper.screen.state.menu.desktop.DesktopMenuState;

public class MenuManager {

    public static final int MENU_KEY = Input.Keys.ESCAPE;

    private MenuManager(){

    }

    public static State getInGameMenuState(final GameScreen screen){
        if(Gdx.app.getType() == Application.ApplicationType.Desktop){
            return new DesktopMenuState(screen);
        }else{
            return new AndroidMenuState(screen);
        }
    }
}
