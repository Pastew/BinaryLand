package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;






import java.io.File;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ftp.FTPManager;

public class UpdateGamePanel extends MenuPanel{
	FTPManager ftpManager;
	
	public UpdateGamePanel(JFrame mainFrame, JPanel controlPanel, String name) {
		super(mainFrame, controlPanel, name);
	}

	private class Listener implements ActionListener{
	      public void actionPerformed(ActionEvent e) {
	         String command = e.getActionCommand();  

	         if( command.equals( "back" ) ) {
	        	 new StartPanel(mainFrame, controlPanel, playerName).load();
	         }else if( command.equals( "check" ) ) {
	        	 try {
					ftpManager = new FTPManager("is2012.vxm.pl", "isvxmpl", "binary");
					checkVersion();
					ftpManager.disconnect();
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(mainFrame, "Nie mo¿na po³¹czyæ siê z serwerem");
					ftpManager.disconnect();
					new StartPanel(mainFrame, controlPanel, playerName).load();
					return;
				}	        	
	         }
	      }	
	   }
	
	private void checkVersion(){
		   if(!ftpManager.isOK()){
			   JOptionPane.showMessageDialog(mainFrame, "Problem z po³aczeniem");
			   return ;
		   }
		   try {
				ftpManager.downloadFile("binaryLand/version.txt", ".\\versionCheck.txt");
								
				Scanner verScanner = new Scanner(new File("version.txt"));
				String ver = verScanner.nextLine();
				verScanner.close();
				
				Scanner verCheckScanner = new Scanner(new File("versionCheck.txt"));
				String verCheck = verCheckScanner.nextLine();
				verCheckScanner.close();
				
				if(ver.equals(verCheck))
					JOptionPane.showMessageDialog(mainFrame, "Masz najnowsz¹ wersjê gry!");
				else{
					ftpManager.downloadFile("binaryLand/binaryLand.jar", ".\\binaryLand.jar");
					JOptionPane.showMessageDialog(mainFrame, "Pobrano Najnowsz¹ wersjê! Aplikacja zaraz zostanie zamkniêta.");
					System.exit(0);
				}
				
				new File(".\\versionCheck.txt").delete();
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(mainFrame, "Nie mo¿na po³aczyc sie z serwerem");
				
			}   
	   }
    
 public void load(){	
	   
	 mainFrame.remove(controlPanel);
	 controlPanel = new JPanel();  	   
	   
	 JButton checkButton = new JButton("SprawdŸ czy jest dostêpna nowa wersja.");	   
	 JButton backButton = new JButton("Wstecz");
	 
	 checkButton.setActionCommand("check");
     backButton.setActionCommand("back");
     
     checkButton.addActionListener(new Listener()); 
     backButton.addActionListener(new Listener()); 

     controlPanel.add(checkButton);
     controlPanel.add(backButton);
     
     mainFrame.add(controlPanel);
     mainFrame.setVisible(true);    
	} 
}
