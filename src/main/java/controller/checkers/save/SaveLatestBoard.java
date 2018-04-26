package controller.checkers.save;

import static controller.checkers.CheckersLogic.HEIGHT;
import static controller.checkers.CheckersLogic.WIDTH;

import java.util.List;

import controller.checkers.CheckersLogic;
import model.TileXMLForm;
import view.Tile;

public class SaveLatestBoard {

	private static Tile[][] savedBoard = new Tile[WIDTH][HEIGHT];

	public static void saveBoard(List<TileXMLForm> list) {
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				Tile tile = new Tile((x + y) % 2 == 0, x, y);
				savedBoard[x][y] = tile;

				for (TileXMLForm piecesForm : list) {
					if (piecesForm.getCoordinateX() == x && piecesForm.getCoordinateY() == y)
						savedBoard[x][y].setPiece(CheckersLogic.makePiece(piecesForm.getPieceType(), x, y));
				}

			}

		}
	}
	
	public static Tile[][] getSavedBoard(){
		return savedBoard;
	}
}
