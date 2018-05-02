package model.board;

import controller.vo.TileVo;
import javafx.scene.Group;

public class Board {
	
	/** A sakktábla kockáinak a mérete. */
	public static final int TILE_SIZE = 70;

	/** A sakktábla kockáinak a száma az X tengelyen, szélesség. */
	public static final int WIDTH = 8;

	/** A sakktábla kockáinak a száma az Y tengelyen, magasság. */
	public static final int HEIGHT = 8;

	private static TileVo[][] board = null;
	private static TileVo[][] savedBoard = null; //new TileVo[WIDTH][HEIGHT];
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
