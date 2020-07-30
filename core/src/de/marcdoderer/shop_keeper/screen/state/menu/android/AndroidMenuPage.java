package de.marcdoderer.shop_keeper.screen.state.menu.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.marcdoderer.shop_keeper.screen.state.GameState;
import de.marcdoderer.shop_keeper.screen.state.menu.MenuPage;
import de.marcdoderer.shop_keeper.screen.state.menu.MenuState;


public class AndroidMenuPage extends MenuPage {


    private final TextButton resumeButton;

    public AndroidMenuPage(float x, float y, float width, float height, MenuState parent) {
        super(x, y, width, height);
        this.parent = parent;
        Skin skin = new Skin(Gdx.files.internal("menu/button/uiskin.json"));
        this.resumeButton = new TextButton("Resume", skin);
        resumeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resumeAction();
            }
        });
        float size = (width * (float)Gdx.graphics.getWidth() / GameState.WIDTH);
        resumeButton.setSize(size, size);

        group.addActor(resumeButton);
    }

    private void resumeAction(){
        this.parent.screen.stateManager.pop();
    }

    @Override
    public Group getGroup() {
        return this.group;
    }

    @Override
    public void dispose() {
        this.resumeButton.getSkin().dispose();
    }
}
