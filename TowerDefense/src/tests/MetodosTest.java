package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import juego.towerDefense.*;
import Utilidades.*;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MetodosTest {

	//Test que comprueba si el metodo utilizado en ventana ranking funciona como deberia ordenando un array
	//de puntuaciones por puntos primero y alfabeticamente por la primera letra si hay conflicto.
	@Test
	public void ordenarTest() {
		ArrayList<Puntuacion> a = new ArrayList<Puntuacion>();
		a.add(new Puntuacion("Jorge", 12));
		a.add(new Puntuacion("Andaluz12" , 100));
		a.add(new Puntuacion("Abigail", 12));
		ArrayList<Puntuacion> b = new ArrayList<Puntuacion>();
		b.add(new Puntuacion("Andaluz12" , 100));
		b.add(new Puntuacion("Abigail", 12));
		b.add(new Puntuacion("Jorge", 12));
		ArrayList<Puntuacion> c = new ArrayList<Puntuacion>();
		
		c = reordenar(a);
		for(int i = 0; i < c.size(); i++) {
			assertEquals((long) b.get(i).getPuntuacion(), (long) c.get(i).getPuntuacion());
			assertEquals(b.get(i).getUsuario_nick(), c.get(i).getUsuario_nick());
		}
	}
	
	//Metodo copiado de VentanaRanking para su uso en el test
	public ArrayList<Puntuacion> reordenar(ArrayList<Puntuacion> p) {
		
		ArrayList<Puntuacion> ret = new ArrayList<Puntuacion>();
		int tamanyo = p.size();
		for (int i = 0; i < tamanyo; i++) {
			Puntuacion mayor = new Puntuacion();
		    mayor =	p.get(0);
		    for(int j = 0; j < p.size(); j++) {
		    	if(mayor.getPuntuacion() < p.get(j).getPuntuacion()) {
		    		mayor = p.get(j);
		    	}else if(mayor.getPuntuacion() == p.get(j).getPuntuacion()) {
		    		char a = mayor.getUsuario_nick().charAt(0);
		    		char b = p.get(j).getUsuario_nick().charAt(0);		    		
		    		if(b < a) {
		    			mayor = p.get(j);
		    		}
		    	}
		    }
		    ret.add(mayor);
		    p.remove(mayor);
		}
		
		
		
		return ret;
	}
	
}
