package view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.dom.Dom;
import model.dom.impl.DomImpl;
import model.dto.PieceDto;
import service.converter.PieceConverter;
import service.impl.CheckersLogic;

public class FXMLSaveGame implements Initializable {

	@FXML
	private Button saveButton, backButton;

	@FXML
	private void actionSave(ActionEvent event) {
		try {
			Stage stage = (Stage) saveButton.getScene().getWindow();

			Parent root = FXMLLoader.load(getClass().getResource("/fxml/SceneMainMenu.fxml"));
			Scene scene = new Scene(root);

			stage.setTitle("Dámajáték - Menü");
			stage.setScene(scene);
			stage.show();

			// Olyan korongok lementése xml-be amik a táblán el lettek helyezve
			Dom dom = new DomImpl();
			List<PieceDto> list = new ArrayList<>();
			for (int i = 0; i < CheckersLogic.getInstance().pieceList().size(); i++) {
				list.add(PieceConverter.toPieceDto(CheckersLogic.getInstance().pieceList().get(i)));
			}
			dom.domWriter(list);

			Stage gameStage = (Stage) stage.getUserData();
			gameStage.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void actionBack(ActionEvent event) {
		Stage stage = (Stage) backButton.getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

}
