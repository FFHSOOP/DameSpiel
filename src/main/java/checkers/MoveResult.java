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
    private ArrayList<Piece> piecesList = new ArrayList<>();

    public MoveResult(MoveType type) {
        this(type, null);
    }

    public MoveResult(MoveType type, ArrayList<Piece> piecesList) {
        this.type = type;
        this.piecesList = piecesList;
    }

    public MoveType getType() {
        return type;
    }

    public ArrayList<Piece> getPiecesList() {
        return piecesList;
    }

}
