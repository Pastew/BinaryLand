package gameObject;

public interface GameObject {
	public void draw();
	
	public void setLocation(float x, float y);
	public void setX(float x);
	public void setY(float y);
	
	public void setSize(int size);
	public float getSize();

	
	public float getX();
	public float getX2();
	public float getY();
	public float getY2();
	
	public int getLocationX();
	public int getLocationY();
	
	public ObjectType getType();
	public void setType(ObjectType type);
	
	//Zwraca true, gdy przecina drugi gameObject
	public boolean intersects(GameObject other);	
}
