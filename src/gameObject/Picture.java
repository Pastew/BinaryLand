package gameObject;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Picture{
		private float x, y;
		private int width, height;
		private Texture texture;
		
		public void draw(){
			
			texture.bind();
			glLoadIdentity();
			glTranslatef(x,y,0);
			glBegin(GL_QUADS);
				glTexCoord2f(0, 0);
				glVertex2f(0, 0);
				glTexCoord2f(1,0);
				glVertex2f(width, 0);
				glTexCoord2f(1, 1);
				glVertex2f(width, height);
				glTexCoord2f(0, 1);
				glVertex2f(0, height);
				
			glEnd();
			glLoadIdentity();
		}
		
		public Picture(String filePath, float x, float y, int width, int height){
			this.x=x;
			this.y=y;
			this.width = width;
			this.height = height;
			try {
				this.texture = TextureLoader.getTexture("PNG", new FileInputStream(new File(filePath)));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public Picture(String filePath, float x, float y){
			this.x=x;
			this.y=y;
			
			try {
				this.texture = TextureLoader.getTexture("PNG", new FileInputStream(new File(filePath)));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.width = texture.getImageWidth();
			this.height = texture.getImageHeight();
		}
	}