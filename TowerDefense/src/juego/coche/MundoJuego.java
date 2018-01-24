package juego.coche;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class MundoJuego {
	private JPanel panel;  // panel visual del juego
	CocheJuego miCoche;    // Coche del juego
	ArrayList<JLabelEstrella> arrayEstrellas = new ArrayList();
	private static long ultima = 0;
	private static Random r = new Random();
	private int perdidas = 0;
	private int puntos = 0;
	/** Construye un mundo de juego
	 * @param panel	Panel visual del juego
	 */
	public MundoJuego( JPanel panel ) {
		this.panel = panel;
	}

	/** Crea un coche nuevo y lo a�ade al mundo y al panel visual
	 * @param posX	Posici�n X de pixel del nuevo coche
	 * @param posY	Posici�n Y de p�xel del nuevo coche
	 */
	public void creaCoche( int posX, int posY ) {
		// Crear y a�adir el coche a la ventana
		miCoche = new CocheJuego();
		miCoche.setPosicion( posX, posY );
		panel.add( miCoche.getGrafico() );  // A�ade al panel visual
		miCoche.getGrafico().repaint();  // Refresca el dibujado del coche
	}
	
	/** Devuelve el coche del mundo
	 * @return	Coche en el mundo. Si no lo hay, devuelve null
	 */
	public CocheJuego getCoche() {
		return miCoche;
	}

	/** Calcula si hay choque en horizontal con los l�mites del mundo
	 * @param coche	Coche cuyo choque se comprueba con su posici�n actual
	 * @return	true si hay choque horizontal, false si no lo hay
	 */
	public boolean hayChoqueHorizontal( CocheJuego coche ) {
		return (coche.getPosX() < JLabelCoche.RADIO_ESFERA_COCHE-JLabelCoche.TAMANYO_COCHE/2 
				|| coche.getPosX()>panel.getWidth()-JLabelCoche.TAMANYO_COCHE/2-JLabelCoche.RADIO_ESFERA_COCHE );
	}
	
	/** Calcula si hay choque en vertical con los l�mites del mundo
	 * @param coche	Coche cuyo choque se comprueba con su posici�n actual
	 * @return	true si hay choque vertical, false si no lo hay
	 */
	public boolean hayChoqueVertical( CocheJuego coche ) {
		return (coche.getPosY() < JLabelCoche.RADIO_ESFERA_COCHE-JLabelCoche.TAMANYO_COCHE/2 
				|| coche.getPosY()>panel.getHeight()-JLabelCoche.TAMANYO_COCHE/2-JLabelCoche.RADIO_ESFERA_COCHE );
	}

	/** Realiza un rebote en horizontal del objeto de juego indicado
	 * @param coche	Objeto que rebota en horizontal
	 */
	public void rebotaHorizontal( CocheJuego coche ) {
		// System.out.println( "Choca X");
		double dir = coche.getDireccionActual();
		dir = 180-dir;   // Rebote espejo sobre OY (complementario de 180)
		if (dir < 0) dir = 360+dir;  // Correcci�n para mantenerlo en [0,360)
		coche.setDireccionActual( dir );
	}
	
	/** Realiza un rebote en vertical del objeto de juego indicado
	 * @param coche	Objeto que rebota en vertical
	 */
	public void rebotaVertical( CocheJuego coche ) {
		// System.out.println( "Choca Y");
		double dir = miCoche.getDireccionActual();
		dir = 360 - dir;  // Rebote espejo sobre OX (complementario de 360)
		miCoche.setDireccionActual( dir );
	}
	
	/** Calcula y devuelve la posici�n X de un movimiento
	 * @param vel    	Velocidad del movimiento (en p�xels por segundo)
	 * @param dir    	Direcci�n del movimiento en grados (0� = eje OX positivo. Sentido antihorario)
	 * @param tiempo	Tiempo del movimiento (en segundos)
	 * @return
	 */
	public static double calcMovtoX( double vel, double dir, double tiempo ) {
		return vel * Math.cos(dir/180.0*Math.PI) * tiempo;
	}
	
	/** Calcula y devuelve la posici�n X de un movimiento
	 * @param vel    	Velocidad del movimiento (en p�xels por segundo)
	 * @param dir    	Direcci�n del movimiento en grados (0� = eje OX positivo. Sentido antihorario)
	 * @param tiempo	Tiempo del movimiento (en segundos)
	 * @return
	 */
	public static double calcMovtoY( double vel, double dir, double tiempo ) {
		return vel * -Math.sin(dir/180.0*Math.PI) * tiempo;
		// el negativo es porque en pantalla la Y crece hacia abajo y no hacia arriba
	}
	
	/** Calcula el cambio de velocidad en funci�n de la aceleraci�n
	 * @param vel		Velocidad original
	 * @param acel		Aceleraci�n aplicada (puede ser negativa) en pixels/sg2
	 * @param tiempo	Tiempo transcurrido en segundos
	 * @return	Nueva velocidad
	 */
	public static double calcVelocidadConAceleracion( double vel, double acel, double tiempo ) {
		return vel + (acel*tiempo);
	}
	
	 public static double calcFuerzaRozamiento( double masa, double coefRozSuelo,
			 double coefRozAire, double vel ) {
			 double fuerzaRozamientoAire = coefRozAire * (-vel); // En contra del movimiento
			 double fuerzaRozamientoSuelo = masa * coefRozSuelo * ((vel>0)?(-1):1); // Contra mvto
			 return fuerzaRozamientoAire + fuerzaRozamientoSuelo;
			 } 
	 
	 public static double calcAceleracionConFuerza( double fuerza, double masa ) {
		 // 2� ley de Newton: F = m*a ---> a = F/m
		 return fuerza/masa;
		 } 

	 public static void aplicarFuerza( double fuerza, Coche coche ) {
		 double fuerzaRozamiento = calcFuerzaRozamiento( Coche.masa , Coche.rozamSuelo, Coche.rozamAire, coche.getVelocidad() );
		 double aceleracion = calcAceleracionConFuerza( fuerza+fuerzaRozamiento, Coche.masa );
		 if (fuerza==0) {
		 // No hay fuerza, solo se aplica el rozamiento
		 double velAntigua = coche.getVelocidad();
		 coche.acelera( aceleracion, 0.04 );
		 if (velAntigua>=0 && coche.getVelocidad()<0
		 || velAntigua<=0 && coche.getVelocidad()>0) {
		 coche.setVelocidad(0); // Si se est� frenando, se para (no anda al rev�s)
		 }
		 } else {
		 coche.acelera( aceleracion, 0.04 );
		 } 
	 }
	 
	 public void creaEstrella() {
	   if ((System.currentTimeMillis()-ultima)>1200){
	     JLabelEstrella nuevaEstrella = new JLabelEstrella();
	     nuevaEstrella.setLocation(r.nextInt(this.panel.getWidth() - 40), r.nextInt(this.panel.getHeight() - 40));
	     this.panel.add(nuevaEstrella);
	     nuevaEstrella.repaint();
	     ultima = System.currentTimeMillis();
	     this.arrayEstrellas.add(nuevaEstrella);
	   }
	  }
	  
	  public int quitaYRotaEstrellas(long maxTiempo) {
	    int estrellasQuitadas = 0;
	    for (int i=this.arrayEstrellas.size()-1;i>=0;i--) {
	      JLabelEstrella estrellaLabel = (JLabelEstrella)this.arrayEstrellas.get(i);
	      if ((System.currentTimeMillis()-estrellaLabel.getMomentoCreacion())>maxTiempo){
	        this.panel.remove(estrellaLabel);
	        this.panel.repaint();
	        this.arrayEstrellas.remove(estrellaLabel);
	        estrellasQuitadas++;
	        this.perdidas += 1;
	      }else{
	    	estrellaLabel.girar(10.0);
	    	estrellaLabel.repaint();
	      }
	    }
	    return estrellasQuitadas;
	  }
//pare detectar la colision (esta parte est� copiada)
	  private boolean colision(JLabelEstrella estrella)
	  {
	    double X = estrella.getX() + 20 - this.miCoche.getPosX() - 50.0;
	    double Y = estrella.getY() + 20 - this.miCoche.getPosY() - 50.0;
	    double dist = Math.sqrt(X * X + Y * Y);
	    return dist <= 52.0;
	  }
	  
	  public void quitaEstrellasYCoche() {
		  this.panel.removeAll();
	  }
	  
	  public int choquesConEstrellas() {
	    int choques = 0;
	    for (int i = this.arrayEstrellas.size() - 1; i >= 0; i--){
	      JLabelEstrella estrellaLabel = (JLabelEstrella)this.arrayEstrellas.get(i);
	      if (colision(estrellaLabel)){
	        choques++;
	        this.panel.remove(estrellaLabel);
	        this.panel.repaint();
	        this.arrayEstrellas.remove(estrellaLabel);
	        this.puntos = puntos + 3;
	      }
	    }
	    return choques;
	  }

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public int getPerdidas() {
		return perdidas;
	}

	public void setPerdidas(int perdidas) {
		this.perdidas = perdidas;
	}
	
	//Modificar aqui para la dificultad del juego
	public boolean GAMEOVER() {
	  return this.perdidas >= 6;
	}

}
