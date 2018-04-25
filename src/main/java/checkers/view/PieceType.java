package checkers.view;

public enum PieceType {
	WHITE(-1), WHITE_KING(0), DARK(1), DARK_KING(0);
	
	final int moveDirection;	//sötét lefele mehet, világos csak felfele
	PieceType (int moveDirection) {
		this.moveDirection = moveDirection;
	}
}
