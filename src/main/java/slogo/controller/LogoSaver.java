package slogo.controller;

import slogo.model.Model;
import java.io.File;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class LogoSaver {

    private final Model mod;
    private final File file;

    public LogoSaver(Model mod, File file) {
        this.mod = mod;
        this.file = file;
    }

}
