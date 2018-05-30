package checkers;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.SVGPath;

import static checkers.Game.TILE_SIZE;

/**
 * Repraesentiert ein Spielstein
 *
 * @author Marco Wyssmann
 * @author Benjamin Steffen
 * @author Stefan Nyffenegger
 * @version 1.0
 */
public class Piece extends StackPane {

    private PieceType type; //Spielstein Typ

    private double mouseX, mouseY; //Mausposition
    private double oldX, oldY; //Alte Position 

    private boolean draughts;

    /**
     * Konstruktor eines Spielsteins
     * Setzt die Farbe, Dimension und Platzierung mit JavaFX Funktionen
     * <p>
     * setOnMousePressed: Speilstein bewegt sich mit bei gedrückter Mausbewegung
     * setOnMouseDragged: Beim loslassen der Maus wird der Spielstein verlagert
     *
     * @param type PieceType des Spielsteins
     * @param x    x-Position des Feldes
     * @param y    y-Position des Feldes
     */

    public Piece(PieceType type, int x, int y) {
        this.type = type;

        move(x, y);

        Ellipse bg = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.26);
        bg.setFill(Color.BLACK);

        bg.setStroke(Color.BLACK);
        bg.setStrokeWidth(TILE_SIZE * 0.03);

        bg.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
        bg.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 2 + TILE_SIZE * 0.07);

        Ellipse ellipse = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.26);
        ellipse.setFill(type == PieceType.BLACK
                ? Color.valueOf("#504438") : Color.valueOf("#e7e7e7"));

        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(TILE_SIZE * 0.03);

        ellipse.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
        ellipse.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 2);

        getChildren().addAll(bg, ellipse);


        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
        });
    }

    /**
     * Gibt Zustand über Dame zurück
     *
     * @return Zustand ob Spielstein Dame ist
     */
    public boolean isDraughts() {
        return draughts;
    }

    /**
     * Setzt den Wert der Dame und verleiht dem Spielsteine eine Krone
     *
     * @param draughts Setzt den Wert der Dame
     */
    public void setDraughts(boolean draughts) {
        this.draughts = draughts;

        SVGPath svgPath = new SVGPath();
        String path = "M" + TILE_SIZE * 0.32 + "," + TILE_SIZE * 0.06 + " "
                + TILE_SIZE * 0.23 + "," + TILE_SIZE * 0.15 + " "
                + TILE_SIZE * 0.16 + "," + TILE_SIZE * 0.06 + " "
                + TILE_SIZE * 0.09 + "," + TILE_SIZE * 0.15 + " "
                + TILE_SIZE * 0.00 + "," + TILE_SIZE * 0.06 + " "
                + TILE_SIZE * 0.02 + "," + TILE_SIZE * 0.26 + " "
                + TILE_SIZE * 0.30 + "," + TILE_SIZE * 0.26 + "Z";

        svgPath.setContent(path);
        svgPath.setTranslateX((TILE_SIZE - TILE_SIZE * 0.32 * 2) / 2);
        svgPath.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 2);
        svgPath.setFill(Color.valueOf("#ffff00"));
        svgPath.setStroke(Color.BLACK);
        svgPath.setStrokeWidth(TILE_SIZE * 0.03);

        getChildren().add(svgPath);

    }

    /**
     * Fragt den PieceType des Spielsteins ab
     *
     * @return Gibt den PieceType zurück
     */
    public PieceType getType() {
        return type;
    }

    /**
     * Fragt die alte alte x-Position ab
     *
     * @return Gibt die alte x-Position zurück
     */
    public double getOldX() {
        return oldX;
    }

    /**
     * Fragt die alte alte y-Position ab
     *
     * @return Gibt die alte y-Position zurück
     */
    public double getOldY() {
        return oldY;
    }

    /**
     * Verschiebt den Spielstein
     *
     * @param x-Position in Spielfeldkoordinate 0 bis WIDTH - 1
     * @param y-Position in Spielfeldkoordinate 0 bis HEIGHT - 1
     */
    public void move(int x, int y) {
        oldX = x * TILE_SIZE;
        oldY = y * TILE_SIZE;
        relocate(oldX, oldY);
    }

    /**
     * Belaesst den Spielstein am aktuellen Ort
     */
    public void abortMove() {
        relocate(oldX, oldY);
    }
}
