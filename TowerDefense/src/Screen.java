import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Screen extends JPanel implements Runnable{
	
	public Thread thread = new Thread(this);
	public static Image[] ground = new Image[100];
	public static Image[] air = new Image[100];
	public static int myWidth, myHeight;
	public static boolean isFirst = true;
	
	public static Room room;
	
	public static Save save;
	public Screen(){
		thread.start();
	}
	
	public void define(){
		room = new Room();
		save = new Save();
		
		for (int i =0; i<ground.length; i++){
			ground[i] = new ImageIcon("resources"+File.separator+"grass.png").getImage();
			ground[i] = createImage(new FilteredImageSource(ground[i].getSource(), new CropImageFilter(0, 32*i, 32, 32)));
		}
		for (int i =0; i<air.length; i++){
			air[i] = new ImageIcon("resources"+File.separator+"air.png").getImage();
			air[i] = createImage(new FilteredImageSource(air[i].getSource(), new CropImageFilter(0, 32*i, 32, 32)));
		}
		
		save.loadSave(new File("save"+File.separator+"mission1.file"));
	}
	
	public void paintComponent(Graphics g){
		if(isFirst){
			define();
			isFirst= false;
			
		}
		g.clearRect(0, 0, getWidth(), getHeight());
		
		room.draw(g); // dibujando room
		
	}
	public void run(){
		while(true){
			if(!isFirst){
				room.physic();
				
			}
			repaint();
		}
	}
}
