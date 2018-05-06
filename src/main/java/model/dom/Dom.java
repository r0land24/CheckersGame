package model.dom;

import java.util.List;

import model.vo.Piece;

public interface Dom {

	/**
	 * Az xml fájlban lévő korongok adatainak kiolvasása.
	 *
	 * 
	 */
	public List<Piece> domPieceReader();

	/**
	 * A program main függvénye.
	 *
	 * 
	 */
	public boolean domAiReader();

	public void domWriter(List<Piece> list, boolean aisTurn);
}
