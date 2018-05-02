package controller.converter;

import controller.vo.PieceTypeVo;
import controller.vo.PieceVo;
import model.dto.PieceDto;

public class PieceConverter {

	public static PieceVo toPieceVo(PieceDto dto) {
		if (dto == null) {
			return null;
		}
		PieceVo vo = new PieceVo();
		vo.setCoordX(dto.getCoordinateX());
		vo.setCoordY(dto.getCoordinateY());
		vo.setType(PieceTypeVo.valueOf(dto.getPieceType()));
		return vo;
	}

	public static PieceDto toPieceDto(PieceVo vo) {
		PieceDto dto = new PieceDto();
		dto.setCoordinateX(vo.getCoordX());
		dto.setCoordinateY(vo.getCoordY());
		dto.setPieceType(vo.getType().toString());
		return dto;
	}
}
