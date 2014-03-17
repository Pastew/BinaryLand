package game;

public class World {
	public static final int BLOCKS_WIDTH=17, 
							BLOCKS_HEIGHT=12,
							
							BLOCK_SIZE = 50, 
							PLAYER_SIZE = BLOCK_SIZE;
	
	public static final int SCREEN_W= World.BLOCKS_WIDTH * World.BLOCK_SIZE, 
							SCREEN_H= World.BLOCKS_HEIGHT * World.BLOCK_SIZE;	//Width, Height;
}
