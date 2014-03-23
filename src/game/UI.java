package game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.lwjgl.opengl.Display;

public class UI
{
  //  private Menu game;
	public static int time;
    public UI() 
    {
    	
        JFrame frame = new JFrame();        
        JLabel timeLabel = new JLabel(Integer.toString(time));
        JButton button = new JButton("ClickMe");
        
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ++time;
            }
        });    
        Canvas canvas = new Canvas();
        canvas.setSize(World.SCREEN_W, World.SCREEN_H+20);
       
        try {        	
            Display.setParent(canvas);
        } catch (Exception e) { 
        	System.out.println("Nie uda³o ustawiæ rodzica dla Display, nie pogramy.");
        }

        frame.add(canvas, BorderLayout.CENTER);
        frame.add(timeLabel, BorderLayout.NORTH);
        frame.add(canvas, BorderLayout.CENTER);

         
        frame.pack();
        frame.setVisible(true);

        new Menu();
         
    }
     
    public static void main(String args[]) 
    {
        new UI();
    }
}