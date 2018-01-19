import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class nombreGuardado extends JFrame {

	private JPanel contentPane;
	private static int[][] arrayMapa ;
	private static String Lvl ;
	public void setArray (int[][] arrayMapa) {
		this.arrayMapa = arrayMapa;
	}
	
	public void setLvl(String Lvl) {
		this.Lvl = Lvl;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JButton guardar = new JButton("Guardar");
			    	JTextField nombre = new JTextField();
			    	if (!nombre.getText().equals(null)) {
			    	for (int i = 0; i < arrayMapa.length; i++) {
						for (int j = 0; j<arrayMapa[7].length; j++) {
							System.out.print(arrayMapa[i][j] + ";");
						}
						System.out.println(" ");
					}
					System.out.println(Lvl+";");
					System.out.println(nombre.getText()+";");
			    	}
					nombreGuardado frame = new nombreGuardado();
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
	public nombreGuardado() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
