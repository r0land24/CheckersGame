package model.vo.board;

/**
 * {@code PieceType} osztály a korongok típusát reprezentálja.
 * 
 * @author roland
 */
public enum PieceType {
	/** Világos korong. */
	WHITE(-1),

	/** Világos király korong, magyarul dáma a helyes megnevezése. */
	WHITE_KING(0),

	/** Sötét korong. */
	DARK(1),

	/** Sötét király korong, magyarul dáma a helyes megnevezése. */
	DARK_KING(0);

	/**
	 * Mozgási irány, -1 esetén lefele, +1 esetén fölfele mozoghat a korong, 0-ra
	 * külön feltétel vonatkozik, azaz fölfele és lefele is mozoghat.
	 * 
	 */
	public final int moveDir;

	private PieceType(int moveDirection) {
		this.moveDir = moveDirection;
	}
}
