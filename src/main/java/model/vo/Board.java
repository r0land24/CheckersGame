package model.vo;

import javafx.scene.Group;

public class Board {
	
	/** A sakktábla kockáinak a mérete. */
	public static final int TILE_SIZE = 70;

	/** A sakktábla kockáinak a száma az X tengelyen, szélesség. */
	public static final int WIDTH = 8;

	/** A sakktábla kockáinak a száma az Y tengelyen, magasság. */
	public static final int HEIGHT = 8;

	private static boolean aisTurn = false;
	private static Tile[][] board = null;
	private static Tile[][] savedBoard = null; //new TileVo[WIDTH][HEIGHT];
	private static Group tileGroup = null;
	private static Group pieceGroup = null;

	/** Visszaadja az aktuális sakktáblát */
	public static Tile[][] getBoard() {
		return board;
	}

	/** Megadhatjuk az új sakktáblát */
	public static void setBoard(Tile[][] board) {
		Board.board = board;
	}
	
	public static Tile[][] getSavedBoard() {
		return savedBoard;
	}

	public static void setSavedBoard(Tile[][] board) {
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

	public static boolean isAIsTurn() {
		return aisTurn;
	}

	public static void setAIsTurn(boolean aisTurn) {
		Board.aisTurn = aisTurn;
	}

}
