package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Osztály a program futtatására.
 * 
 * @author roland
 */
public class MainApp extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/SceneMainMenu.fxml"));

		Scene scene = new Scene(root);
		scene.getStylesheets().add("/styles/Styles.css");

		primaryStage.setTitle("Dámajáték - menü");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * A program main függvénye.
	 *
	 * @param  args  a parancssori argumentumok
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
