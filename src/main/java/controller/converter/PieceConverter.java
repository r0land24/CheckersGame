package controller.converter;

import model.dto.PieceDto;
import model.vo.PieceType;
import model.vo.Piece;

public class PieceConverter {

	public static Piece toPieceVo(PieceDto dto) {
		if (dto == null) {
			return null;
		}
		Piece vo = new Piece();
		vo.setCoordX(dto.getCoordinateX());
		vo.setCoordY(dto.getCoordinateY());
		vo.setType(PieceType.valueOf(dto.getPieceType()));
		return vo;
	}

	public static PieceDto toPieceDto(Piece vo) {
		PieceDto dto = new PieceDto();
		dto.setCoordinateX(vo.getCoordX());
		dto.setCoordinateY(vo.getCoordY());
		dto.setPieceType(vo.getType().toString());
		return dto;
	}
}
