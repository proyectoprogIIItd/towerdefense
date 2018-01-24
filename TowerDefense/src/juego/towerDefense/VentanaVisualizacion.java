package juego.towerDefense;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Utilidades.BD;
import Utilidades.Puntuacion;
import Utilidades.Usuario;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaVisualizacion extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_6;
	private JButton btnReset;
	private JButton btnActualizar;
	private JButton button_1;
	private JButton btnContinuar;
	
	
	
	Connection con = Login.con;
	Statement s = Login.s;
	ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
	ArrayList<Puntuacion> puntuaciones = new ArrayList<Puntuacion>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaVisualizacion frame = new VentanaVisualizacion();
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
	public VentanaVisualizacion() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 600, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblTusDatosDe = new JLabel("TUS DATOS DE USUARIO");
		panel.add(lblTusDatosDe);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
		
		JLabel lblNombreDeUsuario = new JLabel("Nombre: ");
		lblNombreDeUsuario.setBounds(23, 122, 139, 16);
		panel_1.add(lblNombreDeUsuario);
		
		JLabel lblNombre = new JLabel("Nombre de usuario:");
		lblNombre.setBounds(23, 31, 130, 16);
		panel_1.add(lblNombre);
		
		JLabel lblContrasea = new JLabel("Contraseña:");
		lblContrasea.setBounds(23, 159, 110, 16);
		panel_1.add(lblContrasea);
		
		JLabel lblPuntuacin = new JLabel("Puntuación:");
		lblPuntuacin.setBounds(23, 199, 94, 16);
		panel_1.add(lblPuntuacin);
		
		JLabel lblActual = new JLabel("ACTUAL");
		lblActual.setBounds(203, 72, 61, 16);
		panel_1.add(lblActual);
		
		JLabel lblNuevo = new JLabel("NUEVO");
		lblNuevo.setBounds(345, 72, 61, 16);
		panel_1.add(lblNuevo);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setEditable(false);
		textField.setBounds(164, 117, 130, 26);
		panel_1.add(textField);
		textField.setColumns(10);
		
		usuarios = BD.usuarioSelect(s, "nick = '" +Login.textField.getText()+ "'");
		textField.setText(usuarios.get(0).getNick());
		
		textField_1 = new JTextField();
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setEditable(false);
		textField_1.setBounds(232, 26, 130, 26);
		panel_1.add(textField_1);
		textField_1.setColumns(10);
		
		textField_1.setText(usuarios.get(0).getNombre());
		
		textField_2 = new JTextField();
		textField_2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_2.setEditable(false);
		textField_2.setBounds(164, 154, 130, 26);
		panel_1.add(textField_2);
		textField_2.setColumns(10);

		textField_2.setText(usuarios.get(0).getPassword());
		
		textField_3 = new JTextField();
		textField_3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_3.setEditable(false);
		textField_3.setBounds(164, 189, 130, 26);
		panel_1.add(textField_3);
		textField_3.setColumns(10);
		
		puntuaciones = BD.puntuacionSelect(s, "Usuario_nick = '" + Login.textField.getText()+"'");
		textField_3.setText(puntuaciones.get(0).getPuntuacion()+"");
		
		textField_4 = new JTextField();
		textField_4.setBounds(304, 117, 130, 26);
		panel_1.add(textField_4);
		textField_4.setColumns(10);
		
		textField_6 = new JTextField();
		textField_6.setBounds(304, 154, 130, 26);
		panel_1.add(textField_6);
		textField_6.setColumns(10);
		
		btnReset = new JButton("RESET");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql_puntuacion = "update Puntuaciones set Puntuacion = " + 0 + " where Nick = '" + textField_1.getText() +"'";
				try {
					s.executeUpdate(sql_puntuacion);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				textField_3.setText(0+"");
			}
		});
		btnReset.setBounds(306, 186, 117, 29);
		panel_1.add(btnReset);
		
		
		
		btnActualizar = new JButton("ACTUALIZAR");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql_nombre = "update Usuario set Nombre = '" + textField_3.getText()+ "' where Nick = '" + textField_1.getText() +"'";
				
				
				try {
					s.executeUpdate(sql_nombre);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				textField.setText(textField_4.getText());
				textField_4.setText("");
			}
		});
		btnActualizar.setBounds(446, 117, 117, 29);
		panel_1.add(btnActualizar);
		
		button_1 = new JButton("ACTUALIZAR");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql_pass = "update Usuario set Password = '" + textField_6.getText()+ "' where Nick = '" + textField_1.getText() +"'";
				try {
					s.executeUpdate(sql_pass);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				textField_2.setText(textField_6.getText());
				textField_6.setText("");
			}
				
		});
		button_1.setBounds(446, 154, 117, 29);
		panel_1.add(button_1);
		
		btnContinuar = new JButton("CONTINUAR");
		btnContinuar.setBounds(446, 223, 117, 29);
		panel_1.add(btnContinuar);
	}

}
