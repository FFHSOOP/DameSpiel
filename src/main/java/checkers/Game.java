package checkers;

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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game {

    public static final int TILE_SIZE = 100; //Groesse eines einzelnen Feldes
    public static final int WIDTH = 8; //Anzahl Felder Breite
    public static final int HEIGHT = 8; //Anzahl Felder Hoehe

    private checkers.Tile[][] board; // Spielbrett

    private Group tileGroup; //Felder
    private Group pieceGroup; //Spielsteine
    private GameInfo gameInfo;

    private int gameMode; //1=Multi, 2=Single

    private boolean hasToKillLight;
    private boolean hasToKillDark;
    
    public Game() {
	tileGroup = new Group();
	pieceGroup = new Group();
	gameInfo = new GameInfo();
	board = new Tile[WIDTH][HEIGHT]; //Spielbrett mit allen Tiles
	
    }
    
    public Tile[][] getBoard() {
	return board;
    }
    
    public Group getTileGroup() {
	return tileGroup;
    }
    
    public Group getPieceGroup() {
	return pieceGroup;
    }
    
    public GameInfo getGameInfo() {
	return gameInfo;
    }
    
    public boolean getHasToKillLight() {
	return hasToKillLight;
    }
    
    public boolean getHasToKillDark() {
	return hasToKillDark;
    }
    
    public int getGameMode() {
	return gameMode;
    }
    
    public void setGameMode(int mode) {
	gameMode = mode;
    }
    
    

    //Singleplayer
    private Piece nextPiece;
    private int nextX;
    private int nextY;

    /**
     * Hauptmenu
     *
     * @param stage
     * @return
     */
    public Parent createMenu(Stage stage) {
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
            single.setOnAction((ActionEvent event) -> {
                singleplayer(stage);
            });
            multi.setOnAction((ActionEvent event) -> {
                multiplayer(stage);
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
        gameMode = 1;
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
    public void singleplayer(Stage stage) {
        gameMode = 2;
        Scene scene = new Scene(createContent());
        stage.setTitle("Checkers Draughts - Singleplayer");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initialer Aufbau des Spielfeldes
     *
     * @return
     */
    public Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE + 200); //Groesse des Spielfeldes inkl. Infobereich
        root.getChildren().addAll(tileGroup, pieceGroup, gameInfo.getGameInfo());

        //Initialer Aufbau der Spielsteine und der Felder. wenn (x+y) %2 == 0 true ist, haben wir ein weisses Feld
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = new Tile((x + y) % 2 == 0, x, y);
                board[x][y] = tile;

                tileGroup.getChildren().add(tile);

                Piece piece = null;

                if (y <= 2 && (x + y) % 2 != 0) {
                    piece = makePiece(PieceType.BLACK, x, y);
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
     * Erstellt die initialen Spielsteine
     *
     * @param type
     * @param x
     * @param y
     * @return
     */
    private Piece makePiece(PieceType type, int x, int y) {
        Piece piece = new Piece(type, x, y);

        //Logik nach dem loslassen
        piece.setOnMouseReleased(e -> {

            int newX = toBoard(piece.getLayoutX());
            int newY = toBoard(piece.getLayoutY());

            performMove(piece, newX, newY);
        });

        return piece;
    }

    /**
     * Fuehrt den Move aus
     *
     * @param piece Spielstein
     * @param newX neue X Position
     * @param newY neue Y Position
     */
    public void performMove(Piece piece, int newX, int newY) {
        MoveResult result;

        //Check ob Spieler am Zug ist
        if (gameInfo.getTurn() == piece.getType()) {
            //Check ob neue Position stimmen kann
            if (newX < 0 || newY < 0 || newX >= WIDTH || newY >= HEIGHT) {
                result = new MoveResult(MoveType.NONE);
            } else {
                result = tryMove(piece, newX, newY);
            }
        } else {
            System.out.println("Sorry not your turn");
            result = new MoveResult(MoveType.NONE);
        }

        int x0 = toBoard(piece.getOldX());
        int y0 = toBoard(piece.getOldY());

        switch (result.getType()) {
            case NONE:
                piece.abortMove();
                System.out.println("NONE");
                break;
            case NORMAL:
                piece.move(newX, newY);
                board[x0][y0].setPiece(null);
                board[newX][newY].setPiece(piece);

                // Dame Implementierung
                if (!piece.isDraughts() && (newY == 7 && piece.getType() == PieceType.BLACK) || (newY == 0 && piece.getType() == PieceType.WHITE)) {
                    piece.setDraughts(true);
                }

                // check ob gegner killen können für nächsten turn
                canKill(piece);

                gameInfo.countUpRound();
                gameInfo.changeTurn();
                gameInfo.updateGameInfo();
                System.out.println(gameInfo.getRound());

                if (gameMode == 2) {

                }

                System.out.println("NORMAL");

                if (gameMode == 2) {
                    singlePlayer();
                }
                break;
            case KILL:

                piece.move(newX, newY);
                board[x0][y0].setPiece(null);
                board[newX][newY].setPiece(piece);

                if (!piece.isDraughts() && (newY == 7 && piece.getType() == PieceType.BLACK) || (newY == 0 && piece.getType() == PieceType.WHITE)) {
                    piece.setDraughts(true);
                }

                // alte implementation für mehrere kills
                /* for (Piece killPiece : result.getPiecesList()) {
                        board[toBoard(killPiece.getOldX())][toBoard(killPiece.getOldY())].setPiece(null);
                        pieceGroup.getChildren().remove(killPiece);
                    }*/
                Piece otherPiece = result.getPiece();
                board[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
                pieceGroup.getChildren().remove(otherPiece);

                // check ob gegner killen können für nächsten turn
                canKill(piece);

                // check ob gleicher stein nochmals killen kann
                if (piece.getType() == PieceType.BLACK || piece.isDraughts()) {
                    checkKill(piece, 3);
                    checkKill(piece, 4);
                }
                if (piece.getType() == PieceType.WHITE || piece.isDraughts()) {
                    checkKill(piece, 1);
                    checkKill(piece, 2);
                }

                //Verlorene Spielsteine hochzaehlen
                if (piece.getType() == PieceType.WHITE) {
                    gameInfo.countUpLostDark();
                } else if (piece.getType() == PieceType.BLACK) {
                    gameInfo.countUpLostLight();
                }
                //Zug wechseln und Runde hochzaehlen
                if ((gameInfo.getTurn() == PieceType.WHITE && !hasToKillLight) || (gameInfo.getTurn() == PieceType.BLACK && !hasToKillDark)) {
                    gameInfo.countUpRound();
                    gameInfo.changeTurn();
                }
                gameInfo.updateGameInfo();
                System.out.println(gameInfo.getRound());
                System.out.println("KILL");

                if (gameMode == 2) {
                    singlePlayer();
                }
                break;
        }
    }

    /**
     * Gibt zurueck was fuer ein MoveType durchgefuehrt wird
     *
     * @param piece Spielstein
     * @param newX neue X Position
     * @param newY neue Y Position
     * @return MoveType (NONE, NORMAL, KILL)
     */
    private MoveResult tryMove(Piece piece, int newX, int newY) {

        int x0 = toBoard(piece.getOldX());
        int y0 = toBoard(piece.getOldY());

        if (board[newX][newY].hasPiece() || (newX + newY) % 2 == 0 || newY - y0 == 0) {
            return new MoveResult(MoveType.NONE);
        }

        // alte implementation für mehrere kills
        // ArrayList<Piece> piecesList = new ArrayList<>();
        // Wenn nur 1 Schritt und (Richtung stimmt oder Dame ist)
        if (Math.abs(newX - x0) == 1 && (newY - y0 == piece.getType().moveDir || piece.isDraughts())) {

            if (hasToKillLight && (PieceType.WHITE == piece.getType()) || hasToKillDark && (PieceType.BLACK == piece.getType())) {
                return new MoveResult(MoveType.NONE);
            }

            return new MoveResult(MoveType.NORMAL);
        } else if (Math.abs(newX - x0) == 2 && newY - y0 == piece.getType().moveDir * 2 || piece.isDraughts()) {

            int x1 = x0 + (newX - x0) / 2;
            int y1 = y0 + (newY - y0) / 2;

            if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != piece.getType()) {
                return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
            }
        }

        return new MoveResult(MoveType.NONE);
    }

    /**
     * Findet heraus welchem Feld die Position entspricht
     *
     * @param pixel Pixelposition auf dem Spielbrett
     * @return Spielbrettposition
     */
    public int toBoard(double pixel) {
        return (int) (pixel + TILE_SIZE / 2) / TILE_SIZE;
    }

    /**
     * Prueft ob ein Spielstein einen anderen schlagen kann
     *
     * @param piece
     */
    public void canKill(Piece piece) {

        hasToKillLight = false;
        hasToKillDark = false;

        PieceType currentPlayer = gameInfo.getTurn();

        System.out.println("Size: " + pieceGroup.getChildren().size());

        for (int i = 0; i < pieceGroup.getChildren().size(); i++) {

            //if (hasToKillLight && hasToKillDark) break;
            // änderungen bevor mehrfach nacheinander kill
            if (hasToKillLight || hasToKillDark) {
                break;
            }

            Node node = pieceGroup.getChildren().get(i);
            Piece activePiece = (Piece) node;

            // änderungen bevor mehrfach nacheinander kill
            if (activePiece.getType() != currentPlayer) {

                if (activePiece.getType() == PieceType.BLACK || activePiece.isDraughts()) {
                    checkKill(activePiece, 3);
                    checkKill(activePiece, 4);
                }

                if (activePiece.getType() == PieceType.WHITE || activePiece.isDraughts()) {
                    checkKill(activePiece, 1);
                    checkKill(activePiece, 2);
                }

            }
        }
    }

    /**
     * Prueft ob ein Spielstein einen anderen schlagen kann
     *
     * @param activePiece
     * @param direction
     */
    public void checkKill(Piece activePiece, int direction) {

        int oldX = toBoard(activePiece.getOldX());
        int oldY = toBoard(activePiece.getOldY());

        // Richtung in x/y setzen
        int x;
        int y;
        switch (direction) {
            case 1:
                x = -1;
                y = -1;
                break;
            case 2:
                x = 1;
                y = -1;
                break;
            case 3:
                x = -1;
                y = 1;
                break;
            case 4:
                x = 1;
                y = 1;
                break;
            default:
                x = 0;
                y = 0;
                break;
        }

        // Zu überprüfendes kill feld auf gültigkeit auf spielfeld prüfen
        if (oldX + x >= 0 && oldX + x <= 7 && oldY + y >= 0 && oldY + y <= 7) {
            //System.out.println("oldX: " + oldX + " oldY: " + oldY);
            //System.out.println("check position x: " + (oldX + x) + " y: " + (oldY + y));
            //System.out.println("hasPiece: " + board[oldX + x][oldY + y].hasPiece());

            // falls zu prüfendes feld durch feind besetzt ist, funktion mit haskill nochmals aufrufen und nächstes feld prüfen
            // (oldY - (oldY + y) != activePiece.getType().moveDir || board[oldX + x][oldY + y].getPiece().isDraughts()
            if (board[oldX + x][oldY + y].hasPiece() && (board[oldX + x][oldY + y].getPiece().getType() != activePiece.getType())) {
                System.out.println("check if hasToKill");

                if (oldX + (x * 2) >= 0 && oldX + (x * 2) <= 7 && oldY + (y * 2) >= 0 && oldY + (y * 2) <= 7) {

                    if (!board[oldX + (x * 2)][oldY + (y * 2)].hasPiece()) {
                        if (PieceType.WHITE == activePiece.getType()) {
                            System.out.println("hasToKillLight");
                            hasToKillLight = true;
                            if (gameMode == 2) {
                                //Falls Singleplayer
                                System.out.println("autokill");
                                nextPiece = activePiece;
                                nextX = oldX + (x * 2);
                                nextY = oldY + (y * 2);
                            }
                        } else {
                            System.out.println("hasToKillDark");
                            hasToKillDark = true;
                        }
                    }

                }

                //Handling falls outside of board
                if (oldX - x >= 0 && oldX - x <= 7 && oldY - y >= 0 && oldY - y <= 7) {
                    if (!board[oldX - x][oldY - y].hasPiece()) {

                        if (PieceType.WHITE == activePiece.getType()) {
                            System.out.println("hasToKillDark");
                            hasToKillDark = true;
                            //Falls Singleplayer
                            if (gameMode == 2) {
                                System.out.println("autokill");
                                nextPiece = activePiece;
                                nextX = oldX + (x * 2);
                                nextY = oldY + (y * 2);
                            }
                        } else {
                            System.out.println("hasToKillLight");
                            hasToKillLight = true;
                        }
                    }
                }

            }

        }
    }

    /**
     * Singleplayer Modus
     */
    public void singlePlayer() {
        //Autokill falls hasToKillLight
        if (hasToKillLight) {
            performMove(nextPiece, nextX, nextY);
        } else {
            for (int i = 0; i < WIDTH; i++) {
                for (int q = 0; q < HEIGHT; q++) {
                    if (board[i][q].hasPiece()) {
                        if (board[i][q].getPiece().getType() == PieceType.WHITE) {
                            Piece piece = board[i][q].getPiece();
                            if ((toBoard(piece.getOldX()) - 1)>=0 && (toBoard(piece.getOldY()) - 1)>=0 && tryMove(piece, toBoard(piece.getOldX()) - 1, toBoard(piece.getOldY()) - 1).getType() == MoveType.NORMAL) {
                                performMove(piece, toBoard(piece.getOldX()) - 1, toBoard(piece.getOldY()) - 1);
                                break;
                            } else if ((toBoard(piece.getOldX()) + 1)<HEIGHT && (toBoard(piece.getOldY()) - 1)>=0 && tryMove(piece, toBoard(piece.getOldX()) + 1, toBoard(piece.getOldY()) - 1).getType() == MoveType.NORMAL) {
                                performMove(piece, toBoard(piece.getOldX()) + 1, toBoard(piece.getOldY()) - 1);
                                break;
                            } else if (piece.isDraughts()) {
                                if ((toBoard(piece.getOldX()) + 1)<HEIGHT && (toBoard(piece.getOldY()) + 1)<WIDTH && tryMove(piece, toBoard(piece.getOldX()) + 1, toBoard(piece.getOldY()) + 1).getType() == MoveType.NORMAL) {
                                    performMove(piece, toBoard(piece.getOldX()) + 1, toBoard(piece.getOldY()) + 1);
                                    break;
                                } else if ((toBoard(piece.getOldX()) - 1)>=0 && (toBoard(piece.getOldY()) + 1)<WIDTH && tryMove(piece, toBoard(piece.getOldX()) - 1, toBoard(piece.getOldY()) + 1).getType() == MoveType.NORMAL) {
                                    performMove(piece, toBoard(piece.getOldX()) - 1, toBoard(piece.getOldY()) + 1);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
