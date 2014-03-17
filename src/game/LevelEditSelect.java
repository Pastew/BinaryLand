package game;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.io.File;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import gameObject.Picture;
import gameObject.Pointer;

public class LevelEditSelect extends GameFrame{
	private Picture levelEditSelectPicture; 
	private final int options=3;
	private int selection=0;
	private Pointer pointer;
	File[] listOfLevels;
	
	public LevelEditSelect() {
		super();
		setUpObjects();
		while(!Display.isCloseRequested() && !exit){	
			input();
			render();
		}
	}

	@Override
	protected void setUpObjects() {
		levelEditSelectPicture = new Picture("./res/levelEditSelectPicture.png", World.SCREEN_W/2-50, 20);

		pointer = new Pointer();	
		
		listOfLevels = new File("levels").listFiles();
	}

	@Override
	protected void input() {
		while(Keyboard.next()){
			
			if(Keyboard.getEventKey() == Keyboard.KEY_UP && Keyboard.getEventKeyState()){
				if( selection != 0 )
					--selection;
			}
			
			if(Keyboard.getEventKey() == Keyboard.KEY_DOWN && Keyboard.getEventKeyState()){
				if( selection != options-1)
					++selection;
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE && Keyboard.getEventKeyState()){
				exit=true;
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_RETURN && Keyboard.getEventKeyState()){
				new MapEditor(listOfLevels[selection].toString());
			}
			
		}
		
	}

	@Override
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT);

		levelEditSelectPicture.draw();
		pointer.draw(selection);
		
		Display.update();
		Display.sync(30);		
	}

}
