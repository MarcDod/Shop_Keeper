package de.marcdoderer.shop_keeper.screen.hud;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import de.marcdoderer.shop_keeper.entities.EntityManager;
import de.marcdoderer.shop_keeper.entities.items.Item;
import de.marcdoderer.shop_keeper.entities.items.ItemFactory;
import de.marcdoderer.shop_keeper.entities.specialEntity.Chest;
import de.marcdoderer.shop_keeper.movement.InteractiveZone;
import de.marcdoderer.shop_keeper.screen.state.GameState;
import de.marcdoderer.shop_keeper.util.SpriteDragger;

import java.util.HashMap;
import java.util.Map;

public class InventoryHud implements HudElement {

    public static final float WIDTH = GameState.WIDTH * 0.4f;
    public static final float HEIGHT = GameState.HEIGHT * 0.4f;


    private boolean visible;
    private final Texture chestBackground;

    private final Chest chest;
    private final OrthographicCamera gameCamera;
    private final GameState gameState;
    private final float x,y;

    private final float itemWitdh;
    private final float itemHeight;
    private final int itemRows;
    private final int itemCols;
    private Map<Integer, Sprite> itemSprites;

    private SpriteDragger spriteDragger;

    private boolean opendWithThisClick;
    /**
     *
     * @param gameState gamestate
     * @param chest opend chest
     * @param gameCamera gameCamera
     */
    public InventoryHud(final GameState gameState, final Chest chest, final OrthographicCamera gameCamera){
        this.chest = chest;
        this.gameCamera = gameCamera;
        this.gameState = gameState;
        chestBackground = gameState.screen.assetManager.get("menu/menu.png");
        visible = true;
        Vector2 topCenter = gameState.getPlayerZone().getCenter();
        x = topCenter.x - WIDTH / 2f;
        y = topCenter.y - HEIGHT;
        this.itemRows = 5;
        this.itemCols = 3;
        this.itemWitdh = WIDTH / itemRows;
        this.itemHeight = HEIGHT / itemCols;
        this.spriteDragger = null;
        updateSprites();
    }

    public void opendWithThisClick(){
        opendWithThisClick = true;
    }

    @Override
    public void render(SpriteBatch batch) {
        if(!visible) return;
        batch.setProjectionMatrix(gameCamera.combined);
        batch.draw(chestBackground, x, y, WIDTH, HEIGHT);

        for(int row = 0; row < itemRows; row++){
            for(int col = 0; col < itemCols; col++){
                int index = row + col * itemRows;
                if(itemSprites.containsKey(index)) {
                    Sprite itemSprite = itemSprites.get(index);
                    batch.draw(itemSprite, x + row * itemWitdh, y + HEIGHT - itemHeight - ( col * itemHeight), itemWitdh, itemHeight);
                }
            }
        }

        if(spriteDragger != null){
            batch.draw(spriteDragger.getDraggedSprite(), spriteDragger.getPosX() - itemWitdh / 2f, spriteDragger.getPosY() - itemHeight / 2f, itemWitdh, itemHeight);
        }
    }

    public void drawGrid(ShapeRenderer shapeRenderer){
        for(int row = 0; row < itemRows; row++){
            for(int col = 0; col < itemCols; col++){
                shapeRenderer.rect(x + row * itemWitdh, y + col * itemHeight, itemWitdh, itemHeight);
            }
        }
    }

    @Override
    public void update(float delta) {
    }

    private void updateSprites(){
        this.itemSprites = new HashMap<Integer, Sprite>();

        for(int pos : chest.getItemDatas().keySet()){
            this.itemSprites.put(pos, ItemFactory.getItemRegistry().getSpriteByItemID(chest.getItemDatas().get(pos).itemID));
        }
    }

    public void clickEvent(final float x, final float y){
        if(opendWithThisClick) return;
        if(!new Rectangle(this.x, this.y, WIDTH, HEIGHT).contains(new Vector2(x, y))){
            this.gameState.closeInventar();
        }
    }

    @Override
    public void dispose() {

    }

    public void mouseReleased(final float x, final float y){
        if(opendWithThisClick){
            opendWithThisClick = false;
            return;
        }
        if(new Rectangle(this.x, this.y, WIDTH, HEIGHT).contains(new Vector2(x, y))){
            int index = getClickedIndex(x, y);
            if(itemSprites.containsKey(index)) {
                if(spriteDragger!= null){
                    index = getClickedIndex(spriteDragger.originPosX, spriteDragger.originPosY);
                    putItem(index);
                }else {
                    dropItem(index);
                }
            }else{
                if(gameState.player.getCarriedItem() != null){
                    putItem(index);
                }
            }
        }else if(spriteDragger != null){
            this.gameState.closeInventar();
        }
        spriteDragger = null;
        opendWithThisClick = false;
    }

    private void putItem(final int index){
        Item item = gameState.player.getCarriedItem();
        gameState.getCurrentPlace().removeEntity(EntityManager.Layer.ITEM_LAYER, item);
        gameState.player.removeCarriedItem();
        item.dispose();
        chest.carryItem(item, index);
        updateSprites();
    }

    private void dropItem(final int index){
        chest.selectItem(index);
        gameState.takeItemListener.perform((InteractiveZone) gameState.getPlayerZone(), 2);
        updateSprites();
    }

    private int getClickedIndex(final float x, final float y){
        return (int)((x - WIDTH) / itemWitdh) + ((itemCols - 1) -  ((int)((y - HEIGHT) / itemHeight))) * itemRows;
    }

    public void mouseDragged(final float x, final float y){
        if(opendWithThisClick) return;
        if(spriteDragger == null){
            int index = getClickedIndex(x, y);
            if(itemSprites.containsKey(index)){
                spriteDragger = new SpriteDragger(itemSprites.get(index), x, y);
                dropItem(index);
                spriteDragger.mouseDragged(x, y);
            }
        }else {
            spriteDragger.mouseDragged(x, y);
        }

    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}
