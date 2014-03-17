package game;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.io.File;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import gameObject.Picture;
import gameObject.Pointer;

public class LevelSelect extends GameFrame{
	
	private final int options=2;
	private int selection=0;
	private Pointer pointer;
	File[] listOfLevels;
	private Picture levelSelectPicture;

	public LevelSelect() {
		super();
		setUpObjects();
		while(!Display.isCloseRequested() && !exit){	
			input();
			render();
		}
	}

	@Override
	protected void setUpObjects() {
		levelSelectPicture = new Picture("./res/levelSelectPicture.png", World.SCREEN_W/2-50, 20);
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
				new Game(listOfLevels[selection].toString());
			}
			
		}
		
	}

	@Override
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		
		levelSelectPicture.draw();
		pointer.draw(selection);
		
		Display.update();
		Display.sync(30);		
	}

}
