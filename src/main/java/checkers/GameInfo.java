package checkers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
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
    private FlowPane gameInfo;
    Label lbRunde;
    Label lbLostLight;
    Label lbLostDark;

    private int lostLight = 0;
    private int lostDark = 0;
    private int round = 0;
    private boolean roundOfLight; //True falls Weiss dran ist

    public GameInfo() {
        gameInfo = new FlowPane(); //Spielinformationen
        gameInfo.relocate(0, 800);
        gameInfo.setPadding(new Insets(10));
        gameInfo.setHgap(10);
        gameInfo.setVgap(10);
        lbRunde = new Label("Runde " + round + ": Der erste Spieler kann beginnen");
        lbLostLight = new Label("Verlorene Spielsteine Weiss: " + lostLight);
        lbLostDark = new Label("Verlorene Spielsteine Schwarz: " + lostDark);
        
        lbRunde.textProperty().addListener((ObservableValue<? extends String> ov, String t, String t1) -> {
            lbRunde.setText("Runde " + round);
        });
        
        lbRunde.setFont(new Font("Arial", 20));
        lbLostLight.setFont(new Font("Arial", 20));
        lbLostDark.setFont(new Font("Arial", 20));
        gameInfo.getChildren().addAll(lbRunde, lbLostDark, lbLostLight);
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

    public void updateGameInfo() {
        lbRunde.setText("Runde " + getRound()); //Hier kommt noch rein wer dran ist
        lbLostLight.setText("Verlorene Spielsteine Weiss: " + getLostLight());
        lbLostDark.setText("Verlorene Spielsteine Schwarz: " + getLostDark());
    }
}
