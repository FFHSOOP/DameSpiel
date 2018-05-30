package checkers;

/**
 * Enum Type Definition der Spielsteinee
 * Farbe und Spielrichtung des Steins
 *
 * @author Marco Wyssmann
 * @author Benjamin Steffen
 * @author Stefan Nyffenegger
 * @version 1.0
 */
public enum PieceType {
    BLACK(1), WHITE(-1);

    final int moveDir;

    PieceType(int moveDir) {
        this.moveDir = moveDir;
    }
}
