package model;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import controller.checkers.save.SaveLatestBoard;
import view.PieceType;

public class DomReader {

	public static void domReader() {

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			File input = new File("savedGame.xml");

			Document doc = db.parse(input);
			doc.getDocumentElement().normalize();

			NodeList n1 = doc.getElementsByTagName("tile");

			DataFromXML.list.clear(); // Mivel új mentett állás ezért törlöm a régi listát amibe most belerakom.

			for (int i = 0; i < n1.getLength(); i++) {
				Element e = (Element) n1.item(i);

				int coordX = Integer.parseInt(e.getElementsByTagName("coordinateX").item(0).getTextContent());
				int coordY = Integer.parseInt(e.getElementsByTagName("coordinateY").item(0).getTextContent());
				PieceType type = PieceType.valueOf(e.getElementsByTagName("pieceType").item(0).getTextContent());

				DataFromXML.list.add(new TileXMLForm(coordX, coordY, type));
			}
			
			SaveLatestBoard.saveBoard(DataFromXML.list);

		} catch (ParserConfigurationException ex) {
			Logger.getLogger(DomReader.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SAXException ex) {
			Logger.getLogger(DomReader.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(DomReader.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

}
