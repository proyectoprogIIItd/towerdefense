import java.awt.Graphics;

public class Room {
	
	public int worldWidth = 10;
	public int worldHeight = 6;
	public int blockSize = 32;
	
	public Block[] [] block;
	
	public Room(){
		define();
	}
	
	public void define(){
		block = new Block[worldWidth][worldHeight];
		for(int y = 0; y<block.length; y++){
			for(int x = 0; x<block[0].length;x++){
				block[y][x] = new Block(x * blockSize, y * blockSize, blockSize, blockSize,0,0);
			}
		}
	}
	
	public void physic(){
		
	}
	public void draw (Graphics g){
		for(int y = 0; y<block.length; y++){
			for(int x = 0; x<block[0].length;x++){
				block[y][x].draw(g);
			}
		}
	}
}
