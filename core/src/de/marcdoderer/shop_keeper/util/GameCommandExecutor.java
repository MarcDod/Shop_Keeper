package de.marcdoderer.shop_keeper.util;

import com.strongjoshua.console.CommandExecutor;
import com.strongjoshua.console.Console;
import com.strongjoshua.console.LogLevel;
import de.marcdoderer.shop_keeper.screen.GameScreen;
import de.marcdoderer.shop_keeper.screen.state.GameState;

public class GameCommandExecutor extends CommandExecutor {
    private final Console console;
    private final GameState gameState;
    public GameCommandExecutor(final Console console, final GameState gameState){
        this.console = console;
        this.gameState = gameState;
    }

    public final void say(String t){
        console.log(t , LogLevel.SUCCESS);
    }

    /**
     * this operation is to change the time
     * @param time day or night
     */
    public final void time(String time){
        switch(time){
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
     *
     * ensures gameState.dayNightCircle.seconds == time
     * @param time time in seconds
     */
    public final void time(float time){
        if(time <= 86400 && time >= 0){
            gameState.dayNightCircle.setSeconds(time);
            console.log("time changed to " + time + " seconds", LogLevel.SUCCESS);
            return;
        }
        console.log("time could not be changed", LogLevel.ERROR);
    }
}
