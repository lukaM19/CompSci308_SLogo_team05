# SLogo API Design Lab Discussion
### Luke, Luka, Ricky, Vincent
### Team 5


## Planning Questions

* What behaviors (methods) should the Turtle have and what service should it provide?
    * setup() - setups turtle's initial position, image, etc.
    * move() - moves the turtle 
* When does parsing need to take place and what does it need to start properly?
    * Parsing starts when the user presses enter
    * It needs text, variables, commands that exist
* What is the result of parsing (not the details of the algorithm) and who receives it?
  * The parsing returns a method which the controller receives and deals with 
* When are errors detected and how are they reported?
  * Errors are detected in the controller
  * View displays errors to the user
* What do different commands need to know, when do they know it, and how do they get it?
  * Needs to know state of turtle, parameters passed
  * Everything is passed by the controller when the user presses enter
* What behaviors does the result of a command need to have to be used by the View?
  * The result of a command has to update the turtle state in some way
  * Returns an object with methods that can be called
  * Has to contain output to the console
* How is the View updated after a command has completed execution?
  * Controller takes output from model and applies it to view
* What value would Controller(s) have in mediating between the Model and View?
  * Controller would receive user input
  * Controller tells model how to update
  * Controller takes model update and tells view how to update

## APIs

### Model/Backend External API

* Goals
  * Take in command inputs and execute them
  * Move the turtle appropriately
  * Extendable to new functionality (commands, etc.)
  * Closed to direct modification
  * Liskov substitution for subclasses 
* Contract
  * Those using the API should expect the turtle's state to properly update
  * The command API should respond with useful results and error messages
* Services
  * Provides a way to update the turtle's state

### View/Frontend External API

* Goals
  * Takes command from the controller 
  * Update turtle and pen drawings on screen
  * update the "command line" with respective messages
* Contract
  * Those using the API should expect the view to be properly updated after an inputted command
* Services
  * Provides a way to display model updates 


### Model/Backend Internal API

* Goals
  * Turtle provides methods to let commands change its state
  * A way to extend turtle functionality
  * Easy way to add more commands
* Contract
  * Properly handles relations between turtle and command
* Services
  * Provides a way for the turtle and command interface 
  * Provides ways to extend functionality

### View/Frontend Internal API

* Goals
  * View handles buttons, text input
  * Separation of generic view functionality and specific features
* Contract
  * Provided values from button, text input are passed as useful values for backend
* Services
  * Provides a way for buttons, text input to talk 


## Design

### Backend Design CRCs

This class's purpose or value is to represent a customer's order:
![Order Class CRC Card](order_crc_card.png "Order Class")

This class's purpose or value is to represent a customer's order:

|Order| |
|---|---|
|boolean isInStock(OrderLine)         |OrderLine|
|double getTotalPrice(OrderLine)      |Customer|
|boolean isValidPayment (Customer)    | |
|void deliverTo (OrderLine, Customer) | |

This class's purpose or value is to represent a customer's order:
```java
public class Order {
     // returns whether or not the given items are available to order
     public boolean isInStock (OrderLine items)
     // sums the price of all the given items
     public double getTotalPrice (OrderLine items)
     // returns whether or not the customer's payment is valid
     public boolean isValidPayment (Customer customer)
     // dispatches the items to be ordered to the customer's selected address
     public void deliverTo (OrderLine items, Customer customer)
 }
 ```

This class's purpose or value is to manage something:
```java
public class Something {
     // sums the numbers in the given data
     public int getTotal (Collection<Integer> data)
	 // creates an order from the given data
     public Order makeOrder (String structuredData)
 }
```


### Frontend Design CRCs


This class's purpose or value is to represent a customer's order:
![Order Class CRC Card](order_crc_card.png "Order Class")

This class's purpose or value is to represent a customer's order:

|Order| |
|---|---|
|boolean isInStock(OrderLine)         |OrderLine|
|double getTotalPrice(OrderLine)      |Customer|
|boolean isValidPayment (Customer)    | |
|void deliverTo (OrderLine, Customer) | |

This class's purpose or value is to represent a customer's order:
```java
public class Order {
     // returns whether or not the given items are available to order
     public boolean isInStock (OrderLine items)
     // sums the price of all the given items
     public double getTotalPrice (OrderLine items)
     // returns whether or not the customer's payment is valid
     public boolean isValidPayment (Customer customer)
     // dispatches the items to be ordered to the customer's selected address
     public void deliverTo (OrderLine items, Customer customer)
 }
 ```

This class's purpose or value is to manage something:
```java
public class Something {
     // sums the numbers in the given data
     public int getTotal (Collection<Integer> data)
	 // creates an order from the given data
     public Order makeOrder (String structuredData)
 }
```



### Use Cases

* The user types 'fd 50' in the command window, sees the turtle move in the display window leaving a trail, and has the command added to the environment's history.

* The user types '50 fd' in the command window and sees an error message that the command was not formatted correctly.

* The user types 'pu fd 50 pd fd 50' in the command window and sees the turtle move twice (once without a trail and once with a trail).

* The user changes the color of the environment's background.
