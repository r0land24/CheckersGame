package model.vo;

public enum PieceType {
	WHITE(-1), WHITE_KING(0), DARK(1), DARK_KING(0);

	public final int moveDir; // sötét lefele mehet, világos csak felfele, 0-ra KING-re vonatkozó feltétel áll fönn majd, azaz bármerre mehet

	PieceType(int moveDirection) {
		this.moveDir = moveDirection;
	}
}
