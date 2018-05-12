package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * {@code MainApp} osztály a program futtatására.
 * 
 * @author roland
 */
public class MainApp extends Application {

	private static Logger logger = LoggerFactory.getLogger(MainApp.class);

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/SceneMainMenu.fxml"));

		Scene scene = new Scene(root);
		scene.getStylesheets().add("/styles/Styles.css");

		primaryStage.setTitle("Dámajáték - menü");
		primaryStage.setScene(scene);
		primaryStage.show();
		logger.info("A program elindult!");
	}

	/**
	 * A program main függvénye.
	 *
	 * @param args a parancssori argumentumok
	 * 
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
