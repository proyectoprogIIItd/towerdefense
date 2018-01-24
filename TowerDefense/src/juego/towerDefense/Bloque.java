package juego.towerDefense;
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
	public int tiempoPerdida = 100, contadorBucle = 0;
	public int enemigoDisparado = -1;
	public static int enemigoDisparadoDinero = -1;
	public boolean torre1Disparando = false;
	public boolean torre2Disparando = false;
	public boolean torre3Disparando = false;
	public boolean disparando = false;
	
	public Bloque(int x, int y, int width, int height, int tierraID, int aireID){
		setBounds(x, y, width, height);
		rangoTorre = new Rectangle(x - (tamanyoRangoTorre/2), y-(tamanyoRangoTorre/2), width+(tamanyoRangoTorre), height+(tamanyoRangoTorre));
		rangoTorre2 = new Rectangle(x - (tamanyoRangoTorre2/2), y-(tamanyoRangoTorre2/2), width+(tamanyoRangoTorre2), height+(tamanyoRangoTorre2));
		rangoTorre3 = new Rectangle(x - (tamanyoRangoTorre3/2), y-(tamanyoRangoTorre3/2), width+(tamanyoRangoTorre3), height+(tamanyoRangoTorre3));

		this.tierraID = tierraID;
		this.aireID= aireID;
	}
	
	public void draw (Graphics g){
		g.drawImage(Screen.tierra[tierraID],x, y, width, height, null);
		
		if(aireID != Value.AIRE_AIRE){
			g.drawImage(Screen.aire[aireID],x, y, width, height, null);
			
			
		}
	}
	
	public void physic() {
		
		if(enemigoDisparado != -1 && rangoTorre.intersects(Screen.enemigos[enemigoDisparado])) {
			disparando = true;
			
		}else {
			disparando = false;
		}
		
		if (!disparando) {
			if (aireID == Value.AIRE_TORRE_1 ) {
				for (int i = 0; i < Screen.enemigos.length; i++) {
					if (Screen.enemigos[i].inGame) {
						if (rangoTorre.intersects(Screen.enemigos[i])) {
							disparando = true;
							torre1Disparando = true;
							
							enemigoDisparado = i;
							enemigoDisparadoDinero = i;
							
						}
					}
				}
			}else if(aireID == Value.AIRE_TORRE_2) {
				for (int i = 0; i < Screen.enemigos.length; i++) {
					if (Screen.enemigos[i].inGame) {
						if (rangoTorre2.intersects(Screen.enemigos[i])) {
							disparando = true;
							torre2Disparando = true;
							
							enemigoDisparado = i;
							enemigoDisparadoDinero = i;
							
						}
					}
				}
			}else if(aireID == Value.AIRE_TORRE_3) {
				for (int i = 0; i < Screen.enemigos.length; i++) {
					if (Screen.enemigos[i].inGame) {
						if (rangoTorre3.intersects(Screen.enemigos[i])) {
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
				if(contadorBucle >= tiempoPerdida) {
					if(torre1Disparando) {
					Screen.enemigos[enemigoDisparado].enemigoPierdeVida(2);
					}else if(torre2Disparando){
						Screen.enemigos[enemigoDisparado].enemigoPierdeVida(6);
					}else if(torre3Disparando) {
						Screen.enemigos[enemigoDisparado].enemigoPierdeVida(1);
					}
					contadorBucle = 0;
					tiempoPerdida +=3;
				}else {
					contadorBucle += 1;
					
				}
				
				
				
			
				if(Screen.enemigos[enemigoDisparado].muerto()) {
					disparando = false;
					enemigoDisparado = -1;
					enemigoDisparadoDinero = -1;
				}
				
				
			}
		}
	
	public static void getMoney(int enemyID) {
		Screen.monedas += Value.dineroEnemigo[enemyID];
	}
	
	public void fight(Graphics g) {
		if (Screen.rango) {
			if (aireID == Value.AIRE_TORRE_1) {
				g.drawRect(rangoTorre.x, rangoTorre.y, rangoTorre.width, rangoTorre.height);
			}
		}
			if(disparando) {
				g.setColor(new Color(255,255,0));
				Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(5));
				g.drawLine(x + (width/2), y + (height/2), Screen.enemigos[enemigoDisparado].x + (Screen.enemigos[enemigoDisparado].width/2),Screen.enemigos[enemigoDisparado].y + (Screen.enemigos[enemigoDisparado].height/2) );
			}
	}
}


