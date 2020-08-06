package de.marcdoderer.shop_keeper.screen.hud;

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
import de.marcdoderer.shop_keeper.listener.ZoneListener;
import de.marcdoderer.shop_keeper.movement.InteractiveZone;
import de.marcdoderer.shop_keeper.screen.state.GameState;
import de.marcdoderer.shop_keeper.util.ItemDragger;
import de.marcdoderer.shop_keeper.util.SpriteDragger;

import java.util.HashMap;
import java.util.Map;


public class InventoryHud implements HudElement {

    public static final float WIDTH = GameState.WIDTH * 0.4f;
    public static final float HEIGHT = GameState.HEIGHT * 0.4f;


    private boolean visible;

    // The background of the inventory
    private final Texture inventoryBackground;

    // the Entity that stores the items //TODO: change Chest to ItemStorage
    private final Chest chest;

    private final GameState gameState;

    //the bottom right position of this inventory
    private final float x,y;

    // the width a item that gets rendered in the inventory
    private final float itemWidth;
    // the height a item that gets rendered in the inventory
    private final float itemHeight;

    // the number of rows the inventory has.
    private final int itemRows;
    // the number of column the inventory has.
    private final int itemCols;

    // keeps track of all sprites in the ItemStorage and its position.
    private Map<Integer, Sprite> itemSprites;

    // to move the item while dragging
    private ItemDragger itemDragger;

    /**
     * This boolean is needed because if the Player is standing on the eventZone of this ItemStorage entity and
     * tried to open it he would immediately close it again.
     *
     * To prevent that this boolean keeps track weather the Player did just try to open the inventory.
     */
    private boolean openedWithThisClick;

    /**
     * creates the Inventory at the bottom of the ItemStorage entity.
     *
     * @param gameState the gameState
     * @param chest the ItemStorage that got opened.
     */
    public InventoryHud(final GameState gameState, final Chest chest){
        this.chest = chest;
        this.gameState = gameState;
        inventoryBackground = gameState.screen.assetManager.get("menu/menu.png");
        visible = true;
        Vector2 topCenter = gameState.getPlayerZone().getCenter();
        x = topCenter.x - WIDTH / 2f;
        y = topCenter.y - HEIGHT;
        this.itemRows = 5;
        this.itemCols = 3;
        this.itemWidth = WIDTH / itemRows;
        this.itemHeight = HEIGHT / itemCols;
        this.itemDragger = null;
        updateSprites();
    }

    /**
     * should be used if the Player was right beside the itemStorage entity while clicking.
     */
    public void opendWithThisClick(){
        openedWithThisClick = true;
    }


    @Override
    public void render(SpriteBatch batch) {
        if(!visible) return;
        batch.setProjectionMatrix(this.gameState.gameCamera.combined);
        batch.draw(inventoryBackground, x, y, WIDTH, HEIGHT);

        for(int row = 0; row < itemRows; row++){
            for(int col = 0; col < itemCols; col++){
                int index = row + col * itemRows;
                if(itemSprites.containsKey(index)) {
                    Sprite itemSprite = itemSprites.get(index);
                    batch.draw(itemSprite, x + row * itemWidth, y + HEIGHT - itemHeight - ( col * itemHeight), itemWidth, itemHeight);
                }
            }
        }

        if(itemDragger != null){
            batch.draw(itemDragger.getDraggedSprite(), itemDragger.getPosX() - itemWidth / 2f, itemDragger.getPosY() - itemHeight / 2f, itemWidth, itemHeight);
        }
    }

    public void drawGrid(ShapeRenderer shapeRenderer){
        for(int row = 0; row < itemRows; row++){
            for(int col = 0; col < itemCols; col++){
                shapeRenderer.rect(x + row * itemWidth, y + col * itemHeight, itemWidth, itemHeight);
            }
        }
    }


    @Override
    public void update(float delta) {
        if(itemDragger != null){
            itemDragger.update(delta);
        }
    }

    /**
     * loads all Items from the itemStorage in the itemSprite hashMap.
     */
    private void updateSprites(){
        this.itemSprites = new HashMap<Integer, Sprite>();

        for(int pos : chest.getItemDatas().keySet()){
            this.itemSprites.put(pos, ItemFactory.getItemRegistry().getSpriteByItemID(chest.getItemDatas().get(pos).itemID));
        }
    }

