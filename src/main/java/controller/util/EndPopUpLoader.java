package controller.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.vo.board.Board;

/**
 * {@code EndPopUpLoader} osztály a játék vége felugró ablak betöltésére.
 * 
 * @author roland
 */
public class EndPopUpLoader {

	private static Logger logger = LoggerFactory.getLogger(EndPopUpLoader.class);

	/**
	 * Betölti a játék vége felugró ablakot és bezárja az előzőt.
	 */
	public void createEndPopUp() {
		Stage stage = (Stage) Board.getBoard()[0][0].getScene().getWindow();
		Stage stage2 = new Stage();
		Parent root = new Parent() {
		};
		try {
			root = FXMLLoader.load(getClass().getResource("/fxml/SceneEnd.fxml"));
		} catch (IOException e) {
			logger.error("Játék vége ablak betöltése sikertelen: " + e.getMessage());
		}
		Scene scene = new Scene(root);
		scene.getStylesheets().add("/styles/Styles.css");

		stage2.setTitle("Dámajáték");
		stage2.setScene(scene);
		stage2.show();
		logger.info("Játék vége ablak betöltve!");

		stage.close();
		logger.info("Játék (tábla) ablaka bezárva!");
	}
}
