package de.marcdoderer.shop_keeper.screen.state.menu.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.marcdoderer.shop_keeper.screen.GameScreen;
import de.marcdoderer.shop_keeper.screen.state.GameState;
import de.marcdoderer.shop_keeper.screen.state.menu.MenuPage;
import de.marcdoderer.shop_keeper.screen.state.menu.MenuState;

public class DesktopMenuPage extends MenuPage {


    private final TextButton optionButton;
    private final TextButton resumeButton;

    public DesktopMenuPage(final float x, final float y, final float width, final float height, final MenuState parent){
        super(x, y, width, height);

        this.parent = parent;
        Skin skin = new Skin(Gdx.files.internal("menu/button/uiskin.json"));
        this.optionButton = new TextButton("Options", skin);
        this.resumeButton = new TextButton("Resume", skin);
        optionButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                optionAction();
            }
        });
        resumeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resumeAction();
            }
        });
        float size = (width * (float)Gdx.graphics.getWidth() / GameState.WIDTH) / 2;
        optionButton.setSize(size, size);
        resumeButton.setSize(size, size);
        resumeButton.setPosition(size, 0);

        group.addActor(optionButton);
        group.addActor(resumeButton);
    }

    private void optionAction(){
        parent.loadNextPage();
    }

    private void resumeAction(){
        parent.screen.stateManager.pop();
    }

    @Override
    public Group getGroup() {
        return this.group;
    }

    @Override
    public void dispose() {
        optionButton.getSkin().dispose();
        resumeButton.getSkin().dispose();
    }
}
