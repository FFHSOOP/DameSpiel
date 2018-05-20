package checkers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

/**
 * Mainklasse
 *
 * @author Marco Wyssmann
 * @author Benjamin Steffen
 * @author Stefan Nyffenegger
 * @version 1.0
 */
public class Main extends Application {

    public static final int TILE_SIZE = 100; //Groesse eines einzelnen Feldes
    public static final int WIDTH = 8; //Anzahl Felder Breite
    public static final int HEIGHT = 8; //Anzahl Felder Hoehe

    private Tile[][] board = new Tile[WIDTH][HEIGHT]; //Feldarray

    private Group tileGroup = new Group(); //Felder
    private Group pieceGroup = new Group(); //Spielsteine

    private boolean hasToKill;

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
        Scene scene = new Scene(createMenu(primaryStage));
        primaryStage.setTitle("Checkers Draughts - Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * Erstellt ein Auswahlmenu
     * 
     * @return 
     */
    private Parent createMenu(Stage stage){
        FlowPane menu = new FlowPane();
        menu.setPadding(new Insets(10));
        menu.setHgap(10);
        menu.setVgap(10);
        
        //Button Icons
        try {
            FileInputStream inSingle = new FileInputStream("src/main/java/checkers/icon/single.png");
        Image imgSingle = new Image(inSingle);
        ImageView imageViewSingle = new ImageView(imgSingle);
        FileInputStream inMulti = new FileInputStream("src/main/java/checkers/icon/multi.png");
        Image imgMulti = new Image(inMulti);
        ImageView imageViewMulti = new ImageView(imgMulti);
        
        Button single = new Button("One Player", imageViewSingle);
        Button multi = new Button("Two Players", imageViewMulti);
        single.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                singleplayer(stage);
            }
        });
        multi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                multiplayer(stage);
            }
        });

        menu.getChildren().addAll(single, multi);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return menu;
    }
    
    /**
     * Multiplayer Modus
     * 
     * @param stage
     */
    public void multiplayer(Stage stage) {
        Scene scene = new Scene(createContent());
        stage.setTitle("Checkers Draughts - Multiplayer");
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Singleplayer Modus
     * 
     * @param stage 
     */
    public void singleplayer(Stage stage){
        System.out.println("Not implemented yet");
        //KI stuff
    }

    /**
     * Initialer Aufbau des Spielfeldes
     *
     * @return
     */
    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE); //Groesse des Spielfeldes
        root.getChildren().addAll(tileGroup, pieceGroup);

        //Initialer Aufbau der Spielsteine
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = new Tile((x + y) % 2 == 0, x, y);
                board[x][y] = tile;

                tileGroup.getChildren().add(tile);

                Piece piece = null;

                if (y <= 2 && (x + y) % 2 != 0) {
                    piece = makePiece(PieceType.RED, x, y);
                }

                if (y >= 5 && (x + y) % 2 != 0) {
                    piece = makePiece(PieceType.WHITE, x, y);
                }

                if (piece != null) {
                    tile.setPiece(piece);
                    pieceGroup.getChildren().add(piece);
                }
            }
        }
        return root;
    }

    /**
     *
     * @param piece
     * @param newX
     * @param newY
     * @return
     */
    private MoveResult tryMove(Piece piece, int newX, int newY) {

        int x0 = toBoard(piece.getOldX());
        int y0 = toBoard(piece.getOldY());

        if (board[newX][newY].hasPiece() || (newX + newY) % 2 == 0 || newY - y0 == 0) {
            return new MoveResult(MoveType.NONE);
        }

        // hier erweiterung für mehr als einer killen
        // hier erweiterung für dame auch rückwärts
        ArrayList<Piece> piecesList = new ArrayList<>();

        if (Math.abs(newX - x0) == 1 && (newY - y0 == piece.getType().moveDir || piece.isDraughts())) {

            if (hasToKill) {
                return new MoveResult(MoveType.NONE);
            }

            return new MoveResult(MoveType.NORMAL);
        } else if (Math.abs(newX - x0) >= 2 && (newY - y0 == piece.getType().moveDir * Math.abs(newY - y0) || piece.isDraughts())) {

            // tamara implementation
            int directionY = (newY - y0) / Math.abs(newY - y0);
            int directionX = (newX - x0) / Math.abs(newX - x0);
            System.out.println(newX);
            System.out.println(newY);
            System.out.println(x0);
            System.out.println(directionX + " " + directionY);

            for (int i = 1; i < Math.abs(newX - x0); i++) {
                int x1 = x0 + directionX * i;
                int y1 = y0 + directionY * i;

                System.out.println(x1 + " " + y1);

                if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != piece.getType()) {
                    piecesList.add(board[x1][y1].getPiece());
                    if (i + 1 == Math.abs(newX - x0)) {
                        return new MoveResult(MoveType.KILL, piecesList);
                    }
                    continue;
                } else {
                    break;
                }
            }

            // tamara implementation
            // Working
            // int x1 = x0 + (newX - x0) / 2;
            // int y1 = y0 + (newY - y0) / 2;
            //if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != piece.getType()) {
            //    return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
            //}
        }

        return new MoveResult(MoveType.NONE);
    }

    /**
     *
     * @param pixel
     * @return
     */
    private int toBoard(double pixel) {
        return (int) (pixel + TILE_SIZE / 2) / TILE_SIZE;
    }

    /**
     *
     * @param piece
     * @param newX
     * @param newY
     * @param direction
     * @param hasKill
     * @param enemy
     */
    public void canKill(Piece piece, int newX, int newY, int direction, boolean hasKill, boolean enemy) {

        if (hasToKill) {
            return;
        }

        int x = 0;
        int y = 0;

        if (direction == 1) {
            x = -1;
            y = -1;
        }
        if (direction == 2) {
            x = 1;
            y = -1;
        }
        if (direction == 3) {
            x = -1;
            y = 1;
        }
        if (direction == 4) {
            x = 1;
            y = 1;
        }

        if (newX + x >= 0 && newX + x <= 7 && newY + y >= 0 && newY + y <= 7) {
            System.out.println("x: " + (newX + x) + " y: " + (newY + y));
            System.out.println("hasPiece: " + board[newX + x][newY + y].hasPiece());
            System.out.println("haskill: " + hasKill);
            if (!(board[newX + x][newY + y].hasPiece()) && hasKill) {
                System.out.println("double kill");
                hasToKill = true;
                return;
            }
            if (board[newX + x][newY + y].hasPiece() && (board[newX + x][newY + y].getPiece().getType() != piece.getType())) {
                System.out.println("kill");
                canKill(piece, newX + x, newY + y, direction, true, false);

                if (newX - x >= 0 && newX - x <= 7 && newY - y >= 0 && newY - y <= 7) {
                    if (board[newX - x][newY - y].hasPiece() && (board[newX - x][newY - y].getPiece().getType() == piece.getType())) {
                        int oppositDirection = 0;
                        if (direction == 1) {
                            oppositDirection = 4;
                        }
                        if (direction == 2) {
                            oppositDirection = 3;
                        }
                        if (direction == 3) {
                            oppositDirection = 2;
                        }
                        if (direction == 4) {
                            oppositDirection = 1;
                        }
                        canKill(piece, newX, newY, oppositDirection, true, true);
                    }
                    if (!board[newX - x][newY - y].hasPiece()) {
                        hasToKill = true;
                    }
                }

            }
            if (enemy && board[newX + x][newY + y].hasPiece() && (board[newX + x][newY + y].getPiece().getType() == piece.getType())) {
                canKill(piece, newX + x, newY + y, direction, true, true);
            }
        }
    }

    /**
     *
     * @param type
     * @param x
     * @param y
     * @return
     */
    private Piece makePiece(PieceType type, int x, int y) {
        Piece piece = new Piece(type, x, y);

        piece.setOnMouseReleased(e -> {

            int newX = toBoard(piece.getLayoutX());
            int newY = toBoard(piece.getLayoutY());

            MoveResult result;

            if (newX < 0 || newY < 0 || newX >= WIDTH || newY >= HEIGHT) {
                result = new MoveResult(MoveType.NONE);
            } else {
                result = tryMove(piece, newX, newY);
            }

            int x0 = toBoard(piece.getOldX());
            int y0 = toBoard(piece.getOldY());

            switch (result.getType()) {
                case NONE:
                    piece.abortMove();
                    break;
                case NORMAL:
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);

                    // Dame Implementierung
                    if (!piece.isDraughts() && (newY == 7 && piece.getType() == PieceType.RED) || (newY == 0 && piece.getType() == PieceType.WHITE)) {
                        piece.setDraughts(true);
                    }

                    // hasToKill Implementierung
                    /* if ( ( board[newX + 1][newY + piece.getType().moveDir].hasPiece() && board[newX + 1][newY + piece.getType().moveDir].getPiece().getType() != piece.getType() ) ||
                            ( board[newX - 1][newY + piece.getType().moveDir].hasPiece() && board[newX - 1][newY + piece.getType().moveDir].getPiece().getType() != piece.getType() ) )
                            { hasToKill = true; }

                    if ( piece.isDraughts() &&
                            ( board[newX + 1][newY + piece.getType().moveDir * -1].hasPiece() && board[newX + 1][newY + piece.getType().moveDir * -1].getPiece().getType() != piece.getType() ) ||
                            ( board[newX - 1][newY + piece.getType().moveDir * -1].hasPiece() && board[newX - 1][newY + piece.getType().moveDir * -1].getPiece().getType() != piece.getType() ) )
                            { hasToKill = true; } */
                    if (piece.getType().moveDir == -1 || piece.isDraughts()) {
                        canKill(piece, newX, newY, 1, false, false);
                        canKill(piece, newX, newY, 2, false, false);
                    }
                    if (piece.getType().moveDir == 1 || piece.isDraughts()) {
                        canKill(piece, newX, newY, 3, false, false);
                        canKill(piece, newX, newY, 4, false, false);
                    }

                    break;
                case KILL:
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);
                    if (!piece.isDraughts() && (newY == 7 && piece.getType() == PieceType.RED) || (newY == 0 && piece.getType() == PieceType.WHITE)) {
                        piece.setDraughts(true);
                    }

                    // hier code für mehrere kills
                    for (Piece killPiece : result.getPiecesList()) {
                        board[toBoard(killPiece.getOldX())][toBoard(killPiece.getOldY())].setPiece(null);
                        pieceGroup.getChildren().remove(killPiece);
                    }
                    // Piece otherPiece = result.getPiece();

                    hasToKill = false;

                    if (piece.getType().moveDir == -1 || piece.isDraughts()) {
                        canKill(piece, newX, newY, 1, false, false);
                        canKill(piece, newX, newY, 2, false, false);
                    }
                    if (piece.getType().moveDir == 1 || piece.isDraughts()) {
                        canKill(piece, newX, newY, 3, false, false);
                        canKill(piece, newX, newY, 4, false, false);
                    }

                    break;
            }
        });

        return piece;
    }
}
