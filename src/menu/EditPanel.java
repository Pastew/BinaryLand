package menu;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.MapEditor;

public class EditPanel extends MenuPanel{

	public EditPanel(JFrame mainFrame, JPanel controlPanel, String name) {
		super(mainFrame, controlPanel, name);
	}

	public void load(String filePath){
		   mainFrame.setVisible(false);
	       new MapEditor(filePath, mainFrame);
	   }
}
