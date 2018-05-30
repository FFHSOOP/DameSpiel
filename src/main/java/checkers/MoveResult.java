package checkers;

import java.util.ArrayList;

/**
 * Definiert ein Resultat einer Bewegung und bestimmt so die Art der Ausführung in der Game Klasse
 *
 * @author Marco Wyssmann
 * @author Benjamin Steffen
 * @author Stefan Nyffenegger
 * @version 1.0
 */
public class MoveResult {

    private MoveType type;
    private Piece piece;

    /**
     * Konstruktor mit einem Argument
     * MoveType NONE oder NORMAL wird festgesetzt, jedoch kein Spielstein übergeben
     *
     */
    public MoveResult(MoveType type) {
        this(type, null);
    }

    /**
     * Konstruktor mit zwei Argumente für den MoveType KILL
     * Dabei wird der zu fressende Spielstein übergeben
     *
     */
    public MoveResult(MoveType type, Piece piece) {
        this.type = type;
        this.piece = piece;
    }

    /**
     * Fragt den MoveType ab
     *
     * @return Liefert den MoveType zurück
     */
    public MoveType getType() {
        return type;
    }

    /**
     * Fragt den zu fressenden Spielstein ab
     *
     * @return Liefert den zu fressenden Spielstein zurück
     */
    public Piece getPiece() {
        return piece;
    }

}
