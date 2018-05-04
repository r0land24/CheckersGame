package controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.services.BoardServicesImpl;
import model.vo.Board;

public class PressedKeys {
	
	public void addSpace(Stage stage) {
		stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
			if (KeyCode.SPACE == event.getCode()) {
				BoardServicesImpl.getInstance().checkEndGame(Board.getBoard());
				if (Board.isAIsTurn()) {
					BoardServicesImpl.getInstance().aImove();
				}
			}
		});
	}
	
	public void addEscape(Stage stage) {
		stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
			if (KeyCode.ESCAPE == event.getCode()) {
				try {
					Stage stage2 = new Stage();
					Parent root = FXMLLoader.load(getClass().getResource("/fxml/SceneSaveGame.fxml"));
					Scene scene = new Scene(root);

					stage2.setTitle("Dámajáték");
					stage2.setScene(scene);
					stage2.setUserData(stage);//////
					stage2.show();
					// stage.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

}
