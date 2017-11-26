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
	    private JButton[][] chessBoardSquares = new JButton[8][8];
	    private JPanel chessBoard;
		public static Image[] air = new Image[100];
		public static Image[] ground = new Image[100];
		private int[][] arrayMapa;
		private int x = -1;
		private int y = -1;
		
	    Creador() {
	        initializeGui();
	    }

	    public final void initializeGui() {
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


	        chessBoard = new JPanel(new GridLayout(0, 9));
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
	                
	                int bx = jj;
	                int by = ii;
	    			b.addActionListener(new ActionListener() {
	    				public void actionPerformed(ActionEvent arg0) {
	    					boolean isSelected = b.isSelected();
	    					if(isSelected = true) {
	    					x = bx;
	    					y = by;
	    					}
	    				}
	    			});
	    			
	                chessBoardSquares[jj][ii] = b;
	            
	        }
	        }
	        
			ColocarT.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					boolean torreUp = false;
					for (int i = 0; i < arrayMapa.length; i++) {
						for (int j = 0; j<arrayMapa[y].length; j++) {
							if (arrayMapa[j][i] == 4) {
								torreUp = true;
							}
						}
					}
					if(torreUp = false) {
							if (x == -1 || y == -1) {
						
							}else {
								arrayMapa[x][y] = 4;
							}
					}else {
						
					}
				}
			});
			ColocarS.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					boolean torreUp = false;
					for (int i = 0; i < arrayMapa.length; i++) {
						for (int j = 0; j<arrayMapa[y].length; j++) {
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
				}
			});
			Eliminar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (x == -1 || y == -1) {
						
					}else {
					arrayMapa[x][y] = -1;
					}
				}
			});
			Camino.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (x == -1 || y == -1) {
						
					}else {
						arrayMapa[x][y] = 1;
						if ((x+1) <= arrayMapa.length && (y+1) <= arrayMapa[y].length && arrayMapa[x+1][y+1] == -1) {
							arrayMapa[x+1][y+1]= 2;
						}
						if ((x+1) <= arrayMapa.length && arrayMapa[x+1][y] == -1) {
							arrayMapa[x+1][y] = 2;
						}
						if ((x+1) <= arrayMapa.length && (y-1) > 0 && arrayMapa[x+1][y-1] == -1) {
							arrayMapa[x+1][y-1] = 2;
						}
						if ((y-1) > 0 && arrayMapa[x][y-1] == -1) {
							arrayMapa[x][y-1] = 2;
						}
						if ((y-1) > 0 && (x-1) > 0 && arrayMapa[x-1][y-1] == -1) {
							arrayMapa[x-1][y-1] = 2;
						}
						if((x-1) > 0 && arrayMapa[x-1][y] == -1) {
							arrayMapa[x-1][y] = 2;
						}
						if((x-1) > 0 && (y+1) <= arrayMapa[y].length && arrayMapa[x-1][y+1] == -1) {
							arrayMapa[x-1][y+1] = 2;
						}
						if ((y+1) <= arrayMapa[y].length && arrayMapa[x][y+1] == -1) {
							arrayMapa[x][y+1] = 2;
						}
					}
				}
			});
	        
			Guardar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					for (int i = 0; i < arrayMapa.length; i++) {
						for (int j = 0; j<arrayMapa[y].length; j++) {
							System.out.print(arrayMapa[x][y]);
						}
						System.out.println("");
					}
				}
			});
	        //falta por inicializar el array de integer con los for{} y corregir el createImage
	        
//			for (int i =0; i<ground.length; i++){
//				ground[i] = new ImageIcon("resources"+File.separator+"ground.png").getImage();
//				ground[i] = createImage(new FilteredImageSource(ground[i].getSource(), new CropImageFilter(0, 32*i, 32, 32)));
//			}
//			for (int i =0; i<air.length; i++){
//				air[i] = new ImageIcon("resources"+File.separator+"air.png").getImage();
//				air[i] = createImage(new FilteredImageSource(air[i].getSource(), new CropImageFilter(0, 32*i, 32, 32)));
//			}
//	       
//	        for (int x = 0; x < 8; x++) {
//	            for (int y = 0; y < 8; y++) {
//	            	JButton b = new JButton();
//	            	ImageIcon icon = new ImageIcon(
//		            new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB));
//	            	int i = arrayMapa[x][y];
//            if(i != -1) {            	
//            	b.setIcon((Icon) ground[i]);
//            }else {
//            	b.setIcon(icon);
//            }
//	            }
//	        }
	        //fill the chess board
	        chessBoard.add(new JLabel(""));
	        // fill the top row
	        for (int ii = 0; ii < 8; ii++) {
	        	 for (int jj = 0; jj < 8; jj++) {
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
	        for (int ii = 0; ii < 8; ii++) {
	            for (int jj = 0; jj < 8; jj++) {
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
	                f.add(cb.getGui());
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
