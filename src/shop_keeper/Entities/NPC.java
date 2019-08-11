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
public abstract class NPC{
    private final String name;
    private int mood;
    protected final int mentality;
    protected Presence presence;
    private final int texture;
    
    public enum Presence {
        HATED,ABSENCE,CUSTOMER,REGULAR;
    }
    
    public NPC(String name, int texture, int mentality){
        this.name = name;
        this.mood = 0;
        //this.mentality = newRandomMentality();
        this.presence = Presence.CUSTOMER;
        this.texture = texture;
        this.mentality = mentality;
    }

    public String getName(){
        return name;
    }

    public int getMood(){
        return mood;
    }

    public Presence getPresence(){
        return presence;
    }

    public void setMood(int mood){
        this.mood = mood;
    }
}
