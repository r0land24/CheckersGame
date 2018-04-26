package model;

import view.PieceType;

public class TileXMLForm {

	private int coordinateX;

	private int coordinateY;

	private PieceType pieceType;

	public TileXMLForm(int coordinateX, int coordinateY, PieceType pieceType) {
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

	public PieceType getPieceType() {
		return pieceType;
	}

	public void setPieceType(PieceType pieceType) {
		this.pieceType = pieceType;
	}

}
