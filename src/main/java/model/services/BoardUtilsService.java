package model.services;

import static model.vo.Board.HEIGHT;
import static model.vo.Board.WIDTH;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.vo.Board;
import model.vo.MoveResult;
import model.vo.MoveType;
import model.vo.Piece;
import model.vo.PieceType;
import model.vo.Tile;

/**
 * Minden olyan service ami.
 */
public class BoardUtilsService {

	private static BoardUtilsService firstInstance = null;

	private BoardUtilsService() {
	}

	/**
	 * Példányosítja a {@code BoardUtilsService} osztályt, singleton.
	 * 
	 * @return az osztály egyetlen példánya
	 */
	public static BoardUtilsService getInstance() {
		if (firstInstance == null) {
			firstInstance = new BoardUtilsService();
		}
		return firstInstance;
	}

	/**
	 * Egy mentett táblát készít a korongok egy listája alapján.
	 * 
	 * @param list
	 *            a korongok listája
	 * 
	 */
	public void saveBoard(List<Piece> list) {
		Board.setSavedBoard(new Tile[WIDTH][HEIGHT]);
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				Tile tile = new Tile((x + y) % 2 == 0, x, y);
				Board.getSavedBoard()[x][y] = tile;

				for (Piece piecesVo : list) {
					if (piecesVo.getCoordX() == x && piecesVo.getCoordY() == y) {
						Board.getSavedBoard()[x][y]
								.setPiece(BoardService.getInstance().makePiece(piecesVo.getType(), x, y));
					}
				}
			}
		}
	}

	/**
	 * A táblán lévő korongokat listába szedő metódus.
	 * 
	 * @return lista a korongokról
	 */
	public List<Piece> pieceList() {

		List<Piece> list = new ArrayList<>();
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				if (Board.getBoard()[x][y].hasPiece()) {
					list.add(Board.getBoard()[x][y].getPiece());
				}
			}
		}
		return list;
	}

	/**
	 * Leellenőrzi, hogy véget ért-e a játék.
	 * 
	 * @param board tábla amit megvizsgál a metódus
	 * @return játék vége
	 */
	public boolean checkEndGame(Tile[][] board, boolean cantMove) {
		int dark = 0;
		int white = 0;
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				if (board[x][y].hasPiece() && (board[x][y].getPiece().getType().equals(PieceType.DARK)
						|| board[x][y].getPiece().getType().equals(PieceType.DARK_KING))) {
					dark++;
				} else if (board[x][y].hasPiece() && (board[x][y].getPiece().getType().equals(PieceType.WHITE)
						|| board[x][y].getPiece().getType().equals(PieceType.WHITE_KING))) {
					white++;
				}
			}
		}
		if (cantMove || dark == 0 || white == 0) {
			if (dark == 0 || cantMove)
				Board.setWinner("white"); // 0 sötét van ezért a világos nyer
			else if (white == 0)
				Board.setWinner("dark"); // 0 világos van ezért a sötét nyer

			Stage stage = (Stage) board[0][0].getScene().getWindow();
			Stage stage2 = new Stage();
			Parent root = new Parent() {
			};
			try {
				root = FXMLLoader.load(getClass().getResource("/fxml/SceneEnd.fxml"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Scene scene = new Scene(root);
			scene.getStylesheets().add("/styles/Styles.css");

			stage2.setTitle("Dámajáték");
			stage2.setScene(scene);

			stage2.show();
			stage.close();
			return true;
		}
		return false;

	}

	/**
	 * A korongok mozgását kiszámoló logika.
	 * 
	 * @param piece a korong
	 * @param newX új x paraméter
	 * @param newY új x paraméter
	 * @param x0 
	 * @param y0
	 * @return a mozgatás eredménye
	 */
	public MoveResult moveResultLogic(Piece piece, int newX, int newY, int x0, int y0) {
		if (Math.abs(newX - x0) == 1 && newY - y0 == piece.getType().moveDir) {

			return new MoveResult(MoveType.NORMAL);

		} else if (Math.abs(newX - x0) == 2 && newY - y0 == piece.getType().moveDir * 2) {

			int x1 = x0 + (newX - x0) / 2;
			int y1 = y0 + (newY - y0) / 2;

			if (Board.getBoard()[x1][y1].hasPiece()
					&& ((Board.getBoard()[x1][y1].getPiece().getType().equals(PieceType.DARK))
							|| (Board.getBoard()[x1][y1].getPiece().getType().equals(PieceType.DARK_KING)))
					&& (PieceType.DARK != piece.getType() && PieceType.DARK_KING != piece.getType())) {
				return new MoveResult(MoveType.KILL, (Board.getBoard())[x1][y1].getPiece());
			} else if (Board.getBoard()[x1][y1].hasPiece()
					&& ((Board.getBoard()[x1][y1].getPiece().getType().equals(PieceType.WHITE))
							|| (Board.getBoard()[x1][y1].getPiece().getType().equals(PieceType.WHITE_KING)))
					&& (PieceType.WHITE != piece.getType() && PieceType.WHITE_KING != piece.getType())) {
				return new MoveResult(MoveType.KILL, (Board.getBoard())[x1][y1].getPiece());
			}

		} else if (piece.getType().equals(PieceType.DARK_KING) || piece.getType().equals(PieceType.WHITE_KING)) {

			if (Math.abs(newX - x0) == 1 && (newY - y0 == -1 || newY - y0 == 1)) {

				return new MoveResult(MoveType.NORMAL);

			} else if (Math.abs(newX - x0) == 2 && (newY - y0 == -1 * 2 || newY - y0 == 1 * 2)) {

				int x1 = x0 + (newX - x0) / 2;
				int y1 = y0 + (newY - y0) / 2;

				if (Board.getBoard()[x1][y1].hasPiece()
						&& ((Board.getBoard()[x1][y1].getPiece().getType().equals(PieceType.DARK))
								|| (Board.getBoard()[x1][y1].getPiece().getType().equals(PieceType.DARK_KING)))
						&& (PieceType.DARK != piece.getType() && PieceType.DARK_KING != piece.getType())) {

					return new MoveResult(MoveType.KILL, (Board.getBoard())[x1][y1].getPiece());

				} else if (Board.getBoard()[x1][y1].hasPiece()
						&& ((Board.getBoard()[x1][y1].getPiece().getType().equals(PieceType.WHITE))
								|| (Board.getBoard()[x1][y1].getPiece().getType().equals(PieceType.WHITE_KING)))
						&& (PieceType.WHITE != piece.getType() && PieceType.WHITE_KING != piece.getType())) {

					return new MoveResult(MoveType.KILL, (Board.getBoard())[x1][y1].getPiece());

				}
			}
		}

		return new MoveResult(MoveType.NONE);
	}

}
