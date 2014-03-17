package gameObject;

import static org.lwjgl.opengl.GL11.*;


public class Block extends AbstractObject{

		public Block(float x, float y, int size, ObjectType type ) {
		super(x, y, size, type);
		}
	
		public void draw() {
			texture.bind();
			glLoadIdentity();
			glTranslatef(x,y,0);
			glBegin(GL_QUADS);
				glTexCoord2f(0, 0);
				glVertex2f(0, 0);
				glTexCoord2f(1,0);
				glVertex2f(size, 0);
				glTexCoord2f(1, 1);
				glVertex2f(size, size);
				glTexCoord2f(0, 1);
				glVertex2f(0, size);
				
			glEnd();
			glLoadIdentity();
		}

		
}
