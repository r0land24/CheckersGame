package model.services;

import static model.vo.Board.HEIGHT;
import static model.vo.Board.TILE_SIZE;
import static model.vo.Board.WIDTH;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import model.vo.Board;
import model.vo.MoveResult;
import model.vo.MoveType;
import model.vo.Piece;
import model.vo.PieceType;
import model.vo.Tile;

/**
 * {@code BoardService} osztály a tábla logikáját (serviceket) tartalmazza.
 * 
 * @author roland
 */
public class BoardService {

	private static Logger logger = LoggerFactory.getLogger(BoardService.class);

	private static BoardService firstInstance = null;

	private BoardService() {
		// Singleton, emiatt private
	}

	/**
	 * Példányosítja a {@code BoardService} osztályt, singleton.
	 * 
	 * @return az osztály egyetlen példánya
	 */
	public static BoardService getInstance() {
		if (firstInstance == null) {
			firstInstance = new BoardService();
		}
		return firstInstance;
	}

	/**
	 * Létrehoz egy új táblát.
	 * 
	 * @return új tábla
	 */
	public Parent createContent() {
		Board.setBoard(new Tile[WIDTH][HEIGHT]);
		Board.setTileGroup(new Group());
		Board.setPieceGroup(new Group());
		Pane root = new Pane();
		root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE); // a színpad területe
		root.getChildren().addAll(Board.getTileGroup(), Board.getPieceGroup()); //
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				// minden világos tile páros számú
				Tile tile = new Tile((x + y) % 2 == 0, x, y); //

				Board.getBoard()[x][y] = tile;

				Board.getTileGroup().getChildren().add(tile);

				Piece piece = null;

				if (y <= 2 && (x + y) % 2 != 0) {
					piece = makePiece(PieceType.DARK, x, y);
				}

				if (y >= 5 && (x + y) % 2 != 0) {
					piece = makePiece(PieceType.WHITE, x, y);
				}

