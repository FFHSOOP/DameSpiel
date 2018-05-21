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
    private Pane gameInfo;
    private Label lbRound;
    private Label lbTurnOf;
    private Label lbLostLight;
    private Label lbLostDark;

    private boolean turnOfLight; //True falls Weiss dran ist
    private int lostLight = 0;
    private int lostDark = 0;
    private int round = 0;

    public GameInfo() {
        gameInfo = new VBox(); //Spielinformationen
        gameInfo.relocate(0, 800);
        gameInfo.setPadding(new Insets(10));
        //gameInfo.setHgap(10);
        //gameInfo.setVgap(10);
        lbRound = new Label("Runde " + round);
        lbTurnOf = new Label("Der erste Spieler kann beginnen");
        lbLostLight = new Label("Verlorene Spielsteine Weiss: " + lostLight);
        lbLostDark = new Label("Verlorene Spielsteine Schwarz: " + lostDark);
        
        lbRound.setFont(new Font("Arial", 20));
        lbTurnOf.setFont(new Font("Arial", 20));
        lbLostLight.setFont(new Font("Arial", 20));
        lbLostDark.setFont(new Font("Arial", 20));
        gameInfo.getChildren().addAll(lbRound, lbTurnOf, lbLostDark, lbLostLight);
    }

    public Parent getGameInfo() {
        return gameInfo;
    }

    public int getLostLight() {
        return lostLight;
    }

    public int getLostDark() {
        return lostDark;
    }

    public int getRound() {
        return round;
    }

    public void countUpRound() {
        round++;
    }
    
    public void countUpLostLight(){
        lostLight++;
    }
    
    public void countUpLostDark(){
        lostDark++;
    }
    
    private String turnOfMessage(){
        return (turnOfLight ? "Runde von Hell" : "Runde von Dunkel");
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