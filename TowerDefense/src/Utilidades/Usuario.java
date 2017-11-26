package Utilidades;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/** Clase para gestionar usuarios. Ejemplo para ver guardado y recuperaci�n desde ficheros
 * @author Andoni Egu�luz Mor�n
 * Facultad de Ingenier�a - Universidad de Deusto
 */
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nick;
	private String password;
	private String nombre;


	public String getNick() {
		return nick;
	}
	// Modificador de nick solo permitido dentro del paquete
	protected void setNick( String nick ) {
		this.nick = nick;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	/** Constructor protected, s�lo para uso interno dentro del paquete
	 */
	protected Usuario() {
	}
	public Usuario(String nick, String password, String nombre) {
		this.nick = nick;
		this.password = password;
		this.nombre = nombre;
	}
	
	/** Constructor principal de usuario
	 * @param nick
	 * @param password
	 * @param nombre
	 * @param apellidos
	 * @param telefono
	 * @param tipo
	 * @param listaEmails
	 */


	

//	@Override
//	public String toString() {
//		return "Nick: " + nick + "\nNombre: " + nombre + " " + apellidos + 
//			"\nTel�fono: " + telefono + "\nTipo de usuario: " + tipo +
//			"\nEmails: " + listaEmails;
//	}

	
	/** main de prueba
	 * @param s	Par�metros est�ndar (no se utilizan)
	 */
//	public static void main( String[] s ) {
//		Usuario u = new Usuario( "buzz", "#9abbf", "Buzz", "Lightyear", 101202303, TipoUsuario.Admin, "buzz@gmail.com", "amigo.de.woody@gmail.com" );
//		u.getListaEmails().add( "buzz@hotmail.com" );
//		// String ape = u.getApellidos(); ape = "Apellido falso";  // esto no cambia nada
//		System.out.println( u );
//	}

//	// Dos usuarios son iguales si TODOS sus campos son iguales
//	public boolean equals( Object o ) {
//		Usuario u2 = null;
//		if (o instanceof Usuario) u2 = (Usuario) o;
//		else return false;  // Si no es de la clase, son diferentes
//		return (nick.equals(u2.nick))
//			&& (password.equals(u2.password))
//			&& (nombre.equals(u2.nombre))
//			&& (apellidos.equals(u2.apellidos))
//			&& (telefono == u2.telefono)
//			&& (fechaUltimoLogin == u2.fechaUltimoLogin)
//			&& (listaEmails.equals( u2.listaEmails ));
//	}
	
}

