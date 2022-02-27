package slogo.controller;

import slogo.model.Model;
import java.io.File;
import java.util.Collection;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class LogoXMLSaver {

    private final Model mod;
    private final File file;
    private Document doc;
    private String COMMANDS_TAG = "Commands";
    private String LINE_TAG = "line";

    public LogoXMLSaver(Model mod) {
        this.mod = mod;
    }

    /**
     * This will be the method called by controller that will save logo image and xml config file that can be
     * parsed for load file.
     */
    public void saveLogoxml(Collection<String> commandlist, File file) throws ParserConfigurationException{
        this.file = file;
        doc = createDoc();

        Element commands = doc.createElement(COMMANDS_TAG);
        doc.appendChild(commands);

        addCommands(commands, commandlist);
        writeToFile();
    }

    private void addCommands(Element root, Collection<String> commandlist) {
        for (String s : commandlist) {
            Element newline = doc.createElement(LINE_TAG);
            newline.setTextContent(s);
            root.appendChild(newline);
        }
    }

    private Document createDoc() throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        return db.newDocument();
    }

    private void writeToFile() {

    }
}
