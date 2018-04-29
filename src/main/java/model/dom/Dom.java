package model.dom;

import java.util.List;

import model.dto.PieceDto;

public interface Dom {

	public List<PieceDto> domReader();

	public boolean domAiReader();

	public void domWriter(List<PieceDto> list, boolean turnAI);
}
