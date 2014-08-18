package game;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;






import java.awt.Font;
import java.io.InputStream;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

public abstract class GameFrame {
	TrueTypeFont font;
	
	boolean exit = false;
	protected GameFrame(){		
		setUpDisplay();
		setUpOpenGL();
	}
	
	protected void setUpDisplay(){
		Display.setTitle("Binary Land");

		if(!Display.isCreated())
			try{
				Display.setDisplayMode(new DisplayMode(World.SCREEN_W, World.SCREEN_H));
				Display.setLocation(200, 50);
				Display.create();
			} catch(LWJGLException e){
				e.printStackTrace();
			}
	}
	protected void setUpOpenGL(){
		// Inicjalizacja
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, World.SCREEN_W, World.SCREEN_H, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);		
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	protected void setUpFont(){
		try {
			InputStream inputStream	= ResourceLoader.getResourceAsStream("res/TooSimple.ttf");
			
			Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont = awtFont.deriveFont(24f); // set font size
			font = new TrueTypeFont(awtFont, false);
				
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	protected abstract void setUpObjects(String gridPath);
	

}
