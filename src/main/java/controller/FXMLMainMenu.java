package controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.util.PressedKeys;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.dao.Dom;
import model.services.BoardService;
import model.services.BoardUtilService;
import model.vo.board.Board;

/**
 * Az {@code FXMLMainMenu} osztály a főmenüt kezeli.
 * 
 * @author roland
 */
public class FXMLMainMenu implements Initializable {

	private static Logger logger = LoggerFactory.getLogger(FXMLMainMenu.class);

	@FXML
	private Button newGameButton, loadGameButton;

	@FXML
	private void actionNewGame(ActionEvent event) {
		Stage stage = (Stage) newGameButton.getScene().getWindow();

		BoardService service = BoardService.getInstance(); // példányosít
		Scene scene = new Scene(service.createContent()); // elkészül a tábla feltöltve

		PressedKeys key = new PressedKeys();
//		key.addSpace(stage); // space gombnyomásra akció
		key.addEscape(stage); // escape gombnyomásra akció

		logger.info("Új játék elindult!");

		stage.setTitle("Dámajáték - új játék");
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void actionLoadGame(ActionEvent event) {
		Stage stage = (Stage) loadGameButton.getScene().getWindow();

		// XML adatok feldolgozása
		Dom dom = new Dom();
		BoardService service = BoardService.getInstance();
		BoardUtilService utilsService = BoardUtilService.getInstance();
		utilsService.saveBoard(dom.domPieceReader());
		Board.setAIsTurn(dom.domAiReader());

		Scene scene = new Scene(service.createContent(Board.getSavedBoard()));

		PressedKeys key = new PressedKeys();
//		key.addSpace(stage);
		key.addEscape(stage);

		logger.info("Betöltődött a mentés, játék elindult!");

		stage.setTitle("Dámajáték - mentett játék");
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Dom dom = new Dom();
		if (null == dom.domPieceReader())
			loadGameButton.setDisable(true);
		else
			loadGameButton.setDisable(false);
	}

}
