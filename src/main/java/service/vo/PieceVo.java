package service.vo;

import static model.properties.BoardProperties.TILE_SIZE;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import service.board.Board;
import service.impl.BoardServicesImpl;

public class PieceVo extends StackPane {

	private Text text = new Text("");
	private double mouseX, mouseY;
	private double oldX, oldY; // a korong konkrét helye * tilesize = megrajzolt tile
	private int coordX;
	private int coordY;

	public int getCoordX() {
		return coordX;
	}

	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}

	public int getCoordY() {
		return coordY;
	}

	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}

	private PieceTypeVo type;

	public Text getText() {
		return text;
	}

	public void setText(String string) {
		this.text.setText(string);
	}

	public double getMouseX() {
		return mouseX;
	}

	public void setMouseX(double mouseX) {
		this.mouseX = mouseX;
	}

	public double getMouseY() {
		return mouseY;
	}

	public void setMouseY(double mouseY) {
		this.mouseY = mouseY;
	}

	public double getOldX() {
		return oldX;
	}

	public void setOldX(double oldX) {
		this.oldX = oldX;
	}

	public double getOldY() {
		return oldY;
	}

	public void setOldY(double oldY) {
		this.oldY = oldY;
	}

	public PieceTypeVo getType() {
		return type;
	}

	public void setType(PieceTypeVo type) {
		this.type = type;
	}

	public PieceVo() {

	}

	public PieceVo(PieceTypeVo type, int x, int y) {
		this.type = type;

		// relocate(x * TILE_SIZE, y * TILE_SIZE);
		move(x, y); // relocate helyett MOVE

		Circle ellipse = new Circle(TILE_SIZE * 0.26);
		ellipse.setFill(type == PieceTypeVo.DARK ? Color.CORNFLOWERBLUE : Color.WHITE);

		ellipse.setStroke(Color.BLACK);
		ellipse.setStrokeWidth(TILE_SIZE * 0.03);

		ellipse.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3 * 2) / 2);
		ellipse.setTranslateY((TILE_SIZE - TILE_SIZE * 0.3 * 2) / 2);
		getChildren().add(ellipse);

		// TEXT
		getChildren().add(text);
		text.setTranslateX((TILE_SIZE - TILE_SIZE * 0.33 * 2) / 2);
		text.setTranslateY((TILE_SIZE - TILE_SIZE * 0.32 * 2) / 2);
		// text.setStroke(type == PieceTypeVo.DARK ? Color.RED : Color.ORANGERED);
		text.setStroke(Color.BLACK);
		text.setStrokeWidth(TILE_SIZE * 0.1);

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
			if (BoardServicesImpl.ai && (
					((PieceVo) e.getSource()).getType().equals(PieceTypeVo.DARK) ||
					((PieceVo) e.getSource()).getType().equals(PieceTypeVo.DARK_KING))) {
//				System.out.println(((PieceVo) e.getSource()).getType());
//				mouseX = e.getSceneX();
//				mouseY = e.getSceneY();
			} else if (!BoardServicesImpl.ai && (
					((PieceVo) e.getSource()).getType().equals(PieceTypeVo.WHITE) ||
					((PieceVo) e.getSource()).getType().equals(PieceTypeVo.WHITE_KING))) {
				mouseX = e.getSceneX();
				mouseY = e.getSceneY();
			}
		});
		setOnMouseDragged(e -> {
			if (BoardServicesImpl.ai && (
					((PieceVo) e.getSource()).getType().equals(PieceTypeVo.DARK) ||
					((PieceVo) e.getSource()).getType().equals(PieceTypeVo.DARK_KING))) {
//				relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
			} else if(!BoardServicesImpl.ai && (
					((PieceVo) e.getSource()).getType().equals(PieceTypeVo.WHITE) ||
					((PieceVo) e.getSource()).getType().equals(PieceTypeVo.WHITE_KING))) {
				relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
			}
		});

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
