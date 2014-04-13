package gameObject;

import static game.World.*;

import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BlockGrid {
	public Block[][] blocks = new Block[BLOCKS_WIDTH][BLOCKS_HEIGHT];
	
	//konstruktor zwykly, tworzy czysta mape
	public BlockGrid(){
		for(int x=0 ; x < BLOCKS_WIDTH ; x++){
			for ( int y = 0 ; y < BLOCKS_HEIGHT ; y++){
				blocks[x][y]= new Block(x*BLOCK_SIZE, y*BLOCK_SIZE, BLOCK_SIZE,  ObjectType.FLOOR);
			}
		}
	}
	
	public BlockGrid(String fileName){
		load(new File(fileName));
	}
	//konstruktor laduje mape z pliku
	public void load(File loadFile){		
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(loadFile);
			Element root = document.getRootElement();
			
			for(Object block : root.getChildren()){
				Element el = (Element) block;
				int x = Integer.parseInt(el.getAttributeValue("x")),
					y = Integer.parseInt(el.getAttributeValue("y"));
				blocks[x][y] = new Block(x*BLOCK_SIZE, y*BLOCK_SIZE, BLOCK_SIZE, ObjectType.valueOf(el.getAttributeValue("type")));
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//zapis mapy do pliku
	public void save(File saveFile){
		Document document = new Document();
		Element root = new Element("blocks");
		document.setRootElement(root);
		
		for(int x=0 ; x < BLOCKS_WIDTH ; x++){
			for ( int y = 0 ; y < BLOCKS_HEIGHT ; y++){
				
				Element block = new Element("block");
				block.setAttribute("x", String.valueOf((int)(blocks[x][y].getX()/BLOCK_SIZE)));
				block.setAttribute("y", String.valueOf((int)(blocks[x][y].getY()/BLOCK_SIZE)));
				block.setAttribute("type", String.valueOf(blocks[x][y].getType()));
				root.addContent(block);
			}
		}
		
		XMLOutputter output = new XMLOutputter();
		try {
			output.output(document,  new FileOutputStream(saveFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	//postaw kloca
	public void setAt(int x, int y, ObjectType type){
		blocks[x][y] = new Block(x*BLOCK_SIZE, y*BLOCK_SIZE, BLOCK_SIZE, type);
	}
	public Block getAt(int x, int y){
		return blocks[x][y];
	}
	public void draw(){
		for(int x=0 ; x < BLOCKS_WIDTH ; x++){
			for ( int y = 0 ; y < BLOCKS_HEIGHT ; y++){
				blocks[x][y].draw();
			}
		}
	}
	public void clear() {
		for(int x=0 ; x < BLOCKS_WIDTH ; x++){
			for ( int y = 0 ; y < BLOCKS_HEIGHT ; y++){
				blocks[x][y] = new Block(x*BLOCK_SIZE , y*BLOCK_SIZE, BLOCK_SIZE, ObjectType.FLOOR);
			}
		}		
	}

	public void destroy(){
		for(int x=0 ; x < BLOCKS_WIDTH ; x++){
			for ( int y = 0 ; y < BLOCKS_HEIGHT ; y++){
				blocks[x][y].destroy();
			}
		}
	}
	
	public void clearUniqueObjects(ObjectType typeToClear) {
		for(int x=0 ; x < BLOCKS_WIDTH ; x++){
			for ( int y = 0 ; y < BLOCKS_HEIGHT ; y++){
				if(blocks[x][y].type == typeToClear)
					blocks[x][y] = new Block(x*BLOCK_SIZE , y*BLOCK_SIZE, BLOCK_SIZE, ObjectType.FLOOR);
			}
		}
		
	}
}
