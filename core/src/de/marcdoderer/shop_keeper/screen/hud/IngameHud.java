package de.marcdoderer.shop_keeper.screen.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashSet;
import java.util.Set;

public class IngameHud {
    private final Set<HudElement> hudElements;
    private final OrthographicCamera hudCamera;

    public IngameHud(){
        this.hudElements = new HashSet<HudElement>();
        this.hudCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.hudCamera.translate(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        this.hudCamera.update();
    }

    public void resize(final float width, final float height){
        hudCamera.setToOrtho(false, width, height);
        hudCamera.update();
    }

    public void addHudElement(final HudElement hudElement){
        this.hudElements.add(hudElement);
    }

    public void removeHudElement(final HudElement hudElement){
        this.hudElements.remove(hudElement);
    }

    public void render(final SpriteBatch batch){
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        for(HudElement hudElement: hudElements){
            hudElement.render(batch);
        }
        batch.end();
    }

    public void update(final float delta){
        for(HudElement hudElement : hudElements){
            hudElement.update(delta);
        }
    }

    public void dispose(){
        for(HudElement hudElement : hudElements){
            hudElement.dispose();
        }
    }

    public void setVisible(final boolean visible){
        for(HudElement hudElement : hudElements){
            hudElement.setVisible(visible);
        }
    }
}
