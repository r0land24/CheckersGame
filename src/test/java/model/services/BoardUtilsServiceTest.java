package model.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import model.vo.Board;
import model.vo.Piece;
import model.vo.PieceType;

public class BoardUtilsServiceTest {

	public BoardUtilsServiceTest() {

	}

	@Test
	public void testSaveBoard() {
		BoardService.getInstance().createContent();
		List<Piece> list = new ArrayList<>();
		list.add(new Piece(PieceType.DARK_KING, 0, 0));
		list.add(new Piece(PieceType.WHITE_KING, 1, 1));
		list.add(new Piece(PieceType.WHITE, 2, 2));
		list.add(new Piece(PieceType.DARK, 3, 3));
		BoardUtilsService.getInstance().saveBoard(list);
		assertNotNull(Board.getSavedBoard());
	}

	@Test
	public void testPieceList() {
		BoardService.getInstance().createContent();
		assertEquals(24, BoardUtilsService.getInstance().pieceList().size());
	}

	@Test
	public void testCheckEndGame() {
		BoardService.getInstance().createContent();
		boolean check = BoardUtilsService.getInstance().checkEndGame(Board.getBoard());
		assertFalse(check);

	}
}
