## SLogo API Changes
### Luke, Luka, Ricky, Vincent
### Team 5


## Changes to the Initial APIs

### Backend Internal

* Method changed: setUpParamNumber()
    * Change summary
        * Instead of each command calling a checkParamNumber, it sets up the param number and the command automatically checks it in execute()

    * Why was the change made?
        * The code was getting messy and confusing because the setUpExecution() method had repeated code from all the commands checking the parameters.

    * Major or Minor (how much they affected your team mate's code)
        * Minor: no change at all to how command functioned

    * Better or Worse (and why)
        * Better: code is cleaner as the param setting is offloaded to the constructor.

* Method changed: parse(), canParse(), and many others

    * Why was the change made?
        * Adding new features to the parser was a messy process and required inserting another branch into an if statement in one of several different places.

    * Major or Minor (how much they affected your team mate's code)
        * Minor: this change was completely internal and had no changes on other parts of the program

    * Better or Worse (and why)
        * Better: this makes it much easier to add and test new features of the parser, such as the upcoming changes to allow running a command with multiple sets of parameters. It also split up the code into several different classes rather than having a single class doing everything.


### Backend External

* Method changed: execute()
    * Change summary
        * Method declared final in Command super class
        * Implementation changed to two methods: setUpExecution() and run()
            * Overriden methods

    * Why was the change made?
        * setUpExection() replaces constructor, because needed variables are not available at construction
        * run() replaces execute(). The split allows code to not be duplicated because some commands share execution set up but not run.
    * Major or Minor (how much they affected your team mate's code)
        * minor: model just needs to add world, userVars as parameters to execute()
        * No other changes than above
    * Better or Worse (and why)
        * Better: parser does not need world, userVars as instance variables (removes this coupling)

* Method changed: getActiveActors()
    * Change summary
        * Method that returns List of active actors

    * Why was the change made?
        * Multiple actors can appear on stage, and only some are active

    * Major or Minor (how much they affected your team mate's code)
        * Medium: actor commands needed to be changed to execute commands on all actors
        * Minor: actorID implied parameter removed for actor commands
        * Minor: view needs to actively check actorID to see which actor to update
    * Better or Worse (and why)
        * Better: support for multiple actors

### Frontend External

* Method changed: View.getLanguage()

    * Why was the change made?
        * this getter method was added in order to communicate which resource langauge package should be used for errors in the controller/parser.

    * Major or Minor (how much they affected your team mate's code)
        * Minor - the difference is a getter for the Frontend and a Setter for the Backend (parser)

    * Better or Worse (and why)
        * certainly better than having no communication about the language like we had before, but also worse than passing the language each time Run() is executed. This option was ignored (for now) in order to avoid a JavaFX error we were getting.


* Method changed:`setBackGroundColor`

    * Why was the change made?
        * I thought that using consumers( which I learned about after our original design session.) would be more encapsulated. So now the model will get a consumer from me instead of having a public method.

    * Major or Minor (how much they affected your team mate's code)
        * I don't think it was major since it's conseptually very simmilar to what we were doing before.

    * Better or Worse (and why)
        * Better for sure, because it enforces encapsulation.


### Frontend Internal

* Method changed: Controller.save()

    * Why was the change made?
        * I created a LogoSaver class that handles the different methods that need to be called from LogoXML saver class. So the save() method now calls the LogoSaver.saveLogo() method which handles the details

    * Major or Minor (how much they affected your team mate's code)
        * Minor, I still received the list of strings from the Back End, nothing changed API wise

    * Better or Worse (and why)
        * much better, easier to read the logic of save() and offloads a lot of responsibility



* Method changed:public void setColor()

    * Why was the change made?
        * Decided to use a consumer instead of having a public method. I though it was better for encapsulation.

    * Major or Minor (how much they affected your team mate's code)
        * minor, since the change was easy to implement and it didn't cause disturbance in other packages.

    * Better or Worse (and why)
        * Better, more encapsulated, simple to use.