package slogo.controller;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import java.io.File;
import java.nio.charset.MalformedInputException;
import java.util.Arrays;
import java.util.Collection;
import java.util.ArrayList;

/**
 * This class will handle the load method
 */
public class LogoLoader {

    public LogoLoader() {
        super();
    }
    /**
     * This method loads in a logo from a previously created xml file.
     */

    public void loadLogo(File loadfile) throws Exception{
        Node rootnode = getRoot(loadfile);

        Collection<String> commands = getCommands(rootnode);
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

    private Collection<String> getCommands(Node listnode) throws MalformedXMLException {
        if (!listnode.hasChildNodes()) {
            throw new MalformedXMLException("No Command lines in file");
        }
        ArrayList<String> commands = new ArrayList<>();
        NodeList list = listnode.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node subnode = list.item(i);
            commands.add(subnode.getTextContent());
        }
        return commands;
    }
}
