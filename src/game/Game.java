package game;


import static game.World.BLOCKS_HEIGHT;
import static game.World.BLOCKS_WIDTH;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import gameObject.Block;
import gameObject.BlockGrid;
import gameObject.ObjectType;
import gameObject.Player;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;

import javax.swing.JFrame;

import menu.ArcadeModePanel.Score;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import Utils.LocalHighScoresManager;
import Utils.LogManager;
import Utils.Timer;
import Utils.Utils;
 
public class Game extends GameFrame{
	//private String playerName;
	
	private Player player1, player2;
	private Block goal;
	
	private String levelPath;
	private BlockGrid grid;
	private BlockGrid backgroundGrid;
	
	private Timer timer;
	private Point timerLocation;
	
	private long lastFrame = getTime(); // [ms]
	
	private Score resultScore;
	private int score=0;
	
	java.util.Timer scoreSubtractor=null;
	private int levelTime=15;
	
	private Audio music;
	
	private JFrame parentFrame;
	
	public Game(String gridPath, JFrame parentFrame, Score score) {		
		super();
		this.resultScore = score;
		this.parentFrame = parentFrame;
		this.parentFrame.setVisible(false);
		levelPath=gridPath;
		setUpObjects(gridPath);
		render();
		Utils.sleep(2000);
		//petla gry
		while(!exit && !Display.isCloseRequested()){
			render();
			input();
			update();
			logic();	
		}
		clearObjects();
		Display.destroy();
		parentFrame.setVisible(true);
	}
	public Game(String gridPath, JFrame parentFrame) {
		this(gridPath, parentFrame, null);
	}
	public Game(String gridPath, JFrame parentFrame, Score score, String name){
		this(gridPath, parentFrame, score);		
		LogManager.addLog(name, this.score);
		LocalHighScoresManager.addHighScore(name, this.score);
	}
	
	private void logic(){
		if(timeIsUp() )
			gameOver();
		
		if(goal.getLocationY() == player1.getLocationY() && goal.getLocationY() == player2.getLocationY() ){	// czy sa na jednym poziomie ( y )
			if( ( goal.getLocationX() == player1.getLocationX() && goal.getLocationX() == player2.getLocationX() )){ //czy sa na bloczku Goal
					win();
			}
		}			
	}
	
	class SubstractScore extends TimerTask {
	    public void run() {
	       score--; 
	    }
	 }
	private int timeRemaining(){
		return (int) (levelTime-timer.getTime());
	}
	private boolean timeIsUp(){
		if(timeRemaining() < 0 )
			return true;
		else return false;
	}
	
	private void win(){ //wygranie jednego poziomu
		music.stop();
		try {
			Audio gameOverSound = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("sounds/win.wav"));
			gameOverSound.playAsSoundEffect(1, 1, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Utils.sleep(4500);
		
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
	private void gameOver(){ //czas uplynal
		if(resultScore!=null)
			resultScore.s=score;
		scoreSubtractor.cancel();
		music.stop();
		try {
			Audio gameOverSound = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("sounds/gameOver.wav"));
			gameOverSound.playAsSoundEffect(1, 1, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Utils.sleep(4500);
		exit=true;
	}
	
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT );
		backgroundGrid.draw();
		grid.draw();
		goal.draw();
		player1.draw();
		player2.draw();
		
		font.drawString(timerLocation.x-300, timerLocation.y,  "TIME LEFT: "+ timeRemaining() );
		font.drawString(timerLocation.x+100, timerLocation.y,  "SCORE: " + Integer.toString(score));
		
		Display.update();
		Display.sync(World.FPS);
		
	}
	void update(){		
		int delta = getDelta();
		System.out.println(delta);
		player1.updateLocation(delta);
		player2.updateLocation(delta);
	}
	protected void setUpObjects(String gridPath) {	
		
		//czcionka
		setUpFont();
		
		//============== PUNKTY ====================
		score += 108;
		
		if(scoreSubtractor==null){
			scoreSubtractor = new java.util.Timer();
			scoreSubtractor.schedule(new SubstractScore(), 0, 500);
		}
		
		grid = new BlockGrid(gridPath);
		
		//=========== TIMER ===================
		timer = new Timer();
		timerLocation = new Point(World.SCREEN_W/2-20, 5);
		
		//============= MUZYKA ================
		try {
			music = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("sounds/music.wav"));
			music.playAsMusic(1,1,false);
		} catch (IOException e) {
			System.out.println("Nie da sie za³adowaæ muzyki");
			e.printStackTrace();
		}
		
		//============= MAPA ==================
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
					player1.stop();
					player2.stop();
					player1.stopAnimation();
					player2.stopAnimation();
				}				
			while(Keyboard.next()){
			//wyjscie z gry
				if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE){
					//gameOver();
					exit=true;
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_Q){
					//win();
				}				
			}
	}
	
	private void clearObjects() {		
		if(music.isPlaying())
			music.stop();
		//AL.destroy();
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
