package juego.towerDefense;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Tienda {
	
	public static int tiendaWidth = 4; //tienda con los botones
	public static int tamanyoBoton = 64; //objetos a comprar en la tienda
	public static int espacioCelda = 8; // espacio entre boton
	public static int espacioExtra = 10; // espacio fuera de la pantalla de juego
	public static int tamanyoIcono = 30; //tamaño corazon y moneda
	public static int tamanyoIcono2 = 45;
	public static int espacioIcono = 3;
	public static int itemIn = 6; //tamaño del borde
	public static int esteID = -1;
	public static int realID =-1;
	
	public static int[] botonID = {Value.AIRE_TORRE_1,Value.AIRE_TORRE_2,Value.AIRE_TORRE_3,Value.AIRE_PAPELERA};
	public static int[] precioBoton = {20,40,30,0,0,0,0,0};
	
	
	public Rectangle[] boton = new Rectangle[tiendaWidth];
	public Rectangle rectanguloVida;
	public Rectangle rectanguloMonedas;
	public Rectangle rectanguloMuertes;
	
	public boolean tieneObjeto = false;
	
	public Tienda(){
		define();
	}
	
	public void click(int mouseButton) {
		if(mouseButton == 1) { // 1 para botón izquierdo del ratón
			for(int i = 0; i<boton.length;i++) {
				if(boton[i].contains(Screen.raton)) {
					if(botonID[i] != Value.AIRE_AIRE) {
					if(botonID[i] == Value.AIRE_PAPELERA) {//Borra objeto
						tieneObjeto = false;
					}else {
						
					esteID = botonID[i];
					realID = i;
					tieneObjeto = true;
					}
					}
				}
			}
			// permite poner las torres (no se puede en camino, inicio y final)
			if(tieneObjeto) {
				if(Screen.monedas >= precioBoton[realID]) {
					for(int y=0; y < Screen.room.bloque.length; y ++) {
						for(int x = 0; x < Screen.room.bloque[0].length; x++) {
							if(Screen.room.bloque[y][x].contains(Screen.raton)) {
								if(Screen.room.bloque[y][x].tierraID != Value.SUELO_CARRETERA && Screen.room.bloque[y][x].aireID == Value.AIRE_AIRE && Screen.room.bloque[y][x].tierraID != Value.SUELO_END && Screen.room.bloque[y][x].tierraID != Value.SUELO_START) {
									Screen.room.bloque[y][x].aireID = esteID;
									Screen.monedas -= precioBoton[realID];
								}
							}
						}
					}
				}
			}
		}
	}
	public void define(){
		for(int i = 0; i<boton.length; i++){
			boton [i]= new Rectangle((Screen.myWidth/2)-((tiendaWidth* (tamanyoBoton+espacioCelda))/2)+ ((tamanyoBoton + espacioCelda)*i),(Screen.room.bloque[Screen.room.mundoHeight-1][0].y)+Screen.room.tamanyoBloque + espacioExtra,tamanyoBoton, tamanyoBoton);
		}
		
		rectanguloVida = new Rectangle(Screen.room.bloque[0][0].x-1, boton[0].y,tamanyoIcono, tamanyoIcono );
		rectanguloMonedas = new Rectangle(Screen.room.bloque[0][0].x-1, boton[0].y + boton[0].height-tamanyoIcono,tamanyoIcono, tamanyoIcono );
		rectanguloMuertes = new Rectangle(Screen.room.bloque[0][0].x-1, boton[0].y,tamanyoIcono2, tamanyoIcono2 );
		//dibujado teniendo como referencia el buttonHealth
	}
	
	public void draw (Graphics g){
		
		for (int i = 0; i < boton.length; i++) {
			if(boton[i].contains(Screen.raton)){
				g.setColor(new Color(255,255,255,100));
				g.fillRect(boton[i].x,boton[i].y, boton[i].width, boton[i].height);
			}
			g.drawImage(Screen.res[0],boton[i].x, boton[i].y, boton[i].width, boton[i].height, null);
			if(botonID[i] != Value.AIRE_AIRE) g.drawImage(Screen.aire[botonID[i]], boton[i].x + itemIn, boton[i].y + itemIn, boton[i].width -(itemIn*2), boton[i].height - (itemIn*2), null);
			if(precioBoton[i]>0) {
				g.setColor(new Color(255,255,255));
				g.setFont(new Font("Courier New", Font.BOLD, 20));
				g.drawString(precioBoton[i] + "" , boton[i].x + itemIn, boton[i].y + itemIn+10); //+10 para corregir posicionamiento del coste del objeto
			}
		}
		g.drawImage(Screen.res[1],rectanguloVida.x + 40, rectanguloVida.y, rectanguloVida.width, rectanguloVida.height, null);//+40 para centrarlo
		g.drawImage(Screen.res[2], rectanguloMonedas.x + 40, rectanguloMonedas.y, rectanguloMonedas.width, rectanguloMonedas.height, null); //+40 para centrarlo
		g.drawImage(Screen.res[3], rectanguloMuertes.x + 750, rectanguloMuertes.y, rectanguloMuertes.width, rectanguloMuertes.height, null); //+40 para centrarlo

		g.setFont(new Font("Courier New", Font.BOLD, 20));
		g.setColor(new Color(255, 255, 255));
		g.drawString("" +Screen.vida, rectanguloVida.x + 50+ rectanguloVida.width + espacioIcono,rectanguloVida.y+20);
		g.drawString("" +Screen.monedas, rectanguloMonedas.x + 50+ rectanguloMonedas.width + espacioIcono,rectanguloMonedas.y+20);
		g.drawString("" +Screen.enemigosAsesinados, rectanguloMuertes.x + 765+ rectanguloMuertes.width + espacioIcono,rectanguloMuertes.y+30);
		
		if(tieneObjeto) {
			g.drawImage(Screen.aire[esteID], Screen.raton.x - ((boton[0].width-(itemIn*2))/2)+ itemIn, Screen.raton.y  - ((boton[0].width-(itemIn*2))/2)+itemIn, boton[0].width -(itemIn*2), boton[0].height - (itemIn*2), null);
		}
	}
}