				if (piece != null) {
					tile.setPiece(piece);
					Board.getPieceGroup().getChildren().add(piece);
				}
			}
		}
		return root;
	}

	/**
	 * Létrehoz egy új táblát az elmentett táblából.
	 * 
	 * @param savedBoard elmentett tábla
	 * @return Stage új tábla
	 */
	public Parent createContent(Tile[][] savedBoard) {
		Board.setBoard(new Tile[WIDTH][HEIGHT]);
		Board.setTileGroup(new Group());
		Board.setPieceGroup(new Group());
		Pane root = new Pane();
		root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE); // a színpad területe
		root.getChildren().addAll(Board.getTileGroup(), Board.getPieceGroup()); //

		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				Tile tile = new Tile((x + y) % 2 == 0, x, y);
				Board.getBoard()[x][y] = tile;
				Board.getTileGroup().getChildren().add(tile);
				if (savedBoard[x][y].hasPiece()) {
					Piece piece = null;

					if (savedBoard[x][y].getPiece().getType().equals(PieceType.DARK)) {
						piece = makePiece(PieceType.DARK, x, y);
					} else if (savedBoard[x][y].getPiece().getType().equals(PieceType.WHITE)) {
						piece = makePiece(PieceType.WHITE, x, y);
					} else if (savedBoard[x][y].getPiece().getType().equals(PieceType.WHITE_KING)) {
						piece = makePiece(PieceType.WHITE_KING, x, y);
						piece.setText("K");
					} else if (savedBoard[x][y].getPiece().getType().equals(PieceType.DARK_KING)) {
						piece = makePiece(PieceType.DARK_KING, x, y);
						piece.setText("K");
					}

					tile.setPiece(piece);
					Board.getPieceGroup().getChildren().add(piece);
				}
			}
		}
		return root;
	}

	/**
	 * Létrehozza a megadott típusú korongot a megadott koordinátákon.
	 *
	 * @param type a korong típusa
	 * @param x az x koordináta
	 * @param y az y koordináta
	 * 
	 * @return korong
	 */
	public Piece makePiece(PieceType type, int x, int y) {
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
				Board.getBoard()[x0][y0].setPiece(null);
				if (newY == 0 || newY == 7) {
					piece.setText("K");
					if (piece.getType().equals(PieceType.DARK)) {
						piece.setType(PieceType.DARK_KING);
					} else if (piece.getType().equals(PieceType.WHITE)) {
						piece.setType(PieceType.WHITE_KING);
					}
				}
				Board.getBoard()[newX][newY].setPiece(piece);

				Board.setAIsTurn(!Board.isAIsTurn());
				break;
			case KILL:
				piece.move(newX, newY);
				Board.getBoard()[x0][y0].setPiece(null);
				if (newY == 0 || newY == 7) {
					piece.setText("K");
					if (piece.getType().equals(PieceType.DARK)) {
						piece.setType(PieceType.DARK_KING);
					} else if (piece.getType().equals(PieceType.WHITE)) {
						piece.setType(PieceType.WHITE_KING);
					}
				}
				Board.getBoard()[newX][newY].setPiece(piece);
				Piece otherPiece = result.getPiece();
				Board.getBoard()[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
				Board.getPieceGroup().getChildren().remove(otherPiece);

				Board.setAIsTurn(!Board.isAIsTurn());
				break;
			}

		});
		return piece;
	}

	/**
	 * Visszaadja, hogy milyen típusú mozgatásra képes az adott korong.
	 * 
	 * @param piece a korong
	 * @param newX az új x paraméter
	 * @param newY az új y paraméter
	 * 
	 * @return mozgás eredménye
	 */
	public MoveResult tryMove(Piece piece, int newX, int newY) {
		if (Board.getBoard()[newX][newY].hasPiece() || (newX + newY) % 2 == 0) {
			return new MoveResult(MoveType.NONE);
		}

		int x0 = toBoard(piece.getOldX());
		int y0 = toBoard(piece.getOldY());

		return BoardUtilService.getInstance().moveResultLogic(piece, newX, newY, x0, y0);

	}

	private int toBoard(double pixel) {
		return (int) (pixel + TILE_SIZE / 2) / TILE_SIZE;
	}

	/**
	 * Az AI egy lépését szimulálja.
	 */
	public void aImove() {

		Piece currentPiece = null;
		List<Piece> listToShuffleWithPieces = new ArrayList<>();
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				if (Board.getBoard()[j][i].hasPiece()
						&& (Board.getBoard()[j][i].getPiece().getType().equals(PieceType.DARK)
								|| Board.getBoard()[j][i].getPiece().getType().equals(PieceType.DARK_KING))) {
					listToShuffleWithPieces.add(Board.getBoard()[j][i].getPiece());
				}
			}
		}
		Collections.shuffle(listToShuffleWithPieces);

		int movedPiece = 0;
		for (int a = 0; a < listToShuffleWithPieces.size(); a++) {

			currentPiece = listToShuffleWithPieces.get(a);
			Piece piece = Board.getBoard()[currentPiece.getCoordX()][currentPiece.getCoordY()].getPiece();
			for (int y = piece.getCoordY() - 3; y < piece.getCoordY() + 3; y++) {
				for (int x = piece.getCoordX() - 3; x < piece.getCoordX() + 3; x++) {
					int newX = toBoard(x * TILE_SIZE);
					int newY = toBoard(y * TILE_SIZE);
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
						Board.getBoard()[x0][y0].setPiece(null);
						if (newY == 0 || newY == 7) {
							piece.setText("K");
							if (piece.getType().equals(PieceType.DARK)) {
								piece.setType(PieceType.DARK_KING);
							} else if (piece.getType().equals(PieceType.WHITE)) {
								piece.setType(PieceType.WHITE_KING);
							}
						}
						Board.getBoard()[newX][newY].setPiece(piece);

						Board.setAIsTurn(!Board.isAIsTurn());
						movedPiece++;
						break;
					case KILL:
						piece.move(newX, newY);
						Board.getBoard()[x0][y0].setPiece(null);
						if (newY == 0 || newY == 7) {
							piece.setText("K");
							if (piece.getType().equals(PieceType.DARK)) {
								piece.setType(PieceType.DARK_KING);
							} else if (piece.getType().equals(PieceType.WHITE)) {
								piece.setType(PieceType.WHITE_KING);
							}
						}
						Board.getBoard()[newX][newY].setPiece(piece);
						Piece otherPiece = result.getPiece();
						Board.getBoard()[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
						Board.getPieceGroup().getChildren().remove(otherPiece);

						Board.setAIsTurn(!Board.isAIsTurn());
						movedPiece++;
						break;
					}
					if (movedPiece > 0)
						return;
				}
				// if (movedPiece > 0)
				// break;
			}
			if (a == listToShuffleWithPieces.size() - 1 && movedPiece == 0) {
				BoardUtilService.getInstance().checkEndGame(Board.getBoard(), true);
			}
		}
	}

}
