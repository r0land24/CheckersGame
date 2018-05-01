package view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.dom.Dom;
import model.dom.impl.DomImpl;
import service.board.Board;
import service.converter.PieceConverter;
import service.impl.BoardServicesImpl;
import service.vo.PieceVo;

public class FXMLMainMenu implements Initializable {

	@FXML
	private Button newGameButton, loadGameButton;

	@FXML
	private void actionNewGame(ActionEvent event) {
		Stage stage = (Stage) newGameButton.getScene().getWindow();

		BoardServicesImpl checkers = BoardServicesImpl.getInstance();
		Scene scene = new Scene(checkers.createContent());
		checkers.addEscape(stage);
		checkers.addSpace(stage);

		stage.setTitle("Dámajáték (új játék)");
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void actionLoadGame(ActionEvent event) {
		Stage stage = (Stage) loadGameButton.getScene().getWindow();

		BoardServicesImpl checkers = BoardServicesImpl.getInstance();

		// Beolvasás XML-ből
		Dom dom = new DomImpl();
		List<PieceVo> list = new ArrayList<>();
		for (int i = 0; i < dom.domReader().size(); i++) {
			list.add(PieceConverter.toPieceVo(dom.domReader().get(i)));
		}
		BoardServicesImpl.getInstance().saveBoard(list);
		System.out.println("WORKS YET");
		BoardServicesImpl.ai = dom.domAiReader();

		Scene scene = new Scene(checkers.createContent(Board.getSavedBoard()));
		checkers.addEscape(stage);
		checkers.addSpace(stage);

		stage.setTitle("Dámajáték (legutóbbi játék)");
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Dom dom = new DomImpl();
		if (null == dom.domReader())
			loadGameButton.setDisable(true);
		else
			loadGameButton.setDisable(false);
	}

}
