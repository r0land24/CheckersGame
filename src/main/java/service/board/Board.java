package service.board;

import static model.properties.BoardProperties.HEIGHT;
import static model.properties.BoardProperties.WIDTH;

import javafx.scene.Group;
import service.vo.TileVo;

public class Board {

	private static TileVo[][] board = null;
	private static TileVo[][] savedBoard = new TileVo[WIDTH][HEIGHT];
	private static Group tileGroup = null;
	private static Group pieceGroup = null;

	/** Visszaadja az aktuális sakktáblát */
	public static TileVo[][] getBoard() {
		return board;
	}

	/** Megadhatjuk az új sakktáblát */
	public static void setBoard(TileVo[][] board) {
		Board.board = board;
	}
	
	public static TileVo[][] getSavedBoard() {
		return savedBoard;
	}

	public static void setSavedBoard(TileVo[][] board) {
		Board.savedBoard = board;
	}

	public static Group getTileGroup() {
		return tileGroup;
	}

	public static void setTileGroup(Group tileGroup) {
		Board.tileGroup = tileGroup;
	}

	public static Group getPieceGroup() {
		return pieceGroup;
	}

	public static void setPieceGroup(Group pieceGroup) {
		Board.pieceGroup = pieceGroup;
	}

}
