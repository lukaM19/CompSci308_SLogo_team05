package slogo.controller;

import slogo.model.Model;

import java.util.Collection;
import java.io.File;

/**
 * This class will be the controller for the Save Logo function. It will call the appropriate methods for
 * saving the XML and the jpg
 *
 * @author Luke McSween
 */

public class LogoSaver {

    private LogoXMLSaver xmlsaver;

    public LogoSaver() {
        xmlsaver = new LogoXMLSaver();
    }

    public void saveLogo (Collection<String> commandlist, File file) throws Exception {
        xmlsaver.saveLogoxml(commandlist, file);
    }

    // for testing purposes only
    LogoXMLSaver getXmlsaver() {
        return xmlsaver;
    }
}
