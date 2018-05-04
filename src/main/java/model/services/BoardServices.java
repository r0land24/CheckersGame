package model.services;

import java.util.List;

import javafx.scene.Parent;
import javafx.stage.Stage;
import model.vo.PieceType;
import model.vo.Piece;
import model.vo.Tile;

public interface BoardServices {

	public Parent createContent();

	public Parent createContent(Tile[][] savedBoard);

	public Piece makePiece(PieceType type, int x, int y);

	public void saveBoard(List<Piece> list);

	public List<Piece> pieceList();

//	public void addEscape(Stage stage);

}
