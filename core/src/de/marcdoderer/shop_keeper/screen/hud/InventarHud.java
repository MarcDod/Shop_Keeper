package de.marcdoderer.shop_keeper.screen.hud;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import de.marcdoderer.shop_keeper.entities.items.ItemFactory;
import de.marcdoderer.shop_keeper.entities.specialEntity.Chest;
import de.marcdoderer.shop_keeper.movement.InteractiveZone;
import de.marcdoderer.shop_keeper.screen.state.GameState;

public class InventarHud implements HudElement {

    public static final float WIDTH = GameState.WIDTH * 0.4f;
    public static final float HEIGHT = GameState.HEIGHT * 0.4f;


    private boolean visible;
    private final Texture chestBackground;

    private final Chest chest;
    private final OrthographicCamera gameCamera;
    private final GameState gameState;
    private final float x,y;

    private boolean opendWithThisClick;
    /**
     *
     * @param gameState gamestate
     * @param chest opend chest
     * @param gameCamera gameCamera
     */
    public InventarHud(final GameState gameState, final Chest chest, final OrthographicCamera gameCamera){
        this.chest = chest;
        this.gameCamera = gameCamera;
        this.gameState = gameState;
        chestBackground = gameState.screen.assetManager.get("menu/menu.png");
        visible = true;
        Vector2 topCenter = gameState.getPlayerZone().getCenter();
        x = topCenter.x - WIDTH / 2f;
        y = topCenter.y - HEIGHT;
        opendWithThisClick = true;
    }


    @Override
    public void render(SpriteBatch batch) {
        if(!visible) return;
        batch.setProjectionMatrix(gameCamera.combined);
        batch.draw(chestBackground, x, y, WIDTH, HEIGHT);
    }

    @Override
    public void update(float delta) {
        opendWithThisClick = false;
    }

    public void clickEvent(final float x, final float y){
        if(opendWithThisClick) return;
        if(new Rectangle(this.x, this.y, WIDTH, HEIGHT).contains(new Vector2(x, y))){
            //TODO: manage inventar
            chest.selectItem(0);
            gameState.tradeItemListener.perform((InteractiveZone) gameState.getPlayerZone(), 2);
        }else{
            this.gameState.closeInventar();
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}
