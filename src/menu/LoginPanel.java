package menu;

import game.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import database.DbManager;
import database.Hashing;
import database.PlayerData;

public class LoginPanel extends MenuPanel{

	private DbManager dbManager;
	private PlayerData playerData;
	private JLabel infoLabel;   
	private JTextField loginField;
	private JPasswordField passField;

	   
	public LoginPanel(JFrame mainFrame, JPanel controlPanel, String name) {
		super(mainFrame, controlPanel, name);
	}

	private class Listener implements ActionListener{
	      public void actionPerformed(ActionEvent e) {
	         String command = e.getActionCommand();  

	         if( "singIn".equals( command  ))  {
	        	 infoLabel.setText("");
	        	 String login = loginField.getText();
	        	 char[] pass = passField.getPassword();
	        	 String pass2 = new String(pass);
	        	 String hashedPass = Hashing.hash(pass2);
	        	 
	        	 playerData=null;
	        	 for(PlayerData pD : dbManager.selectPlayers()){
	        		
	        		 if(login.equals(pD.getLogin())){
	        			 if(hashedPass.equals(pD.getPass())){
	        				 playerData = pD;
	        				 break;
	        			 }	        			 
	        		 }         			 
	        	 }	   
      		 
	        	 //logowanie sie udalo
	        	 if(playerData!=null){
	        		 infoLabel.setText("Logowanie sie powiodlo");
	        		 String name = login;
	        		 new StartPanel(mainFrame, controlPanel, name).load();
	        	 }
	        	 //logowanie sie nie udalo
	        	 else{
	        		 infoLabel.setText("Zle dane");
	        	 }
	        	 
	        	 
	         }
	         else if( command.equals( "singUp" ) )  {
	        	 new SingUpPanel(mainFrame, controlPanel, playerName).load();
	         }
	         else  if( command.equals( "exit" ) ) {
	        	 dbManager.closeConnection();
	        	 System.exit(0);
	         }
	         else if( command.equals( "kasuj") ) {
	        	 new Game("levels/level_001.xml", mainFrame);
	         }
	      }		
	   }
	   
	public void load(){
		
	      mainFrame.remove(controlPanel);
	  	  controlPanel = new JPanel();
	  	  
	      JButton singInButton = new JButton("Zaloguj");
	      JButton singUpButton = new JButton("Za³ó¿ Konto");
	      JButton kasujButton = new JButton("Direct play");

	      JButton exitButton = new JButton("Wyjœcie");
	      
	      loginField = new JTextField(10);
	  	  passField = new JPasswordField(10);
	  	  
	  	  infoLabel = new JLabel();
	  	  JLabel loginLabel = new JLabel("Login");
	  	  JLabel passLabel = new JLabel("Has³o");
	  	  

	      singInButton.setActionCommand("singIn");
	      singUpButton.setActionCommand("singUp");
	      exitButton.setActionCommand("exit");
	      kasujButton.setActionCommand("kasuj");
	      
	      singInButton.addActionListener(new Listener()); 
	      singUpButton.addActionListener(new Listener()); 
	      exitButton.addActionListener(new Listener()); 
	      kasujButton.addActionListener(new Listener());
	      
	      controlPanel.add(loginLabel);
	      controlPanel.add(loginField);
	      controlPanel.add(passLabel);
	      controlPanel.add(passField);
	      
	      controlPanel.add(singInButton);
	      controlPanel.add(singUpButton);
	      controlPanel.add(exitButton);  
	      
	      controlPanel.add(infoLabel);
	      controlPanel.add(kasujButton);
	      
	      mainFrame.add(controlPanel);
	      mainFrame.setVisible(true);  
	     
	      dbManager = new DbManager("jdbc:sqlite:players.db");	      
	      dbManager.createPlayerTable();
	   }
 
}
