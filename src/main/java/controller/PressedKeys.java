package controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.services.BoardService;
import model.services.BoardUtilsService;
import model.vo.Board;

/**
 * Osztály a billentyűk leütésének kezelésére.
 */
public class PressedKeys {

	private static Logger logger = LoggerFactory.getLogger(PressedKeys.class);

	/**
	 * A space billentyű leütésére körváltás történik.
	 *
	 * @param  stage  az a stage, ami használni fogja a metódust
	 */
	public void addSpace(Stage stage) {
		stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
			if (KeyCode.SPACE == event.getCode()) {
				BoardUtilsService.getInstance().checkEndGame(Board.getBoard());
				if (Board.isAIsTurn()) {
					BoardService.getInstance().aImove();
				}
			}
		});
	}

	/**
	 * Az escape billentyű leütésére felugrik a játék mentése ablak.
	 *
	 * @param  stage  az a stage, ami használni fogja a metódust
	 */
	public void addEscape(Stage stage) {
		stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
			if (KeyCode.ESCAPE == event.getCode()) {
				try {
					Stage stage2 = new Stage();
					Parent root = FXMLLoader.load(getClass().getResource("/fxml/SceneSaveGame.fxml"));
					Scene scene = new Scene(root);
					scene.getStylesheets().add("/styles/Styles.css");

					stage2.setTitle("Mentés");
					stage2.setScene(scene);
					stage2.setUserData(stage); // játék ablak lementése databa
					stage2.show();
					// stage.close();
				} catch (IOException e) {
					logger.error("Mentési ablak betöltése sikertelen: " + e.getMessage());
				}
			}
		});
	}

}
