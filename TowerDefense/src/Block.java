import java.awt.Graphics;
import java.awt.Rectangle;

public class Block extends Rectangle{
	public Rectangle towerSquare;
	public int towerSquareSize = 100;
	public int groundID;
	public int airID;
	public int shotEnemy = 0;
	public boolean shoting = false;
	
	public Block(int x, int y, int width, int height, int groundID, int airID){
		setBounds(x, y, width, height);
		towerSquare = new Rectangle(x - (towerSquareSize/2), y-(towerSquareSize/2), width+(towerSquareSize), height+(towerSquareSize));
		this.groundID = groundID;
		this.airID= airID;
	}
	
	public void draw (Graphics g){
		g.drawImage(Screen.ground[groundID],x, y, width, height, null);
		
		if(airID != Value.AIR_AIR){
			g.drawImage(Screen.air[airID],x, y, width, height, null);
			
			
		}
	}
	
	public void physic() {
		for(int i=0; i<Screen.enemies.length;i++) {
			if(Screen.enemies[i].inGame){
				if(Screen.enemies[i].contains(towerSquare)) {
					System.out.println(i);
				}
			}
		}
	}
	
	public void fight(Graphics g) {
		if (Screen.isDebug) {
			if (airID == Value.AIR_TOWER_1) {
				g.drawRect(towerSquare.x, towerSquare.y, towerSquare.width, towerSquare.height);
			}
		}
	}
}


