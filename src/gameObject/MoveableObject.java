package gameObject;

public interface MoveableObject extends GameObject {
	public float getDX();
	public float getDY();
	public void setDX(float dx);
	public void setDY(float dy);
	
	public void stop();
}
