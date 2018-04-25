package checkers.view;

import checkers.controller.CheckersMainApp;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {

	public int tileCoordinateX; //Roland
	public int tileCoordinateY; //Roland
	
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
	 * Tile! a sakktábla egy kockája, 
	 * light! alapján setFillel beállítjuk a kocka színét,
	 * x! és y! a relocatehez(rajzoláshoz) kell, amik amúgy tábla kordináták.
	 */
	public Tile (boolean light, int x, int y) {
		setWidth(CheckersMainApp.TILE_SIZE);
		setHeight(CheckersMainApp.TILE_SIZE);
		
		relocate(x * CheckersMainApp.TILE_SIZE, y * CheckersMainApp.TILE_SIZE);
	
		setFill(light ? Color.GOLD : Color.BROWN);
		
		tileCoordinateX = x; tileCoordinateY = y; //Roland
	}
}
