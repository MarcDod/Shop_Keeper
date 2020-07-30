package de.marcdoderer.shop_keeper.screen.state.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import de.marcdoderer.shop_keeper.screen.state.GameState;
import de.marcdoderer.shop_keeper.screen.state.menu.desktop.DesktopMenuPage;

public abstract class MenuPage {
    protected MenuState parent;
    protected Group group;

    public MenuPage(final float x, final float y, final float width, final float height, final MenuState parent){
        this.group = new Group();
        this.parent = parent;
        final float scale = (float)Gdx.graphics.getWidth() / GameState.WIDTH;
        group.setBounds(x *  scale, y * scale, width * scale, height * scale);
    }

    abstract public Group getGroup();
    abstract public void dispose();
}
