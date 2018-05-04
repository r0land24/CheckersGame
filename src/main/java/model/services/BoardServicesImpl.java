package model.services;

import static model.vo.Board.HEIGHT;
import static model.vo.Board.TILE_SIZE;
import static model.vo.Board.WIDTH;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.vo.Board;
import model.vo.MoveResult;
import model.vo.MoveType;
import model.vo.PieceType;
import model.vo.Piece;
import model.vo.Tile;

public class BoardServicesImpl implements BoardServices {

	private static BoardServicesImpl firstInstance = null;

	private BoardServicesImpl() {
		// Singleton
	}

	/** Példányosít egy {@code BoardServicesImpl} objektumot. */
	public static BoardServicesImpl getInstance() {
		if (firstInstance == null) {
			firstInstance = new BoardServicesImpl();
		}
		return firstInstance;
	}

	/** Feltölti a és feltölti a korongokkal. */
	@Override
	public Parent createContent() {
		Board.setBoard(new Tile[WIDTH][HEIGHT]);
		Board.setTileGroup(new Group());
		Board.setPieceGroup(new Group());
		Pane root = new Pane();
		root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE); // a színpad területe
		root.getChildren().addAll(Board.getTileGroup(), Board.getPieceGroup()); //
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				// minden világos tile páros számú
				Tile tile = new Tile((x + y) % 2 == 0, x, y); //

				Board.getBoard()[x][y] = tile;

				Board.getTileGroup().getChildren().add(tile);

				Piece piece = null;

				if (y <= 2 && (x + y) % 2 != 0) {
					piece = makePiece(PieceType.DARK, x, y);
				}

				if (y >= 5 && (x + y) % 2 != 0) {
					piece = makePiece(PieceType.WHITE, x, y);
				}

