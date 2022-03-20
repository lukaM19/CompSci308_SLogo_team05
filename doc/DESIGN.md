##Names and roles
* Vincent Chen
  * Worked on Command API and modified Model API to accept multiple actors
* Luke McSween 
  * Worked on the Controller API, as well as the save and load features
##Design goals 
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
##High level design
* Command API
  * Everything extends from an abstract Command class
  * Every command overrides and implements methods that set up execution (e.g. calculate movement) and then run the given command (e.g. moving the turtle)
  * Parser creates commands with one constructor that takes a list of Command objects (these are the parameters for the command)
  * The world (part of model API) executes the command and passes it necessary information about the world.
* Controller API
  * Minimize lines in each method, make Controller only orchestrate communication
  * Load and Save files in their own classes with their own hierarchies (only implemented for save)
  
## Assumptions
* Command API
  * Every command has a set number of parameters
    * For example, sum always takes two parameters. The parser handles `(sum 20 10 10)` by splitting it into `sum 20 20` and `sum 10 10`.

## Differences between original and final 
* Command API
  * Some looping commands not implemented
  * New commands, such as setpalette and setbackground are untested and not linked with the front end.
* Controller API
  * Only syntactical changes between original and final version of controller
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
      * Talk with frontend about what variables in MoveInfo to look out for for background, palette, etc.

