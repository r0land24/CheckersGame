package service.impl;

import static model.vo.Board.HEIGHT;
import static model.vo.Board.WIDTH;

import org.junit.Test;

import model.services.BoardServicesImpl;
import model.vo.Board;
import model.vo.PieceType;

public class BoardServicesImplTest {

	public BoardServicesImplTest() {

	}

	@Test
	public void testCreateContent() {
		BoardServicesImpl.getInstance().createContent();
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				Board.getBoard()[x][y].hasPiece();
			}
		}
	}
	
	@Test
	public void testMakePiece() {
		BoardServicesImpl.getInstance().makePiece(PieceType.DARK, 2, 3);
	}

	
//	@Test
//	public void testAdsa() {
//		Board.setSavedBoard(new TileVo[WIDTH][HEIGHT]);
//		Board.getSavedBoard()[0][0].hasPiece();
////		BoardServicesImpl.getInstance().createContent(Board.getSavedBoard());
//
//	}
}
