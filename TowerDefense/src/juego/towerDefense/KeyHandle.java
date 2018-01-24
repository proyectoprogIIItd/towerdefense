package juego.towerDefense;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class KeyHandle implements MouseMotionListener, MouseListener{

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
		Screen.store.click(e.getButton());
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Screen.raton=new Point((e.getX())- ((Frame.size.width-Screen.myWidth)/2), (e.getY())-((Frame.size.height -(Screen.myHeight))-(Frame.size.width-Screen.myWidth)/2));
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		//Corregimos las coordenadas del ratón (borde superior...)
		Screen.raton=new Point((e.getX())- ((Frame.size.width-Screen.myWidth)/2), (e.getY())-((Frame.size.height -(Screen.myHeight))-(Frame.size.width-Screen.myWidth)/2));
		
	}

}
