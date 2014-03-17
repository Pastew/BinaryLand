package gameObject;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;
import game.World;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Pointer{
		private float x=(float) (World.SCREEN_W*0.25), y;
		private int width=32, height=32;
		private Texture texture;
		
		public void draw(int selection){
			
			y=100 + selection*50;
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
		
		public Pointer(){
			try {
				this.texture = TextureLoader.getTexture("PNG", new FileInputStream(new File("res/pointer.png")));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}