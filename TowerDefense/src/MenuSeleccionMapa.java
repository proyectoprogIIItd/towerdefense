import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Utilidades.BD;

public class MenuSeleccionMapa extends JFrame {

	private JPanel contentPane;
	private JList<String> listaMapas;
	private JPanel panelLista;	
	private JPanel panelBotones;	
	private JButton Atras = new JButton("Atras");
	private JButton Jugar = new JButton("Jugar");
	ArrayList<String> mapas;
	public static MenuSeleccionMapa frame;
	public static String mapaSelec;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new MenuSeleccionMapa();
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
	public MenuSeleccionMapa() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		mapas = BD.mapaNombreSelect(Login.s, null);
		String[] listaM = new String[mapas.size()];
		for(int i = 0; i < mapas.size();i++) {
			listaM[i] = mapas.get(i);
		}
		listaMapas = new JList<String>(listaM);
		listaMapas.setVisibleRowCount(4);  // defines cuántos nombre quieres que aparezcan a la vez
		JScrollPane scroll = new JScrollPane(listaMapas);  //creamos el scroll, pasamos el componente que queremos que tenga scroll
		panelLista = new JPanel();
		panelLista.add(scroll);
		
		Atras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				MenuJugarCrearMapa.main(null);
			}
		});
		
		Jugar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//aqui va el codigo de crear mapa
				mapaSelec = listaMapas.getSelectedValue();
				if(listaMapas.getSelectedValuesList().size() == 1) {
				String mapa = (String) listaMapas.getSelectedValue();
				frame.dispose();
				VentanaJuego.main(null);
				}else if(listaMapas.getSelectedValuesList().size() == 0) {
				JOptionPane.showMessageDialog(null, "Escoge un mapa.");
				}else if(listaMapas.getSelectedValuesList().size() >= 2) {
				JOptionPane.showMessageDialog(null, "Escoge un único mapa.");
				}
			}
		});
		panelBotones= new JPanel();
		panelBotones.add(Atras);
		panelBotones.add(Jugar);
		
		add(panelLista, BorderLayout.NORTH);
		add(panelBotones, BorderLayout.SOUTH);
		
		
	}

}