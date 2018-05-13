package controller.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * {@code PressedKeys} osztály a különböző billentyűk leütésének kezelésére.
 * 
 * @author roland
 */
public class PressedKeys {

	private static Logger logger = LoggerFactory.getLogger(PressedKeys.class);

//	!! Az AI automatikus lépésének bevezetése után ez a funkció fölöslegessé vált, de azért itt marad szemléltetésnek !!
//	/**
//	 * A space billentyű leütésére a másik játékos léphet, amennyiben már volt
//	 * helyes lépés.
//	 *
//	 * @param stage amin élni fog ez a funkció
//	 */
//	public void addSpace(Stage stage) {
//		stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
//			if (KeyCode.SPACE == event.getCode()) {
//				logger.info("SPACE gomb lenyomva!");
//				BoardUtilService.getInstance().checkEndGame(Board.getBoard(), false);
//				if (Board.isAIsTurn()) {
//					BoardService.getInstance().aImove();
//				}
//			}
//		});
//	}

	/**
	 * Az escape billentyű leütésére felugrik a játék mentése ablak.
	 *
	 * @param stage amin élni fog ez a funkció
	 */
	public void addEscape(Stage stage) {
		SavePopUpLoader savePopUpLoader = new SavePopUpLoader();
		stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
			if (KeyCode.ESCAPE == event.getCode()) {
				logger.info("ESCAPE gomb lenyomva!");
				savePopUpLoader.createSavePopUp(stage);
			}
		});
	}

}
