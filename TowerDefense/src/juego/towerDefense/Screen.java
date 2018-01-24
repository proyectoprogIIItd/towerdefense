package juego.towerDefense;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.WindowEvent;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Utilidades.BD;
import Utilidades.Puntuacion;

public class Screen extends JPanel implements Runnable{
	
	public Thread thread = new Thread(this);
	public static Image[] tierra = new Image[100];
	public static Image[] aire = new Image[100];
	public static Image[] res = new Image[100];
	public static Image[] enemigo = new Image[100];
	
	
	public static int myWidth = (int) Frame.size.getWidth();
	public static int myHeight = (int) Frame.size.getHeight();
	
	public static int monedas = 100;
	public static int vida = 10;
	public static int enemigosAsesinados =0;
	
	public static boolean isFirst = true;
	public static boolean muerto = false;
	
	public static boolean rango = false;
	
	public static Point raton = new Point(0,0);
	
	public static Room room;
	public static Save save;
	public static Tienda store;
	
	
	
	public static Enemigo[] enemigos = new Enemigo[100];
	
	
	public Screen(Frame frame){
		frame.addMouseListener(new MenejoRaton());
		frame.addMouseMotionListener(new MenejoRaton());
		thread.start();
	}
	
	public static void cerrarFrame() {
		Frame.frame.dispatchEvent(new WindowEvent(Frame.frame, WindowEvent.WINDOW_CLOSING));

	}
	

	public void define(){
		room = new Room();
		save = new Save();
		store = new Tienda();
		
		//carga en los arrays de las imagenes
		
		for (int i =0; i<tierra.length; i++){
			tierra[i] = new ImageIcon("resources"+File.separator+"ground.png").getImage();
			tierra[i] = createImage(new FilteredImageSource(tierra[i].getSource(), new CropImageFilter(0, 32*i, 32, 32)));
		}
		for (int i =0; i<aire.length; i++){
			aire[i] = new ImageIcon("resources"+File.separator+"air.png").getImage();
			aire[i] = createImage(new FilteredImageSource(aire[i].getSource(), new CropImageFilter(0, 32*i, 32, 32)));
		}
		res[0] = new ImageIcon("resources"+File.separator+"cell.png").getImage();
		res[1] = new ImageIcon("resources"+File.separator+"heart.png").getImage();
		res[2] = new ImageIcon("resources"+File.separator+"coin.png").getImage();
		res[3] = new ImageIcon("resources"+File.separator+"killedEnemy.png").getImage();
		
		enemigo[0] = new ImageIcon("resources"+File.separator+"pink_ghost.gif").getImage();
		enemigo[1] = new ImageIcon("resources"+File.separator+"blue_ghost.gif").getImage();
		enemigo[2] = new ImageIcon("resources"+File.separator+"red_ghost.gif").getImage();
		enemigo[3] = new ImageIcon("resources"+File.separator+"green_ghost.gif").getImage();
		
		
		//metodo que lee el fichero de texto
		save.loadSave(new File("save"+File.separator+"mission1.file"));
		
		for(int i = 0; i< enemigos.length; i++){
			enemigos[i] = new Enemigo();
		}
	}
	
	public void paintComponent(Graphics g){
		if(isFirst){
			myWidth = getWidth();
			myHeight = getHeight(); 
			define();
			isFirst= false;
			
		}
		g.setColor(new Color(80,80,80));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(new Color(0,0,0));
//		g.drawLine(room.block[0][0].x-1, 0, room.block[0][0].x-1, room.block[room.worldHeight-1][0].y + room.blockSize); // linea de la izquierda
//		g.drawLine(room.block[0][room.worldWidth-1].x + room.blockSize, 0, room.block[0][room.worldWidth-1].x + room.blockSize, room.block[room.worldHeight-1][0].y + room.blockSize); // linea de la derecha
		g.drawLine(room.bloque[0][0].x, room.bloque[room.mundoHeight-1][0].y + room.tamanyoBloque, room.bloque[0][room.mundoWidth-1].x+room.tamanyoBloque, room.bloque[room.mundoHeight-1][0].y + room.tamanyoBloque);// linea entre juego y eleccion de torres
		
		room.draw(g); // dibujando room
		
		for(int i = 0; i< enemigos.length; i++){
			if(enemigos[i].inGame){
				enemigos[i].draw(g);
			}
		}
		store.draw(g); // dibujando la tienda
		
		
		//cuando nos quedamos sin vidas guardamos puntuacion en BDD, mapa y usuario
		if(vida < 1) {
			thread.interrupt();
			ArrayList<Puntuacion> puntuaciones2 = new ArrayList<Puntuacion>();
			puntuaciones2 = BD.puntuacionUsuarioSelect(Login.s, Login.textField.getText(),MenuSeleccionMapa.mapaSelec);
			
			if (puntuaciones2.isEmpty()) {
			BD.puntuacionInsert(Login.s, MenuSeleccionMapa.mapaSelec, Login.textField.getText(), enemigosAsesinados);
			}else {
			Puntuacion p = puntuaciones2.get(0);
			if (enemigosAsesinados > p.getPuntuacion()) {
				BD.puntuacionUpdate(Login.s, Login.textField.getText(), enemigosAsesinados, MenuSeleccionMapa.mapaSelec);
			}
			}
			vida = 10;
			Frame.frame.removeAll();
			Frame.frame.dispose();
			VentanaRanking.main(null);
		}
	}
	
	
	/**genera numeros aleatorio entre 1-100 para la probabilidad de aparicion de los diferentes
	 * tipos de enemigos
	 * @return int
	 */
	public int aleatorio() {
		Random random = new Random(System.currentTimeMillis());
		int aleatorio = random.nextInt(100);
		return aleatorio;
	}
	public int tiempoSpawn = 1500;
	public int contadorSpawn = 0;
	
	
	
	//metodo para la frecuencia de aparicion de enemigos y controlando probabildades
	public void enemySpawner(){
		
		if(contadorSpawn >= tiempoSpawn){
			for(int i = 0; i <enemigos.length;i++){
				if(!enemigos[i].inGame){
					if(aleatorio() <= 50) {
						enemigos[i].apareceEnemigo(3);
					}else if(aleatorio() <= 80) {
						enemigos[i].apareceEnemigo(1);
					}else if(aleatorio() <= 95) {
						enemigos[i].apareceEnemigo(2);
					}else if(aleatorio() <=100) {
						enemigos[i].apareceEnemigo(0);
					}
				
					break;
				}
			}
			contadorSpawn = 0;
		}else{
			contadorSpawn += 1;
		}
		
	}
	
	//hilo para las fisicas de cada enemigo
	public void run(){
		while(true){
			if(!isFirst && vida >0){
				room.fisica();
				enemySpawner();
				for(int i = 0; i<enemigos.length; i++){
					if(enemigos[i].inGame){
						
						enemigos[i].physic();
						
					}
				}
			}
			repaint();
			
			try{
				Thread.sleep(1);
			}catch(Exception e){
			}
		}
	}
}