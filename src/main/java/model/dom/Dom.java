package model.dom;

import java.util.List;

import model.vo.Piece;

public interface Dom {

	/**
	 * beolvassuk az xml-t.
	 */
	public List<Piece> domPieceReader();

	public boolean domAiReader();

	public void domWriter(List<Piece> list, boolean aisTurn);
}
