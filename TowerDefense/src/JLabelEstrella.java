import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class JLabelEstrella extends JLabel {
	  public int estrellaXY = 40;
	  public int radio = 17;
	  private long momentoCreacion = System.currentTimeMillis();
	  private boolean dibujar = false;
	  private double giro = 0.0;
	  
	  public JLabelEstrella()
	  {
	    try
	    {
	    	//copiado de JLabelCoche, cambiandolo a estrellas:
	      setIcon(new ImageIcon(("resources"+File.separator+"estrella.png")));
	    }
	    catch (Exception e)
	    {
	      System.err.println("Error en carga de recurso: estrella.png no encontrado");
	      e.printStackTrace();
	    }
	    setBounds(0, 0, estrellaXY, estrellaXY);
	  }

	public long getMomentoCreacion() {
		return momentoCreacion;
	}

	public void setMomentoCreacion(long momentoCreacion) {
		this.momentoCreacion = momentoCreacion;
	} 
	public void girar(double gradosGiro){
	  this.giro = giro + gradosGiro/180*Math.PI;
	}
	
	//también copiado de JLabelCoche y exportado a estrella
	 protected void paintComponent(Graphics g) {
//			super.paintComponent(g);   // En este caso no nos sirve el pintado normal de un JLabel
	    Image img = ((ImageIcon)getIcon()).getImage();
	    Graphics2D g2 = (Graphics2D)g; // El Graphics realmente es Graphics2D
		// Escalado más fino con estos 3 parámetros:
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	 // Prepara rotación (siguientes operaciones se rotarán)
	    g2.rotate(giro, 20.0D, 20.0D);// getIcon().getIconWidth()/2, getIcon().getIconHeight()/2 );
        // Dibujado de la imagen
	    g2.drawImage(img, 0, 0, estrellaXY, estrellaXY, null);
	    if (dibujar) g2.drawOval( estrellaXY/2-radio, estrellaXY/2-radio,
        		radio*2, radio*2 );
	  }
}
