package model.dom.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.dom.Dom;
import model.dto.PieceDto;

public class DomImpl implements Dom {

	@Override
	public List<PieceDto> domReader() {

		List<PieceDto> list = new ArrayList<>();

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			File input = new File("savedGame.xml");

			Document doc = db.parse(input);
			doc.getDocumentElement().normalize();

			NodeList n1 = doc.getElementsByTagName("tile");

			for (int i = 0; i < n1.getLength(); i++) {
				Element e = (Element) n1.item(i);

				int coordinateX = Integer.parseInt(e.getElementsByTagName("coordinateX").item(0).getTextContent());
				int coordinateY = Integer.parseInt(e.getElementsByTagName("coordinateY").item(0).getTextContent());
				String pieceType = e.getElementsByTagName("pieceType").item(0).getTextContent();

				list.add(new PieceDto(coordinateX, coordinateY, pieceType));
			}

		} catch (ParserConfigurationException ex) {
			Logger.getLogger(DomImpl.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SAXException ex) {
			Logger.getLogger(DomImpl.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(DomImpl.class.getName()).log(Level.SEVERE, null, ex);
		}
		return list;
	}

	@Override
	public void domWriter(List<PieceDto> list) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			Document doc = db.newDocument();

			Element board = doc.createElement("board");
			doc.appendChild(board);

			Element tile = null;
			Element coordinateX = null;
			Element coordinateY = null;
			Element pieceType = null;
			for (int x = 0; x < list.size(); x++) {

				tile = doc.createElement("tile");
				coordinateX = doc.createElement("coordinateX");
				coordinateX.appendChild(doc.createTextNode(String.valueOf(list.get(x).getCoordinateX())));
				tile.appendChild(coordinateX);

				coordinateY = doc.createElement("coordinateY");
				coordinateY.appendChild(doc.createTextNode(String.valueOf(list.get(x).getCoordinateY())));
				tile.appendChild(coordinateY);

				pieceType = doc.createElement("pieceType");
				pieceType.appendChild(doc.createTextNode(String.valueOf(list.get(x).getPieceType())));
				tile.appendChild(pieceType);
				board.appendChild(tile);

			}

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("savedGame.xml"));

			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

			t.transform(source, result);

		} catch (ParserConfigurationException ex) {
			Logger.getLogger(DomImpl.class.getName()).log(Level.SEVERE, null, ex);
		} catch (TransformerConfigurationException ex) {
			Logger.getLogger(DomImpl.class.getName()).log(Level.SEVERE, null, ex);
		} catch (TransformerException ex) {
			Logger.getLogger(DomImpl.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

}
