package controller.service;

import java.util.List;

import controller.vo.PieceTypeVo;
import controller.vo.PieceVo;
import controller.vo.TileVo;
import javafx.scene.Parent;
import javafx.stage.Stage;

public interface BoardServices {

	public Parent createContent();

	public Parent createContent(TileVo[][] savedBoard);

	public PieceVo makePiece(PieceTypeVo type, int x, int y);

	public void saveBoard(List<PieceVo> list);

	public List<PieceVo> pieceList();

	public void addEscape(Stage stage);

}
