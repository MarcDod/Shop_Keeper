/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop_keeper.states;

import java.util.Stack;

/**
 *
 * @author Marc
 */
public class StateManager {
    Stack<State> stateStack;
    
    public StateManager(State startState){
        this.stateStack = new Stack<>();
        this.stateStack.push(startState);
    }
    
    /**
     * 
     * @return the state on top of the stack 
     */
    public State getCurrentState(){
        if(stateStack.empty())
            throw new IndexOutOfBoundsException("There is no state");
        return stateStack.peek();
    }
    
    public void pushState(State state){
        if(!stateStack.empty())
            stateStack.peek().cleanUp();
        stateStack.push(state);
    }   
    
    public void popState(){
        stateStack.pop();
        if(stateStack.empty())
            stateStack.peek().revert();
    }
}
