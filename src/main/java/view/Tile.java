package view;

import static controller.checkers.CheckersLogic.TILE_SIZE;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {

	private int tileCoordinateX;
	private int tileCoordinateY;

	public int getTileCoordinateX() {
		return tileCoordinateX;
	}

	public int getTileCoordinateY() {
		return tileCoordinateY;
	}

	private Piece piece;

	public boolean hasPiece() {
		return piece != null;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	/**
	 * Tile! a sakktábla egy kockája, light! alapján setFillel beállítjuk a kocka
	 * színét, x! és y! a relocatehez(rajzoláshoz) kell, amik amúgy tábla
	 * kordináták.
	 */
	public Tile(boolean light, int x, int y) {
		setWidth(TILE_SIZE);
		setHeight(TILE_SIZE);

		relocate(x * TILE_SIZE, y * TILE_SIZE);

		setFill(light ? Color.GOLD : Color.BROWN);

		tileCoordinateX = x;
		tileCoordinateY = y; // Roland
	}
}
