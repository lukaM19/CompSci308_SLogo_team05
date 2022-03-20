SLogo
====

This project implements a development environment that helps users write programs to draw using a
turtle.

Names:
Luka Mdivani, Ricky Weerts, Luke McSween

### Timeline

Start Date: Feb 10

Finish Date: Mar 4

Hours Spent:
Luka- 45hrs+
Ricky- 45hrs
Luke- 25hrs

### Primary Roles

* **Luka**-
    * I worked on all of Front-end. Built everything, designed internal communications and API, as
      well as designed how and provided external api for communication with controller. I also wrote
      the tests for View.
    * wrote all the properties and css files in resource/slogo/view(Used in frontend)
* **Ricky**-
    * I worked on the Parser and the internal representation for the state of the turtles (what we called the Model). I wrote tests for both of these things.
* **Luke**
  * I worked on the Controller for this project, making sure each piece of the project communicated with each other properly.
  * Also worked on the Load and Save features.

### Resources Used

* JavaFX doc
* Stackoverflow
* Mentor questions

### Running the Program

Main class:

To run the program, run the Main class. This class simply makes the Controller object.

Data files needed:

No additional data files are needed to run the program. A sample 
XML file has been provided in the data/XMLfiles folder to facilitate the testing of the load/save features.

Features implemented:

* Front-end:
    * Animated turtle movement, pause able animation, set custom speed.
    * 3 languages for gui
    * change user variables from ui
    * Dark/Light mode
    * help window,error window
    * Select a turtle by clicking it, change pen color/design.
    * clear individual trails of turtles.
    * reRun from command history
    * completely dynamically created toolbar and help window.
* Parser:
    * Support for parsing commands, lists, constants, user variables, user-defined commands, comments, and that syntax using parentheses that we called "unlimited parameters" internally
    * The package to load commands from is configurable
* Model:
    * Stores lines drawn by turtles
    * Supports multiple actors (which are all turtles in this case but don't have to be)
    * Stores user variables
* Controller 
  * Catches and throws all errors 
  * Save feature 
  * load feature
  * New Window button (method in Main class)

### Notes/Assumptions

Assumptions or Simplifications:

Interesting data files:

* Front-end : you can add items to the toolbar just by editing the ToolBarElements.properties file.

Known Bugs:

  * Commands with Multiple Turtles sometimes don't work
  * User defined variables

Noteworthy Features:

### Impressions

* Luka-
    * It was a very hard project. But I think I personally learned a lot and got the opportunity to
      practice the new topics learned in class. I think I did some of the best "architecture" design
      I have ever done in my life. But the project was still very challenging. learned more about
      how javaFX works as well.
* Ricky-
    * The hardest part of the project was the scale rather than the actual architecture design. The design was something I could enjoy planning a little but having to implement multiple iterations of that (each with hundreds of lines of code) on a small time-scale was draining and made it difficult to find motivation when the deadline was near. Testing was nice as it made sure the project held together even as everyone ran out of energy to do more work.

* Luke-
  * I found that most of my work was put into researching how to make the controller work smoothly, especially with the save and load features. 
  Once The hardest part I found was actually communicating between the different parts of the project to make sure the correct objects were being passed. 