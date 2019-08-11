/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop_keeper.gui;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import shop_keeper.gameLoop.GameLoop;

/**
 *
 * @author Marc
 */
public class Window {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;
    
    private JFrame gameFrame;
    private GameLoop gamePanel;
    
    public Window(){
        this.gameFrame = initGameFrame();
        this.gamePanel = initGamePanel();
        
        
        this.gameFrame.add(gamePanel);
        this.gameFrame.pack();
        
        
        this.gamePanel.requestFocus();
    }
    
    private JFrame initGameFrame(){
        JFrame frame = new JFrame();
        Dimension dim = new Dimension(Window.WIDTH, Window.HEIGHT);
        frame.setSize(dim);
        frame.setMaximumSize(dim);
        frame.setMinimumSize(dim);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        
        return frame;
    }

    private GameLoop initGamePanel() {
        GameLoop panel = new GameLoop();
        return panel;
    }
    
}
