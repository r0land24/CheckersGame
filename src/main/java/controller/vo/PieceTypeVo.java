package controller.vo;

public enum PieceTypeVo {
	WHITE(-1), WHITE_KING(0), DARK(1), DARK_KING(0);

	public final int moveDir; // sötét lefele mehet, világos csak felfele

	PieceTypeVo(int moveDirection) {
		this.moveDir = moveDirection;
	}
}
