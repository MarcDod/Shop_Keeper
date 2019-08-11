/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop_keeper.gameLoop;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import shop_keeper.shop.shopState;
import shop_keeper.states.StateManager;

/**
 *
 * @author Marc
 */
public class GameLoop extends JPanel implements Runnable{

    private static final int FPS = 60;
    public static final int MAX_LOOP_TIME = 1000 / FPS;
    
    private boolean running;
    private StateManager stm;
    
    public GameLoop(){
        init();
        
        Thread gameLoop = new Thread(this);
        gameLoop.start();
    }
    
    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.clearRect(0,0,this.getWidth(), this.getHeight());
        stm.getCurrentState().render(g2d);
    }
    
    @Override
    public void run() {
        long timestamp;
        long oldTimestamp;
        
        while(running){
            oldTimestamp = System.currentTimeMillis();
            update();
            render();
            timestamp = System.currentTimeMillis();
            if(timestamp-oldTimestamp <= MAX_LOOP_TIME) {
                try {
                    Thread.sleep(MAX_LOOP_TIME - (timestamp-oldTimestamp) );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * updates the game 
     */
    private void update(){
        stm.getCurrentState().update();
    }
    
    /**
     * renders the game
     */
    private void render(){
        this.repaint();
    }
    
    private void mousePressedEvent(MouseEvent me){
        stm.getCurrentState().mousePressedEvent(me);
    }
    
    private void keyTypedEvent(KeyEvent ke){
        stm.getCurrentState().keyTypedEvent(ke);
    }
    
    private void init(){
        running = true;
        stm = new StateManager(new shopState());       
        
        //<editor-fold defaultstate="collapsed" desc="Init Listener">
this.addMouseListener(new MouseAdapter() {
    @Override
    public void mousePressed(MouseEvent e) {
        mousePressedEvent(e);
    }
    
});
this.addKeyListener(new KeyAdapter() {
    @Override
    public void keyTyped(KeyEvent e) {
        keyTypedEvent(e);
    }
    
});
//</editor-fold>
    }
}
