/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop_keeper.Entities;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

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
    private Point2D.Double coordinates;
    private double speed;

    public enum Presence{
        HATED, ABSENCE, CUSTOMER, REGULAR;
    }

    public NPC(String name, int texture, int mentality){
        this.name = name;
        this.mood = 0;
        //this.mentality = newRandomMentality();
        this.presence = Presence.CUSTOMER;
        this.texture = texture;
        this.mentality = mentality;
        this.speed = 1;
        this.coordinates = new Point2D.Double();
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

    public boolean moveTo(Point2D.Double target){
        double distance = Math.sqrt(coordinates.distance(target));
        if(this.speed >= distance){
            this.coordinates.setLocation(target);
            return true;
        }else{
            double scalefactor = distance / speed;
            double dx = (target.getX() - this.coordinates.getX()) * scalefactor;
            double dy = (target.getY() - this.coordinates.getY()) * scalefactor;
            this.coordinates.setLocation(this.coordinates.getX() + dx,
                    this.coordinates.getY() + dy);
            return false;
        }
    }

    public void testDraw(Graphics2D g2d){
        Ellipse2D.Double s = new Ellipse2D.Double(this.coordinates.getX() - 5,
                this.coordinates.getY() - 5, 10, 10);
        g2d.draw(s);
    }
}
