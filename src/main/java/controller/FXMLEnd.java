package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FXMLEnd implements Initializable {

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Stage stage = new Stage();
		String data = ""+stage.getUserData();
		endLabelDark.setVisible(true);
		if (data=="white")
			endLabelWhite.setVisible(true);
		else 
			endLabelDark.setVisible(true);
	}

}
