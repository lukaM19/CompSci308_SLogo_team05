## Refactoring Lab Discussion
### Luke, Luka, Ricky, Vincent
### Team 5


## Issues in Current Code

### Turtle/Actor class
* Only getters/setters
    * Only holds data

### Custom languages
* Command class would need an additional setter method if it were to handle custom languages

* Parser currently has hardcoded language ("English")

### Protected instance variables
* Command has protected instance variables that should be replaced with protected getters



## Refactoring Plan

* What are the code's biggest issues?
    * Currently the language selected is a medium size issue that needs fixing
    * Exception handler should preserve original exception

* Which issues are easy to fix and which are hard?
    * The issues about communicating the language selected and with the errors thrown should be an easy issue to fix.
    * Exception handler issue should be easy to fix by adding different catches

* What are good ways to implement the changes "in place"?
    * Maintaining the API between each class is the best way to implement changes "in place". By respecting the relationships between each class, no huge reconstruction projects must be done to introduce new features to the project.



## Refactoring Work

* Issue chosen: Fix and Alternatives
    * Exception handlers should preserve original exception
    * Fix: add multiple catch statements for different errors thrown

* Issue chosen: Fix and Alternatives
    * Magic numbers
    * Fix: use public static final variables at the top of the class or read in from a file if necessary
