package checkers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Repraesentiert ein Feld auf dem Spielbrett
 *
 * @author Marco Wyssmann
 * @author Benjamin Steffen
 * @author Stefan Nyffenegger
 * @version 1.0
 */
public class Tile extends Rectangle {

    private Piece piece; //Spielstein

    /**
     * 
     * @param light
     * @param x
     * @param y 
     */
    public Tile(boolean light, int x, int y) {
        setWidth(Main.TILE_SIZE);
        setHeight(Main.TILE_SIZE);

        relocate(x * Main.TILE_SIZE, y * Main.TILE_SIZE);

        setFill(light ? Color.valueOf("#ffffff") : Color.valueOf("#b3b5bc"));
    }

    /**
     * Gibt den Spielstein des Feldes zurueck
     * @return Piece
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Setzt den Spielstein auf dem Feld
     * @param piece Piece
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * Gibt zurueck ob das Feld einen Spielstein beinhaltet
     * @return 
     */
    public boolean hasPiece() {
        return piece != null;
    }
}
