import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;

public class Frame extends JFrame{
	public static String title = "Tower Defense";
	public static Dimension size = new Dimension (960, 640);
	public static Frame frame;
	
	
	
	public Frame(){
		setTitle(title);
		setSize(size);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		init();
		
	}
	
	
	public static void main (String[] args){
		frame = new Frame();
		
	}
	
	public void init(){
		
		setLayout(new GridLayout(1, 1, 0, 0));
		Screen screen = new Screen(this);
		add(screen);
//		screen.setMinimumSize(new Dimension(300 , 200));
//		screen.setMaximumSize(new Dimension(300,200));
//		screen.setPreferredSize(new Dimension(300 , 200));
		setVisible(true);
//		pack();
		
	}
	
	
	
	
	
}
