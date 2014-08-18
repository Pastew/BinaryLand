package menu;

import game.Game;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GamePanel extends MenuPanel{
	
	public GamePanel(JFrame mainFrame, JPanel controlPanel, String name) {
		super(mainFrame, controlPanel, name);
	}

	public void load(String filePath){ 
		   /*//mainFrame.dispose();
		   mainFrame.remove(controlPanel);
		   //mainFrame.setVisible(false);
		   
		   Canvas canvas = new Canvas();
	       canvas.setSize(World.SCREEN_W, World.SCREEN_H+20);
	      
	       try {        	
	           Display.setParent(canvas);
	       } catch (Exception e) { 
	       	System.out.println("Nie uda³o ustawiæ rodzica dla Display, nie pogramy.");
	       }

	       mainFrame.add(canvas, BorderLayout.CENTER);
	       mainFrame.pack();*/
		   mainFrame.setVisible(false);
	       new Game(filePath , mainFrame);
	            
	   }
}
