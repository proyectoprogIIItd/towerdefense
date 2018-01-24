import java.awt.*;
import java.awt.image.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;

import Utilidades.BD;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Creador {
	 private final JPanel gui = new JPanel(new BorderLayout(3, 3));
	    private JButton[][] chessBoardSquares = new JButton[15][8];
	    private JPanel chessBoard;
		public static Image[] air = new Image[100];
		public static Image[] ground = new Image[100];
		private static int[][] arrayMapa = new int[15][15];
		private static int x = -1;
		private static int y = -1;
		private static int tipoC = 0;
		private static boolean torreUp = false;
		private static boolean spawnUp = false;
		public static ImageIcon carretera = new ImageIcon("resources"+File.separator+"carretera.png");
		public static ImageIcon verde = new ImageIcon("resources"+File.separator+"verde.png");
		public static ImageIcon base = new ImageIcon("resources"+File.separator+"base.png");
		public static ImageIcon finalb = new ImageIcon("resources"+File.separator+"final.png");
		public static JFrame f ;
	    Creador() {
	        initializeGui();
	    }

	    
	    /**
	     * @wbp.parser.entryPoint
	     */
	    public final void initializeGui() {
	    	for (int i = 0; i < 8; i++) {
				for (int j = 0; j<15; j++) {
					arrayMapa[i][j] = 0;
				}				
	    	}

	        // set up the main GUI
	    	JButton ColocarT = new JButton( "Colocar Base" );
	    	JButton Eliminar = new JButton( "Eliminar " );
	    	JButton ColocarS = new JButton( "Colocar Spawn" );
			JButton Camino = new JButton( "Camino" );
			JButton Atras = new JButton("Atras");
			JButton Guardar = new JButton( "Guardar" );
			
	        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
	        JToolBar tools = new JToolBar();
	        tools.setFloatable(false);
	        gui.add(tools, BorderLayout.PAGE_START);
			tools.addSeparator();
			tools.add( ColocarT );
			tools.addSeparator();
			tools.add( ColocarS );
			tools.addSeparator();
			tools.add( Eliminar );
			tools.addSeparator();
			tools.add( Camino );
			tools.addSeparator();
			tools.add( Guardar );
			tools.addSeparator();
			tools.add(Atras);


	        chessBoard = new JPanel(new GridLayout(0, 15));
	        chessBoard.setBorder(new LineBorder(Color.BLACK));
	        gui.add(chessBoard);

	        // esto deberia ser un hilo aparte y simplemente hacer una llamada (dibujar el mapa)
	        Insets buttonMargin = new Insets(0,0,0,0);
	        for (int ii = 0; ii < chessBoardSquares.length; ii++) {
	            for (int jj = 0; jj < chessBoardSquares[ii].length; jj++) {
	                JButton b = new JButton();
	                b.setMargin(buttonMargin);
	                // hay que cambiar el icono transparente para que redibuje por la casilla 
	                ImageIcon icon = new ImageIcon(
	                        new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB));
	                b.setIcon(icon);            
	                b.setBackground(Color.white);
	                b.setIcon(verde);
	                int bx = ii;
	                int by = jj;
	    			b.addActionListener(new ActionListener() {
	    				public void actionPerformed(ActionEvent arg0) {
	    					x = bx;
	    					y = by;
	    					boolean encontradoT= false;
	    					boolean encontradoS = false;
	    	    	    	for (int i = 0; i < 15; i++) {
	    	    				for (int j = 0; j<15; j++) {
	    	                if(arrayMapa[i][j] == 4) {
	    						torreUp = true;
	    						encontradoT = true;
	    					}
	    					if(arrayMapa[i][j] == 3) {
	    						spawnUp = true;
	    						encontradoS = true;	
	    					}
	    	    				}
	    	    				}
	    	    	    	if (encontradoT == false) {
	    	    	    		torreUp = false;
	    	    	    	}
	    	    	    	if (encontradoS == false) {
	    	    	    		spawnUp = false;
	    	    	    	}
	    					if((torreUp == true && tipoC == 4) || (spawnUp == true && tipoC == 3)) {
	    					}else {
	    					arrayMapa[by][bx] = tipoC;
	    					if(tipoC == 1) {
	    						b.setIcon(carretera);
	    					}else if(tipoC == 4) {
	    						b.setIcon(finalb);
	    					}else if(tipoC == 0) {
	    						b.setIcon(verde);
	    					}else if(tipoC == 3) {
	    						b.setIcon(base);
	    					}
	    					}
	    				}	    			
	    			});
	    			
	                chessBoardSquares[ii][jj] = b;
	            
	        }
	        }
	        Atras.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent arg0) {
	        		f.dispose();
	        		MenuJugarCrearMapa.main(null);
	        	}
	        });
	        
			ColocarT.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					tipoC = 4;

				}
			});
			ColocarS.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					boolean torreUp = false;
					for (int i = 0; i < arrayMapa.length; i++) {
						for (int j = 0; j<arrayMapa.length; j++) {
							if (arrayMapa[i][j] == 3) {
								torreUp = true;
							}
						}
					}
					if(torreUp = false) {
							if (x == -1 || y == -1) {
						
							}else {
								arrayMapa[y][x] = 3;
							}
					}else {
						
					}
					tipoC = 3;

				}
			});
			Eliminar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					tipoC = 0;

				}
			});
			Camino.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					tipoC = 1;

				}
			});
	        //Los Syso meterlos como Insert con la clase BD
			Guardar.addActionListener(new ActionListener() {
				String mapaEntero = "";
				public void actionPerformed(ActionEvent arg0) {
					for (int i = 0; i < 8; i++) {
						for (int j = 0; j<15; j++) {
							mapaEntero = mapaEntero + (arrayMapa[i][j]+";");
						}
					}
					String nombreMapa;
					nombreMapa = JOptionPane.showInputDialog("Inserte el nombre del mapa:");

					ArrayList<String> cogido = new ArrayList<String>();
					if (nombreMapa.equals("")) {
						JOptionPane.showMessageDialog(null, "Escoge un nombre.");
					}else {
						cogido = BD.mapaNombreSelect(Login.s, nombreMapa);
						if(cogido.isEmpty()) {
							BD.mapasInsert(Login.s, Login.textField.getText(), mapaEntero, nombreMapa);
							BD.puntuacionInsert(Login.s, nombreMapa, "", 0);
							f.dispose();
							MenuJugarCrearMapa.main(null);
						}else {
						JOptionPane.showMessageDialog(null, "Nombre de mapa ya existente");	
						}
					}

				}
			});

	        // fill the top row
	        for (int ii = 0; ii < 8; ii++) {
	        	 for (int jj = 0; jj < 15; jj++) {
		                switch (jj) {
		                    case 0:
		                    default:
		                        chessBoard.add(chessBoardSquares[jj][ii]);
		                }
		            }
	        }

	    }

	    public final JComponent getChessBoard() {
	        return chessBoard;
	    }

	    public final JComponent getGui() {
	        return gui;
	    }


	    public static void main(String[] args) {
	        Runnable r = new Runnable() {
	        	
	            @Override
	            public void run() {
	              	Creador cb =
	                        new Creador();
	                f = new JFrame("Creador de mapas");
	                f.getContentPane().add(cb.getGui());
	                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	                f.setLocationByPlatform(true);
	                // ensures the frame is the minimum size it needs to be
	                // in order display the components within it
	                f.pack();
	                // ensures the minimum size is enforced.
	                f.setMinimumSize(f.getSize());
	                f.setVisible(true);
	            }
	        };
	        SwingUtilities.invokeLater(r);
	    }
}
