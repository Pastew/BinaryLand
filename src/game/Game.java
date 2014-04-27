package game;


import static game.World.BLOCKS_HEIGHT;
import static game.World.BLOCKS_WIDTH;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

import gameObject.Block;
import gameObject.BlockGrid;
import gameObject.ObjectType;
import gameObject.Player;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.Timer;

 
public class Game extends GameFrame{
	private Player player1, player2;
	private Block goal;
	private BlockGrid grid;
	private BlockGrid backgroundGrid;
	private Timer timer;
	private Point timerLocation;
	private long lastFrame = getTime(); // [ms]
	private String levelPath;
	
	public Game(String gridPath, JFrame parentFrame) {
		
		super();
		levelPath=gridPath;
		setUpObjects(gridPath);
		setUpFont();
		
		//petla gry
		while(!exit && !Display.isCloseRequested()){
			input();
			update();
			logic();
			render();
		}
		
		clearObjects();
		Display.destroy();
		parentFrame.setVisible(true);
	}
	
	private void logic(){
		if(goal.getLocationY() == player1.getLocationY() && goal.getLocationY() == player2.getLocationY() ){	// czy sa na jednym poziomie ( y )
			if( ( goal.getLocationX() == player1.getLocationX() && goal.getLocationX() == player2.getLocationX() )){ //czy sa na bloczku Goal
					win();
			}
		}	
	}
	
	private void win(){
		
		File[] arrayOfFiles = new File("levels").listFiles();
		
		int nextLevelIndex=0;
		
		for(int i = 0 ; i < arrayOfFiles.length ; i++){
			if(levelPath.substring(7).equals( arrayOfFiles[i].getPath().substring(7) ) ){
				nextLevelIndex= i+1;
				break;
			}
		}
		
		if(nextLevelIndex < arrayOfFiles.length){
			levelPath = arrayOfFiles[nextLevelIndex].getPath();
			setUpObjects(levelPath);
		}else
			exit=true;
	}
	
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT );
		backgroundGrid.draw();
		grid.draw();
		goal.draw();
		player1.draw();
		player2.draw();
		
		font.drawString(timerLocation.x, timerLocation.y,  String.format("%.1f", timer.getTime()) );
		font.drawString(200, 50, "lol");

		Display.update();
		Display.sync(World.FPS);
		Timer.tick();
	}
	
	void update(){		
		int delta = getDelta();
		player1.updateLocation(delta);
		player2.updateLocation(delta);
	}
	
	protected void setUpObjects(String gridPath) {	
		grid = new BlockGrid(gridPath);
		timerLocation = new Point(World.SCREEN_W/2-20, 5);
		timer = new Timer();		
		
		backgroundGrid = new BlockGrid();
		for(int x=0 ; x < BLOCKS_WIDTH ; x++){
			for ( int y = 0 ; y < BLOCKS_HEIGHT ; y++){
				//postac1
				if (grid.getAt(x, y).getType()== ObjectType.PLAYER1){
					player1 = new Player(x*World.PLAYER_SIZE, y*World.PLAYER_SIZE, World.PLAYER_SIZE, ObjectType.PLAYER1);
					grid.setAt(x,  y,  ObjectType.FLOOR);
				}else
				//postac2
				if (grid.getAt(x, y).getType()== ObjectType.PLAYER2){
					player2 = new Player(x*World.PLAYER_SIZE, y*World.PLAYER_SIZE, World.PLAYER_SIZE, ObjectType.PLAYER2);
					grid.setAt(x, y, ObjectType.FLOOR);
				}else
				//goal
				if (grid.getAt(x, y).getType()== ObjectType.GOAL){
					goal = new Block(x*World.BLOCK_SIZE, y*World.BLOCK_SIZE, World.BLOCK_SIZE, ObjectType.GOAL);
					grid.setAt(x, y, ObjectType.FLOOR);
				}
			}
		}// end for		
	}
	
	protected void input(){
		player1.stop();
		player2.stop();		
			// Strza³ki
				// PRAWO 
				if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || Keyboard.isKeyDown(Keyboard.KEY_D)) {
					player1.goRight(grid.blocks[player1.getLocationX()+1] [player1.getLocationY()]);
					player2.goLeft(grid.blocks[player2.getLocationX()-1] [player2.getLocationY()]);
					return;
				}else
				
				// LEWO				
				if(Keyboard.isKeyDown(Keyboard.KEY_LEFT) || Keyboard.isKeyDown(Keyboard.KEY_A)) {
					player1.goLeft(grid.blocks[player1.getLocationX()-1] [player1.getLocationY()]);
					player2.goRight(grid.blocks[player2.getLocationX()+1] [player2.getLocationY()]);				
					return;
				}else
				
				// GÓRA
				if( (Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W))){
					player1.goUp(grid.blocks[player1.getLocationX()] [player1.getLocationY()-1]);
					player2.goUp(grid.blocks[player2.getLocationX()] [player2.getLocationY()-1]);
					return;
				}else
				
				// DÓ£
				if((Keyboard.isKeyDown(Keyboard.KEY_DOWN) || Keyboard.isKeyDown(Keyboard.KEY_S))){
					player1.goDown(grid.blocks[player1.getLocationX()] [player1.getLocationY()+1]);
					player2.goDown(grid.blocks[player2.getLocationX()] [player2.getLocationY()+1]);
					return;
				}else
				{
					player1.stopAnimation();
					player2.stopAnimation();
				}
				
			while(Keyboard.next()){
			//wyjscie z gry
				if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE){
					exit=true;
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_Q){
					win();
				}
				
			}
	}
	
	private void clearObjects() {
		player1.destroy();
		player2.destroy();	
		grid.destroy();
		backgroundGrid.destroy();	
		goal.destroy();
	}
	
	private long getTime(){
		return (Sys.getTime()*1000)/Sys.getTimerResolution();
	}
	
	private int getDelta(){
		long currentTime = getTime();
		int delta = (int) (currentTime - lastFrame);
		lastFrame = getTime();
		return delta;
	}
}
