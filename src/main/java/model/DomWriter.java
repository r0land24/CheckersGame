package model;

import static controller.checkers.CheckersLogic.HEIGHT;
import static controller.checkers.CheckersLogic.WIDTH;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import controller.checkers.CheckersLogic;

public class DomWriter {

	public static void domWriter() {

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			Document doc = db.newDocument();

			Element board = doc.createElement("board");
			doc.appendChild(board);

			// Attr neptun = doc.createAttribute("neptun");
			// neptun.setValue("fut642");
			// board.setAttributeNode(neptun);
			
			Element tile = null;
			Element coordinateX = null;
			Element coordinateY = null;
			Element pieceType = null;
			for (int y = 0; y < HEIGHT; y++) {
				for (int x = 0; x < WIDTH; x++) {
					if(CheckersLogic.getBoard()[x][y].hasPiece()) {
						tile = doc.createElement("tile");
						coordinateX = doc.createElement("coordinateX");
						coordinateX.appendChild(
								doc.createTextNode(String.valueOf(CheckersLogic.getBoard()[x][y].getTileCoordinateX())));
						tile.appendChild(coordinateX);

						coordinateY = doc.createElement("coordinateY");
						coordinateY.appendChild(
								doc.createTextNode(String.valueOf(CheckersLogic.getBoard()[x][y].getTileCoordinateY())));
						tile.appendChild(coordinateY);

						pieceType = doc.createElement("pieceType");
						pieceType.appendChild(
								doc.createTextNode(String.valueOf(CheckersLogic.getBoard()[x][y].getPiece().getType())));
						tile.appendChild(pieceType);
						board.appendChild(tile);
					}
				}
			}

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("savedGame.xml"));

			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

			t.transform(source, result);

		} catch (ParserConfigurationException ex) {
			Logger.getLogger(DomWriter.class.getName()).log(Level.SEVERE, null, ex);
		} catch (TransformerConfigurationException ex) {
			Logger.getLogger(DomWriter.class.getName()).log(Level.SEVERE, null, ex);
		} catch (TransformerException ex) {
			Logger.getLogger(DomWriter.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

}
