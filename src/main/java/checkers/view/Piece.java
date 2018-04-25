package checkers.view;

import checkers.controller.CheckersMainApp;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Piece extends StackPane{	//stackPanen lesz rajta
	
	private Text text = new Text("K");
	
	private PieceType type;
	
	public PieceType getType() {
		return type;
	}

	public void setType(PieceType type) {
		this.type = type;
	}

	public Piece(PieceType type, int x, int y) {	//coordinates again
		this.type=type;
		
		relocate(x * CheckersMainApp.TILE_SIZE, y * CheckersMainApp.TILE_SIZE);
		
//		Ellipse background = new Ellipse(CheckersMainApp.TILE_SIZE*0.3125, CheckersMainApp.TILE_SIZE*0.26);
//		background.setFill(Color.BLACK);
//		
//		background.setStroke(Color.BLACK);
//		background.setStrokeWidth(CheckersMainApp.TILE_SIZE*0.03);
//		
//		background.setTranslateX((CheckersMainApp.TILE_SIZE - CheckersMainApp.TILE_SIZE * 0.3125 * 2) / 2);
//		background.setTranslateY((CheckersMainApp.TILE_SIZE - CheckersMainApp.TILE_SIZE * 0.26 * 2) / 2 + CheckersMainApp.TILE_SIZE * 0.07);
		
		Circle ellipse = new Circle(CheckersMainApp.TILE_SIZE * 0.26);
//		Ellipse ellipse = new Ellipse(CheckersMainApp.TILE_SIZE * 0.3125, CheckersMainApp.TILE_SIZE * 0.26);
        ellipse.setFill(type == PieceType.DARK
                ? Color.CORNFLOWERBLUE : Color.WHITE);

        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(CheckersMainApp.TILE_SIZE * 0.03);

//        ellipse.setTranslateX((CheckersMainApp.TILE_SIZE - CheckersMainApp.TILE_SIZE * 0.3125 * 2) / 2);
//        ellipse.setTranslateY((CheckersMainApp.TILE_SIZE - CheckersMainApp.TILE_SIZE * 0.26 * 2) / 2);
//        getChildren().addAll(background, ellipse);
        ellipse.setTranslateX((CheckersMainApp.TILE_SIZE - CheckersMainApp.TILE_SIZE * 0.3 * 2) / 2);
        ellipse.setTranslateY((CheckersMainApp.TILE_SIZE - CheckersMainApp.TILE_SIZE * 0.3 * 2) / 2);
        getChildren().add(ellipse);

//        text.setStroke(Color.RED);
//        text.setStrokeWidth(CheckersMainApp.TILE_SIZE * 0.03);;
//        text.setTranslateX((CheckersMainApp.TILE_SIZE - CheckersMainApp.TILE_SIZE * 0.3 * 2) / 2);
//        text.setTranslateY((CheckersMainApp.TILE_SIZE - CheckersMainApp.TILE_SIZE * 0.33 * 2) / 2);
//        getChildren().addAll(ellipse,text);
	}

}
