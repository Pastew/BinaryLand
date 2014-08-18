package gameObject;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class AbstractStaticObject extends AbstractObject{
	
	Image image;
	public AbstractStaticObject(float x, float y, int size, ObjectType type) {
		super(x, y, size, type);
		
		try {
			image = new Image(this.type.location);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(){
		image.draw(x, y, size, size);
	}
	
	public void destroy(){
		try {
			image.destroy();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
