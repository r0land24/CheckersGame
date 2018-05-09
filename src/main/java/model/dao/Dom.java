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

import model.vo.Piece;
import model.vo.PieceType;

/**
 * Osztály az xml fájlokba való írásra.
 *
 * Az XML állományokat operációs rendszertől függően máshol keresi. Windows:
 * {@code C:\Users\}felhasználónév{@code \Documents\savedGame.xml} Linux:
 * {@code /home/}felhasználónév{@code /savedGame.xml}.
 *
 * @author roland
 */
public class Dom {

	private static Logger logger = LoggerFactory.getLogger(Dom.class);

	/** Az operációs rendszer neve. */

	public final String osName = System.getProperty("os.name").toLowerCase();

	/** Az felhasználói fiók neve. */
	public final String userName = System.getProperty("user.name");

	/** Az xml fájl elérési helye Windows operációs rendszeren. */
	public final String windowsFilePath = "C:" + File.separator + "Users" + File.separator + userName + File.separator
			+ "Documents" + File.separator + "savedGame.xml";

	/** Az xml fájl elérési helye Linux operációs rendszeren. */
	public final String linuxFilePath = File.separator + "home" + File.separator + userName + File.separator
			+ "savedGame.xml";

	private File file = null;

	/**
	 * Az osztály konstruktora, fájl elérési útvonalát adja vissza különböző
	 * operációs rendszerek esetén.
	 */
	public Dom() {
		if (osName.contains("windows")) {
			this.file = new File(windowsFilePath);
		} else if (osName.contains("linux") || osName.contains("unix")) {
			this.file = new File(linuxFilePath);
		}
	}

	/**
	 * Az xml fájlban lévő korongok adatainak kiolvasása.
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
		return list;
	}

	/**
	 * A játék állásának lementése xml fájlba.
	 * 
	 * @param list
	 *            a korongok listája
	 * @param aisTurn
	 *            megadja, hogy az adott körben az AI következik vagy sem
	 * 
	 */
	public void domWriter(List<Piece> list, boolean aisTurn) {
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

		} catch (ParserConfigurationException ex) {
			logger.error(ex.getMessage());
		} catch (TransformerConfigurationException ex) {
			logger.error(ex.getMessage());
		} catch (TransformerException ex) {
			logger.error(ex.getMessage());
		}

	}

	/**
	 * Az xml fájlban lévő körváltásra vonatkozó adat kiolvasása.
	 *
	 * @return megadja, hogy az AI lép következő körben vagy sem
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
		return aiTurn;
	}

}
