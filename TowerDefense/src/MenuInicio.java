import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuInicio extends JFrame {

	private JPanel contentPane;
	Image image;

	/**
	 * Launch the application.
	 */
	static MenuInicio frame;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					 frame = new MenuInicio();
					frame.setTitle("TOWER DEFENSE GAME");
					
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MenuInicio() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 640);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JButton btnStart = new JButton("START");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuInicio.this.setVisible(false);
				MenuJugarCrearMapa.main(null);
			}
		});
		contentPane.add(btnStart, BorderLayout.SOUTH);
		JLabel label = new JLabel();
		label.setSize(this.getSize());
		ImageIcon icon = new ImageIcon("resources"+File.separator+"fondoMenuInicio.jpg");
		Icon icono = new ImageIcon(icon.getImage().getScaledInstance(label.getWidth(),label.getHeight(), Image.SCALE_DEFAULT));
		label.setIcon(icono);
		contentPane.add(label, BorderLayout.CENTER);
		
		Thread hilo = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) { 
					Icon icono = new ImageIcon(icon.getImage().getScaledInstance(label.getWidth(),label.getHeight(), Image.SCALE_DEFAULT));
					label.setIcon(icono);
					try {
						Thread.sleep(25);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
				}
			
		});
		hilo.start();
	
		
		
		
	}


}

