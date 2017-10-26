import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Screen extends JPanel implements Runnable{
	
	public Thread thread = new Thread(this);
	public static int myWidth, myHeight;
	public static boolean isFirst = true;
	
	public static Room room;
	public Screen(){
		thread.start();
	}
	
	public void define(){
		room = new Room();
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
