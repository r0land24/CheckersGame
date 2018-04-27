package model.dom;

import java.util.List;

import model.dto.PieceDto;

public interface Dom {

	public List<PieceDto> domReader();

	public void domWriter(List<PieceDto> list);
}
