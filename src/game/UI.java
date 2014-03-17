package game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.lwjgl.opengl.Display;

public class UI
{
    private Menu game;
     
    public UI() 
    {
        JFrame frame = new JFrame();        
         
        // The exit button.
        JButton button = new JButton("Exit");
         
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
         
        // Create a new canvas and set its size.
        Canvas canvas = new Canvas();
        // Must be 640*480 to match the size of an Env3D window
        canvas.setSize(900, 900);
        // This is the magic!  The setParent method attaches the 
        // opengl window to the awt canvas.
        try {
        	
            Display.setParent(canvas);
        } catch (Exception e) {
        }
         
        // Construct the GUI as normal
        frame.add(button, BorderLayout.NORTH);
        frame.add(canvas, BorderLayout.CENTER);
         
        frame.pack();
        frame.setVisible(true);
         
        // Make sure you run the game, which 
        // executes on a separate thread.
        game = new Menu();
         
    }
     
  /*  public static void main(String args[]) 
    {
        new UI();
    }*/
}