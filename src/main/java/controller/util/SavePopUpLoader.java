package controller.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * {@code SavePopUpLoader} osztály a játék mentése felugró ablak betöltésére.
 * 
 * @author roland
 */
public class SavePopUpLoader {

	private static Logger logger = LoggerFactory.getLogger(EndPopUpLoader.class);

	/**
	 * Betölti a játék mentése ablakot.
	 * 
	 * @param stage ami a táblát és a rajta lévő korongokat tartalmazza
	 */
	public void createSavePopUp(Stage stage) {
		try {
			Stage stage2 = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/SceneSaveGame.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add("/styles/Styles.css");

			stage2.setTitle("Mentés");
			stage2.setScene(scene);
			stage2.setUserData(stage); // játék ablak lementése databa
			stage2.show();
		} catch (IOException e) {
			logger.error("Mentési ablak betöltése sikertelen: " + e.getMessage());
		}
	}

}