				if (piece != null) {
					tile.setPiece(piece);
					Board.getPieceGroup().getChildren().add(piece);
				}
			}
		}
		// (new ThreadWhile()).start();
		// (new ThreadWhile()).run();
		return root;
	}

	/**
	 * boardAttr elemei alapján létrehozza az új táblát, frissül a static board
	 * tábla
	 */
	@Override
	public Parent createContent(Tile[][] savedBoard) {
		Board.setBoard(new Tile[WIDTH][HEIGHT]);
		Board.setTileGroup(new Group());
		Board.setPieceGroup(new Group());
		Pane root = new Pane();
		root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE); // a színpad területe
		root.getChildren().addAll(Board.getTileGroup(), Board.getPieceGroup()); //

		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				Tile tile = new Tile((x + y) % 2 == 0, x, y);
				Board.getBoard()[x][y] = tile;
				Board.getTileGroup().getChildren().add(tile);
				if (savedBoard[x][y].hasPiece()) {
					Piece piece = null;

					if (savedBoard[x][y].getPiece().getType().equals(PieceType.DARK)) {
						piece = makePiece(PieceType.DARK, x, y);
					} else if (savedBoard[x][y].getPiece().getType().equals(PieceType.WHITE)) {
						piece = makePiece(PieceType.WHITE, x, y);
					} else if (savedBoard[x][y].getPiece().getType().equals(PieceType.WHITE_KING)) {
						piece = makePiece(PieceType.WHITE_KING, x, y);
						piece.setText("K");
					} else if (savedBoard[x][y].getPiece().getType().equals(PieceType.DARK_KING)){
						piece = makePiece(PieceType.DARK_KING, x, y);
						piece.setText("K");
					}

					tile.setPiece(piece);
					Board.getPieceGroup().getChildren().add(piece);
				}
			}
		}
		return root;
	}

	 /**
     * Létrehozza a megadott típusú korongot a megadott koordinátákon.
     *
     * @param type a korong típusa
     * @param x az x koordináta
     * @param y az y koordináta
     */
	@Override
	public Piece makePiece(PieceType type, int x, int y) {
		Piece piece = new Piece(type, x, y);
		piece.setOnMouseReleased(e -> {
			int newX = toBoard(piece.getLayoutX());
			int newY = toBoard(piece.getLayoutY());

			MoveResult result;

			if (newX < 0 || newY < 0 || newX >= WIDTH || newY >= HEIGHT) {
				result = new MoveResult(MoveType.NONE);
			} else {
				result = tryMove(piece, newX, newY);
			}

			int x0 = toBoard(piece.getOldX());
			int y0 = toBoard(piece.getOldY());

			switch (result.getType()) {
			case NONE:
				piece.abortMove();
				break;
			case NORMAL:
				piece.move(newX, newY);
				Board.getBoard()[x0][y0].setPiece(null);
				if (newY == 0 || newY == 7) {
					piece.setText("K");
					if (piece.getType().equals(PieceType.DARK)) {
						piece.setType(PieceType.DARK_KING);
					} else if (piece.getType().equals(PieceType.WHITE)) {
						piece.setType(PieceType.WHITE_KING);
					}
				}
				Board.getBoard()[newX][newY].setPiece(piece);
				
				Board.setAIsTurn(!Board.isAIsTurn());
//				ai = !ai;
				break;
			case KILL:
				piece.move(newX, newY);
				Board.getBoard()[x0][y0].setPiece(null);
				if (newY == 0 || newY == 7) {
					piece.setText("K");
					if (piece.getType().equals(PieceType.DARK)) {
						piece.setType(PieceType.DARK_KING);
					} else if (piece.getType().equals(PieceType.WHITE)) {
						piece.setType(PieceType.WHITE_KING);
					}
				}
				Board.getBoard()[newX][newY].setPiece(piece);
				Piece otherPiece = result.getPiece();
				Board.getBoard()[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
				Board.getPieceGroup().getChildren().remove(otherPiece);

				Board.setAIsTurn(!Board.isAIsTurn());
//				ai = !ai;
				break;
			}

		});
		return piece;
	}

	@Override
	public void saveBoard(List<Piece> list) {
		Board.setSavedBoard(new Tile[WIDTH][HEIGHT]);
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				Tile tile = new Tile((x + y) % 2 == 0, x, y);
				Board.getSavedBoard()[x][y] = tile;

				for (Piece piecesVo : list) {
					if (piecesVo.getCoordX() == x && piecesVo.getCoordY() == y) {
						Board.getSavedBoard()[x][y]
								.setPiece(BoardServicesImpl.getInstance().makePiece(piecesVo.getType(), x, y));
					}

				}

			}

		}
	}

	@Override
	public List<Piece> pieceList() {

		List<Piece> list = new ArrayList<>();
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				if (Board.getBoard()[x][y].hasPiece()) {
					list.add(Board.getBoard()[x][y].getPiece());
				}
			}
		}
		return list;
	}

	/** ESC billentyű leütése játékban. Mentés ablak behozása. */
