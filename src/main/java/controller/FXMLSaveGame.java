package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import controller.converter.PieceConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.dom.Dom;
import model.dom.DomImpl;
import model.dto.PieceDto;
import model.services.BoardServicesImpl;
import model.vo.Board;

public class FXMLSaveGame implements Initializable {

	@FXML
	private Button saveButton, backButton;

	@FXML
	private void actionSave(ActionEvent event) {
		try {
			// Olyan korongok lementése xml-be amik a táblán el lettek helyezve
			Dom dom = new DomImpl();
			List<PieceDto> list = new ArrayList<>();
			for (int i = 0; i < BoardServicesImpl.getInstance().pieceList().size(); i++) {
				list.add(PieceConverter.toPieceDto(BoardServicesImpl.getInstance().pieceList().get(i)));
			}
			dom.domWriter(list, Board.isAIsTurn());
			
			Stage stage = (Stage) saveButton.getScene().getWindow();

			Parent root = FXMLLoader.load(getClass().getResource("/fxml/SceneMainMenu.fxml"));
			Scene scene = new Scene(root);

			stage.setTitle("Dámajáték - Menü");
			stage.setScene(scene);
			stage.show();

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
