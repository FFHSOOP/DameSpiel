package checkers;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Spielinformationen
 *
 * @author Marco Wyssmann
 * @author Benjamin Steffen
 * @author Stefan Nyffenegger
 * @version 1.0
 */
public class GameInfo {

    private final int MAX_PIECES = 12; //Spielsteine pro Spieler

    private VBox gameInfo;
    private Label lbRound;
    private Label lbTurnOf;
    private Label lbLostLight;
    private Label lbLostDark;

    private PieceType turnOf = PieceType.BLACK; //Runde von
    private int lostLight = 0; //Verlorene Spielsteine Hell
    private int lostDark = 0; //Verlorene Spielsteine Dunkel
    private int round = 0; //Aktuelle Spielrunde
    private boolean gameOver = false; //Spiel vorbei

    /**
     * Konfiguration der Labels
     */
    public GameInfo() {
        gameInfo = new VBox(); //Vertikales Pane
        gameInfo.relocate(0, 800);
        gameInfo.setPadding(new Insets(10));
        gameInfo.setSpacing(10);
        lbRound = new Label("Runde " + round);
        lbTurnOf = new Label("Dunkel kann beginnen");
        lbLostLight = new Label("Verlorene Spielsteine Hell: " + lostLight);
        lbLostDark = new Label("Verlorene Spielsteine Dunkel: " + lostDark);

        lbRound.setFont(new Font("Arial", 20));
        lbTurnOf.setFont(new Font("Arial", 20));
        lbLostLight.setFont(new Font("Arial", 20));
        lbLostDark.setFont(new Font("Arial", 20));
        gameInfo.getChildren().addAll(lbRound, lbTurnOf, lbLostDark, lbLostLight);
    }

    /**
     * Gibt das Grafikelement mit den Spielinformationen aus
     *
     * @return VBox mit Spielinformationen
     */
    public Parent getGameInfo() {
        return gameInfo;
    }

    /**
     * Gibt zurueck welcher Spieler am Zug ist
     *
     * @return PieceType mit Farbe
     */
    public PieceType getTurn() {
        return turnOf;
    }

    /**
     * Gibt die Verlorenen Spielsteine von Hell aus
     *
     * @return Anzahl verlorene Spielsteine Hell
     */
    public int getLostLight() {
        return lostLight;
    }

    /**
     * Gibt die Verlorenen Spielsteine von Dunkel aus
     *
     * @return Anzahl verlorene Spielsteine Dunkel
     */
    public int getLostDark() {
        return lostDark;
    }

    /**
     * Gibt die aktuelle Runde zurueck
     *
     * @return Aktuelle Runde
     */
    public int getRound() {
        return round;
    }

    /**
     * Zaehlt die Runde hoch
     */
    public void countUpRound() {
        round++;
    }

    /**
     * Zaehlt die Verlorenen Spielsteine von Hell hoch
     */
    public void countUpLostLight() {
        lostLight++;
    }

    /**
     * Zaehlt die Verlorenen Spielsteine von Dunkel hoch
     */
    public void countUpLostDark() {
        lostDark++;
    }

    public void changeTurn() {
        if (turnOf == PieceType.WHITE) {
            turnOf = PieceType.BLACK;
        } else {
            turnOf = PieceType.WHITE;
        }
    }

    /**
     * Gibt als String zurueck wer am Zug ist
     *
     * @return String mit der aktuellen Farbe
     */
    private String turnOfMessage() {
        return (turnOf == PieceType.WHITE ? "Runde von Hell" : "Runde von Dunkel");
    }

    /**
     * Gibt zurueck ob das Spiel vorbei ist
     *
     * @return Wenn true ist das Spiel vorbei
     */
    public boolean gameOver() {
        return gameOver;
    }

    /**
     * Aktualisiert die Spielinformationen
     */
    public void updateGameInfo() {
        if (lostLight >= MAX_PIECES) {
            gameOver = true;
            lbTurnOf.setText("Dunkel hat gewonnen!");
        } else if (lostDark >= MAX_PIECES) {
            gameOver = true;
            lbTurnOf.setText("Hell hat gewonnen!");
        } else {
            lbTurnOf.setText(turnOfMessage());
        }
        lbRound.setText("Runde " + round);
        lbLostLight.setText("Verlorene Spielsteine Hell: " + getLostLight());
        lbLostDark.setText("Verlorene Spielsteine Dunkel: " + getLostDark());
    }
}
