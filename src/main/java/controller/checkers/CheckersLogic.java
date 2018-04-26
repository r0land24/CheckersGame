package controller.checkers;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.Piece;
import view.PieceType;
import view.Tile;

public class CheckersLogic {

	/** A sakktábla kockáinak a mérete. */
	public static final int TILE_SIZE = 70;

	/** A sakktábla kockáinak a száma az X tengelyen, szélesség. */
	public static final int WIDTH = 8;

	/** A sakktábla kockáinak a száma az Y tengelyen, magasság. */
	public static final int HEIGHT = 8;

	private static CheckersLogic firstInstance = null;
	private static Tile[][] board = new Tile[WIDTH][HEIGHT];

	private Group tileGroup = new Group();
	private Group pieceGroup = new Group();

	private CheckersLogic() {
		// Singleton
	}

	/** Singleton példány */
	public static CheckersLogic getInstance() {
		if (firstInstance == null) {
			firstInstance = new CheckersLogic();
		}
		return firstInstance;
	}

	/** Visszaadja az aktuális sakktáblát */
	public static Tile[][] getBoard() {
		return board;
	}

	/** Megadhatjuk az új sakktáblát */
	public static void setBoard(Tile[][] board) {
		CheckersLogic.board = board;
	}

	/** Új játék esetén létrehozott sakktábla */
	public Parent createContent() {
		Pane root = new Pane();
		root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE); // a színpad területe
		root.getChildren().addAll(tileGroup, pieceGroup); //

		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				// minden világos tile páros számú
				Tile tile = new Tile((x + y) % 2 == 0, x, y); //

				board[x][y] = tile;

				tileGroup.getChildren().add(tile);

				Piece piece = null;

				if (y <= 2 && (x + y) % 2 != 0) {
					piece = makePiece(PieceType.DARK, x, y);
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
	 * boardAttr elemei alapján létrehozza az új táblát, frissül a static board
	 * tábla
	 */
	public Parent createContent(Tile[][] boardAttr) {
		Pane root = new Pane();
		root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE); // a színpad területe
		root.getChildren().addAll(tileGroup, pieceGroup); // ?

		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				Tile tile = new Tile((x + y) % 2 == 0, x, y);
				board[x][y] = tile;
				tileGroup.getChildren().add(tile);
				if (boardAttr[x][y].getTileCoordinateX() == x && boardAttr[x][y].getTileCoordinateY() == y
						&& boardAttr[x][y].hasPiece()) {
					Piece piece = null;

					if (boardAttr[x][y].getPiece().getType().equals(PieceType.DARK)) {
						piece = makePiece(PieceType.DARK, x, y);
					} else {
						piece = makePiece(PieceType.WHITE, x, y);
					}

					tile.setPiece(piece);
					pieceGroup.getChildren().add(piece);
				}
			}
		}
		return root;
	}

	/** Korong létrehozása */
	public static Piece makePiece(PieceType type, int x, int y) {
		Piece piece = new Piece(type, x, y);
		return piece;
	}

	/** ESC billentyű leütése játékban. Mentés ablak behozása. */
	public void addEscape(Stage stage) {
		stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
			if (KeyCode.ESCAPE == event.getCode()) {
				try {
					Stage stage2 = new Stage();
					Parent root = FXMLLoader.load(getClass().getResource("/fxml/SceneSaveGame.fxml"));
					Scene scene = new Scene(root);

					stage2.setTitle("Dámajáték");
					stage2.setScene(scene);
					stage2.setUserData(stage);//////
					stage2.show();
					// stage.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}