//	@Override
//	public void addEscape(Stage stage) {
//		stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
//			if (KeyCode.ESCAPE == event.getCode()) {
//				try {
//					Stage stage2 = new Stage();
//					Parent root = FXMLLoader.load(getClass().getResource("/fxml/SceneSaveGame.fxml"));
//					Scene scene = new Scene(root);
//
//					stage2.setTitle("Dámajáték");
//					stage2.setScene(scene);
//					stage2.setUserData(stage);//////
//					stage2.show();
//					// stage.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	private MoveResult tryMove(Piece piece, int newX, int newY) {
		if (Board.getBoard()[newX][newY].hasPiece() || (newX + newY) % 2 == 0) {
			return new MoveResult(MoveType.NONE);
		}

		int x0 = toBoard(piece.getOldX());
		int y0 = toBoard(piece.getOldY());

		if (Math.abs(newX - x0) == 1 && newY - y0 == piece.getType().moveDir) {
			return new MoveResult(MoveType.NORMAL);
		} else if (Math.abs(newX - x0) == 2 && newY - y0 == piece.getType().moveDir * 2) {

			int x1 = x0 + (newX - x0) / 2;
			int y1 = y0 + (newY - y0) / 2;

//			if ((Board.getBoard())[x1][y1].hasPiece()
//					&& (Board.getBoard())[x1][y1].getPiece().getType() != piece.getType()) {
//				return new MoveResult(MoveType.KILL, (Board.getBoard())[x1][y1].getPiece());
//			}
			
			if (Board.getBoard()[x1][y1].hasPiece() &&
					(	(Board.getBoard()[x1][y1].getPiece().getType().equals(PieceType.DARK))  ||
						(Board.getBoard()[x1][y1].getPiece().getType().equals(PieceType.DARK_KING))
					)
					&&	(PieceType.DARK != piece.getType() && PieceType.DARK_KING != piece.getType())
			   ) 
			{
				return new MoveResult(MoveType.KILL, (Board.getBoard())[x1][y1].getPiece());
			} else if (Board.getBoard()[x1][y1].hasPiece() &&
						(	(Board.getBoard()[x1][y1].getPiece().getType().equals(PieceType.WHITE))  ||
							(Board.getBoard()[x1][y1].getPiece().getType().equals(PieceType.WHITE_KING))
						)
						&&	(PieceType.WHITE != piece.getType() && PieceType.WHITE_KING != piece.getType())
					  ) 
			{
				return new MoveResult(MoveType.KILL, (Board.getBoard())[x1][y1].getPiece());
			}
			
		}
		// ************
		else if (piece.getType().equals(PieceType.DARK_KING) || piece.getType().equals(PieceType.WHITE_KING)) {
			if (Math.abs(newX - x0) == 1 && (newY - y0 == -1 || newY - y0 == 1)) {
				return new MoveResult(MoveType.NORMAL);
			} else if (Math.abs(newX - x0) == 2 && (newY - y0 == -1 * 2 || newY - y0 == 1 * 2)) {

				int x1 = x0 + (newX - x0) / 2;
				int y1 = y0 + (newY - y0) / 2;

				// if ((Board.getBoard())[x1][y1].hasPiece()
				// && (Board.getBoard())[x1][y1].getPiece().getType() != piece.getType()) {
				// return new MoveResult(MoveType.KILL, (Board.getBoard())[x1][y1].getPiece());
				// }
				if (Board.getBoard()[x1][y1].hasPiece() &&
						(	(Board.getBoard()[x1][y1].getPiece().getType().equals(PieceType.DARK))  ||
							(Board.getBoard()[x1][y1].getPiece().getType().equals(PieceType.DARK_KING))
						)
						&&	(PieceType.DARK != piece.getType() && PieceType.DARK_KING != piece.getType())
				   ) 
				{
					return new MoveResult(MoveType.KILL, (Board.getBoard())[x1][y1].getPiece());
				} else if (Board.getBoard()[x1][y1].hasPiece() &&
							(	(Board.getBoard()[x1][y1].getPiece().getType().equals(PieceType.WHITE))  ||
								(Board.getBoard()[x1][y1].getPiece().getType().equals(PieceType.WHITE_KING))
							)
							&&	(PieceType.WHITE != piece.getType() && PieceType.WHITE_KING != piece.getType())
						  ) 
				{
					return new MoveResult(MoveType.KILL, (Board.getBoard())[x1][y1].getPiece());
				}
				
				
			}
		}

		return new MoveResult(MoveType.NONE);
	}

	private int toBoard(double pixel) {
		return (int) (pixel + TILE_SIZE / 2) / TILE_SIZE;
	}

	public void checkEndGame(Tile[][] board) {
		int dark = 0;
		int white = 0;
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				if(board[x][y].hasPiece() && 
						( board[x][y].getPiece().getType().equals(PieceType.DARK) 
								|| board[x][y].getPiece().getType().equals(PieceType.DARK_KING))) {
					dark++;
				} else if(board[x][y].hasPiece() && 
						( board[x][y].getPiece().getType().equals(PieceType.WHITE) 
								|| board[x][y].getPiece().getType().equals(PieceType.WHITE_KING))) {
					white++;
				}
			}
		}
		if (dark==0 || white==0) {
			Stage stage = (Stage) board[0][0].getScene().getWindow();
				Stage stage2 = new Stage();
				Parent root = new Parent() {
				};
				try {
					root = FXMLLoader.load(getClass().getResource("/fxml/SceneEnd.fxml"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Scene scene = new Scene(root);
	
				stage2.setTitle("Dámajáték");
				stage2.setScene(scene);
				if (dark==0)
					stage2.setUserData("white");
				else if (white==0)
					stage2.setUserData("dark");
				stage2.show();
				stage.close();
			
		}
		
	}

	public void closeStage(Stage stage) {
		stage.close();
	}

	public void aImove() {

		Piece currentPiece = null;
		List<Piece> listToShuffleWithPieces = new ArrayList<>();
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				if (Board.getBoard()[j][i].hasPiece()
						&& (Board.getBoard()[j][i].getPiece().getType().equals(PieceType.DARK)
								|| Board.getBoard()[j][i].getPiece().getType().equals(PieceType.DARK_KING))) {
					listToShuffleWithPieces.add(Board.getBoard()[j][i].getPiece());
				}
			}
		}
		Collections.shuffle(listToShuffleWithPieces);

		int movedPiece = 0;
		for (int a = 0; a < listToShuffleWithPieces.size(); a++) {

			currentPiece = listToShuffleWithPieces.get(a);
			Piece piece = Board.getBoard()[currentPiece.getCoordX()][currentPiece.getCoordY()].getPiece();
			// System.out.println(""+currentPiece.getType() + currentPiece.getCoordX() +
			// currentPiece.getCoordY()+ " list size: "+listToShuffleWithPieces.size());
			// MOUSE
			for (int y = piece.getCoordY() - 3; y < piece.getCoordY() + 3; y++) {
				for (int x = piece.getCoordX() - 3; x < piece.getCoordX() + 3; x++) {
					// int newX = toBoard(piece.getLayoutX());
					// int newY = toBoard(piece.getLayoutY());
					int newX = toBoard(x * TILE_SIZE);
					int newY = toBoard(y * TILE_SIZE);
					MoveResult result;

					if (newX < 0 || newY < 0 || newX >= WIDTH || newY >= HEIGHT) {
						result = new MoveResult(MoveType.NONE);
					} else {
						result = tryMove(piece, newX, newY);
					}

					int x0 = toBoard(piece.getOldX());
					int y0 = toBoard(piece.getOldY());

					switch (result.getType()) {
					case NONE:
						piece.abortMove();
						break;
					case NORMAL:
						piece.move(newX, newY);
						Board.getBoard()[x0][y0].setPiece(null);
						if (newY == 0 || newY == 7) {
							piece.setText("K");
							if (piece.getType().equals(PieceType.DARK)) {
								piece.setType(PieceType.DARK_KING);
							} else if (piece.getType().equals(PieceType.WHITE)) {
								piece.setType(PieceType.WHITE_KING);
							}
						}
						Board.getBoard()[newX][newY].setPiece(piece);
						
						Board.setAIsTurn(!Board.isAIsTurn());
//						ai = !ai;
						movedPiece++;
						break;
					case KILL:
						piece.move(newX, newY);
						Board.getBoard()[x0][y0].setPiece(null);
						if (newY == 0 || newY == 7) {
							piece.setText("K");
							if (piece.getType().equals(PieceType.DARK)) {
								piece.setType(PieceType.DARK_KING);
							} else if (piece.getType().equals(PieceType.WHITE)) {
								piece.setType(PieceType.WHITE_KING);
							}
						}
						Board.getBoard()[newX][newY].setPiece(piece);
						Piece otherPiece = result.getPiece();
						Board.getBoard()[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
						Board.getPieceGroup().getChildren().remove(otherPiece);
						
						Board.setAIsTurn(!Board.isAIsTurn());
//						ai = !ai;
						movedPiece++;
						break;
					}

					if (movedPiece > 0)
						return;
				}
				if (movedPiece > 0)
					break;
			}
		}
	}

//	public void addSpace(Stage stage) {
//		stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
//			if (KeyCode.SPACE == event.getCode()) {
//				checkEndGame(Board.getBoard());
//				if (Board.isAIsTurn()) {
//					aImove();
//				}
//			}
//		});
//	}
}
