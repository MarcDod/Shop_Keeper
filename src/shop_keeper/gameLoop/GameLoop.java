/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop_keeper.gameLoop;

import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Marc
 */
public class GameLoop extends JPanel implements Runnable{

    private static final int FPS = 60;
    public static final int MAX_LOOP_TIME = 1000 / FPS;
    
    private boolean running;
    
    public GameLoop(){
        init();
        
        Thread gameLoop = new Thread(this);
        gameLoop.start();
    }
    
    @Override
    public void paintComponent(Graphics g){
        
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
        
    }
    
    /**
     * renders the game
     */
    private void render(){
        this.repaint();
    }
    
    
    private void init(){
        running = true;
    }
}
