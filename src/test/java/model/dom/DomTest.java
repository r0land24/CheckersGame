package model.dom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.util.List;

import org.junit.Test;

import model.dao.Dom;
import model.services.BoardService;
import model.services.BoardUtilsService;
import model.vo.Board;
import model.vo.Piece;

public class DomTest {

	public DomTest() {

	}

	@Test
	public void domTest() {
		BoardService.getInstance().createContent();
		Dom dom = new Dom();
		List<Piece> list = BoardUtilsService.getInstance().pieceList();
		boolean aisTurn = Board.isAIsTurn();
		dom.domWriter(list, aisTurn);
		List<Piece> pieces = dom.domPieceReader();
		assertEquals(24, pieces.size());
		boolean ai = dom.domAiReader();
		assertFalse(ai);

		File xmlFile = null;
		if (dom.osName.contains("windows")) {
			xmlFile = new File(dom.windowsFilePath);
		} else if (dom.osName.contains("linux") || dom.osName.contains("unix")) {
			xmlFile = new File(dom.linuxFilePath);
		}
		xmlFile.delete();

	}

}
