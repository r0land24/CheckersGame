package service.impl;

import static model.properties.BoardProperties.HEIGHT;
import static model.properties.BoardProperties.TILE_SIZE;
import static model.properties.BoardProperties.WIDTH;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import service.BoardServices;
import service.board.Board;
import service.vo.MoveResult;
import service.vo.MoveType;
import service.vo.PieceTypeVo;
import service.vo.PieceVo;
import service.vo.TileVo;

public class CheckersLogic implements BoardServices {

	private static CheckersLogic firstInstance = null;

	private CheckersLogic() {
		// Singleton
	}

	/** Singleton példány */
	public static CheckersLogic getInstance() {
		if (firstInstance == null) {
			firstInstance = new CheckersLogic();
		}
		return firstInstance;
	}

	/** Új játék esetén létrehozott sakktábla */
	@Override
	public Parent createContent() {
		Board.setBoard(new TileVo[WIDTH][HEIGHT]);
		Board.setTileGroup(new Group());
		Board.setPieceGroup(new Group());
		Pane root = new Pane();
		root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE); // a színpad területe
		root.getChildren().addAll(Board.getTileGroup(), Board.getPieceGroup()); //
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				// minden világos tile páros számú
				TileVo tile = new TileVo((x + y) % 2 == 0, x, y); //

				Board.getBoard()[x][y] = tile;

				Board.getTileGroup().getChildren().add(tile);

				PieceVo piece = null;

				if (y <= 2 && (x + y) % 2 != 0) {
					piece = makePiece(PieceTypeVo.DARK, x, y);
				}

				if (y >= 5 && (x + y) % 2 != 0) {
					piece = makePiece(PieceTypeVo.WHITE, x, y);
				}

				if (piece != null) {
					tile.setPiece(piece);
					Board.getPieceGroup().getChildren().add(piece);
				}
			}
		}
		return root;
	}

	/**
	 * boardAttr elemei alapján létrehozza az új táblát, frissül a static board
	 * tábla
	 */
	@Override
	public Parent createContent(TileVo[][] savedBoard) {
		Board.setBoard(new TileVo[WIDTH][HEIGHT]);
		Board.setTileGroup(new Group());
		Board.setPieceGroup(new Group());
		Pane root = new Pane();
		root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE); // a színpad területe
		root.getChildren().addAll(Board.getTileGroup(), Board.getPieceGroup()); //

		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				TileVo tile = new TileVo((x + y) % 2 == 0, x, y);
				Board.getBoard()[x][y] = tile;
				Board.getTileGroup().getChildren().add(tile);
				if (savedBoard[x][y].hasPiece()) {
					PieceVo piece = null;

					if (savedBoard[x][y].getPiece().getType().equals(PieceTypeVo.DARK)) {
						piece = makePiece(PieceTypeVo.DARK, x, y);
					} else {
						piece = makePiece(PieceTypeVo.WHITE, x, y);
					}

					tile.setPiece(piece);
					Board.getPieceGroup().getChildren().add(piece);
				}
			}
		}
		return root;
	}

	/** Korong létrehozás, mozgásra történő újrarajzolás stb. */
	@Override
	public PieceVo makePiece(PieceTypeVo type, int x, int y) {
		PieceVo piece = new PieceVo(type, x, y);

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
					if (piece.getType().equals(PieceTypeVo.DARK)) {
						piece.setType(PieceTypeVo.DARK_KING);
						System.out.println("dark king");
					} else {
						piece.setType(PieceTypeVo.WHITE_KING);
						System.out.println("white king");
					}
				}
				Board.getBoard()[newX][newY].setPiece(piece);
				break;
			case KILL:
				piece.move(newX, newY);
				Board.getBoard()[x0][y0].setPiece(null);
				if (newY == 0 || newY == 7) {
					piece.setText("K");
					if (piece.getType().equals(PieceTypeVo.DARK)) {
						piece.setType(PieceTypeVo.DARK_KING);
						System.out.println("dark king");
					} else {
						piece.setType(PieceTypeVo.WHITE_KING);
						System.out.println("white king");
					}
				}
				Board.getBoard()[newX][newY].setPiece(piece);
				PieceVo otherPiece = result.getPiece();
				Board.getBoard()[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
				Board.getPieceGroup().getChildren().remove(otherPiece);
				break;
			}
		});

		return piece;
	}

	@Override
	public void saveBoard(List<PieceVo> list) {
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				TileVo tile = new TileVo((x + y) % 2 == 0, x, y);
				Board.getSavedBoard()[x][y] = tile;

				for (PieceVo piecesVo : list) {
					if (piecesVo.getCoordX() == x && piecesVo.getCoordY() == y) {
						Board.getSavedBoard()[x][y]
								.setPiece(CheckersLogic.getInstance().makePiece(piecesVo.getType(), x, y));
					}

				}

			}

		}
	}

	@Override
	public List<PieceVo> pieceList() {

		List<PieceVo> list = new ArrayList<>();
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
	@Override
	public void addEscape(Stage stage) {
		stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
			if (KeyCode.ESCAPE == event.getCode()) {
				try {
					Stage stage2 = new Stage();
					Parent root = FXMLLoader.load(getClass().getResource("/fxml/SceneSaveGame.fxml"));
					Scene scene = new Scene(root);

					stage2.setTitle("Dámajáték");
					stage2.setScene(scene);
					stage2.setUserData(stage);//////
					stage2.show();
					// stage.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	private MoveResult tryMove(PieceVo piece, int newX, int newY) {
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

			if ((Board.getBoard())[x1][y1].hasPiece()
					&& (Board.getBoard())[x1][y1].getPiece().getType() != piece.getType()) {
				return new MoveResult(MoveType.KILL, (Board.getBoard())[x1][y1].getPiece());
			}
		}

		return new MoveResult(MoveType.NONE);
	}

	private int toBoard(double pixel) {
		return (int) (pixel + TILE_SIZE / 2) / TILE_SIZE;
	}

}
