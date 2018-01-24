package Utilidades;

import java.sql.*;
import java.util.*; 
import java.util.logging.*;

//Documentaciï¿½n particular de foreign keys en sqlite en
//https://www.sqlite.org/foreignkeys.html
//Si se quiere hacer sin foreign keys, quitar las lï¿½neas marcadas con (1) y sustituirlas por las (2) en BD.java

/** Clase de gestiï¿½n de base de datos del sistema de usuarios - partidas
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class BD {

	private static Exception lastError = null;  // Informaciï¿½n de ï¿½ltimo error SQL ocurrido
	
	/** Inicializa una BD SQLITE y devuelve una conexiï¿½n con ella
	 * @param nombreBD	Nombre de fichero de la base de datos
	 * @return	Conexiï¿½n con la base de datos indicada. Si hay algï¿½n error, se devuelve null
	 */
	public static Connection initBD( String nombreBD ) {
		try {
		    Class.forName("org.sqlite.JDBC");
		    Connection con = DriverManager.getConnection("jdbc:sqlite:" + nombreBD );
			log( Level.INFO, "Conectada base de datos " + nombreBD, null );
		    return con;
		} catch (ClassNotFoundException | SQLException e) {
			lastError = e;
			log( Level.SEVERE, "Error en conexiï¿½n de base de datos " + nombreBD, e );
			e.printStackTrace();
			return null;
		}
	}
	
	/** Devuelve statement para usar la base de datos
	 * @param con	Conexiï¿½n ya creada y abierta a la base de datos
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
	 * @param con	Conexiï¿½n ya creada y abierta a la base de datos
	 * @return	sentencia de trabajo si se crea correctamente, null si hay cualquier error
	 */
	public static Statement usarCrearTablasBD( Connection con ) {
		try {
			Statement statement = con.createStatement();
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			try {
				statement.executeUpdate("create table usuario " +
					// "(nick string "  // (2) Esto serï¿½a sin borrado en cascada ni relaciï¿½n de claves ajenas
					"(nick string PRIMARY KEY" // (1) Solo para foreign keys
					+ ", password string, nombre string" + ")");
			} catch (SQLException e) {} // Tabla ya existe. Nada que hacer
			try {
				statement.executeUpdate("create table puntuaciones " +
					"(usuario_nick string REFERENCES usuario(nick) ON DELETE CASCADE, puntuacion integer, nombreMapa string REFERENCES mapas(nombreMapa) ON DELETE CASCADE"+")"); // (1) Solo para foreign keys
					
			} catch (SQLException e) {} // Tabla ya existe. Nada que hacer
			try {
				statement.executeUpdate("create table mapas " +
					"(usuario_nick string REFERENCES usuario(nick) ON DELETE CASCADE, mapa string, nombreMapa string PRIMARY KEY"+")"); 
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
	 * UTILIZAR ESTE Mï¿½TODO CON PRECAUCIï¿½N. Borra todos los datos que hubiera ya en las tablas
	 * @param con	Conexiï¿½n ya creada y abierta a la base de datos
	 * @return	sentencia de trabajo si se borra correctamente, null si hay cualquier error
	 */
	public static Statement reiniciarBD( Connection con ) {
		try {
			Statement statement = con.createStatement();
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			statement.executeUpdate("drop table if exists usuario");
			statement.executeUpdate("drop table if exists mapas");
			statement.executeUpdate("drop table if exists puntuaciones");
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
	 * @param con	Conexiï¿½n abierta de la BD
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
	
	/** Devuelve la informaciï¿½n de excepciï¿½n del ï¿½ltimo error producido por cualquiera 
	 * de los mï¿½todos de gestiï¿½n de base de datos
	 */
	public static Exception getLastError() {
		return lastError;
	}
	
	/////////////////////////////////////////////////////////////////////
	//                      Operaciones de usuario y mapa                    //
	/////////////////////////////////////////////////////////////////////
	
	/** Aï¿½ade un usuario a la tabla abierta de BD, usando la sentencia INSERT de SQL
	 * @param st	Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente al usuario)
	 * @param u	Usuario a aï¿½adir en la base de datos
	 * @return	true si la inserciï¿½n es correcta, false en caso contrario
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
			if (val!=1) {  // Se tiene que aï¿½adir 1 - error si no
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
	public static boolean puntuacionInsert( Statement st, String nombreMapa, String u_nick, int puntuacion ) {
		String sentSQL = "";
		try {
			
			sentSQL = "insert into puntuaciones values(" +
					"'" + u_nick + "', " +
					    + puntuacion + ", " +
					"'" + nombreMapa + "')";
			// System.out.println( sentSQL );  // para ver lo que se hace en consola
			int val = st.executeUpdate( sentSQL );
			if (val!=1) {  // Se tiene que aï¿½adir 1 - error si no
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
	public static boolean mapasInsert( Statement st,String u, String mapa, String nombre ) {
		String sentSQL = "";
		try {
			
			sentSQL = "insert into mapas values(" +
					"'" + u + "', " +
					"'" + mapa + "', " +
					"'" + nombre + "')";
			// System.out.println( sentSQL );  // para ver lo que se hace en consola
			int val = st.executeUpdate( sentSQL );
			if (val!=1) {  // Se tiene que aï¿½adir 1 - error si no
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
	 * @param codigoSelect	Sentencia correcta de WHERE (sin incluirlo) para filtrar la bï¿½squeda (vacï¿½a si no se usa)
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
	public static ArrayList<Puntuacion> puntuacionSelect( Statement st, String codigoSelect ) {
		String sentSQL = "";
		ArrayList<Puntuacion> ret = new ArrayList<>();
		try {
			sentSQL = "select * from puntuaciones";
			if (codigoSelect!=null && !codigoSelect.equals(""))
				sentSQL = sentSQL + " where nombreMapa='" + codigoSelect+"'";
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
	public static ArrayList<Puntuacion> puntuacionUsuarioSelect( Statement st, String codigoSelect, String codigoSelect2 ) {
		String sentSQL = "";
		ArrayList<Puntuacion> ret = new ArrayList<>();
		try {
			sentSQL = "select * from puntuaciones";
			if (codigoSelect!=null && !codigoSelect.equals(""))
				sentSQL = sentSQL + " where usuario_nick='" + codigoSelect+"' and nombreMapa='"+codigoSelect2+"'";
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
	public static ArrayList<String> mapaNombreSelect( Statement st, String codigoSelect ) {
		String sentSQL = "";
		ArrayList<String> ret = new ArrayList<>();
		try {
			sentSQL = "select * from mapas";
			if (codigoSelect!=null && !codigoSelect.equals(""))
				sentSQL = sentSQL + " where nombreMapa = '" + codigoSelect +"'";
			// System.out.println( sentSQL );  // Para ver lo que se hace en consola
			ResultSet rs = st.executeQuery( sentSQL );
			while (rs.next()) {
				ret.add( rs.getString("nombreMapa") );
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
	
	public static ArrayList<String> mapaSelect( Statement st, String codigoSelect ) {
		String sentSQL = "";
		ArrayList<String> ret = new ArrayList<>();
		try {
			sentSQL = "select * from mapas";
			if (codigoSelect!=null && !codigoSelect.equals(""))
				sentSQL = sentSQL + " where nombreMapa = '" + codigoSelect +"'";
			// System.out.println( sentSQL );  // Para ver lo que se hace en consola
			ResultSet rs = st.executeQuery( sentSQL );
			while (rs.next()) {
				ret.add( rs.getString("mapa") );
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
	 * @return	true si la inserciï¿½n es correcta, false en caso contrario
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
	
	public static boolean puntuacionUpdate( Statement st, String nick, int puntuacion, String nombreMapa ) {
		String sentSQL = "";
		try {
			String listaEms = "";
			String sep = "";
			sentSQL = "update puntuaciones set" +
					// " nick='" + u.getNick() + "', " +  // No hay que actualizar el nick, solo el resto de campos
					" usuario_nick='" + nick + "', " +
					" puntuacion=" + puntuacion + "," +
					"nombreMapa='"+ nombreMapa +"' where usuario_nick ='"+nick+"' and nombreMapa='"+nombreMapa+"'";
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
	//                      Mï¿½todos privados                           //
	/////////////////////////////////////////////////////////////////////

	// Devuelve el string "securizado" para volcarlo en SQL
	// (Implementaciï¿½n 1) Sustituye ' por '' y quita saltos de lï¿½nea
	// (Implementaciï¿½n 2) Mantiene solo los caracteres seguros en espaï¿½ol
	private static String secu( String string ) {
		// Implementaciï¿½n (1)
		// return string.replaceAll( "'",  "''" ).replaceAll( "\\n", "" );
		// Implementaciï¿½n (2)
		StringBuffer ret = new StringBuffer();
		for (char c : string.toCharArray()) {
			if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½.,:;-_(){}[]-+*=<>'\"ï¿½?ï¿½!&%$@#/\\0123456789".indexOf(c)>=0) ret.append(c);
		}
		return ret.toString();
	}
	

	/////////////////////////////////////////////////////////////////////
	//                      Logging                                    //
	/////////////////////////////////////////////////////////////////////
	
	private static Logger logger = null;
	// Mï¿½todo pï¿½blico para asignar un logger externo
	/** Asigna un logger ya creado para que se haga log de las operaciones de base de datos
	 * @param logger	Logger ya creado
	 */
	public static void setLogger( Logger logger ) {
		BD.logger = logger;
	}
	// Mï¿½todo local para loggear (si no se asigna un logger externo, se asigna uno local)
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

	
	
}