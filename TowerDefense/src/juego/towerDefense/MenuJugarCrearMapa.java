package juego.towerDefense;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ActionMapUIResource;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuJugarCrearMapa extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static MenuJugarCrearMapa frame;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new MenuJugarCrearMapa();
					
					frame.setVisible(true);
					actualizarBotones();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	static JPanel panel;
	static JPanel panel_1;
	static JButton botonJugar;
	static JButton botonMapa;
	
	public MenuJugarCrearMapa() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 640);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new GridLayout(2, 1));
		setContentPane(contentPane);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.WEST);
		panel.setLayout(new BorderLayout(0, 0));
		
		
		
		
		botonJugar = new JButton();
		botonJugar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuSeleccionMapa.main(null);
				frame.dispose();
			}
		});
		
		panel.add(botonJugar);
		
		panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		botonMapa = new JButton();
		botonMapa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Creador.main(null);
				frame.dispose();

			}
		});
		
		panel_1.add(botonMapa);
		
	}
	
	public static void actualizarBotones() {
		ImageIcon icon = new ImageIcon("resources"+File.separator+"fondoMenuJugar.png");
		ImageIcon icon2 = new ImageIcon("resources"+File.separator+"fondoMenuCrearMapa.png");
		Thread hilo = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) { 
					
					Icon icono = new ImageIcon(icon.getImage().getScaledInstance(botonJugar.getWidth(),botonJugar.getHeight(), Image.SCALE_DEFAULT));
					Icon icono2 = new ImageIcon(icon2.getImage().getScaledInstance(botonJugar.getWidth(),botonJugar.getHeight(), Image.SCALE_DEFAULT));

					botonJugar.setIcon(icono);
					botonMapa.setIcon(icono2);
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