package menu;

import game.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import database.DbManager;
import socket.ScoreSender;


public class ArcadeModePanel extends MenuPanel{
	//int score=0;
	
	public class Score{
		public int s;
	}
	Score score;
	
	public ArcadeModePanel(JFrame mainFrame, JPanel controlPanel, String name) {
		super(mainFrame, controlPanel, name);
		System.out.println(this.playerName);
	}

	private  class Listener implements ActionListener{
		      
		   public void actionPerformed(ActionEvent e) {
		         String command = e.getActionCommand();  

		         if( command.equals( "back" ) ) {
		        	 new StartPanel(mainFrame, controlPanel, playerName).load();
		         }else if( command.equals( "play") ){
		        	 mainFrame.setVisible(false);
		        	 score = new Score();
		  	       	 new Game("levels\\level_001.xml" , mainFrame, score, playerName);
		  	       	 if(score.s>0){
		  	       		 //zapisanie do bazy danych uzytkownikow 
		  	       		 //najlepszego wyniku, jesli jest wiekszy niz poprzedni
			  	       	DbManager dbManager = new DbManager("jdbc:sqlite:players.db");	      
			  		    dbManager.createPlayerTable();
			  		    int highScore = dbManager.getHighScore(playerName);
			  		    System.out.println("Dotychczasowy highScore= "+highScore);
			  		    if(score.s > highScore)
			  		    	dbManager.setHighScore(playerName, score.s);
			  		    
			  		    dbManager.closeConnection();
			  		    //zapytanie czy wyslac wynik na serwer 
		  	       		 promptWindow();
		  	       	 }
		         }
		      }		
		   }
	   
	   public void load(){	
		   mainFrame.remove(controlPanel);
	  	   controlPanel = new JPanel();	  	   
	  	   
	  	   JButton startButton = new JButton("ARCADE MODE");
	  	   startButton.setActionCommand("play");
	  	   startButton.addActionListener(new Listener());
	  	   controlPanel.add(startButton);
	       
	  	   JButton backButton = new JButton("Wstecz");
	       backButton.setActionCommand("back");
	       backButton.addActionListener(new Listener()); 

	       controlPanel.add(backButton);
	       mainFrame.add(controlPanel);
	       mainFrame.setVisible(true);  
		}
	   
	   private void promptWindow(){
		   int n = JOptionPane.showConfirmDialog(
				    mainFrame,
				    "Twój wynik to: " + score.s +
				    "\nChcesz go zapisaæ na serwerze?",
				    "Game Over",
				    JOptionPane.YES_NO_OPTION);
		   //0 = tak, 1 = nie
		   
		   if(n==0)
			   if(ScoreSender.sendScoreToServer(this.playerName, score.s) == false)
		       	 	JOptionPane.showMessageDialog(mainFrame, "Problem z wys³aniem wyniku do serwera");
			   else
		       	 	JOptionPane.showMessageDialog(mainFrame, "Wys³ano wynik!");
	   }
	   
}
