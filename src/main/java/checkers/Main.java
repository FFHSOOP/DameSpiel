package checkers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import checkers.Game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Mainklasse
 *
 * @author Marco Wyssmann
 * @author Benjamin Steffen
 * @author Stefan Nyffenegger
 * @version 1.0
 */
public class Main extends Application {

    /**
     * Main
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Start Methode von JavaFX, wird durch launch() aufgerufen
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        Game game = new Game();

        Scene scene = new Scene( game.createMenu(primaryStage) );
        primaryStage.setTitle("Checkers Draughts - Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Erstellt ein Auswahlmenu
     *
     * @return
     */

}