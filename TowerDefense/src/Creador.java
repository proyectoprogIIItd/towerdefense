import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;

public class Creador {
	 private final JPanel gui = new JPanel(new BorderLayout(3, 3));
	    private JButton[][] chessBoardSquares = new JButton[8][8];
	    private JPanel chessBoard;


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
			Lvl.addItem(1);
			Lvl.addItem(2);
			Lvl.addItem(3);
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

	        // create the chess board squares
	        Insets buttonMargin = new Insets(0,0,0,0);
	        for (int ii = 0; ii < chessBoardSquares.length; ii++) {
	            for (int jj = 0; jj < chessBoardSquares[ii].length; jj++) {
	                JButton b = new JButton();
	                b.setMargin(buttonMargin);
	                // our chess pieces are 64x64 px in size, so we'll
	                // 'fill this in' using a transparent icon..
	                ImageIcon icon = new ImageIcon(
	                        new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
	                b.setIcon(icon);
	                if ((jj % 2 == 1 && ii % 2 == 1)
	                        //) {
	                        || (jj % 2 == 0 && ii % 2 == 0)) {
	                    b.setBackground(Color.WHITE);
	                } else {
	                    b.setBackground(Color.BLACK);
	                }
	                chessBoardSquares[jj][ii] = b;
	            }
	        }

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

	                JFrame f = new JFrame("ChessChamp");
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
