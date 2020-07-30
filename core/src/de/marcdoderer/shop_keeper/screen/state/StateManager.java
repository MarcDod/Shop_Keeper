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

    /**
     * Requires stateStach.size() > 1;
     * @return state at the top of the stateStack;
     */
    public State pop(){
        if(stateStack.size() > 1 ){
            State state = stateStack.pop();
            state.dispose();
            stateStack.peek().resume();
            return state;
        }
        return null;
    }

    public void push(final State state){
        this.stateStack.push(state);
    }
}
