import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Block extends Rectangle{
	public Rectangle towerSquare;
	public Rectangle towerSquare2;
	public int towerSquareSize = 100;
	public int towerSquareSize2 = 75;
	public int groundID;
	public int airID;
	public int loseTime = 100, loseFrame = 0;
	public int shotEnemy = -1;
	public boolean shotingTower1 = false;
	public boolean shotingTower2 = false;
	public boolean shoting = false;
	
	public Block(int x, int y, int width, int height, int groundID, int airID){
		setBounds(x, y, width, height);
		towerSquare = new Rectangle(x - (towerSquareSize/2), y-(towerSquareSize/2), width+(towerSquareSize), height+(towerSquareSize));
		towerSquare2 = new Rectangle(x - (towerSquareSize2/2), y-(towerSquareSize2/2), width+(towerSquareSize2), height+(towerSquareSize2));
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
		
		if(shotEnemy != -1 && towerSquare.intersects(Screen.enemies[shotEnemy])) {
			shoting = true;
			
		}else {
			shoting = false;
		}
		
		if (!shoting) {
			if (airID == Value.AIR_TOWER_1 ) {
				for (int i = 0; i < Screen.enemies.length; i++) {
					if (Screen.enemies[i].inGame) {
						if (towerSquare.intersects(Screen.enemies[i])) {
							shoting = true;
							shotingTower1 = true;
							
							shotEnemy = i;
							
						}
					}
				}
			}else if(airID == Value.AIR_TOWER_2) {
				for (int i = 0; i < Screen.enemies.length; i++) {
					if (Screen.enemies[i].inGame) {
						if (towerSquare2.intersects(Screen.enemies[i])) {
							shoting = true;
							shotingTower2 = true;
							
							shotEnemy = i;
							
						}
					}
				}
			}
		}
		
		
		
		
		
		
		
		
			if(shoting) {
				if(loseFrame >= loseTime) {
					if(shotingTower1) {
					Screen.enemies[shotEnemy].enemyLooseHealth(2);
					}else if(shotingTower2){
						Screen.enemies[shotEnemy].enemyLooseHealth(6);
					}
					loseFrame = 0;
					loseTime +=2;
				}else {
					loseFrame += 1;
					
				}
				
				
				
			
				if(Screen.enemies[shotEnemy].isDead()) {
					shoting = false;
					shotEnemy = -1;
				}
				
				
			}
		}
	
	
	public void fight(Graphics g) {
		if (Screen.isDebug) {
			if (airID == Value.AIR_TOWER_1) {
				g.drawRect(towerSquare.x, towerSquare.y, towerSquare.width, towerSquare.height);
			}
			if(shoting) {
				g.setColor(new Color(255,255,0));
				Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(5));
				g.drawLine(x + (width/2), y + (height/2), Screen.enemies[shotEnemy].x + (Screen.enemies[shotEnemy].width/2),Screen.enemies[shotEnemy].y + (Screen.enemies[shotEnemy].height/2) );
			}
		}
	}
}


