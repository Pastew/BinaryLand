package gameObject;

public abstract class AbstractMoveableObject extends AbstractObject implements MoveableObject {

	protected float dx, dy;
	
	//konstruktor
	public AbstractMoveableObject(float x, float y, int size, ObjectType type) {
		super(x, y, size, type);
		this.dx = 0;
		this.dy = 0;
	}
	

		
	
		public float getDX(){ return dx; }
		public float getDY(){ return dy; }
		public void setDX(float dx){ stop(); this.dx = dx; }
		public void setDY(float dy){ stop(); this.dy = dy; }
		
		
		
		public void stop(){ this.dx = 0 ; this.dy=0;}
}
