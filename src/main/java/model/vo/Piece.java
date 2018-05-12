package model.vo;

import static model.vo.Board.TILE_SIZE;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * A {@code Piece} osztály a korongot reprezentálja.
 * 
 * @author roland
 */
public class Piece extends StackPane {

	private Text text = new Text("");

	private double mouseX, mouseY;

	private double oldX, oldY; // a korong konkrét helye * tilesize = megrajzolt tile

	private int coordX;

	private int coordY;

	private PieceType type;

	/**
	 * A {@code Piece} osztály paraméter nélküli konstruktora.
	 */
	public Piece() {

	}

	/**
	 * A {@code Piece} osztály konstruktora, inicializálja a {@code type}
	 * adattagot.
	 * 
	 * @param type a mozgás típusa
	 */
	public Piece(PieceType type, int x, int y) {
		this.type = type;

		// relocate(x * TILE_SIZE, y * TILE_SIZE);
		move(x, y); // relocate helyett MOVE

		Circle ellipse = new Circle(TILE_SIZE * 0.26);
		ellipse.setFill(type == PieceType.DARK ? Color.CORNFLOWERBLUE : Color.WHITE);

		ellipse.setStroke(Color.BLACK);
		ellipse.setStrokeWidth(TILE_SIZE * 0.03);

		ellipse.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3 * 2) / 2);
		ellipse.setTranslateY((TILE_SIZE - TILE_SIZE * 0.3 * 2) / 2);
		getChildren().add(ellipse);

		// TEXT
		getChildren().add(text);
		text.setTranslateX((TILE_SIZE - TILE_SIZE * 0.33 * 2) / 2);
		text.setTranslateY((TILE_SIZE - TILE_SIZE * 0.32 * 2) / 2);
		text.setStroke(Color.BLACK);
		text.setStrokeWidth(TILE_SIZE * 0.1);

		// for 2 players
		// setOnMousePressed(e -> {
		// System.out.println(((PieceVo) e.getSource()).getType());
		// mouseX = e.getSceneX();
		// mouseY = e.getSceneY();
		//
		// });
		//
		// setOnMouseDragged(e -> {
		// System.out.println("draged");
		// relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
		// });

		setOnMousePressed(e -> {
			if (Board.isAIsTurn() && (((Piece) e.getSource()).getType().equals(PieceType.DARK)
					|| ((Piece) e.getSource()).getType().equals(PieceType.DARK_KING))) {

				// mouseX = e.getSceneX();
				// mouseY = e.getSceneY();

			} else if (!Board.isAIsTurn() && (((Piece) e.getSource()).getType().equals(PieceType.WHITE)
					|| ((Piece) e.getSource()).getType().equals(PieceType.WHITE_KING))) {

				mouseX = e.getSceneX();
				mouseY = e.getSceneY();

			}
		});
		setOnMouseDragged(e -> {
			if (Board.isAIsTurn() && (((Piece) e.getSource()).getType().equals(PieceType.DARK)
					|| ((Piece) e.getSource()).getType().equals(PieceType.DARK_KING))) {

				// relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);

			} else if (!Board.isAIsTurn() && (((Piece) e.getSource()).getType().equals(PieceType.WHITE)
					|| ((Piece) e.getSource()).getType().equals(PieceType.WHITE_KING))) {

				relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);

			}
		});

	}

	/**
	 * Megadja az X koordinátát.
	 * 
	 * @return az X koordináta
	 */
	public int getCoordX() {
		return coordX;
	}

	/** Beállítja az X koordinátát. */
	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}

	/**
	 * Megadja az Y koordinátát.
	 * 
	 * @return az Y koordináta
	 */
	public int getCoordY() {
		return coordY;
	}

	/** Beállítja az X koordinátát. */
	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}

	/**
	 * Visszaadja a szöveget.
	 * 
	 * @return a szöveg
	 */
	public Text getText() {
		return text;
	}

	/**
	 * Beállítja a szöveget.
	 */
	public void setText(String string) {
		this.text.setText(string);
	}

	public double getOldX() {
		return oldX;
	}

	public double getOldY() {
		return oldY;
	}

	public PieceType getType() {
		return type;
	}

	public void setType(PieceType type) {
		this.type = type;
	}

	public void move(int x, int y) {
		oldX = x * TILE_SIZE;
		oldY = y * TILE_SIZE;
		coordX = x;
		coordY = y;
		relocate(oldX, oldY);
	}

	public void abortMove() {
		relocate(oldX, oldY);
	}

}
