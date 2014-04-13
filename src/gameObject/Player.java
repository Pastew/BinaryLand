package gameObject;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

import game.World;


public class Player extends AbstractMoveableObject{
	
	private float speed=World.BLOCK_SIZE*0.01f;
	
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
		}

	
	public void goRight(GameObject rightBlock){
		if (animation!=goRightAnimation) animation = goRightAnimation; 
		
		if( !(rightBlock.getType() != ObjectType.FLOOR && (this.intersects(rightBlock)))) {					
			setDX(speed);
		}
		adjustHeight(rightBlock.getType());
	}
	
	public void goLeft(GameObject leftBlock){
		if (animation!=goLeftAnimation) animation = goLeftAnimation;
		
		if( !(leftBlock.getType() != ObjectType.FLOOR && (this.intersects(leftBlock)))){					
			this.setDX(-speed);					
		}
		adjustHeight(leftBlock.getType());
	}
	
	public void goUp(GameObject topBlock){
		if (animation!=goUpAnimation) animation = goUpAnimation;
		
		if(!(topBlock.getType() != ObjectType.FLOOR && (intersects(topBlock)))){			
			this.setDY(-speed);
		}
		adjustWidth(topBlock.getType());
	}
	
	public void goDown(GameObject bottomBlock){
		if (animation!=goDownAnimation) animation = goDownAnimation;
		
		if(!(bottomBlock.getType() != ObjectType.FLOOR && (this.intersects(bottomBlock)))){
			this.setDY(speed);							
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
		animation.draw(x, y, World.PLAYER_SIZE, World.PLAYER_SIZE);
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
