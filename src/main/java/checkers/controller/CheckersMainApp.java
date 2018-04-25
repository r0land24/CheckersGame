package checkers.controller;

import checkers.view.Piece;
import checkers.view.PieceType;
import checkers.view.Tile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CheckersMainApp extends Application {
	
	public static final int TILE_SIZE = 70;
	public static final int WIDTH = 8;
	public static final int HEIGHT = 8;
	
	private Tile[][] board = new Tile[WIDTH][HEIGHT];
	
	private Group tileGroup = new Group(); //?
	private Group pieceGroup = new Group(); //?
    
	public Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE); //a színpad területe
        root.getChildren().addAll(tileGroup,pieceGroup); //?
        
        for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				//minden világos tile páros számú
				Tile tile = new Tile((x+y)%2 == 0, x, y); //
				
				board[x][y] = tile;
				
				tileGroup.getChildren().add(tile);
				
				Piece piece = null;

                if (y <= 2 && (x + y) % 2 != 0) {
                    piece = makePiece(PieceType.DARK, x, y);
                }

                if (y >= 5 && (x + y) % 2 != 0) {
                    piece = makePiece(PieceType.WHITE, x, y);
                }

                if (piece != null) {
                    tile.setPiece(piece);
                    pieceGroup.getChildren().add(piece);
                }
			}
		}
        return root;
    }

    @Override	//*************KELL
    public void start(Stage primaryStage) throws Exception {
//        Scene scene = new Scene(createContent());
//        primaryStage.setTitle("Dámajáték");
//        primaryStage.setScene(scene);
//        primaryStage.show();
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/SceneMenu.fxml"));
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("JavaFX and Maven");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Piece makePiece(PieceType type, int x, int y) {
        Piece piece = new Piece(type, x, y);
        return piece;
    }
    
    //*************KELL
    public static void main(String[] args) {
        launch(args);
    }

}
