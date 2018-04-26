package controller.menu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import controller.checkers.CheckersLogic;
import controller.checkers.save.SaveLatestBoard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.DomReader;

public class FXMLMainMenu implements Initializable {

	@FXML
	private Button newGameButton, loadGameButton, rankButton;

	@FXML
	private void actionNewGame(ActionEvent event) {
		Stage stage = (Stage) newGameButton.getScene().getWindow();

		CheckersLogic checkers = CheckersLogic.getInstance();
		Scene scene = new Scene(checkers.createContent());
		checkers.addEscape(stage);

		stage.setTitle("Dámajáték (új játék)");
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void actionLoadGame(ActionEvent event) {
		Stage stage = (Stage) loadGameButton.getScene().getWindow();

		CheckersLogic checkers = CheckersLogic.getInstance();
		DomReader.domReader(); // beolvassuk xml-ből
		Scene scene = new Scene(checkers.createContent(SaveLatestBoard.getSavedBoard()));
		checkers.addEscape(stage);

		stage.setTitle("Dámajáték (legutóbbi játék)");
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void actionRank(ActionEvent event) {
		try {
			Stage stage = (Stage) rankButton.getScene().getWindow();

			Parent root = FXMLLoader.load(getClass().getResource("/fxml/SceneRank.fxml"));
			Scene scene = new Scene(root);

			stage.setTitle("Dámajáték ranglista");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

}
