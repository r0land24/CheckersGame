package model.vo.ai;

import model.vo.board.MoveResult;
import model.vo.board.Piece;

/**
 * A {@code AiMoveResult} osztály az AI mozgási adatait reprezentálja.
 * 
 * @author roland
 */
public class AiMoveResult {

	private MoveResult result;
	private Piece piece;
	private int newX;
	private int newY;
	private int x0;
	private int y0;

	/**
	 * Az {@code AiMoveResult} osztály konstruktora, inicializálja az adattagokat.
	 * 
	 * @param result a lépés lehetséges eredménye
	 * @param piece a korong
	 * @param newX a korong új x koordinátája
	 * @param newY a korong új y koordinátája
	 * @param x0 a korong előző x koordinátája
	 * @param y0 a korong előző y koordinátája
	 */
	public AiMoveResult(MoveResult result, Piece piece, int newX, int newY, int x0, int y0) {
		super();
		this.result = result;
		this.piece = piece;
		this.newX = newX;
		this.newY = newY;
		this.x0 = x0;
		this.y0 = y0;
	}

	/** Visszaadja a lépés lehetséges eredményét.
	 * 
	 *  @return a lépés lehetséges ererdménye
	 *  
	 */
	public MoveResult getResult() {
		return result;
	}

	/** Visszaadja a korongot.
	 * 
	 *  @return a korong
	 *  
	 */
	public Piece getPiece() {
		return piece;
	}

	/** Visszaadja a korong új x koordinátáját.
	 * 
	 *  @return a korong új x koordinátája
	 *  
	 */
	public int getNewX() {
		return newX;
	}

	/** Visszaadja a korong új y koordinátáját.
	 * 
	 *  @return a korong új y koordinátája
	 *  
	 */
	public int getNewY() {
		return newY;
	}

	/** Visszaadja a korong előző x koordinátáját.
	 * 
	 *  @return a korong előző x koordinátája
	 *  
	 */
	public int getX0() {
		return x0;
	}

	/** Visszaadja a korong előző y koordinátáját.
	 * 
	 *  @return a korong előző y koordinátája
	 *  
	 */
	public int getY0() {
		return y0;
	}

}
