package game;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2i;
import gameObject.Block;
import gameObject.BlockGrid;
import gameObject.ObjectType;

import java.io.File;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;


public class MapEditor extends GameFrame{
	private String filePath;
	private BlockGrid grid;
	private List<ObjectType> typeList = new LinkedList<ObjectType>(EnumSet.allOf(ObjectType.class));
	
	public MapEditor(String filePath, JFrame parentFrame){
		this.filePath = filePath;
		setUpObjects(filePath);
		while(!exit && !Display.isCloseRequested()){
			input();
			render();
		}
		clearObjects();
		Display.destroy();
		parentFrame.setVisible(true);
	}
			
	private ObjectType chosenBlock;	
	
	private int selector_x, selector_y ;
	private boolean mouseEnabled;	
	
	private void drawSelectionBox(){
		int x = selector_x * World.BLOCK_SIZE;
		int y = selector_y * World.BLOCK_SIZE;
		
		int x2 = x + World.BLOCK_SIZE;
		int y2 = y + World.BLOCK_SIZE;
		
		

		if(grid.getAt(selector_x, selector_y).getType()== ObjectType.FLOOR && chosenBlock==ObjectType.FLOOR){
			glColor4f(1f, 1f, 1f, 0.8f); //biale pole z 50% przezroczystoscy
			
			glBegin(GL_QUADS);
				glVertex2i(x, y);
				glVertex2i(x2, y);
				glVertex2i(x2, y2);
				glVertex2i(x, y2);
			glEnd();
			glColor4f(1f, 1f, 1f, 1f);
		
		}else if(grid.getAt(selector_x, selector_y).getType() != chosenBlock){
			
			glColor4f(1f, 1f, 1f, 0.5f);
			new Block(selector_x*World.BLOCK_SIZE , selector_y*World.BLOCK_SIZE, World.BLOCK_SIZE,  chosenBlock).draw();
			glColor4f(1f, 1f, 1f, 1f);			
		}
	}
	
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		grid.draw();

		drawSelectionBox();
		
		Display.update();
		Display.sync(60);
	}
	
	protected void setUpObjects(String gridPath) {

		grid = new BlockGrid();		
		chosenBlock = ObjectType.FLOOR;
		
		selector_x = 0;
		selector_y = 0;
		mouseEnabled = true;
		grid.load(new File(gridPath));
	}
	
	protected void input(){
		
		//#### MYSZ ####
		if(!mouseEnabled && Mouse.isButtonDown(1)){
			mouseEnabled = true;
		}else	if(mouseEnabled){			
			int mousex = Mouse.getX();
			int mousey = World.SCREEN_H - Mouse.getY() -1 ;
			boolean mouseClicked = Mouse.isButtonDown(0);
			
			selector_x = Math.round(mousex / World.BLOCK_SIZE);
			selector_y = Math.round(mousey / World.BLOCK_SIZE);
			
			if(mouseClicked){
				if(chosenBlock == ObjectType.GOAL || chosenBlock == ObjectType.PLAYER1 || chosenBlock == ObjectType.PLAYER2 )					
					grid.clearUniqueObjects(chosenBlock);
				grid.setAt(selector_x, selector_y, chosenBlock);
			}
		}
		
		
		//#### KLAWIATURA #####
		
		
		while(Keyboard.next()){
			
			//zapisz mape
			if(Keyboard.getEventKey() == Keyboard.KEY_S){
				grid.save(new File(filePath));
			}
			//wczytaj mape
			if(Keyboard.getEventKey() == Keyboard.KEY_L){
				grid.load(new File(filePath));
			}
			
			
			//zmiana klockow
			for(int key = Keyboard.KEY_1 , number=0; number < typeList.size() ; ++key, ++number){
				if(Keyboard.getEventKey() == key && Keyboard.getEventKeyState()){
					chosenBlock = typeList.get(number);
					}
			}
		
			// Strza³ki
			if(Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()){
				if(!(selector_x + 1 > World.BLOCKS_WIDTH-1)){
					mouseEnabled = false;
					++selector_x;					
				}
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()){
				if(!(selector_x - 1 < 0)){
					mouseEnabled = false;
					--selector_x;					
				}
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_UP && Keyboard.getEventKeyState()){
				if(!(selector_y - 1 < 0 )){
					mouseEnabled = false;
					--selector_y;					
				}
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_DOWN && Keyboard.getEventKeyState()){
				if(!(selector_y + 1 > World.BLOCKS_HEIGHT-1)){
					mouseEnabled = false;
					++selector_y;					
				}
			}
			
			// Postaw kloca
			if(Keyboard.getEventKey() == Keyboard.KEY_SPACE && Keyboard.getEventKeyState()){
				if(chosenBlock == ObjectType.GOAL || chosenBlock == ObjectType.PLAYER1 || chosenBlock == ObjectType.PLAYER2 )					
					grid.clearUniqueObjects(chosenBlock);
				grid.setAt(selector_x, selector_y, chosenBlock);
					
				grid.setAt(selector_x, selector_y, chosenBlock);
				
			}
			//czyszczenie mapy
			if(Keyboard.getEventKey() == Keyboard.KEY_C){
				grid.clear();
			}
			//wyjscie z gry
			if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE){
				exit=true;
			}
		}	
		
	}
		
	private void clearObjects() {
		grid.destroy();
	}
	
}
