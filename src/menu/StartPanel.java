package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class StartPanel extends MenuPanel{
	
	public StartPanel(JFrame mainFrame, JPanel controlPanel, String name ) {
		super(mainFrame, controlPanel, name);
	}

	private class Listener implements ActionListener{
	      public void actionPerformed(ActionEvent e) {
	         String command = e.getActionCommand();  

	         if( command.equals( "play" ))  {
	        	 new LevelSelectPanel(mainFrame, controlPanel, playerName).load();
	         }
	         else if( command.equals( "arcadePlay" ) )  {
	        	 new ArcadeModePanel(mainFrame, controlPanel, playerName).load();
	         }
	         else if( command.equals( "highScores" ) )  {
	        	 new HighScoresPanel(mainFrame, controlPanel, playerName).load();
	         }
	         else if( command.equals( "editor" ) )  {
	        	 new LevelEditSelectPanel(mainFrame, controlPanel, playerName).load();
	         }
	         else if( command.equals( "downloadLevels" ) )  {
	        	 new DownloadLevelsPanel(mainFrame, controlPanel, playerName).load();
	         }
	         else if( command.equals( "updateGame" ) )  {
	        	 new UpdateGamePanel(mainFrame, controlPanel, playerName).load();
	         }
	         else  if( command.equals( "exit" ) ) {
	        	 System.exit(0);
	         } 
	      }		
	   }
	 
	   public void load(){
		   	  
		      mainFrame.remove(controlPanel);
		  	  controlPanel = new JPanel();
		  	  
		      JButton playButton = new JButton("Graj");
		      JButton arcadePlayButton = new JButton("ArcadeMode");
		      JButton highScoresButton = new JButton("Najlepsze wyniki");
		      JButton editorButton = new JButton("Edytor Map");
		      JButton downloadLevelsButton = new JButton("Dodatkowe mapy!");
		      JButton updateGameButton = new JButton("Sprawdü aktualizacje");
		      JButton exitButton = new JButton("Wyjúcie");

		      playButton.setActionCommand("play");
		      arcadePlayButton.setActionCommand("arcadePlay");
		      highScoresButton.setActionCommand("highScores");
		      editorButton.setActionCommand("editor");
		      downloadLevelsButton.setActionCommand("downloadLevels");
		      updateGameButton.setActionCommand("updateGame");
		      exitButton.setActionCommand("exit");

		      playButton.addActionListener(new Listener()); 
		      arcadePlayButton.addActionListener(new Listener()); 
		      highScoresButton.addActionListener(new Listener());
		      editorButton.addActionListener(new Listener());
		      downloadLevelsButton.addActionListener(new Listener());
		      updateGameButton.addActionListener(new Listener());
		      exitButton.addActionListener(new Listener()); 

		      controlPanel.add(playButton);
		      controlPanel.add(arcadePlayButton);
		      controlPanel.add(highScoresButton);
		      controlPanel.add(editorButton);
		      controlPanel.add(downloadLevelsButton);
		      controlPanel.add(updateGameButton);
		      controlPanel.add(exitButton);  
		      
		      mainFrame.add(controlPanel);
		      mainFrame.setVisible(true);  
		   }	
}
