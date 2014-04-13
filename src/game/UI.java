package game;

import java.awt.BorderLayout;
import java.awt.Canvas;

import javax.swing.JButton;
import javax.swing.JFrame;



import org.lwjgl.opengl.Display;

public class UI
{
    public UI() 
    {    	
        JFrame frame = new JFrame("Gra");        
        Canvas canvas = new Canvas();
        canvas.setSize(World.SCREEN_W, World.SCREEN_H+20);
       
        try {        	
            Display.setParent(canvas);
        } catch (Exception e) { 
        	System.out.println("Nie uda³o ustawiæ rodzica dla Display, nie pogramy.");
        }

        frame.add(canvas, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
       
        new Game("levels/level_001.xml" , frame);         
    }
     
    public static void main(String[] args) {
        
                new UI();
           
    }


}