package game;



import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

import dataModel.Hashing;
import dataModel.PlayerData;



public class Start {
   private JFrame mainFrame;
   private JPanel controlPanel;
   private JTextField loginField;
   private JPasswordField passField;
   private JPasswordField repeatPassField;
   private Image backgroundImage;
   
   private JLabel infoLabel;
   
   private Connection conn;
   private Statement stat;
   
   private PlayerData playerData;
   
   public Start(){
      prepareGUI();
      loadLoginPanel();      
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

   //################################# LOGOWANIE   ########################################
   
   private class LoginButtonClickListener implements ActionListener{
	      public void actionPerformed(ActionEvent e) {
	         String command = e.getActionCommand();  

	         if( "singIn".equals( command  ))  {
	        	 infoLabel.setText("");
	        	 String login = loginField.getText();
	        	 String pass = Hashing.hash(passField.getText());
	        	 
	        	 playerData=null;
	        	 for(PlayerData pD : selectPlayers()){
	        		
	        		 if(login.equals(pD.getLogin())){
	        			 if(pass.equals(pD.getPass())){
	        				 playerData = pD;
	        				 break;
	        			 }	        			 
	        		 }         			 
	        	 }	   
        		 
	        	 //logowanie sie udalo
	        	 if(playerData!=null){
	        		 infoLabel.setText("Logowanie sie powiodlo");
	        		 loadStartPanel();
	        	 }
	        	 //logowanie sie nie udalo
	        	 else{
	        		 infoLabel.setText("Zle dane");
	        	 }
	        	 
	        	 
	         }
	         else if( command.equals( "singUp" ) )  {
	        	 loadSignUpPanel();
	         }
	         else  if( command.equals( "exit" ) ) {
	        	 closeConnection();
	        	 System.exit(0);
	         }
	         else if( command.equals( "kasuj") ) {
	        	 new Game("levels/level_001.xml", mainFrame);
	         }
	      }		
	   }
	   
   private void loadLoginPanel(){
		
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
	      
	      singInButton.addActionListener(new LoginButtonClickListener()); 
	      singUpButton.addActionListener(new LoginButtonClickListener()); 
	      exitButton.addActionListener(new LoginButtonClickListener()); 
	      kasujButton.addActionListener(new LoginButtonClickListener());
	      
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
	     
	      try {
	            Class.forName("org.sqlite.JDBC");
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	 
	        try {
	            conn = DriverManager.getConnection("jdbc:sqlite:players.db");
	            stat = conn.createStatement();
	        } catch (SQLException e) {
	            System.err.println("Problem z otwarciem polaczenia");
	            e.printStackTrace();
	        }
	 
	        createTables();
	   }
   
   private boolean createTables(){
	        String createPlayers = "CREATE TABLE IF NOT EXISTS players (player_id INTEGER PRIMARY KEY AUTOINCREMENT, login varchar(255), pass varchar(255))";

	        try {
	            stat.execute(createPlayers);
	        } catch (SQLException e) {
	            System.err.println("Blad przy tworzeniu tabeli");
	            e.printStackTrace();
	            return false;
	        }
	        return true;
   }
   
   public boolean insertPlayer(String login, String pass) {
       try {
           PreparedStatement prepStmt = conn.prepareStatement(
                   "insert into players values (NULL, ?, ?);");
           prepStmt.setString(1, login);
           prepStmt.setString(2, pass);
           prepStmt.execute();
       } catch (SQLException e) {
           System.err.println("Blad przy wstawianiu playera");
           e.printStackTrace();
           return false;
       }
       
       return true;
   }
   
   public List<PlayerData> selectPlayers() {
       List<PlayerData> players = new LinkedList<PlayerData>();
       try {
           ResultSet result = stat.executeQuery("SELECT * FROM players"); // SELECT 1 FROM players WHERE login=? AND pass=? limit 1
           int id;
           String login, pass;
           
           while(result.next()) {
               id = result.getInt("player_id");
               login = result.getString("login");
               pass = result.getString("pass");
               
               players.add(new PlayerData(id, login, pass));
           }
       } catch (SQLException e) {
           e.printStackTrace();
           return null;
       }
       return players;
   }

   public void closeConnection() {
       try {
           conn.close();
       } catch (SQLException e) {
           System.err.println("Problem z zamknieciem polaczenia");
           e.printStackTrace();
       }
   }
   
   //################################# ZAKLADANIE KONTA ##################################
   private class SignUpClickListener implements ActionListener{
	      public void actionPerformed(ActionEvent e) {
	         String command = e.getActionCommand();  
    		 infoLabel.setText("");

	         String login = loginField.getText();
        	 String pass = Hashing.hash(passField.getText());        	 
        	 
        	 if( command.equals( "signUp" ) )  {
	        	 
        		 for(PlayerData pD : selectPlayers()){
	        		 if(pD.getLogin().equals(login)){
	        			 infoLabel.setText("Login "+login+" jest juz zajety");
	        			 return;
	        		 }
	        	 }
        	 	        	 	        	 
	        	 if( !repeatPassField.getText().equals(passField.getText() ) ){
	        		 infoLabel.setText("Has³a nie s¹ identyczne");
	        		 return;
	        	 }
	        	 if(pass.isEmpty() || login.isEmpty()){
	        		 infoLabel.setText("Bledne dane");
	        		 return;
	        	 }
	        	 
	        	 insertPlayer(login,  pass);
	        	 
	        	 JOptionPane.showMessageDialog(mainFrame, "Konto za³o¿ono!");
	        	 for(PlayerData pD : selectPlayers())
	          	   System.out.println(pD);
	        	 loadLoginPanel();
	        	 
	         }
	         else  if( command.equals( "back" ) ) {
	        	 loadLoginPanel();
	         } 
	      }		
	   }
	   
   private void loadSignUpPanel(){
		   	  
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

		      signUpButton.addActionListener(new SignUpClickListener()); 
		      backButton.addActionListener(new SignUpClickListener()); 
		      
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
		   }
   //################################# EKRAN START ########################################
   
   private class StartButtonClickListener implements ActionListener{
      public void actionPerformed(ActionEvent e) {
         String command = e.getActionCommand();  

         if( command.equals( "play" ))  {
        	 loadLevelSelectPanel();
         }
         else if( command.equals( "editor" ) )  {
        	 loadLevelEditSelectPanel();
         }
         else  if( command.equals( "exit" ) ) {
        	 System.exit(0);
         } 
      }		
   }
 
   private void loadStartPanel(){
	   		
	      mainFrame.remove(controlPanel);
	  	  controlPanel = new JPanel();
	  	  
	      JButton playButton = new JButton("Graj");
	      JButton editorButton = new JButton("Edytor Map");
	      JButton exitButton = new JButton("Wyjœcie");

	      playButton.setActionCommand("play");
	      editorButton.setActionCommand("editor");
	      exitButton.setActionCommand("exit");

	      playButton.addActionListener(new StartButtonClickListener()); 
	      editorButton.addActionListener(new StartButtonClickListener()); 
	      exitButton.addActionListener(new StartButtonClickListener()); 

	      controlPanel.add(playButton);
	      controlPanel.add(editorButton);
	      controlPanel.add(exitButton);  
	      
	      mainFrame.add(controlPanel);
	      mainFrame.setVisible(true);  
	   }
   
   //################################# Wybieranie poziomu do grania ########################################

   private class LevelSelectButtonClickListener implements ActionListener{
	      
	   public void actionPerformed(ActionEvent e) {
	         String command = e.getActionCommand();  

	         if( command.equals( "back" ) ) {
	        	 loadStartPanel();
	         }else{
	        	 loadGamePanel(command.toString());
	        	 
	         }
	      }		
	   }
   
   private void loadLevelSelectPanel(){	
	   mainFrame.remove(controlPanel);
  	   controlPanel = new JPanel();
  	   
  	   
	   File[] listOfFiles = new File("levels").listFiles();
	   List<JButton> listOfButtons = new LinkedList<JButton>();
	   
	   for(int i = 0 ; i < listOfFiles.length ; ++i)
		   listOfButtons.add(new JButton( listOfFiles[i].toString() ));		
	   
	   //przyklad: listOfFiles[0].toString() == "levels\level_001.xml"
	   //			listOfButtons.get(0).getText()) == "levels\level_001.xml"
	   
	   for(JButton button : listOfButtons) {
		   button.setActionCommand(button.getText()); 
		   button.addActionListener(new LevelSelectButtonClickListener());
		   controlPanel.add(button);
	   }
	   
	   JButton backButton = new JButton("Wstecz");
       backButton.setActionCommand("back");
       backButton.addActionListener(new LevelSelectButtonClickListener()); 

       controlPanel.add(backButton);
       
       mainFrame.add(controlPanel);
       mainFrame.setVisible(true);  
	}
   
   //################################# Wybieranie poziomu do edytowania ########################################

   private class LevelEditSelectButtonClickListener implements ActionListener{
	      public void actionPerformed(ActionEvent e) {
	         String command = e.getActionCommand();  

	         if( command.equals( "back" ) ) {
	        	 loadStartPanel();
	         }else{
	        	 loadEditPanel(command.toString());
	        	 
	         }
	      }	
	   }
      
   private void loadLevelEditSelectPanel(){	
	   
	   mainFrame.remove(controlPanel);
  	   controlPanel = new JPanel();  	   
  	   
	   File[] listOfFiles = new File("levels").listFiles();
	   List<JButton> listOfButtons = new LinkedList<JButton>();
	   
	   for(int i = 0 ; i < listOfFiles.length ; ++i)
		   listOfButtons.add(new JButton( listOfFiles[i].toString() ));		
	   
	   //przyklad: listOfFiles[0].toString() == "levels\level_001.xml"
	   //			listOfButtons.get(0).getText()) == "levels\level_001.xml"
	   
	   for(JButton button : listOfButtons) {
		   button.setActionCommand(button.getText()); 
		   button.addActionListener(new LevelEditSelectButtonClickListener());
		   controlPanel.add(button);
	   }
	   
	   JButton backButton = new JButton("Wstecz");
       backButton.setActionCommand("back");
       backButton.addActionListener(new LevelEditSelectButtonClickListener()); 

       controlPanel.add(backButton);
       
       mainFrame.add(controlPanel);
       mainFrame.setVisible(true);    
	}

   
   //##################################      INNE    #######################################
   private void loadGamePanel(String filePath){ 
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
   
   private void loadEditPanel(String filePath){
	   mainFrame.setVisible(false);
       new MapEditor(filePath, mainFrame);
   }

}