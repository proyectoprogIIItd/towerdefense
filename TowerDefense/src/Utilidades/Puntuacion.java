package Utilidades;

public class Puntuacion {
	
	private String usuario_nick;
	private int puntuacion;
	
	
	public Puntuacion(String usuario_nick, int puntuacion) {
		this.usuario_nick = usuario_nick;
		this.puntuacion = puntuacion;
	}


	public String getUsuario_nick() {
		return usuario_nick;
	}


	public void setUsuario_nick(String usuario_nick) {
		this.usuario_nick = usuario_nick;
	}


	public int getPuntuacion() {
		return puntuacion;
	}


	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}


	public Puntuacion(){}
}
