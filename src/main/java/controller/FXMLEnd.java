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

/**
 * Osztály a játé végi ablak kezelésére.
 */
public class FXMLEnd implements Initializable {

	private static Logger logger = LoggerFactory.getLogger(FXMLEnd.class);

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
		} catch (IOException e) {
			logger.error("MainMenu betöltése sikertelen: " + e.getMessage());
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Stage stage = new Stage();
		String winner = "" + stage.getUserData();
		if (winner == "white") {
			endLabelWhite.setVisible(true);
			logger.info("Játék vége, világos nyert");
		} else {
			endLabelDark.setVisible(true);
			logger.info("Játék vége, sötét nyert");
		}
	}

}
