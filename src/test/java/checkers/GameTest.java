package checkers;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;

/**
* Diese Klasse testet die grundlegende Funktionalitaet des Spiels
* 
* @author Marco Wyssmann
* @author Benjamin Steffen
* @author Stefan Nyffenegger
* @version 1.0
*/
public class GameTest {
    
    //Testfixture
    private Game game;
    private final int MULTIPLAYER = 1;
    private final int SINGLEPLAYER = 2;
    private Tile[][] board; 	//[x]WIDTH [y]HEIGHT
  
    
    
      @BeforeClass
      public static void setUpBeforeClass() throws Exception {
	  JFXPanel dummy = new JFXPanel(); // Nötig damit Test funktioniert 
      }

    /** Initialisierung des Spiels fuer die Tests
     * Wird vor jedem Test ausgeführt
     * @throws java.lang.Exception
     */
    @Before
    public void initTestFixtures(){
	game = new Game();
	game.setGameMode(MULTIPLAYER);
	game.createContent();
	board = game.getBoard();
    }
    /** 
     * Test ob die Höhe und die Weite des Spielbretts richtig sind
     */
    @Test
    public void testBoardSize() {
	int xa = Game.WIDTH;
	int ya = Game.HEIGHT;
	int xb = board[0].length;
	int yb = board.length;
	
	assertEquals(xa , xb , "nicht richtige Weite!");
	assertEquals(ya , yb , "nicht richtige Höhe!");
    }
    
    /**
     * Testet ob alle Felder des Spielbretts mit Feldern initialisiert wurden
     * und ob die Farben der Felder stimmen
     */
    @Test
    public void testFieldInitialisation() {
	int failure = 0;
	int failureColor = 0;
	
	for (int y = 0; y < Game.HEIGHT; y++) {
	    for (int x = 0; x < Game.WIDTH; x++) {
		if ( !board[x][y].getClass().equals(Tile.class)) {
	
		  failure++; 
		}
		if ((x + y) % 2 == 0 && board[x][y].getTileType() != TileType.WHITE  ) {
		     failureColor++;
		}
	    }   
	}
	assertEquals( 0 , failure , "Felder nicht korrekt initialisiert!" );
	assertEquals( 0 , failureColor , "Felder haben falsche Farbe!");
	
    }
    /**
     * Testet ob die richtige Anzahl an Spielsteinen erstellt wurde und ob die Spielsteine richtig 
     * gesetzt wurden
     */
    @Test
    public void testPieceInitialisation(){
	int pieceCount = 0;
	int pieceCountBlack = 0;
	int pieceCountWhite = 0;
	int pieceWrongPosition = 0;
	
	for (int y = 0; y < Game.HEIGHT; y++) {
	    for (int x = 0; x < Game.WIDTH; x++) {
		if ( board[x][y].hasPiece()) {
	
		  pieceCount++; 
		}
		if (y <= 2 && (x + y) % 2 != 0 && board[x][y].hasPiece()) {
		    pieceCountBlack++;
		}
		if (y >= 5 && (x + y) % 2 != 0 && board[x][y].hasPiece()) {
		    pieceCountWhite++;
		}
		if (y > 2 && y < 5 && board[x][y].hasPiece()) {
		     pieceWrongPosition++; 
		}
	    }      
	}
	assertEquals( 24 , pieceCount , "Anzahl Spielsteine nicht korrekt!" );
	assertEquals( 12 , pieceCountBlack , "Anzahl schwarze Steine nicht korrekt!" );
	assertEquals( 12 , pieceCountWhite , "Anzahl weisse Steine nicht korrekt!" );
	assertEquals( 0 , pieceWrongPosition , "Steine wurden falsch gesetzt!" );
    }

    //Ab hier Test der Spielfunktionen --------------------------------------------------
    
