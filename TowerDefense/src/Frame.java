import java.awt.Dimension;

import javax.swing.*;

public class Frame extends JFrame{
	public static String title = "Tower Defense";
	public static Dimension size = new Dimension (800, 500);
	
	public Frame(){
		setTitle(title);
		setSize(size);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		init();
		
	}
	public static void main (String[] args){
		Frame frame = new Frame();
		
	}
	
	public void init(){
		setVisible(true);
	}
	
	
	
}
