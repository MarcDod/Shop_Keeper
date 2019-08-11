/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop_keeper.Entities;

/**
 *
 * @author Julian
 */
public class Adventurer extends NPC{
    
    private Rank rank;
    private State state;

    public enum Mentality{
        //@TODO: Fill in Mentalities
    }
    
    public enum Rank{
        E,D,C,B,A,S
    }
    
    public enum State{
        ALIVE,ON_MISSION,WOUNDED,MISSED,DEAD
    }

    public Adventurer(String name, int texture, Rank rank){
        super(name, texture, Mentality.valueOf(generateRandomMentality()).
                ordinal());
        this.state = State.ALIVE;
        this.rank = rank;
    }

    private static String generateRandomMentality(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Rank getRank(){
        return rank;
    }

    public State getState(){
        return state;
    }

    public void setRank(Rank rank){
        this.rank = rank;
    }

    public void setState(State state){
        this.state = state;
    }
}
