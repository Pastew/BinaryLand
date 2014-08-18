package menu;

import game.World;

import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Start {
   private JFrame mainFrame;
   private JPanel controlPanel;
    
   public Start(){
      prepareGUI();
      new LoginPanel(mainFrame, controlPanel, "Guest").load();      
   }
   
   public static void main(String[] args){
      new Start();      
   }
      
   private void prepareGUI(){
	  
		  mainFrame = new JFrame("Main Menu");
	      mainFrame.setSize(World.SCREEN_W,World.SCREEN_H);
	      mainFrame.setLocation(200, 50);
	      mainFrame.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
		        System.exit(0);
	         }        
	      });    
	      controlPanel = new JPanel();
	      controlPanel.setLayout(new FlowLayout());
   }

}