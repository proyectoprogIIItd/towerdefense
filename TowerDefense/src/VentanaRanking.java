import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import Utilidades.BD;
import Utilidades.Puntuacion;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JList;

public class VentanaRanking extends JFrame {

	private JPanel contentPane;
	private JList<Puntuacion> ranking;
	private TreeSet<Puntuacion> rankingOrdenado;
	public static VentanaRanking frame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new VentanaRanking();
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
	public VentanaRanking() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel botonera = new JPanel();
		contentPane.add(botonera, BorderLayout.SOUTH);
		botonera.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnMenu = new JButton("Menu");
		botonera.add(btnMenu);
		
		ArrayList<Puntuacion> puntuaciones = new ArrayList<Puntuacion>();
		puntuaciones = BD.puntuacionSelect(Login.s, null);
		Puntuacion[] listaM = new Puntuacion[puntuaciones.size()];
		if(!puntuaciones.isEmpty()) {
		for(int i = 0; i < puntuaciones.size();i++) {
			if(puntuaciones.get(i) == null) {
				
			}else {
			rankingOrdenado.add(puntuaciones.get(i));
			}
		}
		}else {
			rankingOrdenado.add(new Puntuacion("",0));
		}
		listaM = (Puntuacion[]) rankingOrdenado.toArray();
		ranking = new JList<Puntuacion>(listaM);
		ranking.setVisibleRowCount(4);  // defines cuántos nombre quieres que aparezcan a la vez
		JScrollPane scroll = new JScrollPane(ranking);  //creamos el scroll, pasamos el componente que queremos que tenga scroll
		JPanel panelRanking = new JPanel();
		panelRanking.add(scroll);
		contentPane.add(panelRanking, BorderLayout.CENTER);	
		
		btnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				MenuJugarCrearMapa.main(null);
			}
		});
		
		JButton btnSalir = new JButton("Salir");
		botonera.add(btnSalir);
		
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		

	}

}
