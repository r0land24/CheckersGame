package model.dto;

public class PieceDto {

	private int coordinateX;

	private int coordinateY;

	private String pieceType;

	public PieceDto() {

	}

	public PieceDto(int coordinateX, int coordinateY, String pieceType) {
		super();
		this.coordinateX = coordinateX;
		this.coordinateY = coordinateY;
		this.pieceType = pieceType;
	}

	public int getCoordinateX() {
		return coordinateX;
	}

	public void setCoordinateX(int coordinateX) {
		this.coordinateX = coordinateX;
	}

	public int getCoordinateY() {
		return coordinateY;
	}

	public void setCoordinateY(int coordinateY) {
		this.coordinateY = coordinateY;
	}

	public String getPieceType() {
		return pieceType;
	}

	public void setPieceType(String pieceType) {
		this.pieceType = pieceType;
	}

}
