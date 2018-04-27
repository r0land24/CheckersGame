package service.vo;

public enum PieceTypeVo {
	WHITE(-1), WHITE_KING(-2), DARK(1), DARK_KING(2);

	public final int moveDir; // sötét lefele mehet, világos csak felfele

	PieceTypeVo(int moveDirection) {
		this.moveDir = moveDirection;
	}
}
