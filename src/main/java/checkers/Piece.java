package checkers;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import static checkers.Main.TILE_SIZE;

/**
 * Repraesentiert einen Spielstein
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
     * 
     * @param type
     * @param x
     * @param y 
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
    
    // by bensch
    /**
     * 
     * @return 
     */
    public boolean isDraughts() {
        return draughts;
    }

    /**
     * 
     * @param draughts 
     */
    public void setDraughts(boolean draughts) {
        this.draughts = draughts;

        Ellipse ellipse = new Ellipse(TILE_SIZE * 0.15625, TILE_SIZE * 0.13);
        ellipse.setFill(Color.BLACK);

        //ellipse.setStroke(Color.BLACK);
        //ellipse.setStrokeWidth(TILE_SIZE * 0.03);
        ellipse.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
        ellipse.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 2);

        getChildren().add(ellipse);
    }

    /**
     * 
     * @return 
     */
    public PieceType getType() {
        return type;
    }

    /**
     * Gibt die alte X Position zurueck
     * @return 
     */
    public double getOldX() {
        return oldX;
    }

    /**
     * Gibt die alte Y Position zurueck
     * @return 
     */
    public double getOldY() {
        return oldY;
    }

    /**
     * Verschiebt den Spielstein
     *
     * @param x
     * @param y
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
