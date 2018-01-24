import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Bloque extends Rectangle{
	public Rectangle rangoTorre;
	public Rectangle rangoTorre2;
	public Rectangle rangoTorre3;
	public int tamanyoRangoTorre = 100;
	public int tamanyoRangoTorre2 = 75;
	public int tamanyoRangoTorre3 = 300;
	public int tierraID;
	public int aireID;
	public int loseTime = 100, loseFrame = 0;
	public int enemigoDisparado = -1;
	public static int enemigoDisparadoDinero = -1;
	public boolean torre1Disparando = false;
	public boolean torre2Disparando = false;
	public boolean torre3Disparando = false;
	public boolean disparando = false;
	
	public Bloque(int x, int y, int width, int height, int groundID, int airID){
		setBounds(x, y, width, height);
		rangoTorre = new Rectangle(x - (tamanyoRangoTorre/2), y-(tamanyoRangoTorre/2), width+(tamanyoRangoTorre), height+(tamanyoRangoTorre));
		rangoTorre2 = new Rectangle(x - (tamanyoRangoTorre2/2), y-(tamanyoRangoTorre2/2), width+(tamanyoRangoTorre2), height+(tamanyoRangoTorre2));
		rangoTorre3 = new Rectangle(x - (tamanyoRangoTorre3/2), y-(tamanyoRangoTorre3/2), width+(tamanyoRangoTorre3), height+(tamanyoRangoTorre3));

		this.tierraID = groundID;
		this.aireID= airID;
	}
	
	public void draw (Graphics g){
		g.drawImage(Screen.ground[tierraID],x, y, width, height, null);
		
		if(aireID != Value.AIR_AIR){
			g.drawImage(Screen.air[aireID],x, y, width, height, null);
			
			
		}
	}
	
	public void physic() {
		
		if(enemigoDisparado != -1 && rangoTorre.intersects(Screen.enemies[enemigoDisparado])) {
			disparando = true;
			
		}else {
			disparando = false;
		}
		
		if (!disparando) {
			if (aireID == Value.AIR_TOWER_1 ) {
				for (int i = 0; i < Screen.enemies.length; i++) {
					if (Screen.enemies[i].inGame) {
						if (rangoTorre.intersects(Screen.enemies[i])) {
							disparando = true;
							torre1Disparando = true;
							
							enemigoDisparado = i;
							enemigoDisparadoDinero = i;
							
						}
					}
				}
			}else if(aireID == Value.AIR_TOWER_2) {
				for (int i = 0; i < Screen.enemies.length; i++) {
					if (Screen.enemies[i].inGame) {
						if (rangoTorre2.intersects(Screen.enemies[i])) {
							disparando = true;
							torre2Disparando = true;
							
							enemigoDisparado = i;
							enemigoDisparadoDinero = i;
							
						}
					}
				}
			}else if(aireID == Value.AIR_TOWER_3) {
				for (int i = 0; i < Screen.enemies.length; i++) {
					if (Screen.enemies[i].inGame) {
						if (rangoTorre3.intersects(Screen.enemies[i])) {
							disparando = true;
							torre3Disparando = true;
							
							enemigoDisparado = i;
							enemigoDisparadoDinero = i;
							
						}
					}
				}
			}
		}
		
		
		
		
		
		
		
		
			if(disparando) {
				if(loseFrame >= loseTime) {
					if(torre1Disparando) {
					Screen.enemies[enemigoDisparado].enemyLooseHealth(2);
					}else if(torre2Disparando){
						Screen.enemies[enemigoDisparado].enemyLooseHealth(6);
					}else if(torre3Disparando) {
						Screen.enemies[enemigoDisparado].enemyLooseHealth(1);
					}
					loseFrame = 0;
					loseTime +=4;
				}else {
					loseFrame += 1;
					
				}
				
				
				
			
				if(Screen.enemies[enemigoDisparado].isDead()) {
					disparando = false;
					enemigoDisparado = -1;
					enemigoDisparadoDinero = -1;
				}
				
				
			}
		}
	
	public static void getMoney(int enemyID) {
		Screen.money += Value.dineroEnemigo[enemyID];
	}
	
	public void fight(Graphics g) {
		if (Screen.isDebug) {
			if (aireID == Value.AIR_TOWER_1) {
				g.drawRect(rangoTorre.x, rangoTorre.y, rangoTorre.width, rangoTorre.height);
			}
		}
			if(disparando) {
				g.setColor(new Color(255,255,0));
				Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(5));
				g.drawLine(x + (width/2), y + (height/2), Screen.enemies[enemigoDisparado].x + (Screen.enemies[enemigoDisparado].width/2),Screen.enemies[enemigoDisparado].y + (Screen.enemies[enemigoDisparado].height/2) );
			}
	}
}


