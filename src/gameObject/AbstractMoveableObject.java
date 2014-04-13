package gameObject;

import game.World;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public abstract class AbstractMoveableObject extends AbstractObject implements MoveableObject {

	protected float dx, dy;	
	protected Animation animation;
	int animationSpeed;
	protected SpriteSheet spriteSheet;
	
	//konstruktor
	public AbstractMoveableObject(float x, float y, int size, ObjectType type) {
		super(x, y, size, type);
		this.dx = 0;
		this.dy = 0;
		this.animationSpeed=(int) (World.FPS*2.5); // [ms]		
		
		try {
			spriteSheet = new SpriteSheet(this.type.location, 32, 32);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
		public float getDX(){ return dx; }
		public float getDY(){ return dy; }
		public void setDX(float dx){ stop(); this.dx = dx; }
		public void setDY(float dy){ stop(); this.dy = dy; }
			
		public void stop(){ this.dx = 0 ; this.dy=0;}
}
