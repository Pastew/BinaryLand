package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ftp.FTPManager;


public class DownloadLevelsPanel extends MenuPanel{
	
	private FTPManager ftpManager;
	boolean err=false;
	public DownloadLevelsPanel(JFrame mainFrame, JPanel controlPanel, String name) {
		super(mainFrame, controlPanel, name);
	}

	private  class Listener implements ActionListener{
		
		   public void actionPerformed(ActionEvent e) {
		         String command = e.getActionCommand();  

		         if( command.equals( "back" ) ) {
		        	 new StartPanel(mainFrame, controlPanel, playerName).load();
		         }else{		        	 
		        	 downloadFile(command);		        	 
		         }
		      }		
		   }
	   
	   public void load(){	
		   if(!connectToSever())
			   return;
			   
		   mainFrame.remove(controlPanel);
	  	   controlPanel = new JPanel();	

	  	   List<String> listOfFiles = ftpManager.getListOfFiles();
	  	   
		   List<JButton> listOfButtons = new LinkedList<JButton>();
		   
		   for(String x : listOfFiles)
			   listOfButtons.add(new JButton( x ));		
		   
		   
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
	   
	   
	   private boolean connectToSever(){
		   try {
				ftpManager = new FTPManager("is2012.vxm.pl", "isvxmpl", "binary");
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(mainFrame, "Nie mo¿na po³¹czyæ siê z serwerem");
				return false;
			}
		   return true;
	   }

	   private void downloadFile(String command){
		   if(!ftpManager.isOK()){
			   JOptionPane.showMessageDialog(mainFrame, "Problem z po³aczeniem");
			   return;
		   }
		   try {
       			String fileName = command.toString();//.substring( "levels\\".length());
				ftpManager.downloadFile("binaryLand/"+fileName, ".\\levels\\" + fileName);
				JOptionPane.showMessageDialog(mainFrame, "Pobrano mapê!");
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(mainFrame, "Nie mo¿na pobraæ mapy");
				
			}   
	   }
}
