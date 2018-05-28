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
        setWidth(Game.TILE_SIZE);
        setHeight(Game.TILE_SIZE);

        relocate(x * Game.TILE_SIZE, y * Game.TILE_SIZE);

        setFill(light ? Color.valueOf("#fff9e9") : Color.valueOf("#986430"));
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
