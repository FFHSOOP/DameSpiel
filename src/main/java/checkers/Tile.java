package checkers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Repräsentiert ein Feld auf dem Spielbrett
 *
 * @author Marco Wyssmann
 * @author Benjamin Steffen
 * @author Stefan Nyffenegger
 * @version 1.0
 */
public class Tile extends Rectangle {

    private Piece piece; //Spielstein
    private TileType tileType;

    /**
     * Konstruktor eines Feldes
     * Setzt die Farbe, Dimension und Platzierun mit JavaFX Funktionen
     *
     * @param light Definiert Farbe des Steins BLACK oder WHITE per Enum Type TileType
     * @param x     x-Position des Feldes
     * @param y     y-Position des Feldes
     */
    public Tile(boolean light, int x, int y) {
        setWidth(Game.TILE_SIZE);
        setHeight(Game.TILE_SIZE);

        relocate(x * Game.TILE_SIZE, y * Game.TILE_SIZE);

        setFill(light ? Color.valueOf("#fff9e9") : Color.valueOf("#986430"));

        if (light) {
            tileType = TileType.WHITE;
        } else {
            tileType = TileType.BLACK;
        }
    }


    /**
     * Gibt den Spielstein des Feldes zurueck
     *
     * @return Piece
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Setzt den Spielstein auf dem Feld
     *
     * @param piece Piece
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * Gibt zurueck ob das Feld einen Spielstein beinhaltet
     *
     * @return
     */
    public boolean hasPiece() {
        return piece != null;
    }

    /**
     * Gibt die Farbe eines Feldes zurueck
     *
     * @return
     */
    public TileType getTileType() {
        return tileType;
    }
}
