import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Store {
	
	public static int shopWidth = 8; //tienda con los botones
	public static int buttonSize = 64; //objetos a comprar en la tienda
	public static int cellSpace = 8; // espacio entre boton
	public static int awayFromGame = 10; // espacio fuera de la pantalla de juego
	public static int iconSize = 30; //tamaño corazon y moneda
	public static int iconSpace = 3;
	public static int itemIn = 6; //tamaño del borde
	public static int heldID = -1;
	public static int realID =-1;
	
	public static int[] buttonID = {Value.AIR_TOWER_1,Value.AIR_AIR,Value.AIR_AIR,Value.AIR_AIR,Value.AIR_AIR,Value.AIR_AIR,Value.AIR_AIR,Value.AIR_TRASHCAN};
	public static int[] buttonPrice = {10,0,0,0,0,0,0,0};
	
	
	public Rectangle[] button = new Rectangle[shopWidth];
	public Rectangle buttonHealth;
	public Rectangle buttonCoins;
	
	public boolean tieneObjeto = false;
	
	public Store(){
		define();
	}
	
	public void click(int mouseButton) {
		if(mouseButton == 1) { // 1 para botón izquierdo del ratón
			for(int i = 0; i<button.length;i++) {
				if(button[i].contains(Screen.mse)) {
					if(buttonID[i] != Value.AIR_AIR) {
					if(buttonID[i] == Value.AIR_TRASHCAN) {//Borra objeto
						tieneObjeto = false;
					}else {
						
					heldID = buttonID[i];
					realID = i;
					tieneObjeto = true;
					}
					}
				}
			}
			// permite poner las torres (no se puede en camino, inicio y final)
			if(tieneObjeto) {
				if(Screen.money >= buttonPrice[realID]) {
					for(int y=0; y < Screen.room.block.length; y ++) {
						for(int x = 0; x < Screen.room.block[0].length; x++) {
							if(Screen.room.block[y][x].contains(Screen.mse)) {
								if(Screen.room.block[y][x].groundID != Value.GROUND_ROAD && Screen.room.block[y][x].airID == Value.AIR_AIR && Screen.room.block[y][x].groundID != Value.GROUND_END && Screen.room.block[y][x].groundID != Value.GROUND_START) {
									Screen.room.block[y][x].airID = heldID;
									Screen.money -= buttonPrice[realID];
								}
							}
						}
					}
				}
			}
		}
	}
	public void define(){
		for(int i = 0; i<button.length; i++){
			button [i]= new Rectangle((Screen.myWidth/2)-((shopWidth* (buttonSize+cellSpace))/2)+ ((buttonSize + cellSpace)*i),(Screen.room.block[Screen.room.worldHeight-1][0].y)+Screen.room.blockSize + awayFromGame,buttonSize, buttonSize);
		}
		
		buttonHealth = new Rectangle(Screen.room.block[0][0].x-1, button[0].y,iconSize, iconSize );
		buttonCoins = new Rectangle(Screen.room.block[0][0].x-1, button[0].y + button[0].height-iconSize,iconSize, iconSize ); //dibujado teniendo como referencia el buttonHealth
	}
	
	public void draw (Graphics g){
		
		for (int i = 0; i < button.length; i++) {
			if(button[i].contains(Screen.mse)){
				g.setColor(new Color(255,255,255,100));
				g.fillRect(button[i].x,button[i].y, button[i].width, button[i].height);
			}
			g.drawImage(Screen.res[0],button[i].x, button[i].y, button[i].width, button[i].height, null);
			if(buttonID[i] != Value.AIR_AIR) g.drawImage(Screen.air[buttonID[i]], button[i].x + itemIn, button[i].y + itemIn, button[i].width -(itemIn*2), button[i].height - (itemIn*2), null);
			if(buttonPrice[i]>0) {
				g.setColor(new Color(255,255,255));
				g.setFont(new Font("Courier New", Font.BOLD, 20));
				g.drawString(buttonPrice[i] + "" , button[i].x + itemIn, button[i].y + itemIn+10); //+10 para corregir posicionamiento del coste del objeto
			}
		}
		g.drawImage(Screen.res[1],buttonHealth.x + 40, buttonHealth.y, buttonHealth.width, buttonHealth.height, null);//+40 para centrarlo
		g.drawImage(Screen.res[2], buttonCoins.x + 40, buttonCoins.y, buttonCoins.width, buttonCoins.height, null); //+40 para centrarlo
		g.setFont(new Font("Courier New", Font.BOLD, 20));
		g.setColor(new Color(255, 255, 255));
		g.drawString("" +Screen.health, buttonHealth.x + 50+ buttonHealth.width + iconSpace,buttonHealth.y+20);
		g.drawString("" +Screen.money, buttonCoins.x + 50+ buttonCoins.width + iconSpace,buttonCoins.y+20);
		
		if(tieneObjeto) {
			g.drawImage(Screen.air[heldID], Screen.mse.x - ((button[0].width-(itemIn*2))/2)+ itemIn, Screen.mse.y  - ((button[0].width-(itemIn*2))/2)+itemIn, button[0].width -(itemIn*2), button[0].height - (itemIn*2), null);
		}
	}
}