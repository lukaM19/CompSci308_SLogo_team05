package slogo.controller;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import java.io.File;
import java.nio.charset.MalformedInputException;

/**
 * This class will handle the load method
 */
public class LogoLoader {

    public LogoLoader() {
        super();
    }

    public void loadLogo(File loadfile) throws Exception{
        Node rootnode = getRoot(loadfile);


    }

    private Node getRoot(File loadfile) throws MalformedXMLException, Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(loadfile);
        Node rootNode = doc.getDocumentElement();
        if (rootNode == null) {
            throw new MalformedXMLException("Invalid XML file");
        }
        return rootNode;
    }
}
