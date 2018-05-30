package checkers;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Marco Wyssmann
 * @author Benjamin Steffen
 * @author Stefan Nyffenegger
 * @version 1.0
 */

public class PieceTest {


    //Testfixture
    private int xPos;
    private int yPos;
    private int newXPos;
    private int newYPos;
    private Piece piece;

    /**
     *
     */
    @Before
    public void initTestFixtures() {
        xPos = 2;
        yPos = 1;
        newXPos = 4;
        newYPos = 3;

        piece = new Piece(PieceType.BLACK, xPos, yPos);
    }

    /**
     * Test ob bei Zugabbruch die Position stimmt
     * <p>
     * Test method for {@link checkers.Piece#abortMove()}.
     */
    @Test
    public void testAbortMove() {
        piece.abortMove();
        assertTrue("Position wurde nicht geaendert", piece.getOldX() / Game.TILE_SIZE == xPos &&
                piece.getOldY() / Game.TILE_SIZE == yPos);
        assertTrue("Position Grafikposition und Feldposition stimmen ueberein", piece.getOldX() ==
                piece.getLayoutX() && piece.getOldY() == piece.getLayoutY());
    }

    /**
     * Test ob die Spielsteine richtig verschoben werden
     * <p>
     * Test method for {@link checkers.Piece#move(int, int)}.
     */
    @Test
    public void testMove() {
        piece.move(newXPos, newYPos);
        assertTrue("Piece wurde auf richtiges Feld verschoben", piece.getLayoutX() / Game.TILE_SIZE ==
                newXPos && piece.getLayoutY() / Game.TILE_SIZE == newYPos);
        assertTrue("Position Grafikposition und Feldposition stimmen ueberein",
                piece.getOldX() == piece.getLayoutX() && piece.getOldY() == piece.getLayoutY());

    }

}
