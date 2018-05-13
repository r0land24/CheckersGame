package model.vo.board;

/**
 * {@code MoveType} osztály a mozgás típusait reprezentálja.
 * 
 * @author roland
 */
public enum MoveType {
	/** Nincs lépés. */
	NONE,

	/** Normál lépés. */
	NORMAL,

	/** Korongot leütő lépés. */
	KILL
}
