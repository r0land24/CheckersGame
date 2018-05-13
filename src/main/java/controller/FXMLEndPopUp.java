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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.vo.board.Board;

/**
 * Az {@code FXMLEndPopUp} osztály a játék végén felugró ablakot kezeli.
 * 
 * @author roland
 */
public class FXMLEndPopUp implements Initializable {

	private static Logger logger = LoggerFactory.getLogger(FXMLEndPopUp.class);

	@FXML
	private Button endButton;

	@FXML
	private Label endLabelWhite, endLabelDark;

	@FXML
	private void actionEnd(ActionEvent event) {
		try {
			Stage stage = (Stage) endButton.getScene().getWindow();

			Parent root = FXMLLoader.load(getClass().getResource("/fxml/SceneMainMenu.fxml"));
			Scene scene = new Scene(root);

			stage.setTitle("Dámajáték - Menü");
			stage.setScene(scene);
			stage.show();
			logger.info("Főmenü betöltve!");
		} catch (IOException e) {
			logger.error("Főmenü betöltése sikertelen: " + e.getMessage());
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		String winner = Board.getWinner();
		if (winner.contains("white")) {
			endLabelWhite.setVisible(true);
			logger.info("Világos nyert!");
		} else if (winner.contains("dark")) {
			endLabelDark.setVisible(true);
			logger.info("Sötét nyert!");
		}
	}

}
