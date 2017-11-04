import java.awt.Graphics;
import java.awt.Rectangle;

public class Block extends Rectangle{
	public int groundID;
	public int airID;
	
	public Block(int x, int y, int width, int height, int groundID, int airID){
		setBounds(x, y, width, height);
		this.groundID = groundID;
		this.airID= airID;
	}
	
	public void draw (Graphics g){
		g.drawRect(x, y, width, height);
		
		if(airID != Value.airAir){
			
		}
	}
}
