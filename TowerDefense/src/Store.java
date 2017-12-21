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
	
	
	public Rectangle[] button = new Rectangle[shopWidth];
	public Rectangle buttonHealth;
	public Rectangle buttonCoins;
	
	public Store(){
		define();
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
		}
		g.drawImage(Screen.res[1],buttonHealth.x + 40, buttonHealth.y, buttonHealth.width, buttonHealth.height, null);//+40 para centrarlo
		g.drawImage(Screen.res[2], buttonCoins.x + 40, buttonCoins.y, buttonCoins.width, buttonCoins.height, null); //+40 para centrarlo
		g.setFont(new Font("Courier New", Font.BOLD, 20));
		g.setColor(new Color(255, 255, 255));
		g.drawString("" +Screen.health, buttonHealth.x + 50+ buttonHealth.width + iconSpace,buttonHealth.y+20);
		g.drawString("" +Screen.money, buttonCoins.x + 50+ buttonCoins.width + iconSpace,buttonCoins.y+20);
	}
}