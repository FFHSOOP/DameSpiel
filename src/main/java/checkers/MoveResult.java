package checkers;

import java.util.ArrayList;

/**
 * 
 *
 * @author Marco Wyssmann
 * @author Benjamin Steffen
 * @author Stefan Nyffenegger
 * @version 1.0
 */
public class MoveResult {

    private MoveType type;
    private Piece piece;


    public MoveResult(MoveType type) {
        this(type, null);
    }

    public MoveResult(MoveType type, Piece piece) {
        this.type = type;
        this.piece = piece;
    }

    public MoveType getType() {
        return type;
    }

    public Piece getPiece() {
        return piece;
    }


    // alte implementation für mehrere kills
    // private ArrayList<Piece> piecesList = new ArrayList<>();

    /* public ArrayList<Piece> getPiecesList() {
        return piecesList;
    }*/

    /*public MoveResult(MoveType type, ArrayList<Piece> piecesList) {
        this.type = type;
        this.piecesList = piecesList;
    }*/

}
