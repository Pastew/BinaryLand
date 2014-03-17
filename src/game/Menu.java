package game;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.awt.EventQueue;

import gameObject.Picture;
import gameObject.Pointer;




import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class Menu extends GameFrame{
	private final int options=3;
	private int selection=0;
	private Pointer pointer;
	private Picture menuPicture;

	
	public Menu() {
		super();
		setUpObjects();
		while(!Display.isCloseRequested()){		
			input();
			render();
		}		
	}

	@Override
	protected void setUpObjects() {
		menuPicture = new Picture("./res/menuPicture.png", World.SCREEN_W/2-50, 20);
		pointer = new Pointer();
	}

	@Override
	protected void input() {
		while(Keyboard.next()){
			
			if(Keyboard.getEventKey() == Keyboard.KEY_UP && Keyboard.getEventKeyState()){
				if( selection != 0)
					--selection;
			}
			
			if(Keyboard.getEventKey() == Keyboard.KEY_DOWN && Keyboard.getEventKeyState()){
				if( selection != options-1)
					++selection;
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE && Keyboard.getEventKeyState()){
				Display.destroy();
				System.exit(0);
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_RETURN && Keyboard.getEventKeyState()){
				switch(selection){
					case 0:
						new LevelSelect();
						break;
					case 1: 
						new LevelEditSelect();										
						break;
				}				
			}			
		}
	}

	@Override
	protected void render() {

		glClear(GL_COLOR_BUFFER_BIT);
		menuPicture.draw();
		pointer.draw(selection);
		Display.update();
		Display.sync(30);
		
	}
	
	
	public static void main(String[] args){
		
		new Menu();
	}

}
