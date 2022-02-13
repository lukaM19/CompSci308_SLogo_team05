// User wants to load in a previous logo to continue editing it

class Controller {
    private void makeEventHandlers(){
        ...
        loadHandler = event -> load(); // set up event handler that will be activated from somewhere (undecided)
        ...
    }

    private void load() {
            ...
        LoadFile load = new LoadFile();
        save.loadLogo();
    }
}

class LoadFile {
    public void loadFile() {
        ...
        XMLParser parse = new XMLParser();
        parse.parseXML(filename); // feed xml to a separate parser
    }
}