package de.marcdoderer.shop_keeper.command;

public abstract interface Command {
    void execute();
    boolean isFinished();
    void requestCancel();
}
