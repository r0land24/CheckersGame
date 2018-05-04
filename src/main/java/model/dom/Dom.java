package model.dom;

import java.util.List;

import model.dto.PieceDto;

public interface Dom {

	/**
     * beolvassuk az xml-t.
     */
	public List<PieceDto> domPieceReader();

	public boolean domAiReader();

	public void domWriter(List<PieceDto> list, boolean turnAI);
}
