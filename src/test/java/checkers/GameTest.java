package checkers;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;

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

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void initTestFixtures(){
	game = new Game();
	game.setGameMode(MULTIPLAYER);
	game.createContent();
	board = game.getBoard();
    }
    
    @Test
    public void testBoardSize() {
	int xa = Game.WIDTH;
//	int xa = 5;
	int ya = Game.HEIGHT;
	int xb = board[0].length;
	int yb = board.length;
	
	assertEquals(xa , xb , "nicht richtige Weite!");
	assertEquals(ya , yb , "nicht richtige Höhe!");
    }
    
    @Test
    public void testFieldInitialisation() {
	int failure = 0;
	for (int y = 0; y < Game.HEIGHT; y++) {
	    for (int x = 0; x < Game.WIDTH; x++) {
		if ( !board[x][y].getClass().equals(Tile.class)) {
	
		  failure++; 
		}
	    }   
	}
	assertEquals( 0 , failure , "Felder nicht korrekt initialisiert!" );
	
    }

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

        
    @Test
    public void testCheckKill() throws Exception {
	
	assertTrue(true);
    }

    @Test
    public void testCanKill() {
	assertTrue(true);
    }

}
