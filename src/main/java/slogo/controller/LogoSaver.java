package slogo.controller;

import slogo.model.Model;
import java.io.File;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class LogoSaver {

    private final Model mod;
    private final File file;
    private Document doc;

    public LogoSaver(Model mod, File file, Map<String, String> input) {
        this.mod = mod;
        this.file = file;
    }

    /**
     * This will be the method called by controller that will save logo image and xml config file that can be
     * parsed for load file.
     */
    public void saveLogo() {

    }

    private Document createDoc() throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        return db.newDocument();
    }

    private void addToRoot(Element root, String name, String content) {
        return;
    }

    private void writeToFile() {

    }
}
