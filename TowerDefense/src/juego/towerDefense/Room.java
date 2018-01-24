package juego.towerDefense;
import java.awt.Graphics;

public class Room {
	
	public int mundoWidth = 15;
	public int mundoHeight =8;
	public int tamanyoBloque = 64;
	
	public Bloque[][] bloque;
	
	public Room(){
		define();
	}
	//definimos dimensions del juego y de cada bloque por separado
	public void define(){
		bloque = new Bloque[mundoHeight][mundoWidth];
		for(int y = 0; y<bloque.length; y++){
			for(int x = 0; x<bloque[0].length;x++){
				bloque[y][x] = new Bloque(x * tamanyoBloque, y * tamanyoBloque, tamanyoBloque, tamanyoBloque,Value.SUELO_HIERBA,Value.AIRE_AIRE);
			}
		}
	}
	
	public void fisica(){
		for (int y = 0; y < bloque.length; y++) {
			for(int x = 0; x<bloque[0].length; x++) {
				bloque[y][x].physic();
			}
		}
	}
	//dibujamos los bloques
	public void draw (Graphics g){
		for(int y = 0; y<bloque.length; y++){
			for(int x = 0; x<bloque[0].length;x++){
				bloque[y][x].draw(g);
			}
		}	//se usan dos for para que se renderice por separado el rectangulo si no se veria solo parte del rectangulo
		//dibujamos el rango y el disparo
		for(int y = 0 ; y<bloque.length; y++) {
			for(int x = 0; x<bloque[0].length; x++) {
				bloque[y][x].fight(g);
			}
		}
	}
}
