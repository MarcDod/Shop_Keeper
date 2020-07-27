package de.marcdoderer.shop_keeper.screen.state;

import java.util.Stack;

public class StateManager {
    private final Stack<State> stateStack;

    public StateManager(final State state){
        this.stateStack = new Stack<State>();
        push(state);
    }

    public State peek(){
        return stateStack.peek();
    }

    public State pop(){
        if(!stateStack.empty()){
            return stateStack.pop();
        }
        return null;
    }

    public void push(final State state){
        this.stateStack.push(state);
    }
}
