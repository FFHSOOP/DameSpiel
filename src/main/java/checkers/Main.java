package checkers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


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
}