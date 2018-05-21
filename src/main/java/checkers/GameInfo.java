package checkers;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
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

    private VBox gameInfo;
    private Label lbRound;
    private Label lbTurnOf;
    private Label lbLostLight;
    private Label lbLostDark;

    private PieceType turnOfLight = PieceType.BLACK;
    private int lostLight = 0;
    private int lostDark = 0;
    private int round = 0;

    /**
     * Konfiguration der Labels
     */
    public GameInfo() {
        gameInfo = new VBox(); //Spielinformationen
        gameInfo.relocate(0, 800);
        gameInfo.setPadding(new Insets(10));
        gameInfo.setSpacing(10);
        //gameInfo.setHgap(10);
        //gameInfo.setVgap(10);
        lbRound = new Label("Runde " + round);
        lbTurnOf = new Label("Dunkel kann beginnen");
        lbLostLight = new Label("Verlorene Spielsteine Weiss: " + lostLight);
        lbLostDark = new Label("Verlorene Spielsteine Schwarz: " + lostDark);

        lbRound.setFont(new Font("Arial", 20));
        lbTurnOf.setFont(new Font("Arial", 20));
        lbLostLight.setFont(new Font("Arial", 20));
        lbLostDark.setFont(new Font("Arial", 20));
        gameInfo.getChildren().addAll(lbRound, lbTurnOf, lbLostDark, lbLostLight);
    }

    /**
     * Gibt das Grafikelement mit den Spielinformationen aus
     *
     * @return
     */
    public Parent getGameInfo() {
        return gameInfo;
    }
    
    public PieceType getTurn(){
        return turnOfLight;
    }

    /**
     * Gibt die Verlorenen Spielsteine von Hell aus
     *
     * @return
     */
    public int getLostLight() {
        return lostLight;
    }

    /**
     * Gibt die Verlorenen Spielsteine von Dunkel aus
     *
     * @return
     */
    public int getLostDark() {
        return lostDark;
    }

    /**
     * Gibt die aktuelle Runde zurueck
     *
     * @return
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
        if (turnOfLight == PieceType.WHITE) {
            turnOfLight = PieceType.BLACK;
        } else {
            turnOfLight = PieceType.WHITE;
        }
    }

    /**
     * Gibt zurueck wer am Zug ist
     *
     * @return
     */
    private String turnOfMessage() {
        return (turnOfLight == PieceType.WHITE ? "Runde von Hell" : "Runde von Dunkel");
    }

    /**
     * Aktualisiert die Spielinformationen
     */
    public void updateGameInfo() {
        lbRound.setText("Runde " + round);
        lbTurnOf.setText(turnOfMessage());
        lbLostLight.setText("Verlorene Spielsteine Weiss: " + getLostLight());
        lbLostDark.setText("Verlorene Spielsteine Schwarz: " + getLostDark());
    }
}
