package game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.lwjgl.opengl.Display;

public class Start extends JFrame{
  
	private ButtonStart buttonStart;
    private JButton buttonEdit;
    private JButton buttonExit;
    private Canvas canvas;
    
    public Start() 
    {    	
	     buttonStart = new ButtonStart();
	     buttonEdit= new ButtonEdit();
	     buttonExit = new ButtonExit();
	
	     setLayout(new FlowLayout());
	     add(buttonStart);
	     add(buttonEdit);
	     add(buttonExit);     
                
        canvas = new Canvas();
        canvas.setSize(World.SCREEN_W, World.SCREEN_H+20);
       
        try {        	
            Display.setParent(canvas);
        } catch (Exception e) { 
        	System.out.println("Nie uda³o ustawiæ rodzica dla Display, nie pogramy.");
        }

        add(canvas, BorderLayout.CENTER);

         
        pack();
        setVisible(true);         
    }
    
    class ButtonStart extends JButton implements ActionListener {
    	 
        ButtonStart() {
            super("Play");
            addActionListener(this);
        }
 
        @Override
        public void actionPerformed(ActionEvent e) {
        	System.out.println("Dziala");
        	try {        	
                Display.setParent(canvas);
            } catch (Exception e1) { 
            	System.out.println("Nie uda³o ustawiæ rodzica dla Display, nie pogramy.");
            }
        }
    }
    class ButtonEdit extends JButton implements ActionListener {
   	 
        ButtonEdit() {
            super("Edytor");
            addActionListener(this);
        }
 
        @Override
        public void actionPerformed(ActionEvent e) {
        	
        }
    }
    
    class ButtonExit extends JButton implements ActionListener {
   	 
        ButtonExit() {
            super("Wyjscie");
            addActionListener(this);
        }
 
        @Override
        public void actionPerformed(ActionEvent e) {
        	
        }
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Start();
            }
        });
    }


}