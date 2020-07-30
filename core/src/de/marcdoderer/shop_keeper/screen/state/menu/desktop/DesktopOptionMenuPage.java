package de.marcdoderer.shop_keeper.screen.state.menu.desktop;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.marcdoderer.shop_keeper.Shop_Keeper;
import de.marcdoderer.shop_keeper.screen.state.GameState;
import de.marcdoderer.shop_keeper.screen.state.menu.MenuPage;
import de.marcdoderer.shop_keeper.screen.state.menu.MenuState;
import de.marcdoderer.shop_keeper.shop.Shop;

public class DesktopOptionMenuPage extends MenuPage {

    public final TextButton fullScreenButton;
    public final TextButton VSyncButton;
    public final TextButton back;

    public DesktopOptionMenuPage(final float x, final float y, final float width, final float height, final MenuState parent){
        super(x, y, width, height);
        this.parent = parent;
        Skin skin = new Skin(Gdx.files.internal("menu/button/uiskin.json"));
        this.fullScreenButton = new TextButton(getFullScreenText(), skin);
        this.VSyncButton = new TextButton(getVsyncText(), skin);
        this.back = new TextButton("Back", skin);
        fullScreenButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fullScreenAction();
            }
        });
        VSyncButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                vSyncButton();
            }
        });
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                backButton();
            }
        });

        float size = (width * (float)Gdx.graphics.getWidth() / GameState.WIDTH) / 3;
        fullScreenButton.setSize(size, size);
        back.setSize(size, size);
        VSyncButton.setSize(size, size);
        VSyncButton.setPosition(size, 0);
        back.setPosition(2 * size, 0);

        group.addActor(fullScreenButton);
        group.addActor(VSyncButton);
        group.addActor(back);
    }

    private void fullScreenAction(){
        Gdx.graphics.setVSync(false);
        if(Gdx.graphics.isFullscreen()){
            Gdx.graphics.setWindowedMode(GameState.WIDTH * GameState.SCALE, GameState.HEIGHT * GameState.SCALE);
         }else {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        }
        this.fullScreenButton.setText(getFullScreenText());
        Gdx.graphics.setVSync(Shop_Keeper.vSync);
        parent.resize(Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
        Gdx.input.setInputProcessor(parent.getInputProcessor());
    }

    private String getFullScreenText(){
        return (Gdx.graphics.isFullscreen()) ? "FullScreen Off" : "FullScreen ON";
    }

    private String getVsyncText(){
        return (Shop_Keeper.vSync) ? "Vsync Off" : "Vsync ON";
    }

    private void vSyncButton(){
        Shop_Keeper.vSync = !Shop_Keeper.vSync;
        Gdx.graphics.setVSync(Shop_Keeper.vSync);
        this.VSyncButton.setText(getVsyncText());
    }

    private void backButton(){
        parent.loadLastPage();
    }

    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public void dispose() {
        back.getSkin().dispose();
        VSyncButton.getSkin().dispose();
        fullScreenButton.getSkin().dispose();
    }
}
