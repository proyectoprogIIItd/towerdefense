package Utilidades;

import java.sql.*;
import java.util.*; 
import java.util.logging.*;

//Documentaci�n particular de foreign keys en sqlite en
//https://www.sqlite.org/foreignkeys.html
//Si se quiere hacer sin foreign keys, quitar las l�neas marcadas con (1) y sustituirlas por las (2) en BD.java

/** Clase de gesti�n de base de datos del sistema de usuarios - partidas
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class BD {

	private static Exception lastError = null;  // Informaci�n de �ltimo error SQL ocurrido
	
	/** Inicializa una BD SQLITE y devuelve una conexi�n con ella
	 * @param nombreBD	Nombre de fichero de la base de datos
	 * @return	Conexi�n con la base de datos indicada. Si hay alg�n error, se devuelve null
	 */
	public static Connection initBD( String nombreBD ) {
		try {
		    Class.forName("org.sqlite.JDBC");
		    Connection con = DriverManager.getConnection("jdbc:sqlite:" + nombreBD );
			log( Level.INFO, "Conectada base de datos " + nombreBD, null );
		    return con;
		} catch (ClassNotFoundException | SQLException e) {
			lastError = e;
			log( Level.SEVERE, "Error en conexi�n de base de datos " + nombreBD, e );
			e.printStackTrace();
			return null;
		}
	}
	
	/** Devuelve statement para usar la base de datos
	 * @param con	Conexi�n ya creada y abierta a la base de datos
	 * @return	sentencia de trabajo si se crea correctamente, null si hay cualquier error
	 */
	public static Statement usarBD( Connection con ) {
		try {
			Statement statement = con.createStatement();
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			return statement;
		} catch (SQLException e) {
			lastError = e;
			log( Level.SEVERE, "Error en uso de base de datos", e );
			e.printStackTrace();
			return null;
		}
	}
	
	/** Crea las tablas de la base de datos. Si ya existen, las deja tal cual
	 * @param con	Conexi�n ya creada y abierta a la base de datos
	 * @return	sentencia de trabajo si se crea correctamente, null si hay cualquier error
	 */
	public static Statement usarCrearTablasBD( Connection con ) {
		try {
			Statement statement = con.createStatement();
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			try {
				statement.executeUpdate("create table usuario " +
					// "(nick string "  // (2) Esto ser�a sin borrado en cascada ni relaci�n de claves ajenas
					"(nick string PRIMARY KEY" // (1) Solo para foreign keys
					+ ", password string, nombre string" + ")");
			} catch (SQLException e) {} // Tabla ya existe. Nada que hacer
			try {
				statement.executeUpdate("create table puntuaciones " +
					"(usuario_nick string REFERENCES usuario(nick) ON DELETE CASCADE, puntuacion integer)"); // (1) Solo para foreign keys
					
			} catch (SQLException e) {} // Tabla ya existe. Nada que hacer
			try {
				statement.executeUpdate("create table mapas " +
					"(usuario_nick string REFERENCES usuario(nick) ON DELETE CASCADE, mapa string, lvl string, nombreMapa string)"); 
					// (1) Solo para foreign keys
					
			} catch (SQLException e) {} //
			try {
				statement.executeUpdate("create table monedasExtras " +
					"(usuario_nick string REFERENCES usuario(nick) ON DELETE CASCADE, extras int)"); 
					// (1) Solo para foreign keys
					
			} catch (SQLException e) {} //
			return statement;
		} catch (SQLException e) {
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}
	
	/** Reinicia en blanco las tablas de la base de datos. 
	 * UTILIZAR ESTE M�TODO CON PRECAUCI�N. Borra todos los datos que hubiera ya en las tablas
	 * @param con	Conexi�n ya creada y abierta a la base de datos
	 * @return	sentencia de trabajo si se borra correctamente, null si hay cualquier error
	 */
	public static Statement reiniciarBD( Connection con ) {
		try {
			Statement statement = con.createStatement();
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			statement.executeUpdate("drop table if exists usuario");
			statement.executeUpdate("drop table if exists partida");
			log( Level.INFO, "Reiniciada base de datos", null );
			return usarCrearTablasBD( con );
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en reinicio de base de datos", e );
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}
	
	/** Cierra la base de datos abierta
	 * @param con	Conexi�n abierta de la BD
	 * @param st	Sentencia abierta de la BD
	 */
	public static void cerrarBD( Connection con, Statement st ) {
		try {
			if (st!=null) st.close();
			if (con!=null) con.close();
			log( Level.INFO, "Cierre de base de datos", null );
		} catch (SQLException e) {
			lastError = e;
			log( Level.SEVERE, "Error en cierre de base de datos", e );
			e.printStackTrace();
		}
	}
	
	/** Devuelve la informaci�n de excepci�n del �ltimo error producido por cualquiera 
	 * de los m�todos de gesti�n de base de datos
	 */
	public static Exception getLastError() {
		return lastError;
	}
	
	/////////////////////////////////////////////////////////////////////
	//                      Operaciones de usuario y mapa                    //
	/////////////////////////////////////////////////////////////////////
	
	/** A�ade un usuario a la tabla abierta de BD, usando la sentencia INSERT de SQL
	 * @param st	Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente al usuario)
	 * @param u	Usuario a a�adir en la base de datos
	 * @return	true si la inserci�n es correcta, false en caso contrario
	 */
	public static boolean usuarioInsert( Statement st, Usuario u ) {
		String sentSQL = "";
		try {
			
			sentSQL = "insert into usuario values(" +
					"'" + u.getNick() + "', " +
					"'" + u.getPassword() + "', " +
					"'" + u.getNombre() + "')";
			// System.out.println( sentSQL );  // para ver lo que se hace en consola
			int val = st.executeUpdate( sentSQL );
			if (val!=1) {  // Se tiene que a�adir 1 - error si no
				return false;  
			}
			return true;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}
	public static boolean mapasInsert( Statement st,String u, String mapa, String lvl, String nombre ) {
		String sentSQL = "";
		try {
			
			sentSQL = "insert into mapas values(" +
					"'" + u + "', " +
					"'" + mapa + "', " +
					"'" + lvl + "', " +
					"'" + nombre + "')";
			// System.out.println( sentSQL );  // para ver lo que se hace en consola
			int val = st.executeUpdate( sentSQL );
			if (val!=1) {  // Se tiene que a�adir 1 - error si no
				return false;  
			}
			return true;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}

	/** Realiza una consulta a la tabla abierta de usuarios de la BD, usando la sentencia SELECT de SQL
	 * @param st	Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente al usuario)
	 * @param codigoSelect	Sentencia correcta de WHERE (sin incluirlo) para filtrar la b�squeda (vac�a si no se usa)
	 * @return	lista de usuarios cargados desde la base de datos, null si hay cualquier error
	 */
	public static ArrayList<Usuario> usuarioSelect( Statement st, String codigoSelect ) {
		String sentSQL = "";
		ArrayList<Usuario> ret = new ArrayList<>();
		try {
			sentSQL = "select * from usuario";
			if (codigoSelect!=null && !codigoSelect.equals(""))
				sentSQL = sentSQL + " where " + codigoSelect;
			// System.out.println( sentSQL );  // Para ver lo que se hace en consola
			ResultSet rs = st.executeQuery( sentSQL );
			while (rs.next()) {
				Usuario u = new Usuario();
				u.setNick( rs.getString( "nick" ) );
				u.setPassword( rs.getString( "password" ) );
				u.setNombre( rs.getString( "nombre" ) );
				ret.add( u );
			}
			rs.close();
			log( Level.INFO, "BD\t" + sentSQL, null );
			return ret;
		} catch (IllegalArgumentException e) {  // Error en tipo usuario (enumerado)
			log( Level.SEVERE, "Error en BD en tipo de usuario\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}

	/** Modifica un usuario en la tabla abierta de BD, usando la sentencia UPDATE de SQL
	 * @param st	Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente al usuario)
	 * @param u	Usuario a modificar en la base de datos. Se toma su nick como clave
	 * @return	true si la inserci�n es correcta, false en caso contrario
	 */
	public static boolean usuarioUpdate( Statement st, Usuario u ) {
		String sentSQL = "";
		try {
			String listaEms = "";
			String sep = "";
			sentSQL = "update usuario set" +
					// " nick='" + u.getNick() + "', " +  // No hay que actualizar el nick, solo el resto de campos
					" password='" + u.getPassword() + "', " +
					" nombre='" + u.getNombre() + "'";
			// System.out.println( sentSQL );  // para ver lo que se hace en consola
			int val = st.executeUpdate( sentSQL );
			if (val!=1) {  // Se tiene que modificar 1 - error si no
				return false;  
			}
			return true;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}

	/** Borrar un usuario de la tabla abierta de BD, usando la sentencia DELETE de SQL
	 * @param st	Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente al usuario)
	 * @param u	Usuario a borrar de la base de datos  (se toma su nick para el borrado)
	 * @return	true si el borrado es correcto, false en caso contrario
	 */
	public static boolean usuarioDelete( Statement st, Usuario u ) {
		String sentSQL = "";
		try {
			sentSQL = "delete from usuario where nick='" + secu(u.getNick()) + "'";
			int val = st.executeUpdate( sentSQL );
			log( Level.INFO, "BD borrada " + val + " fila\t" + sentSQL, null );
			return (val==1);
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}

	
	
	/////////////////////////////////////////////////////////////////////
	//                      M�todos privados                           //
	/////////////////////////////////////////////////////////////////////

	// Devuelve el string "securizado" para volcarlo en SQL
	// (Implementaci�n 1) Sustituye ' por '' y quita saltos de l�nea
	// (Implementaci�n 2) Mantiene solo los caracteres seguros en espa�ol
	private static String secu( String string ) {
		// Implementaci�n (1)
		// return string.replaceAll( "'",  "''" ).replaceAll( "\\n", "" );
		// Implementaci�n (2)
		StringBuffer ret = new StringBuffer();
		for (char c : string.toCharArray()) {
			if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ��������������.,:;-_(){}[]-+*=<>'\"�?�!&%$@#/\\0123456789".indexOf(c)>=0) ret.append(c);
		}
		return ret.toString();
	}
	

	/////////////////////////////////////////////////////////////////////
	//                      Logging                                    //
	/////////////////////////////////////////////////////////////////////
	
	private static Logger logger = null;
	// M�todo p�blico para asignar un logger externo
	/** Asigna un logger ya creado para que se haga log de las operaciones de base de datos
	 * @param logger	Logger ya creado
	 */
	public static void setLogger( Logger logger ) {
		BD.logger = logger;
	}
	// M�todo local para loggear (si no se asigna un logger externo, se asigna uno local)
	private static void log( Level level, String msg, Throwable excepcion ) {
		if (logger==null) {  // Logger por defecto local:
			logger = Logger.getLogger( BD.class.getName() );  // Nombre del logger - el de la clase
			logger.setLevel( Level.ALL );  // Loguea todos los niveles
			try {
				// logger.addHandler( new FileHandler( "bd-" + System.currentTimeMillis() + ".log.xml" ) );  // Y saca el log a fichero xml
				logger.addHandler( new FileHandler( "bd.log.xml", true ) );  // Y saca el log a fichero xml
			} catch (Exception e) {
				logger.log( Level.SEVERE, "No se pudo crear fichero de log", e );
			}
		}
		if (excepcion==null)
			logger.log( level, msg );
		else
			logger.log( level, msg, excepcion );
	}
	public static ArrayList<Puntuacion> puntuacionSelect( Statement st, String codigoSelect ) {
		String sentSQL = "";
		ArrayList<Puntuacion> ret = new ArrayList<>();
		try {
			sentSQL = "select * from puntuaciones";
			if (codigoSelect!=null && !codigoSelect.equals(""))
				sentSQL = sentSQL + " where " + codigoSelect;
			// System.out.println( sentSQL );  // Para ver lo que se hace en consola
			ResultSet rs = st.executeQuery( sentSQL );
			while (rs.next()) {
				Puntuacion p = new Puntuacion();
				p.setUsuario_nick(rs.getString("Usuario_nick"));
				p.setPuntuacion(rs.getInt("Puntuacion"));
				
				ret.add( p );
			}
			rs.close();
			log( Level.INFO, "BD\t" + sentSQL, null );
			return ret;
		} catch (IllegalArgumentException e) {  // Error en tipo usuario (enumerado)
			log( Level.SEVERE, "Error en BD en tipo de puntuacion\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}
	
	
}