    /**
     * closes the Inventory if x and y position is not in the border of this inventory.
     * @param x mouse x position relative to the game.
     * @param y mouse y position relative to the game.
     */
    public void clickEvent(final float x, final float y){
        if(openedWithThisClick) return;
        if(!new Rectangle(this.x, this.y, WIDTH, HEIGHT).contains(new Vector2(x, y))){
            this.gameState.closeInventar();
        }
    }

    @Override
    public void dispose() {

    }

    /**
     *
     * If the x and y position are not in the boundaries of the inventory, the inventory will be closed.
     *
     * if the x and y position is above an item and the item is the same item that the player is dragging the items will be stacked.
     * else the item will be placed at its origin position.
     *
     * if the x and y position is above a free place in the inventory the dragged item will be places there.
     *
     * Ensures spriteDragger = false.
     * Ensures openedWithThisClick = false.
     * @param x mouse x position relative to the game
     * @param y mouse y position relative to the game
     */
    public void mouseReleased(final float x, final float y){
        if(openedWithThisClick){
            openedWithThisClick = false;
            return;
        }
        if(new Rectangle(this.x, this.y, WIDTH, HEIGHT).contains(new Vector2(x, y))){
            int index = getClickedIndex(x, y);
            if(itemSprites.containsKey(index)) {
                if(itemDragger != null){
                    final int draggedItemIndex = getClickedIndex(itemDragger.originPosX, itemDragger.originPosY);
                    if(itemDragger.getItemID().equals(chest.getItemDatas().get(index).itemID)){
                        takeItem(index);
                        putItemBackInChest(index);
                    }else{
                        putItemBackInChest(draggedItemIndex);
                    }
                }else {
                    takeItem(index);
                }
            }else{
                if(gameState.player.getCarriedItem() != null && (itemDragger == null || itemDragger.getTimeDraggedInSeconds() > 0.2)){
                    putItemBackInChest(index);
                }
            }
        }else if(itemDragger != null){
            this.gameState.closeInventar();
        }
        itemDragger = null;
        openedWithThisClick = false;
    }

    /**
     * puts a item in the index position in to the itemStorage
     * calls updateSprite();
     * @param index the position in which the item should be placed.
     */
    private void putItemBackInChest(final int index){
        Item item = gameState.player.getCarriedItem();
        gameState.getCurrentPlace().removeEntity(EntityManager.Layer.ITEM_LAYER, item);
        gameState.player.removeCarriedItem();
        item.dispose();
        chest.carryItem(item, index);
        updateSprites();
    }

    /**
     * Requires chest.getItemDatas.contains(index) == true;
     * takes the item that is on index position in to the hand of the Player
     * @param index the position of the item that the player should take.
     */
    private void takeItem(final int index){
        chest.selectItem(index);
        gameState.takeItemListener.perform((InteractiveZone) gameState.getPlayerZone(), ZoneListener.PLAYER_CLICKED_AGAIN, gameState.player);
        updateSprites();
    }

    /**
     * calculates the 2 dimensional mouse position to a 1 dimensional index.
     * @param x the x mouse position
     * @param y the y mouse position
     * @return the clicked index in the inventory
     */
    private int getClickedIndex(final float x, final float y){
        return (int)((x - WIDTH) / itemWidth) + ((itemCols - 1) -  ((int)((y - HEIGHT) / itemHeight))) * itemRows;
    }

    /**
     * if the itemDragger does not exist a new itemDragger will be created with the selected item
     * else the itemDragger will be updated;
     *
     * @param x the mouse x position
     * @param y the mouse y position
     */
    public void mouseDragged(final float x, final float y){
        if(openedWithThisClick) return;
        if(itemDragger == null){
            int index = getClickedIndex(x, y);
            if(itemSprites.containsKey(index)){
                itemDragger = new ItemDragger(itemSprites.get(index), x, y, chest.getItemDatas().get(index).itemID);
                takeItem(index);
                itemDragger.mouseDragged(x, y);
            }
        }else {
            itemDragger.mouseDragged(x, y);
        }

    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}
