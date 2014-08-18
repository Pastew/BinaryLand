package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class LevelSelectPanel extends MenuPanel{
	
	public LevelSelectPanel(JFrame mainFrame, JPanel controlPanel, String name) {
		super(mainFrame, controlPanel, name);
	}

	private  class Listener implements ActionListener{
		      
		   public void actionPerformed(ActionEvent e) {
		         String command = e.getActionCommand();  

		         if( command.equals( "back" ) ) {
		        	 new StartPanel(mainFrame, controlPanel, playerName).load();
		         }else{
		        	 new GamePanel(mainFrame, controlPanel, playerName).load("levels\\"+command.toString()+".xml");		        	 
		         }
		      }		
		   }
	   
	   public void load(){	
		   mainFrame.remove(controlPanel);
	  	   controlPanel = new JPanel();	  	   
	  	   
		   File[] listOfFiles = new File("levels").listFiles();
		   List<JButton> listOfButtons = new LinkedList<JButton>();
		   
		   for(int i = 0 ; i < listOfFiles.length ; ++i){
			   String levelName = listOfFiles[i].toString().substring("levels\\".length() ,
					   												  listOfFiles[i].toString().length()-4);
			   listOfButtons.add(new JButton( levelName ) );		
		   }
		   
		   //przyklad: listOfFiles[0].toString() == "levels\level_001.xml"
		   //			listOfButtons.get(0).getText()) == "level_001.xml"
		   
		   for(JButton button : listOfButtons) {
			   button.setActionCommand(button.getText()); 
			   button.addActionListener(new Listener());
			   controlPanel.add(button);
		   }
		   
		   JButton backButton = new JButton("Wstecz");
	       backButton.setActionCommand("back");
	       backButton.addActionListener(new Listener()); 

	       controlPanel.add(backButton);
	       
	       mainFrame.add(controlPanel);
	       mainFrame.setVisible(true);  
		}
}
