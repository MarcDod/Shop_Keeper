package de.marcdoderer.shop_keeper.util;

import com.strongjoshua.console.CommandExecutor;
import com.strongjoshua.console.Console;
import com.strongjoshua.console.LogLevel;
import de.marcdoderer.shop_keeper.entities.EntityManager;
import de.marcdoderer.shop_keeper.entities.MovableEntity;
import de.marcdoderer.shop_keeper.entities.items.Item;
import de.marcdoderer.shop_keeper.entities.items.ItemFactory;
import de.marcdoderer.shop_keeper.screen.GameScreen;
import de.marcdoderer.shop_keeper.screen.state.GameState;

public class GameCommandExecutor extends CommandExecutor {
    private final Console console;
    private final GameState gameState;

    public GameCommandExecutor(final Console console, final GameState gameState) {
        this.console = console;
        this.gameState = gameState;
    }

    public final void say(String t) {
        console.log(t, LogLevel.SUCCESS);
    }

    /**
     * this operation is to change the time
     *
     * @param time day or night
     */
    public final void time(String time) {
        switch (time) {
            case "day":
                gameState.dayNightCircle.setSeconds(28800);
                console.log("Time set day", LogLevel.SUCCESS);
                break;
            case "night":
                gameState.dayNightCircle.setSeconds(79200);
                console.log("Time set night", LogLevel.SUCCESS);
                break;
            default:
                console.log("there is no such time", LogLevel.ERROR);
        }
    }

    /**
     * Requires time <= 86400;
     * Requires time >= 0;
     * this operation is to change the time
     * <p>
     * ensures gameState.dayNightCircle.seconds == time
     *
     * @param time time in seconds
     */
    public final void time(float time) {
        if (time <= 86400 && time >= 0) {
            gameState.dayNightCircle.setSeconds(time);
            console.log("time changed to " + time + " seconds", LogLevel.SUCCESS);
            return;
        }
        console.log("time could not be changed", LogLevel.ERROR);
    }

    /**
     * calls getItem(itemID, 1);
     * @param itemID the ID of the item that should be created.
     */
    public final void getItem(final String itemID){
        getItem(itemID, 1);
    }

    /**
     * Requires count >= 1;
     *
     * creates a Item from nothing
     *
     * calls removeItem();
     * creates a Item by the itemID.
     * gives the Item to the Player to carry.
     * gives the Item to the place the Player is currently in.
     *
     * @param itemID the ID of the item that should be created.
     * @param count the amount of items that should be created.
     */
    public final void getItem(final String itemID, final int count){
        if(count < 1) {
            console.log("invalid number of items " + itemID, LogLevel.ERROR);
            return;
        }
        removeItem();
        Item item = ItemFactory.getItemRegistry().createItemByID(itemID);
        item.setStackCount(count);
        this.gameState.player.carryItem(item);
        this.gameState.getCurrentPlace().addEntity(EntityManager.Layer.ITEM_LAYER, this.gameState.player.getCarriedItem());

        console.log("Successful created " + itemID, LogLevel.SUCCESS);
    }

    /**
     * Removes the Item the player is holding at the moment.
     * If the player not holding anything the method will do nothing;
     *
     * removes the item from the Player.
     * disposes the item
     * removes the item from the Player currentPlace
     */
    public final void removeItem(){
        final Item oldItem = this.gameState.player.getCarriedItem();
        if (oldItem != null) {
            this.gameState.player.removeCarriedItem();
            oldItem.dispose();
            this.gameState.getPlace(this.gameState.player.getCurrentPlaceID()).removeEntity(EntityManager.Layer.ITEM_LAYER, oldItem);
            console.log("Successful remove " + oldItem.id, LogLevel.SUCCESS);

        }
    }

    public final void enableDebug(boolean enabled) {
        this.gameState.debugOn = enabled;
        console.log(((enabled) ? "Enabled" : "Disabled") + " Debug", LogLevel.SUCCESS);
    }

    public final void toggleDebug(String type) {
        switch (type) {
            case "hitbox":
                gameState.debugHitbox ^= true;//toggle code
                console.log(((gameState.debugHitbox) ? "Enabled" : "Disabled") + " Debug hitbox", LogLevel.SUCCESS);
                break;
            case "grid":
                gameState.debugGrid ^= true;//toggle code
                console.log(((gameState.debugGrid) ? "Enabled" : "Disabled") + " Debug grid", LogLevel.SUCCESS);
                break;
            case "path":
                gameState.debugPath ^= true;//toggle code
                console.log(((gameState.debugPath) ? "Enabled" : "Disabled") + " Debug path", LogLevel.SUCCESS);
                break;
            default:
                console.log("there is no such debug type", LogLevel.ERROR);
        }
    }
}
