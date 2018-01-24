package juego.towerDefense;
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
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JList;

public class VentanaRanking extends JFrame {

	private JPanel contentPane;
	private JList<String> ranking;
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
	//este metodo es la sustitucion de un treeSet: reordena el array que le pasas por parametros y te lo mete en uno nuevo
	public ArrayList<Puntuacion> reordenar(ArrayList<Puntuacion> p) {
		
		ArrayList<Puntuacion> ret = new ArrayList<Puntuacion>();
		int tamanyo = p.size();
		for (int i = 0; i < tamanyo; i++) {
			Puntuacion mayor = new Puntuacion();
		    mayor =	p.get(0);
		    for(int j = 0; j < p.size(); j++) {
		    	if(mayor.getPuntuacion() < p.get(j).getPuntuacion()) {
		    		mayor = p.get(j);
		    	}else if(mayor.getPuntuacion() == p.get(j).getPuntuacion()) {
		    		char a = mayor.getUsuario_nick().charAt(0);
		    		char b = p.get(j).getUsuario_nick().charAt(0);		    		
		    		if(b < a) {
		    			mayor = p.get(j);
		    		}
		    	}
		    }
		    ret.add(mayor);
		    p.remove(mayor);
		}
		
		
		
		return ret;
	}
	
	
	
	
	public VentanaRanking() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		setTitle("GAME OVER ---- RANKING");
		JPanel botonera = new JPanel();
		contentPane.add(botonera, BorderLayout.SOUTH);
		botonera.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnMenu = new JButton("Menu");
		botonera.add(btnMenu);
		//aqui rellenas la Jlist cargando las puntuaciones de ese mapa desde la BD y reordenandolas con el metodo
		ArrayList<Puntuacion> puntuaciones = new ArrayList<Puntuacion>();
		ArrayList<Puntuacion> puntuaciones2 = new ArrayList<Puntuacion>();
		puntuaciones2 = BD.puntuacionSelect(Login.s, MenuSeleccionMapa.mapaSelec);
		if (puntuaciones2.isEmpty()) {
			puntuaciones2.add(new Puntuacion("Nadie",0));
		}
		puntuaciones = reordenar(puntuaciones2);
		String[] listaM = new String[puntuaciones.size()];
		for (int i = 0;i<puntuaciones.size();i++) {
			Puntuacion p = new Puntuacion();
			p.setPuntuacion(puntuaciones.get(i).getPuntuacion());
			p.setUsuario_nick(puntuaciones.get(i).getUsuario_nick());
			listaM[i] = "Usuario: "+p.getUsuario_nick()+" Puntos: "+p.getPuntuacion();
		}
		ranking = new JList<String>(listaM);
		ranking.setVisibleRowCount(5);  // defines cuántos nombre quieres que aparezcan a la vez
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
				BD.cerrarBD(Login.con, Login.s);
				System.exit(0);
			}
		});
		

	}

}
