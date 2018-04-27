package service;

import java.util.List;

import javafx.scene.Parent;
import javafx.stage.Stage;
import service.vo.PieceTypeVo;
import service.vo.PieceVo;
import service.vo.TileVo;

public interface BoardServices {

	public Parent createContent();

	public Parent createContent(TileVo[][] savedBoard);

	public PieceVo makePiece(PieceTypeVo type, int x, int y);

	public void saveBoard(List<PieceVo> list);

	public List<PieceVo> pieceList();

	public void addEscape(Stage stage);

}
