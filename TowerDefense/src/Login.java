import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextField;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Utilidades.BD;
import Utilidades.Usuario;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	
	private JPanel contentPane;
	protected static JTextField textField;
	protected static JPasswordField passwordField;
	private ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
	
	public static Connection con;
	public static Statement s;
	
	Properties properties = new Properties();


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
					con = BD.initBD("Login");
					s = BD.usarBD(con);
					BD.usarCrearTablasBD(con);
					
					
					
	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		
		try {
			properties.load(new FileInputStream("Propiedades"));
		} catch (FileNotFoundException e3) {

		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblIntroduceTuNombre = new JLabel("Introduce tu nombre de usuario y contraseña");
		panel.add(lblIntroduceTuNombre);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		JLabel lblSiNoEsts = new JLabel("Si no estás registrado tienes que registrarte primero");
		panel_1.add(lblSiNoEsts);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(null);
		
		JLabel lblNombreDeUsuario = new JLabel("Nombre de usuario:");
		lblNombreDeUsuario.setBounds(61, 49, 130, 16);
		panel_2.add(lblNombreDeUsuario);
		
		JLabel lblContrasea = new JLabel("Contraseña:");
		lblContrasea.setBounds(89, 123, 102, 16);
		panel_2.add(lblContrasea);
		
		textField = new JTextField();
		textField.setBounds(256, 44, 130, 26);
		panel_2.add(textField);
		textField.setColumns(10);
		
		
		
		passwordField = new JPasswordField();
		passwordField.setBounds(256, 118, 130, 26);
		panel_2.add(passwordField);
		
		textField.setText(properties.getProperty("Nombre de usuario"));
		passwordField.setText(properties.getProperty("Password"));
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usuarios = BD.usuarioSelect(s, "Nick = '" + textField.getText() + "' " + "AND " + "Password = '" + passwordField.getText() + "'");
				if (!usuarios.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Has iniciado sesión correctamente");
					
					//VentanaVisualizacion.main(null);
					//Login.this.setVisible(false);
					MenuInicio.main(null);
					Login.this.setVisible(false);
					
					properties.put("Nombre de usuario", textField.getText());
					properties.put("Password", passwordField.getText());
					try {
						FileOutputStream out = new FileOutputStream("Propiedades");
						properties.save(out, null);
					} catch (Exception e2) {
					}
					
					
					
					
				} else if (usuarios.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Nombre y/o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
				}

				
			}
		});
		btnLogin.setBounds(74, 168, 117, 29);
		panel_2.add(btnLogin);
		
		JButton btnRegistro = new JButton("Registro");
		btnRegistro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usuarios = BD.usuarioSelect(s, "Nick = '"+textField.getText()+"'");
				
				if(textField.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Introduce un usuario", "Error", JOptionPane.ERROR_MESSAGE);
				}else if(passwordField.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Introduce una contraseña", "Error", JOptionPane.ERROR_MESSAGE);
				}else if(!usuarios.isEmpty()){
					JOptionPane.showMessageDialog(null, "Usuario en uso", "Error", JOptionPane.ERROR_MESSAGE);
				}else if(usuarios.isEmpty()){
					Usuario u = new Usuario(textField.getText(), passwordField.getText(), textField.getText());
					BD.usuarioInsert(s, u);
					
					try {
						s.execute("insert into puntuaciones values('"+u.getNick()+"', "+ 0 +")");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						s.execute("insert into monedasExtras values('"+u.getNick()+"', "+ 0 +")");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Te has registrado correctamente");
				}
				
				
			}
		});
		btnRegistro.setBounds(258, 168, 117, 29);
		panel_2.add(btnRegistro);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				BD.cerrarBD(con, s);
			}
		});	
	}
}
