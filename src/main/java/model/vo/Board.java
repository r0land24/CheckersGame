package model.vo;

import javafx.scene.Group;

/**
 * A {@code Board} osztály a táblát reprezentálja.
 * 
 * @author roland
 */
public class Board {

	/** A tábla mezőinek a mérete. */
	public static final int TILE_SIZE = 70;

	/** A tábla mezőinek a száma az X tengelyen, szélesség. */
	public static final int WIDTH = 8;

	/** A tábla mezőinek a száma az Y tengelyen, magasság. */
	public static final int HEIGHT = 8;

	private static boolean aisTurn = false;
	private static String winner = null;
	private static Tile[][] board = null;
	private static Tile[][] savedBoard = null; // new TileVo[WIDTH][HEIGHT];
	private static Group tileGroup = null;
	private static Group pieceGroup = null;

	/**
	 * Visszaadja az aktuális táblát.
	 * 
	 * @return a tábla
	 */
	public static Tile[][] getBoard() {
		return board;
	}

	/**
	 * Beállíta a táblát.
	 * 
	 * @param board a tábla
	 */
	public static void setBoard(Tile[][] board) {
		Board.board = board;
	}

	/**
	 * Visszaadja a mentett táblát.
	 * 
	 * @return a mentett tábla
	 */
	public static Tile[][] getSavedBoard() {
		return savedBoard;
	}

	/**
	 * Beállítja a mentett táblát.
	 * 
	 * @param board a tábla
	 */
	public static void setSavedBoard(Tile[][] board) {
		Board.savedBoard = board;
	}

	/**
	 * Visszaadja az tábla mezőinek a csoportját.
	 * 
	 * @return a mezők csoportja
	 */
	public static Group getTileGroup() {
		return tileGroup;
	}

	/**
	 * Beállítja a mezők csoportját.
	 * 
	 * @param tileGroup a mezők csoportja
	 */
	public static void setTileGroup(Group tileGroup) {
		Board.tileGroup = tileGroup;
	}

	/**
	 * Visszaadja az tábla korongjainak a csoportját.
	 * 
	 * @return a korongok csoportja
	 */
	public static Group getPieceGroup() {
		return pieceGroup;
	}

	/**
	 * Beállítja a korongok csoportját.
	 * 
	 * @param pieceGroup a korongok csoportja
	 */
	public static void setPieceGroup(Group pieceGroup) {
		Board.pieceGroup = pieceGroup;
	}

	/**
	 * Visszaadja, hogy az AI köre következik vagy sem.
	 * 
	 * @return <code>true</code> ha az AI köre következik; 
	 * 		   <code>false</code> egyébként
	 */
	public static boolean isAIsTurn() {
		return aisTurn;
	}

	/**
	 * Beállítja, hogy az AI köre következik vagy sem.
	 * 
	 * @param aisTurn <code>true</code> ha az AI köre következik; 
	 * 		   		  <code>false</code> egyébként
	 */
	public static void setAIsTurn(boolean aisTurn) {
		Board.aisTurn = aisTurn;
	}

	/**
	 * Visszaadja a játék győztesét.
	 * 
	 * @return a győztes
	 */
	public static String getWinner() {
		return winner;
	}

	/**
	 * Beállítja a játék győztesét.
	 * 
	 * @param winner a győztes
	 */
	public static void setWinner(String winner) {
		Board.winner = winner;
	}

}
