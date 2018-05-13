package model.vo.board;

/**
 * A {@code MoveResult} osztály a mozgás eredményét reprezentálja.
 * 
 * @author roland
 */
public class MoveResult {

	private MoveType type;

	private Piece piece;

	/**
	 * Az {@code MoveResult} osztály konstruktora, inicializálja a {@code type}
	 * adattagot.
	 * 
	 * @param type a mozgás típusa
	 */
	public MoveResult(MoveType type) {
		this(type, null);
	}

	/**
	 * Az {@code MoveResult} osztály konstruktora, inicializálja a {@code type} és
	 * {@code piece} adattagokat.
	 * 
	 * @param type a mozág típusa
	 * @param piece a korong
	 */
	public MoveResult(MoveType type, Piece piece) {
		this.type = type;
		this.piece = piece;
	}

	/**
	 * Visszaadja a mozgás típusát.
	 * 
	 * @return a mozgás típusa
	 */
	public MoveType getType() {
		return type;
	}

	/**
	 * Visszaadja a korongot.
	 * 
	 * @return a korong
	 */
	public Piece getPiece() {
		return piece;
	}

}