package menu;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class MenuPanel {
	protected JFrame mainFrame;
	protected JPanel controlPanel;
	protected String playerName;
	
	public MenuPanel(JFrame mainFrame, JPanel controlPanel, String name) {
		this.mainFrame = mainFrame;
		this.controlPanel = controlPanel;
		this.playerName = name;
	}
	
}
