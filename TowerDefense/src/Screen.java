import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Screen extends JPanel implements Runnable{
	
	public Thread thread = new Thread(this);
	public static Image[] ground = new Image[100];
	public static Image[] air = new Image[100];
	public static Image[] res = new Image[100];
	public static Image[] enemy = new Image[100];
	
	
	public static int myWidth = (int) Frame.size.getWidth();
	public static int myHeight = (int) Frame.size.getHeight();
	
	public static int money = 100;
	public static int health = 10;
	
	public static boolean isFirst = true;
	public static boolean isDead = false;
	
	public static boolean isDebug = true;
	
	public static Point mse = new Point(0,0);
	
	public static Room room;
	public static Save save;
	public static Store store;
	
	
	
	public static Enemy[] enemies = new Enemy[100];
	
	
	public Screen(Frame frame){
		frame.addMouseListener(new KeyHandle());
		frame.addMouseMotionListener(new KeyHandle());
		thread.start();
	}
	

	public void define(){
		room = new Room();
		save = new Save();
		store = new Store();
		
		
		
		for (int i =0; i<ground.length; i++){
			ground[i] = new ImageIcon("resources"+File.separator+"ground.png").getImage();
			ground[i] = createImage(new FilteredImageSource(ground[i].getSource(), new CropImageFilter(0, 32*i, 32, 32)));
		}
		for (int i =0; i<air.length; i++){
			air[i] = new ImageIcon("resources"+File.separator+"air.png").getImage();
			air[i] = createImage(new FilteredImageSource(air[i].getSource(), new CropImageFilter(0, 32*i, 32, 32)));
		}
		res[0] = new ImageIcon("resources"+File.separator+"cell.png").getImage();
		res[1] = new ImageIcon("resources"+File.separator+"heart.png").getImage();
		res[2] = new ImageIcon("resources"+File.separator+"coin.png").getImage();
		
		enemy[0] = new ImageIcon("resources"+File.separator+"pink_ghost.gif").getImage();
		enemy[1] = new ImageIcon("resources"+File.separator+"blue_ghost.gif").getImage();
		enemy[2] = new ImageIcon("resources"+File.separator+"red_ghost.gif").getImage();
		enemy[3] = new ImageIcon("resources"+File.separator+"green_ghost.gif").getImage();
		
		save.loadSave(new File("save"+File.separator+"mission1.file"));
		
		for(int i = 0; i< enemies.length; i++){
			enemies[i] = new Enemy();
			//enemies[i].SpawnEnemy(0);
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
		g.drawLine(room.block[0][0].x, room.block[room.worldHeight-1][0].y + room.blockSize, room.block[0][room.worldWidth-1].x+room.blockSize, room.block[room.worldHeight-1][0].y + room.blockSize);// linea entre juego y eleccion de torres
		
		room.draw(g); // dibujando room
		
		for(int i = 0; i< enemies.length; i++){
			if(enemies[i].inGame){
				enemies[i].draw(g);
			}
		}
		store.draw(g); // dibujando la tienda
		
		if(health < 1) {
			g.setColor(new Color(240, 20, 20));
			g.fillRect(0, 0, myWidth, myHeight);
			g.setColor(new Color(255, 255, 255));
			g.setFont(new Font("Courier New",Font.BOLD, 80));
			g.drawString("GAME OVER", 270, 200);
			thread.interrupt();
			Frame.frame.dispose();
			VentanaRanking.main(null);
		}
	}
	
	public int aleatorio() {
		Random random = new Random(System.currentTimeMillis());
		int aleatorio = random.nextInt(4);
		return aleatorio;
	}
	
	public int spawnTime = 1500;
	public int spawnFrame = 0;
	
	public void enemySpawner(){
		
		if(spawnFrame >= spawnTime){
			for(int i = 0; i <enemies.length;i++){
				if(!enemies[i].inGame){
					enemies[i].SpawnEnemy(aleatorio());
					break;
				}
			}
			spawnFrame = 0;
		}else{
			spawnFrame += 1;
		}
		
	}
	
	
	public void run(){
		while(true){
			if(!isFirst && health >0){
				room.physic();
				enemySpawner();
				for(int i = 0; i<enemies.length; i++){
					if(enemies[i].inGame){
						
						enemies[i].physic();
						
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
