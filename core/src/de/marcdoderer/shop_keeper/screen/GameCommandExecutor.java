package de.marcdoderer.shop_keeper.screen;

import com.strongjoshua.console.CommandExecutor;
import com.strongjoshua.console.Console;
import com.strongjoshua.console.LogLevel;

public class GameCommandExecutor extends CommandExecutor {
    private final Console console;
    public GameCommandExecutor(final Console console){
        this.console = console;
    }

    public void say(String t){
        console.log(t , LogLevel.SUCCESS);
    }
}
