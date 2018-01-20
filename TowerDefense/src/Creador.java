import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Creador {
	 private final JPanel gui = new JPanel(new BorderLayout(3, 3));
	    private JButton[][] chessBoardSquares = new JButton[15][15];
	    private JPanel chessBoard;
		public static Image[] air = new Image[100];
		public static Image[] ground = new Image[100];
		private static int[][] arrayMapa = new int[15][15];
		private static int x = -1;
		private static int y = -1;
		private static int tipoC = -1;
		private static boolean torreUp = false;
		private static boolean spawnUp = false;
		
	    Creador() {
	        initializeGui();
	    }

	    
	    /**
	     * @wbp.parser.entryPoint
	     */
	    public final void initializeGui() {
	    	for (int i = 0; i < 15; i++) {
				for (int j = 0; j<15; j++) {
					arrayMapa[j][i] = -1;
				}				
	    	}
	        // set up the main GUI
	    	JButton ColocarT = new JButton( "Colocar Base" );
	    	JButton Eliminar = new JButton( "Eliminar " );
	    	JButton ColocarS = new JButton( "Colocar Spawn" );
			JButton Camino = new JButton( "Camino" );
			JLabel textoLvl = new JLabel ("Nivel Dificultad:");
			JComboBox Lvl = new JComboBox();
			Lvl.addItem("Facil");
			Lvl.addItem("Medio");
			Lvl.addItem("Dificil");
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
			tools.add(textoLvl);
			tools.add( Lvl );
			tools.addSeparator();
			tools.add( Guardar );


	        chessBoard = new JPanel(new GridLayout(0, 16));
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
	                
	                int bx = ii;
	                int by = jj;
	    			b.addActionListener(new ActionListener() {
	    				public void actionPerformed(ActionEvent arg0) {
	    					x = bx;
	    					y = by;
	    					System.out.println(x +" "+y);
	    					boolean encontradoT= false;
	    					boolean encontradoS = false;
	    	    	    	for (int i = 0; i < 15; i++) {
	    	    				for (int j = 0; j<15; j++) {
	    	                if(arrayMapa[j][i] == 4) {
	    						torreUp = true;
	    						encontradoT = true;
	    					}
	    					if(arrayMapa[j][i] == 3) {
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
	    					arrayMapa[bx][by] = tipoC;
	    					}
	    				}	    			
	    			});
	    			
	                chessBoardSquares[jj][ii] = b;
	            
	        }
	        }
	        
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
							if (arrayMapa[j][i] == 3) {
								torreUp = true;
							}
						}
					}
					if(torreUp = false) {
							if (x == -1 || y == -1) {
						
							}else {
								arrayMapa[x][y] = 3;
							}
					}else {
						
					}
					tipoC = 3;

				}
			});
			Eliminar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					tipoC = -1;

				}
			});
			Camino.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					tipoC = 2;

				}
			});
	        
			Guardar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					for (int i = 0; i < arrayMapa.length; i++) {
						for (int j = 0; j<15; j++) {
							System.out.print(arrayMapa[i][j]+";");
						}
						System.out.println(" ");
					}
					System.out.println(Lvl.getSelectedItem());
					String nombreMapa;
					nombreMapa = JOptionPane.showInputDialog("Inserte el nombre del mapa:");
				}
			});

	        //fill the chess board
	        chessBoard.add(new JLabel(""));
	        // fill the top row
	        for (int ii = 0; ii < 15; ii++) {
	        	 for (int jj = 0; jj < 15; jj++) {
		                switch (jj) {
		                    case 0:
		                        chessBoard.add(new JLabel("" + (ii + 1),
		                                SwingConstants.CENTER));
		                    default:
		                        chessBoard.add(chessBoardSquares[jj][ii]);
		                }
		            }
	        }
	        // fill the black non-pawn piece row
	        for (int ii = 0; ii < 15; ii++) {
	            for (int jj = 0; jj < 15; jj++) {
	                switch (jj) {
	                    case 0:
	                        chessBoard.add(new JLabel("" + (ii + 1),
	                                SwingConstants.CENTER));
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
	                JFrame f = new JFrame("Creador de mapas");
	                f.getContentPane().add(cb.getGui());
	                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
