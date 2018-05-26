/**
 * 
 */
package checkers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author 
 *
 */
public class PieceTest {

    
    //Testfixture
    private int xPos;
    private int yPos;
    private Piece piece;
  
    

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void initTestFixtures(){
	xPos = 5; 
	yPos = 2;
	piece = new Piece(PieceType.BLACK, xPos, yPos);
    }

    /**
     * Test method for {@link checkers.Piece#move(int, int)}.
     */
    @Test
    public void testMove() {
	piece.move(6, 6);
	assertTrue(message, condition);
    }

    /**
     * Test method for {@link checkers.Piece#abortMove()}.
     */
    @Test
    public void testAbortMove() {
	fail("Not yet implemented");
    }

}
