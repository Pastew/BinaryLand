package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import database.DbManager;
import database.Hashing;
import database.PlayerData;

public class SingUpPanel extends MenuPanel{

	private DbManager dbManager;
	private JLabel infoLabel;   
	private JTextField loginField;
	private JPasswordField passField;
	private JPasswordField repeatPassField;
	
	public SingUpPanel(JFrame mainFrame, JPanel controlPanel, String name) {
		super(mainFrame, controlPanel, name);
	}
	
	 private class Listener implements ActionListener{
	      public void actionPerformed(ActionEvent e) {
	         String command = e.getActionCommand();  
	         infoLabel.setText("");

	         String login = loginField.getText();
	         char[] pass = passField.getPassword();
	         
       	     System.out.println(pass);
       	 
       	 if( command.equals( "signUp" ) )  {
	        	 
       		 for(PlayerData pD : dbManager.selectPlayers()){
	        		 if(pD.getLogin().equals(login)){
	        			 infoLabel.setText("Login "+login+" jest juz zajety");
	        			 return;
	        		 }
	        	 }
       	 	        	 	        	 
	        	 if( !java.util.Arrays.equals(repeatPassField.getPassword(), pass)){
	        		 infoLabel.setText("Has³a siê ró¿ni¹!");
	        		 return;
	        	 }
	        	 if(new String(pass).isEmpty() || login.isEmpty()){
	        		 infoLabel.setText("Bledne dane (puste pole?)");
	        		 return;
	        	 }
	        	 if(new String(pass).length()>15 || login.length()>15){
	        		 infoLabel.setText("Pola mog¹ mieæ maksymalnie 15 znakow");
	        		 return;
	        	 }
	        	 String hashedPass = Hashing.hash(new String(passField.getPassword())); 
	        	 dbManager.insertPlayer(login,  hashedPass);
	        	 
	        	 JOptionPane.showMessageDialog(mainFrame, "Konto za³o¿ono!");
	        	 
	        	 //TODO usun - wypisywanie wszystkich playerow
	        	 for(PlayerData pD : dbManager.selectPlayers())
	          	   System.out.println(pD);
	        	 
	        	 new LoginPanel(mainFrame, controlPanel, playerName).load();	        	 
	         }
	         else  if( command.equals( "back" ) ) {
	        	 new LoginPanel(mainFrame, controlPanel, playerName).load();
	         } 
	      }		
	   }
	   
	 public void load(){
		   	  
		      mainFrame.remove(controlPanel);
		  	  controlPanel = new JPanel();
		  	  
		      JButton signUpButton = new JButton("Za³ó¿ konto");
		      JButton backButton = new JButton("Powrót");
		      
		      JLabel loginLabel = new JLabel("Login");
		  	  JLabel passLabel = new JLabel("Has³o");
		  	  JLabel repeatPassLabel = new JLabel("Powtórz has³o");
		  	  infoLabel = new JLabel();
		  	  
		      loginField = new JTextField(15);
		  	  passField = new JPasswordField(15);
		  	  repeatPassField = new JPasswordField(15);
		      signUpButton.setActionCommand("signUp");
		      backButton.setActionCommand("back");

		      signUpButton.addActionListener(new Listener()); 
		      backButton.addActionListener(new Listener()); 
		      
		      controlPanel.add(loginLabel);
		      controlPanel.add(loginField);
		      
		      controlPanel.add(passLabel);
		      controlPanel.add(passField);
		      
		      controlPanel.add(repeatPassLabel);
		      controlPanel.add(repeatPassField);
		      
		      controlPanel.add(signUpButton);
		      controlPanel.add(backButton);
		      
		      controlPanel.add(infoLabel);
		      
		      mainFrame.add(controlPanel);
		      mainFrame.setVisible(true);
		      
		      dbManager = new DbManager("jdbc:sqlite:players.db");	      
		      dbManager.createPlayerTable();
		 }
}
