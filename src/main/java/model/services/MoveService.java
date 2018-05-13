package model.services;

import javafx.scene.paint.Color;
import model.vo.ai.AiMoveResult;
import model.vo.board.Board;
import model.vo.board.MoveResult;
import model.vo.board.MoveType;
import model.vo.board.Piece;
import model.vo.board.PieceType;

/**
 * {@code MoveService} osztály a korongok mozgatásának logikáját tartalmazza.
 * 
 * @author roland
 */
public class MoveService {

	/**
	 * Mozgatja a korongot.
	 * 
	 * @param aiMoveResult az AI korongjának adatait tartalmazza
	 * @return a végrehajtott mozgás típusa
	 */
	public static MoveType switchMoveType(AiMoveResult aiMoveResult) {
		MoveResult result = aiMoveResult.getResult();
		Piece piece = aiMoveResult.getPiece();
		int newX = aiMoveResult.getNewX();
		int newY = aiMoveResult.getNewY();
		int x0 = aiMoveResult.getX0();
		int y0 = aiMoveResult.getY0();
		return switchMoveType(result, piece, newX, newY, x0, y0);
	}

	/**
	 * Mozgatja a korongot.
	 * 
	 * @param result a lépés lehetséges eredménye
	 * @param piece a korong
	 * @param newX a korong új x koordinátája
	 * @param newY a korong új y koordinátája
	 * @param x0 a korong előző x koordinátája
	 * @param y0 a korong előző y koordinátája
	 * @return a végrehajtott mozgás típusa
	 */
	public static MoveType switchMoveType(MoveResult result, Piece piece, int newX, int newY, int x0, int y0) {

		switch (result.getType()) {
		case NONE:
			piece.abortMove();
			return MoveType.NONE;
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

			if (Board.isAIsTurn()) {
				marker(x0, y0, newX, newY, Color.WHITE);
			}
			
			Board.setAIsTurn(!Board.isAIsTurn());
			return MoveType.NORMAL;
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
			Board.getBoard()[BoardService.getInstance().toBoard(otherPiece.getOldX())][BoardService.getInstance()
					.toBoard(otherPiece.getOldY())].setPiece(null);
			Board.getPieceGroup().getChildren().remove(otherPiece);

			if (Board.isAIsTurn()) {
				marker(x0, y0, newX, newY, Color.DEEPSKYBLUE);
			}
			
			Board.setAIsTurn(!Board.isAIsTurn());
			return MoveType.KILL;
		default:
			return null;
		}

	}

	private static void marker(int x0, int y0, int newX, int newY, Color color) {
		Board.getBoard()[Board.oldMarkerX][Board.oldMarkerY].setStroke(Color.BLACK);
		Board.getBoard()[Board.oldMarkerX][Board.oldMarkerY].setStrokeWidth(0);
		Board.getBoard()[Board.markerX][Board.markerY].setStroke(Color.BLACK);
		Board.getBoard()[Board.markerX][Board.markerY].setStrokeWidth(0);
		Board.oldMarkerX = x0;
		Board.oldMarkerY = y0;
		Board.getBoard()[x0][y0].setStroke(color);
		Board.getBoard()[x0][y0].setStrokeWidth(2);

		Board.markerX = newX;
		Board.markerY = newY;
		Board.getBoard()[newX][newY].setStroke(color);
		Board.getBoard()[newX][newY].setStrokeWidth(2);
	}

}
