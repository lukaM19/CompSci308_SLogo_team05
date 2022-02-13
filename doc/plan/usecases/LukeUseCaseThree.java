// User wants to open multiple windows at once

class Main {

    private void addController() {
        Stage newStage = new Stage();
        ... // set up Stage
        newStage.show();
        myControllers.add(new Controller(newStage, ...)) // add the new controller to the list of controllers
    }
    // each controller will then have its own Simulation step functions, parsers, view, etc.
}