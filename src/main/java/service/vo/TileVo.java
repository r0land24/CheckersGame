package service.vo;

import static model.properties.BoardProperties.TILE_SIZE;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TileVo extends Rectangle {

	private PieceVo piece;

	public boolean hasPiece() {
		return piece != null;
	}

	public PieceVo getPiece() {
		return piece;
	}

	public void setPiece(PieceVo piece) {
		this.piece = piece;
	}

	/**
	 * Tile! a sakktábla egy kockája, light! alapján setFillel beállítjuk a kocka
	 * színét, x! és y! a relocatehez(rajzoláshoz) kell, amik amúgy tábla
	 * kordináták.
	 */
	public TileVo(boolean light, int x, int y) {
		setWidth(TILE_SIZE);
		setHeight(TILE_SIZE);

		relocate(x * TILE_SIZE, y * TILE_SIZE);

		setFill(light ? Color.GOLD : Color.BROWN);

	}
}