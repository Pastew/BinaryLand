package gameObject;



import game.World;


public abstract class AbstractObject implements GameObject {

	protected float x, y;
	int size;
	ObjectType type;	
	
	public AbstractObject(float x, float y, int size, ObjectType type){
		this.x = x;
		this.y = y;
		this.size = size;
		this.type = type;
	}
		
	@Override
	public void setLocation(float x, float y){ 
		this.x = x;
		this.y = y;
	}
	@Override
	public void setX(float x){ this.x = x; }
	@Override
	public void setY(float y){ this.y = y; }
	@Override
	public void setSize(int size){ this.size = size; }
	@Override
	public float getX(){ return x; }
	@Override
	public float getY(){ return y; }
	@Override
	public float getX2(){ return x + size; }
	@Override
	public float getY2(){ return y + size; }
	@Override
	public float getSize(){ return size; }
	@Override
	public ObjectType getType() {return type;}
	@Override
	public void setType(ObjectType type) {this.type = type;}	
	@Override
	public int getLocationX(){
		return Math.round(x/World.BLOCK_SIZE);
	}
	@Override
	public int getLocationY(){
		return Math.round(y/World.BLOCK_SIZE);

	}
	@Override
	public boolean intersects(GameObject other) {
		if(other.getType()==ObjectType.FLOOR)
			return false;
		if(
			(this.getY2() < other.getY()) ||
			(this.y > other.getY2()) ||
			(this.x > other.getX2()) ||
			(this.getX2() < other.getX()) 
		)	return false;
		//else przecinaj¹ sie
		return true;
	}
}
