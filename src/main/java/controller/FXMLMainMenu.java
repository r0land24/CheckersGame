package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.converter.PieceConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.dom.Dom;
import model.dom.DomImpl;
import model.services.BoardServicesImpl;
import model.vo.Board;
import model.vo.Piece;

public class FXMLMainMenu implements Initializable {

	/**
	 * Logger változó.
	 */
	final static Logger logger = LoggerFactory.getLogger(FXMLMainMenu.class);

	@FXML
	private Button newGameButton, loadGameButton;

	@FXML
	private void actionNewGame(ActionEvent event) {
		Stage stage = (Stage) newGameButton.getScene().getWindow();

		BoardServicesImpl checkers = BoardServicesImpl.getInstance(); // példányosít
		Scene scene = new Scene(checkers.createContent()); // elkészül a tábla feltöltve
		
		PressedKeys key = new PressedKeys();
		key.addSpace(stage); // space gombnyomásra akció
		key.addEscape(stage); // escape gombnyomásra akció

		logger.info("Új játék elindult");

		stage.setTitle("Dámajáték - új játék");
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void actionLoadGame(ActionEvent event) {
		Stage stage = (Stage) loadGameButton.getScene().getWindow();

		// Beolvasás XML-ből
		Dom dom = new DomImpl();
		List<Piece> list = new ArrayList<>();
		for (int i = 0; i < dom.domPieceReader().size(); i++) {
			list.add(PieceConverter.toPieceVo(dom.domPieceReader().get(i)));
		}
		
		BoardServicesImpl checkers = BoardServicesImpl.getInstance();
		
		checkers.saveBoard(list);
//		BoardServicesImpl.getInstance().saveBoard(list);
		Board.setAIsTurn(dom.domAiReader());
//		BoardServicesImpl.ai = dom.domAiReader();

		Scene scene = new Scene(checkers.createContent(Board.getSavedBoard()));
//		checkers.addEscape(stage);
		
		PressedKeys key = new PressedKeys();
		key.addSpace(stage);
		key.addEscape(stage);
//		checkers.addSpace(stage);

		stage.setTitle("Dámajáték - mentett játék");
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Dom dom = new DomImpl();
		if (null == dom.domPieceReader())
			loadGameButton.setDisable(true);
		else
			loadGameButton.setDisable(false);
	}

}
