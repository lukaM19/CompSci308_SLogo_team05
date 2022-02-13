// User wants to save his/her work into a jpg

class Controller {
    private void makeEventHandlers(){
        ...
            saveHandler = event -> save(); // set up event handler that will be activated from somewhere (undecided)
        ...
        }

    private void save() {
            ...
            SaveFile save = new SaveFile();
            save.saveLogo();
        }
}

class SaveFile {
    private final Simulation logo;
    private final File file;
    private final Map<String, String> input;
    private Document doc;

    public void saveLogo(){
        doc = createDoc(); // this method will create the doc using DocumentBuilder or something
        ...
        ChildrenElements = getChildrenElements(); // method will get different elements like logo, description, etc
        ...
        for (elements in Children Elements) {
            doc.appendChild(ChildrenElements[k]); // loop through and get all the elements
        }
        writeToFile(); //write doc to a jpg
    }
}