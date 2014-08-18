package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import database.DbManager;
import database.PlayerData;


public class HighScoresPanel extends MenuPanel{
	
	public HighScoresPanel(JFrame mainFrame, JPanel controlPanel, String name) {
		super(mainFrame, controlPanel, name);
	}

	private  class Listener implements ActionListener{
		      
		   public void actionPerformed(ActionEvent e) {
		         String command = e.getActionCommand();  

		         if( command.equals( "back" ) ) {
		        	 new StartPanel(mainFrame, controlPanel, playerName).load();
		         }
		      }		
		   }
	   
   public void load(){	
	   mainFrame.remove(controlPanel);
  	   controlPanel = new JPanel();
  	   DbManager dbManager = new DbManager("jdbc:sqlite:players.db");
  	   ArrayList<PlayerData> scoreList = (ArrayList<PlayerData>) dbManager.selectHighScoresList();
  	   dbManager.closeConnection();
  	   
  	   StringBuilder data = new StringBuilder("<html>");
  	   int i = 0;
  	   for(PlayerData el : scoreList){
  		   ++i;
  		   data.append(i + ". " + el.getLogin() + "\t" + el.getHighScore() + "<br>");
  	   }
  	   data.append("</html>");
  	   		
  	   JLabel listLabel = new JLabel(data.toString());
  	   
	   JButton backButton = new JButton("Wstecz");
       backButton.setActionCommand("back");
       backButton.addActionListener(new Listener());

       controlPanel.add(listLabel);
       controlPanel.add(backButton);
       
       mainFrame.add(controlPanel);
       mainFrame.setVisible(true);  
	}
}
