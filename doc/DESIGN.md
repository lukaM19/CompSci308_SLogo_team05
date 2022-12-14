## Names and roles

* Vincent Chen
  * Worked on Command API and modified Model API to accept multiple actors
* Luke McSween 
  * Worked on the Controller API, as well as the save and load features
* Ricky Weerts
  * Worked on the Parser API and the original implementation of the Model API
* Luka Mdivani
    * Worked on all of frontend including API.

## Design goals
* Command API
    * Organized hierarchy and packages
    * Easy to add and test new commands
    * No repetition of code
    * Compatible with reflection so the parser does not need hard-coded cases
* Model API
    * Able to easily support addition of new actor attributes
* Controller API
  * Centralize the functionality of each piece of the project
  * Easy to read code
* Parser API
  * No modification to the parser required to add new commands
  * Modular design for new language features
* View API
    * Organized architecture of all elements
    * Individual classes follow SRP, are clear and easy to understand
    * UI elements are easily extendable,dynamic and customizable.

## High level design
* Command API
    * Everything extends from an abstract Command class
    * Every command overrides and implements methods that set up execution (e.g. calculate movement)
      and then run the given command (e.g. moving the turtle)
    * Parser creates commands with one constructor that takes a list of Command objects (these are
      the parameters for the command)
    * The world (part of model API) executes the command and passes it necessary information about
      the world.
* Controller API
  * Minimize lines in each method, make Controller only orchestrate communication
  * Load and Save files in their own classes with their own hierarchies (only implemented for save)
* Parser API
  * Loads commands from a specified package or according to a given set of rules selecting packages
  * All parser class extend from AbstractParser, making them substitutable (although different parser classes parse different parts of the language)
  * Parser classes can check if a token matches the start of something they can parse
  * Parser classes can then take in a string and parse it, returning a command representing the first complete thing parsed (for example a command and its arguments)
  * Some parser classes take other parsers to use in parsing arguments or list elements
  * SlogoParser itself can parse a string for all of the commands it finds, not just the first one
* Model API
  * Takes in a command and executes it, causing the internal state of the model to update in response to whatever happened. Returns a MoveInfo representing all changes to the turtles.
  * Contains a world with all the actors that exist (leaving the door open for actors that are not our normal turtles)
  * Actors can move around, have an orientation, and are either visible or invisible
  * Turtles are Actors that leave pen trails when the pen is down
* View API
    * Have concise classes and methods which all serve a single purpose are clear and readable.
    * User defined variable/command histories as well as executed command history all extend an
      abstract InfoDisplay.java class.
    * Things like the toolbar, helpwindow and language choice options are extendable without the
      need to add any code
      (by changing property files).

## Assumptions

* Command API
  * Every command has a set number of parameters
    * For example, sum always takes two parameters. The parser handles `(sum 20 20 10 10)` by splitting it into `sum 20 20` and `sum 10 10`.
* Parser API
  * It is possible to determine if a command can be parsed by a parser based on the first token
    * For example, the list parser can parse all commands starting with the `[` token
  * All tokens are separated by whitespace
* View API
    * Info about a move to be visualized is passed as a MoveInfo object, this was a design choice.

## Differences between original and final
* Command API
    * Some looping commands not implemented
    * New commands, such as setpalette and setbackground are untested and not linked with the front
      end.
* Controller API
  * Only syntactical changes between original and final version of controller
* Parser API
  * Added loadCommands to specify where commands are to be loaded from and allow for loading from multiple different packages through repeated calls to this method
  * Added canParse and parseToken to have a unified API across all of the parts of the parser
* View API
    * No major differences, especially in external API.
    * I had to add getters for User variable consumers and language selection as public api, this
      came up during the development process and was not planned.
## Addition of new features
* Command API
  * Adding new classes that implement the looping function and tagged with the appropriate name.
    * For example, repeat would be tagged as a SlogoCommand that takes two parameters and no implied parameters. 
    * The setUpExecution() method for repeat would make sure the appropriate number of parameters are passed (although this is a redundant check because the parser also checks for the correct parameter number)
    * The run() method will do a simple for loop the number of times the first parameter states and run the command given in the body. 
  * setpalette and setbackground
    * Add test cases with JUnit
      * Test incorrect number of parameters passed (sad tests)
      * Test passing correct values (happy test)
        * Should return correct number
        * MoveInfo should contain info regarding what changes in the frontend
    * Link with frontend
      * Talk with frontend about what variables in MoveInfo to look out for background, palette, etc.
* Parser API
  * Adding new features to the slogo parser
    * Create a new class extending AbstractParser and add a new instance of it to the `tokenParsers` list, passing it any other parsers it needs
    * Implement AbstractParser's abstract methods according to what it should parse
* Model API
  * Adding new data that gets returned after executing a command
    * Add it to MoveInfo
    * Make sure any commands editing that data also set it in MoveInfo
* View API
    * Most of the changes which had to do with the view are implemented.
        * If a new turtle id is observed in moveinfo, a new graphical turtle is created with that
          id.
        * ReRun calls the run method on the selected history entry(this is found using a listener).
        * Some of them like color palette setting from command line is implemented but not connected
          to backend because of time limitations, but they work on the front end.
        * Feature which lets us edit user variable, user command is also mostly implemented, I did
          this by having a change listener on list entries, and just re-running the edited line when
          something is changed, like you would do a new command line command .
          * It was very easy to extend color change functions to command line, I just had to assign an index
          to the color options and keep a list of them.
        * When one edits command history entry, the edited version will be moved to the command line
          and user will be able to Run it by hitting the button.
        * Button for predefined movement (left,right up down) were not added, bugt it should be simle.
            * I was thinking of adding the String for corresponding commands to a properties file,
              and associating the button press to feeding that string to the runHandler.
