package model.services;

import static model.vo.board.Board.HEIGHT;
import static model.vo.board.Board.WIDTH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.services.BoardService;
import model.vo.board.Board;
import model.vo.board.MoveResult;
import model.vo.board.MoveType;
import model.vo.board.Piece;
import model.vo.board.PieceType;
import model.vo.board.Tile;

public class BoardServiceTest {

	public BoardServiceTest() {

	}

	@Test
	public void testCreateContent() {
		BoardService.getInstance().createContent();
		int hasPiece = 0;
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				if (Board.getBoard()[x][y].hasPiece()) {
					hasPiece++;
				}
			}
		}
		assertEquals(24, hasPiece);
	}

	@Test
	public void testCreateSavedContent() {
		Board.setSavedBoard(new Tile[WIDTH][HEIGHT]);
		assertNotNull(BoardService.getInstance().createContent(Board.getBoard()));
	}

	@Test
	public void testMakePiece() {
		BoardService.getInstance().makePiece(PieceType.DARK, 2, 3);
	}
	
	@Test
	public void testTryMove() {
		Piece piece = new Piece(PieceType.DARK, 1, 2);
		BoardService.getInstance().createContent();
		MoveResult mr1 = BoardService.getInstance().tryMove(piece, 2, 3);
		MoveResult mr2 = BoardService.getInstance().tryMove(piece, 1, 1);
		assertEquals(MoveType.NORMAL, mr1.getType());
		assertEquals(MoveType.NONE, mr2.getType());
		Board.getBoard()[2][3].setPiece(new Piece(PieceType.WHITE, 2, 3));
		MoveResult mr3 = BoardService.getInstance().tryMove(piece, 3, 4);
		assertEquals(MoveType.KILL, mr3.getType());
	}
	
	@Test
	public void testAiMove() {
		BoardService.getInstance().createContent();
		assertFalse(Board.isAIsTurn());
		BoardService.getInstance().aImove();
		assertTrue(Board.isAIsTurn());
		BoardService.getInstance().aImove();
		assertFalse(Board.isAIsTurn());
	}
	
}
