package juego.towerDefense;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Enemigo extends Rectangle {
	public int xC, yC;

	public int tamanyoEnemigo = 64;
	public int caminoEnemigo = 0;
	public int vida;
	public int espacioVida = 3;
	public int alturaVida = 6;
	public int arriba = 0, abajo = 1, derecha = 2, izquierda = 3;
	public int direccion = derecha;
	public int enemigoID = Value.ENEMIGO_AIRE;
	public boolean inGame = false;
	public boolean estaArriba = false;
	public boolean estaAbajo = false;
	public boolean estaIzquierda = false;
	public boolean estaDerecha = false;

	public Enemigo() {

	}

	//metodo para que aparezcan enemigos que se llama en screen
	public void apareceEnemigo(int enemigoID) {

		for (int y = 0; y < Screen.room.bloque.length; y++) {
			if (Screen.room.bloque[y][0].tierraID == Value.SUELO_CARRETERA
					|| Screen.room.bloque[y][0].tierraID == Value.SUELO_START) {
				setBounds(Screen.room.bloque[y][0].x, Screen.room.bloque[y][0].y, tamanyoEnemigo, tamanyoEnemigo);
				xC = 0;
				yC = y;
			}
		}
		this.enemigoID = enemigoID;
		this.vida = tamanyoEnemigo;
		inGame = true;
	}

	public void deleteEnemyFueraMapa() {
		inGame = false;
		direccion = derecha;
		caminoEnemigo = 0;
	}
	// borra enemigo y aqui sumamos a dinero lo que nos da ese enemigo en especial
	public void deleteEnemy() {
		inGame = false;
		direccion = derecha;
		caminoEnemigo = 0;
		if (Bloque.enemigoDisparadoDinero == -1) {

		} else {
			Bloque.getMoney(Screen.enemigos[Bloque.enemigoDisparadoDinero].enemigoID);
		}
		Screen.enemigosAsesinados += 1;

	}

	public void perderVida() {
		Screen.vida -= 1;
	}

	public int contadorAndar = 0, frecuenciaAndar = 10;

	
	
	//metodo que mueve a los enemigos
	public void physic() {
		if (contadorAndar >= frecuenciaAndar) {
			if (direccion == derecha) {
				x += 1;
			} else if (direccion == arriba) {
				y -= 1;
			} else if (direccion == abajo) {
				y += 1;
			} else if (direccion == izquierda) {
				x -= 1;
			}

			caminoEnemigo += 1;
			if (caminoEnemigo == Screen.room.tamanyoBloque) { // acaba un bloque y comprueba el siguiente
				if (direccion == derecha) {
					xC += 1;
					estaDerecha = true;
				} else if (direccion == arriba) {
					yC -= 1;
					estaArriba = true;
				} else if (direccion == abajo) {
					yC += 1;
					estaAbajo = true;
				} else if (direccion == izquierda) {
					xC -= 1;
					estaIzquierda = true;
				}
				if (!estaArriba) {
					try {
						if (Screen.room.bloque[yC + 1][xC].tierraID == Value.SUELO_CARRETERA
								|| Screen.room.bloque[yC + 1][xC].tierraID == Value.SUELO_END) {
							direccion = abajo;
						}
					} catch (Exception e) {
					}
				}
				if (!estaAbajo) {
					try {
						if (Screen.room.bloque[yC - 1][xC].tierraID == Value.SUELO_CARRETERA
								|| Screen.room.bloque[yC - 1][xC].tierraID == Value.SUELO_END) {
							direccion = arriba;
						}
					} catch (Exception e) {
					}
				}
				if (!estaIzquierda) {
					try {
						if (Screen.room.bloque[yC][xC + 1].tierraID == Value.SUELO_CARRETERA
								|| Screen.room.bloque[yC][xC + 1].tierraID == Value.SUELO_END) {
							direccion = derecha;
						}
					} catch (Exception e) {
					}
				}
				if (!estaDerecha) {
					try {
						if (Screen.room.bloque[yC][xC - 1].tierraID == Value.SUELO_CARRETERA
								|| Screen.room.bloque[yC][xC - 1].tierraID == Value.SUELO_END) {
							direccion = izquierda;
						}
					} catch (Exception e) {
					}
				}
				if (Screen.room.bloque[yC][xC].tierraID == Value.SUELO_END) {
					deleteEnemyFueraMapa();
					perderVida();
				}

				estaArriba = false;
				estaAbajo = false;
				estaIzquierda = false;
				estaDerecha = false;
				caminoEnemigo = 0;
			}
			contadorAndar = 0;
		} else {
			contadorAndar += 1;
		}

	}

	public void enemigoPierdeVida(int amo) {
		vida -= amo;
		comprobarMuerto();
	}

	public void comprobarMuerto() {
		if (vida <= 0) {
			deleteEnemy();
		}
	}

	public boolean muerto() {
		if (inGame) {
			return false;
		} else {
			return true;
		}
	}

	public void draw(Graphics g) {

		g.drawImage(Screen.enemigo[enemigoID], x, y, width, height, null);
		// HEALTH BAR
		g.setColor(new Color(180, 50, 50));
		g.fillRect(x, y - (espacioVida + alturaVida), width, alturaVida);

		g.setColor(new Color(50, 180, 50));
		g.fillRect(x, y - (espacioVida + alturaVida), vida, alturaVida);
		g.setColor(new Color(0, 0, 0));
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(1));
		g.drawRect(x, y - (espacioVida + alturaVida), vida - 1, alturaVida - 1);
	}
}