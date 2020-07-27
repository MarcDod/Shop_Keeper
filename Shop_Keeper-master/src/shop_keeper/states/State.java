/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop_keeper.states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author Marc
 */
public abstract class State {
    public abstract void update();
    public abstract void render(Graphics g2d);
    public abstract void cleanUp();
    public abstract void revert();   
    public abstract void mousePressedEvent(MouseEvent me);
    public abstract void keyTypedEvent(KeyEvent ke);
}
