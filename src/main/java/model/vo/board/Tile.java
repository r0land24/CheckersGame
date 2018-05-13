package model.vo.board;

import static model.vo.board.Board.TILE_SIZE;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A {@code Tile} osztály a tábla mezőit reprezentálja.
 * 
 * @author roland
 */
public class Tile extends Rectangle {

	private Piece piece;

	/**
	 * Az {@code Tile} osztály konstruktora, inicializálja a {@code piece}
	 * adattagot.
	 * 
	 * @param light a mező színe, <code>true</code> esetén világos a mező;
	 *            <code>false</code> esetén sötét a mező
	 * @param x a tábla x koordinája
	 * @param y a tábla y koordinája
	 */
	public Tile(boolean light, int x, int y) {
		setWidth(TILE_SIZE);
		setHeight(TILE_SIZE);

		relocate(x * TILE_SIZE, y * TILE_SIZE);

		setFill(light ? Color.GOLD : Color.BROWN);

	}

	/**
	 * Visszaadja, hogy a mezőn van-e korong.
	 * 
	 * @return <code>true</code> ha van rajta korong; 
	 * 		   <code>false</code> ha nincs rajta korong
	 * 
	 */
	public boolean hasPiece() {
		return piece != null;
	}

	/** Visszaadja a korongot.
	 * 
	 *  @return a korong
	 *  
	 */
	public Piece getPiece() {
		return piece;
	}

	/** Beállítja a korongot.
	 * 
	 * @param piece a korong
	 *
	 */
	public void setPiece(Piece piece) {
		this.piece = piece;
	}

}
