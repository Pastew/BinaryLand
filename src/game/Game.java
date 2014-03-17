package game;


import static game.World.BLOCKS_HEIGHT;
import static game.World.BLOCKS_WIDTH;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;


import gameObject.Block;
import gameObject.BlockGrid;
import gameObject.GameObject;
import gameObject.ObjectType;
import gameObject.Player;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;


public class Game extends GameFrame{
	private Player player1, player2;
	private Player[] players = {player1, player2};
	private float playerSpeed=World.BLOCK_SIZE*0.01f;
	private Block goal;
	//BlockGrid backGrid;
	private long lastFrame=getTime(); // [ms]
	
	private BlockGrid grid;


	public Game(String name) {
		super();
		grid = new BlockGrid(name);	
		setUpObjects();
		while(!exit && !Display.isCloseRequested()){	
			input();
			update();
			logic();
			render();
		}
	}
	
	
	private void logic(){
		if(goal.getLocationY() == player1.getLocationY() && goal.getLocationY() == player2.getLocationY() ){	// czy sa na jednym poziomie ( y )
			if( ( goal.getLocationX()-1 == player1.getLocationX() && goal.getLocationX()+1 == player2.getLocationX() )
				||  ( goal.getLocationX()-1 == player2.getLocationX() && goal.getLocationX()+1 == player1.getLocationX() ) ){
					win();
			}
		}	
	}
	
	private void win(){
		exit=true;
	}
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT);
				
		
		//backGrid.draw();
		
		grid.draw();
		goal.draw();
		player1.draw();
		player2.draw();
		Display.update();
		Display.sync(60);
	}
	
	void update(){
		
		int delta = getDelta();
		for(Player p : players){
			p.setX(p.getX() + delta * p.getDX() * playerSpeed);
			p.setY(p.getY()+delta * p.getDY() * playerSpeed);
		}
	}
	
	protected void setUpObjects() {		

		for(int x=0 ; x < BLOCKS_WIDTH ; x++){
			for ( int y = 0 ; y < BLOCKS_HEIGHT ; y++){
				//postac1
				if (grid.getAt(x, y).getType()== ObjectType.PLAYER1){
					player1 = new Player(x*World.PLAYER_SIZE, y*World.PLAYER_SIZE, World.PLAYER_SIZE, ObjectType.PLAYER1);
					grid.setAt(x,  y,  ObjectType.FLOOR);
				}
				//postac2
				if (grid.getAt(x, y).getType()== ObjectType.PLAYER2){
					player2 = new Player(x*World.PLAYER_SIZE, y*World.PLAYER_SIZE, World.PLAYER_SIZE, ObjectType.PLAYER2);
					grid.setAt(x, y, ObjectType.FLOOR);
				}
				//goal
				if (grid.getAt(x, y).getType()== ObjectType.GOAL){
					goal = new Block(x*World.BLOCK_SIZE, y*World.BLOCK_SIZE, World.BLOCK_SIZE, ObjectType.GOAL);
					grid.setAt(x, y, ObjectType.FLOOR);
				}
			}
		}
		players[0]=player1;
		players[1]=player2;
	}
	
	protected void input(){
		player1.stop();
		player2.stop();
		
			// Strza³ki
			
			
				GameObject rightBlock1 = grid.blocks[player1.getLocationX()+1] [player1.getLocationY()];
				GameObject leftBlock2 = grid.blocks[player2.getLocationX()-1] [player2.getLocationY()];
				// == >
				if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
					if( !(rightBlock1.getType() != ObjectType.FLOOR  
						&& (player1.intersects(rightBlock1)))) {					
					player1.setDX(playerSpeed);
					}			
					
					if( !(leftBlock2.getType() != ObjectType.FLOOR  
							&& (player2.intersects(leftBlock2)))) {					
						player2.setDX(-playerSpeed);
					}					
					adjustHeight(player1, rightBlock1.getType());
					adjustHeight(player2, leftBlock2.getType());				
					return;
				}
				// < ==
				GameObject leftBlock1 = grid.blocks[player1.getLocationX()-1] [player1.getLocationY()];
				GameObject rightBlock2 = grid.blocks[player2.getLocationX()+1] [player2.getLocationY()];
				
				if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
					if( !(leftBlock1.getType() != ObjectType.FLOOR && (player1.intersects(leftBlock1)))){					
					player1.setDX(-playerSpeed);					
					}
					
					if( !(rightBlock2.getType() != ObjectType.FLOOR && (player2.intersects(rightBlock2)))){					
						player2.setDX(playerSpeed);
					}
					
					adjustHeight(player1, rightBlock1.getType());
					adjustHeight(player2, leftBlock2.getType());
					return;
				}
				
				// GÓRA
				for(Player p : players){
				GameObject topBlock = grid.blocks[p.getLocationX()] [p.getLocationY()-1];
				
				if(Keyboard.isKeyDown(Keyboard.KEY_UP) 
						&& !(topBlock.getType() != ObjectType.FLOOR  
						&& (p.intersects(topBlock)))){
					
					p.setDY(-playerSpeed);
					
					adjustWidth(p, topBlock.getType());
					
				}
				
				// DÓ£
				GameObject bottomBlock = grid.blocks[p.getLocationX()] [p.getLocationY()+1];
				if(Keyboard.isKeyDown(Keyboard.KEY_DOWN) 
						&& !(bottomBlock.getType() != ObjectType.FLOOR  
						&& (p.intersects(bottomBlock)))){
					
					p.setDY(playerSpeed);
					
					adjustWidth(p, topBlock.getType());
					
				}
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
	
	private void adjustHeight(Player player, ObjectType BlockType){
		if( player.getY() == player.getLocationY()*World.BLOCK_SIZE) return;
		else if( player.getY() > (player.getLocationY() * World.BLOCK_SIZE +4) &&  BlockType == ObjectType.FLOOR)
			player.setDY(-playerSpeed);
		
		else if( player.getY() < player.getLocationY() * World.BLOCK_SIZE  &&  BlockType == ObjectType.FLOOR)
			player.setDY(playerSpeed);
	}
	
	private void adjustWidth(Player player, ObjectType BlockType){
		if( player.getX() == player.getLocationX()*World.BLOCK_SIZE) return;
		else if( player.getX() > (player.getLocationX() * World.BLOCK_SIZE +4) &&  BlockType == ObjectType.FLOOR)
			player.setDX(-playerSpeed);
		
		else if( player.getX() < player.getLocationX() * World.BLOCK_SIZE  &&  BlockType == ObjectType.FLOOR)
			player.setDX(playerSpeed);
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