    /** 
     * 	Ausgangslage bei Spielstart
     * 	
     * 		[ ][b][ ][b][ ][b][ ][b]
     * 		[b][ ][b][ ][b][ ][b][ ]
     * 		[ ][b][ ][b][ ][b][ ][b]
     * 		[ ][ ][ ][ ][ ][ ][ ][ ]
     * 		[ ][ ][ ][ ][ ][ ][ ][ ]
     * 		[w][ ][w][ ][w][ ][w][ ]
     * 		[ ][w][ ][w][ ][w][ ][w]
     * 		[w][ ][w][ ][w][ ][w][ ]
     * 
     */
    
    
    
    
    
    /**	Setup der Spielsteine für die Tests
     * 
     * 		[ ][ ][ ][b][ ][b][ ][b]
     * 		[b][ ][b][ ][ ][ ][b][ ]
     * 		[ ][b][ ][b][ ][b][ ][b]
     * 		[b][ ][ ][ ][b][ ][ ][ ]
     * 		[ ][w][ ][ ][ ][w][ ][ ]
     * 		[ ][ ][w][ ][w][ ][w][ ]
     * 		[ ][w][ ][w][ ][w][ ][w]
     * 		[w][ ][ ][ ][w][ ][w][ ]
     * 
     * Weiss ist dann am Zug
     * Setup wird durch Methode setupForTest erzeugt
     */
    public void setupForTest() {
	game.performMove(board[3][2].getPiece(), 4, 3); // erster Zug schwarz
	game.performMove(board[0][5].getPiece(), 1, 4); // erster Zug weiss
	game.performMove(board[4][1].getPiece(), 3, 2);
	game.performMove(board[4][5].getPiece(), 5, 4);
	game.performMove(board[1][2].getPiece(), 0, 3);
	game.performMove(board[3][6].getPiece(), 4, 5);
	game.performMove(board[0][1].getPiece(), 1, 2);
	game.performMove(board[2][7].getPiece(), 3, 6);
	game.performMove(board[1][0].getPiece(), 0, 1); // Zug schwarz
	
    }
    
    
    /**
     * Test ob die methode setupForTest das richtige Ausgangssetup erstellt hat
     */
    @Test
    public void testSetupForTest() {
	setupForTest();
	
	assertTrue( board[4][3].hasPiece() && board[4][3].getPiece().getType() == PieceType.BLACK );
	assertTrue( board[1][4].hasPiece() && board[1][4].getPiece().getType() == PieceType.WHITE );
	assertTrue( board[3][2].hasPiece() && board[3][2].getPiece().getType() == PieceType.BLACK );
	assertTrue( board[5][4].hasPiece() && board[5][4].getPiece().getType() == PieceType.WHITE );
	assertTrue( board[0][3].hasPiece() && board[0][3].getPiece().getType() == PieceType.BLACK );
	assertTrue( board[4][5].hasPiece() && board[4][5].getPiece().getType() == PieceType.WHITE );
	assertTrue( board[1][2].hasPiece() && board[1][2].getPiece().getType() == PieceType.BLACK );
	assertTrue( board[3][6].hasPiece() && board[3][6].getPiece().getType() == PieceType.WHITE );
	assertTrue( board[0][1].hasPiece() && board[0][1].getPiece().getType() == PieceType.BLACK );
	
    }
    /**
     * Testet ob die normale Bewegung funktioniert
     */
    @Test
    public void testPerformMove() {
	setupForTest();
	
	assertEquals( true , board[1][4].hasPiece() , "Position 1 / 4 hat keinen Spielstein!!" );
	assertEquals( false , board[2][3].hasPiece() , "Position 2 / 3 hat bereits einen Spielstein!" );
	
	game.performMove(board[1][4].getPiece(), 2, 3);
	
	assertEquals( true , board[2][3].hasPiece() , "Position 2 / 3 hat noch keinen Spielstein!!" );
	assertEquals( false , board[1][4].hasPiece() , "Position 1 / 4 hat immer noch einen Spielstein!" );
	
    }
    
    @Test
    public void testCheckKill() {
	
	assertTrue(true);
    }

    @Test
    public void testCanKill() {
	assertTrue(true);
    }

}
