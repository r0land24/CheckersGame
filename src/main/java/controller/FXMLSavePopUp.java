package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.dao.Dom;
import model.services.BoardUtilService;
import model.vo.Board;

/**
 * Az {@code FXMLSavePopUp} osztály a felugró játék mentése ablakot kezeli.
 * 
 * @author roland
 */
public class FXMLSavePopUp implements Initializable {

	private static Logger logger = LoggerFactory.getLogger(FXMLSavePopUp.class);

	@FXML
	private Button saveButton, backButton;

	@FXML
	private void actionSave(ActionEvent event) {
		try {
			// XML feltöltése a konrongok típusával, helyzetével és hogy kinek a köre van épp
			Dom dom = new Dom();
			dom.domWriter(BoardUtilService.getInstance().pieceList(), Board.isAIsTurn());

			Stage stage = (Stage) saveButton.getScene().getWindow();

			Parent root = FXMLLoader.load(getClass().getResource("/fxml/SceneMainMenu.fxml"));
			Scene scene = new Scene(root);

			stage.setTitle("Dámajáték - Menü");
			stage.setScene(scene);
			stage.show();
			logger.info("Főmenü betöltve!");

			Stage gameStage = (Stage) stage.getUserData(); // a játék stage-e
			gameStage.close();

		} catch (IOException e) {
			logger.error("Főmenü betöltése sikertelen: " + e.getMessage());
		}
	}

	@FXML
	private void actionBack(ActionEvent event) {
		Stage stage = (Stage) backButton.getScene().getWindow();
		stage.close();
		logger.info("Visszalépés a játékba!");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

}
