package model.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.vo.board.Piece;
import model.vo.board.PieceType;

/**
 * A {@code Dom} osztály az xml fájl írására és olvasására.
 *
 * Az XML állományokat operációs rendszertől függően máshol hozza létre és
 * keresi a "user.home" property alapján. <br>
 * Windows:
 * {@code C:\Users\}felhasználónév{@code \CheckersGame\checkersSavedGame.xml}
 * Linux:
 * {@code /home/}felhasználónév{@code /CheckersGame/checkersSavedGame.xml}.
 *
 */
public class Dom {

	private static Logger logger = LoggerFactory.getLogger(Dom.class);

	/** A felhasználó home könyvtára operációs rendszertől függően. */
	public final String userHome = System.getProperty("user.home");

	private File file = new File(userHome + File.separator + "CheckersGame" + File.separator + "chekersSavedGame.xml");

	/**
	 * A {@code Dom} osztály paraméter nélküli konstruktora.
	 */
	public Dom() {

	}

	/**
	 * Lementi a játék állását az xml fájlba.
	 * 
	 * @param list a korongok listája
	 * @param aisTurn <code>true</code> ha az AI köre következik; 
	 * 		   		  <code>false</code> ha nem
	 * 
	 */
	public void domWriter(List<Piece> list, boolean aisTurn) {

		File dir = new File(userHome + File.separator + "CheckersGame");
		dir.mkdirs();

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			Document doc = db.newDocument();

			Element ai = doc.createElement("aiTurn");
			ai.appendChild(doc.createTextNode(Boolean.toString(aisTurn)));

			Element board = doc.createElement("board");
			doc.appendChild(board);
			board.appendChild(ai);

			Element tile = null;
			Element coordinateX = null;
			Element coordinateY = null;
			Element pieceType = null;
			for (int x = 0; x < list.size(); x++) {

				tile = doc.createElement("tile");
				coordinateX = doc.createElement("coordinateX");
				coordinateX.appendChild(doc.createTextNode(String.valueOf(list.get(x).getCoordX())));
				tile.appendChild(coordinateX);

				coordinateY = doc.createElement("coordinateY");
				coordinateY.appendChild(doc.createTextNode(String.valueOf(list.get(x).getCoordY())));
				tile.appendChild(coordinateY);

				pieceType = doc.createElement("pieceType");
				pieceType.appendChild(doc.createTextNode(String.valueOf(list.get(x).getType())));
				tile.appendChild(pieceType);
				board.appendChild(tile);

			}

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			DOMSource source = new DOMSource(doc);

			StreamResult result = new StreamResult(file);

			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

			t.transform(source, result);

			logger.info("Sikeres mentés az XML fájlba!");

		} catch (ParserConfigurationException ex) {
			logger.error(ex.getMessage());
		} catch (TransformerConfigurationException ex) {
			logger.error(ex.getMessage());
		} catch (TransformerException ex) {
			logger.error(ex.getMessage());
		}

	}

	/**
	 * Kiolvassa az xml fájlból a korongok adatait.
	 * 
	 * @return a korongok listája
	 * 
	 */
	public List<Piece> domPieceReader() {

		if (!file.exists()) { // null esetén a menü kikapcsolja a gombot
			return null;
		}

		List<Piece> list = new ArrayList<>();

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();

			NodeList n1 = doc.getElementsByTagName("tile");

			for (int i = 0; i < n1.getLength(); i++) {
				Element e = (Element) n1.item(i);

				int coordinateX = Integer.parseInt(e.getElementsByTagName("coordinateX").item(0).getTextContent());
				int coordinateY = Integer.parseInt(e.getElementsByTagName("coordinateY").item(0).getTextContent());
				String pieceType = e.getElementsByTagName("pieceType").item(0).getTextContent();

				list.add(new Piece(PieceType.valueOf(pieceType), coordinateX, coordinateY));
			}

		} catch (ParserConfigurationException ex) {
			logger.error(ex.getMessage());
		} catch (SAXException ex) {
			logger.error(ex.getMessage());
		} catch (IOException ex) {
			logger.error(ex.getMessage());
		}
		logger.info("Betöltődtek a korongok az XML fájlból!");
		return list;
	}

	/**
	 * Kiolvassa az xml fájlból, hogy melyik játékos köre következik.
	 *
	 * @return <code>true</code> ha az AI köre következik; 
	 * 		   <code>false</code> ha nem
	 * 
	 */
	public boolean domAiReader() {

		Boolean aiTurn = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();

			NodeList n1 = doc.getElementsByTagName("aiTurn");

			Element aiElement = (Element) n1.item(0);

			boolean ai = Boolean.parseBoolean(aiElement.getTextContent());

			aiTurn = ai;

		} catch (ParserConfigurationException ex) {
			logger.error(ex.getMessage());
		} catch (SAXException ex) {
			logger.error(ex.getMessage());
		} catch (IOException ex) {
			logger.error(ex.getMessage());
		}
		logger.info("Betöltődött az AI státusza az XML fájlból!");
		return aiTurn;
	}

}
