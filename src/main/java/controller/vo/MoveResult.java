package controller.vo;

public class MoveResult {

	private MoveType type;

	public MoveType getType() {
		return type;
	}

	private PieceVo piece;

	public PieceVo getPiece() {
		return piece;
	}

	public MoveResult(MoveType type) {
		this(type, null);
	}

	public MoveResult(MoveType type, PieceVo piece) {
		this.type = type;
		this.piece = piece;
	}
}