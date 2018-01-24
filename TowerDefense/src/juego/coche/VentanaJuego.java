package juego.coche;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;

import Utilidades.BD;
import Utilidades.Usuario;
import juego.towerDefense.Frame;
import juego.towerDefense.Screen;



public class VentanaJuego extends JFrame {

	public static int monedas = 0;
	private static final long serialVersionUID = 1L;  // Para serialización
	JPanel pPrincipal;         // Panel del juego (layout nulo)
	MundoJuego miMundo;        // Mundo del juego
	CocheJuego miCoche;        // Coche del juego
	MiRunnable miHilo = null;  // Hilo del bucle principal de juego	
	boolean[] teclas = new boolean [4];
	//para poder utilizar el label en el hilo, lo colocamos aqui junto a las demas variables de clase
	JLabel lMensaje;
	/** Constructor de la ventana de juego. Crea y devuelve la ventana inicializada
	 * sin coches dentro
	 */
	public VentanaJuego() {
		// Liberación de la ventana por defecto al cerrar
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		// Creación contenedores y componentes
		pPrincipal = new JPanel();
		JPanel pBotonera = new JPanel();
		this.lMensaje = new JLabel(" ");
		// Formato y layouts
		pPrincipal.setLayout( null );
		pPrincipal.setBackground( Color.white );
		// Añadido de componentes a contenedores
		add( pPrincipal, BorderLayout.CENTER );
	    pBotonera.add(lMensaje);
	    add(pBotonera, "South");
		add( pBotonera, BorderLayout.SOUTH );
		// Formato de ventana
		setSize( 1000, 750 );
		setResizable( false );

		
		// Añadido para que también se gestione por teclado con el KeyListener
		pPrincipal.addKeyListener( new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_UP: {
						teclas[0] = true;
						break;
					}
					case KeyEvent.VK_DOWN: {
						teclas[1] = true;
						break;
					}
					case KeyEvent.VK_LEFT: {
						teclas[2] = true;
						break;
					}
					case KeyEvent.VK_RIGHT: {
						teclas[3] = true;
						break;
					}
				}
			}
			
			public void keyReleased(KeyEvent e){
				if( e.getKeyCode() == KeyEvent.VK_UP) {
					if(e.getID() == KeyEvent.KEY_RELEASED){
						teclas[0] = false;
					}
				}
				
				if( e.getKeyCode() == KeyEvent.VK_DOWN) {
					if(e.getID() == KeyEvent.KEY_RELEASED){
						teclas[1] = false;
					}
				}
				
				if( e.getKeyCode() == KeyEvent.VK_LEFT) {
					if(e.getID() == KeyEvent.KEY_RELEASED){
						teclas[2] = false;
					}
				}
				
				if( e.getKeyCode() == KeyEvent.VK_RIGHT) {
					if(e.getID() == KeyEvent.KEY_RELEASED){
						teclas[3] = false;
					}
				}

			}
		});
		pPrincipal.setFocusable(true);
		pPrincipal.requestFocus();
		pPrincipal.addFocusListener( new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				pPrincipal.requestFocus();
			}
		});
		// Cierre del hilo al cierre de la ventana
		addWindowListener( new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (miHilo!=null) miHilo.acaba();
			}
		});
	}
	
	/** Programa principal de la ventana de juego
	 * @param args
	 */
	final static VentanaJuego miVentana = new VentanaJuego();
	public static void main(String[] args) {
		// Crea y visibiliza la ventana con el coche
		try {
			
			miVentana.setVisible( true );				
			miVentana.miMundo = new MundoJuego( miVentana.pPrincipal );
			miVentana.miMundo.creaCoche( 150, 100 );
			miVentana.miCoche = miVentana.miMundo.getCoche();
			miVentana.miCoche.setPiloto( "Fernando Alonso" );
			// Crea el hilo de movimiento del coche y lo lanza
			miVentana.miHilo = miVentana.new MiRunnable();  // Sintaxis de new para clase interna
			Thread nuevoHilo = new Thread( miVentana.miHilo );
			nuevoHilo.start();
		} catch (Exception e) {
			System.exit(1);  // Error anormal
		}
	}
	
	/** Clase interna para implementación de bucle principal del juego como un hilo
	 * @author Andoni Eguíluz
	 * Facultad de Ingeniería - Universidad de Deusto (2014)
	 */
	class MiRunnable implements Runnable {
		boolean sigo = true;
		@Override
		public void run() {
			// Bucle principal forever hasta que se pare el juego...
			while (sigo) {
				// Mover coche
				miCoche.mueve( 0.040 );
				// Chequear choques
				// (se comprueba tanto X como Y porque podría a la vez chocar en las dos direcciones (esquinas)
				if (miMundo.hayChoqueHorizontal(miCoche)) // Espejo horizontal si choca en X
					miMundo.rebotaHorizontal(miCoche);
				if (miMundo.hayChoqueVertical(miCoche)) // Espejo vertical si choca en Y
					miMundo.rebotaVertical(miCoche);
				//movimiento del coche
		        double Aceleracion = 0.0;
		        if (teclas[0] == true) {
		          Aceleracion = VentanaJuego.this.miCoche.fuerzaAceleracionAdelante();
		        }
		        if (teclas[1] == true) {
		          Aceleracion = -VentanaJuego.this.miCoche.fuerzaAceleracionAtras();
		        }
		        MundoJuego.aplicarFuerza(Aceleracion, VentanaJuego.this.miCoche);
		        if (teclas[2] == true){
					miCoche.gira( +10 );
				}else if (teclas[3] == true){
					miCoche.gira( -10 );
				}
		        //crear estrellas
		        VentanaJuego.this.miMundo.creaEstrella();
		        VentanaJuego.this.miMundo.quitaYRotaEstrellas(6000);
		        VentanaJuego.this.miMundo.choquesConEstrellas();	
		        String mensaje = "Monedas: " + VentanaJuego.this.miMundo.getPuntos() + "  -  Fallos: " + VentanaJuego.this.miMundo.getPerdidas();
		        VentanaJuego.this.lMensaje.setText(mensaje);        
		        if (VentanaJuego.this.miMundo.GAMEOVER()){
		          this.sigo = false;
		          VentanaJuego.this.lMensaje.setText("GAME OVER: Has sacado " +  VentanaJuego.this.miMundo.getPuntos() + " monedas.");
		          monedas = VentanaJuego.this.miMundo.getPuntos();
		          Screen.money = monedas;
		          monedas = 0;
		          if (miHilo!=null) miHilo.acaba();
		          VentanaJuego.this.miMundo.quitaEstrellasYCoche();
		          
					try {
						
						Thread.sleep( 2000 );
						VentanaJuego.this.dispose();
						
				        Frame.main(null);
						
							
						
				        
				        
					} catch (Exception e) {	
					}
					miVentana.dispose();
		        }
				// Dormir el hilo 40 milisegundos
				try {
					Thread.sleep( 40 );
				} catch (Exception e) {
				}
			}
		}
		/** Ordena al hilo detenerse en cuanto sea posible
		 */
		public void acaba() {
			sigo = false;
			
		}
	};
	
}