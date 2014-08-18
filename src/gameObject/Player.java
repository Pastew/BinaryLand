package gameObject;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

import game.World;


public class Player extends AbstractMoveableObject{
		
	private final float speed=World.BLOCK_SIZE*0.01f;
	
	private Animation stayAnimation, 
					  goRightAnimation,
					  goLeftAnimation,
					  goUpAnimation,
					  goDownAnimation;	 
	
	
	public Player(float x, float y, int size, ObjectType type) {
		super(x, y, size, type);
		stayAnimation = new Animation(spriteSheet, 0, 1, 0, 1, true , animationSpeed, true); 
		goRightAnimation = new Animation(spriteSheet, 6,0,7,0, true, animationSpeed, true);
		goLeftAnimation = new Animation(spriteSheet, 0,0,1,0, true, animationSpeed, true);
		goUpAnimation = new Animation(spriteSheet, 4,0,5,0, true, animationSpeed, true);
		goDownAnimation = new Animation(spriteSheet, 2,0,3,0, true, animationSpeed, true);

		animation = stayAnimation;
		
		Thread live = new Thread(){
			@Override
			public void run(){
				while(true){
					if(right) {setDX(speed);System.out.println("ide w prawo");}
					if(up) setDY(-speed);
					if(down)setDY(speed);
					if(left)setDX(-speed);
					Thread.yield();
					/*try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
				}

			}		
		};
		live.start();
	}

	public void goRight(GameObject rightBlock){
		if (animation!=goRightAnimation) animation = goRightAnimation; 
		
		if( !(rightBlock.getType() != ObjectType.FLOOR && (this.intersects(rightBlock)))) {					
			right=true;
		}
		adjustHeight(rightBlock.getType());
	}
	
	public void goLeft(GameObject leftBlock){
		if (animation!=goLeftAnimation) animation = goLeftAnimation;
		
		if( !(leftBlock.getType() != ObjectType.FLOOR && (this.intersects(leftBlock)))){					
			left=true;					
		}
		adjustHeight(leftBlock.getType());
	}
	
	public void goUp(GameObject topBlock){
		if (animation!=goUpAnimation) animation = goUpAnimation;
		
		if(!(topBlock.getType() != ObjectType.FLOOR && (intersects(topBlock)))){			
			up=true;
		}
		adjustWidth(topBlock.getType());
	}
	
	public void goDown(GameObject bottomBlock){
		if (animation!=goDownAnimation) animation = goDownAnimation;
		
		if(!(bottomBlock.getType() != ObjectType.FLOOR && (this.intersects(bottomBlock)))){
			down=true;							
		}
		adjustWidth(bottomBlock.getType());
	}

	private void adjustHeight(ObjectType BlockType){
		if( y == getLocationY()*World.BLOCK_SIZE) return;
		else if( y > (getLocationY() * World.BLOCK_SIZE +4) &&  BlockType == ObjectType.FLOOR)
			setDY(-speed);
		
		else if( y < getLocationY() * World.BLOCK_SIZE  &&  BlockType == ObjectType.FLOOR)
			setDY(speed);
	}
	
	private void adjustWidth(ObjectType BlockType){
		if( x == getLocationX()*World.BLOCK_SIZE) return;
		else if( x > (getLocationX() * World.BLOCK_SIZE +4) &&  BlockType == ObjectType.FLOOR)
			setDX(-speed);
		
		else if( x < getLocationX() * World.BLOCK_SIZE  &&  BlockType == ObjectType.FLOOR)
			setDX(speed);
	}

	public void stopAnimation(){
		this.animation = stayAnimation;
	}

	@Override
 	public void draw() {
		new Runnable() {
			public void run() {
				animation.draw(x, y, World.PLAYER_SIZE, World.PLAYER_SIZE);
			}
		}.run();
	}
	public void destroy(){
		try {
			spriteSheet.destroy();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void updateLocation(int delta) {
		x += delta * dx * speed;
		y += delta * dy * speed;
	}

}
