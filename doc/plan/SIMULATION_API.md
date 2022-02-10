# Cell Society API Lab Discussion
### Luke, Luka, Ricky, Vincent
### Team 5


#Current Simulation API

## External

###Simulation class

* Identified Classes/Methods
  * public Simulation(String type, String title, String author, String description, ...)
  * public void step()
  * public int getStatus(int row, int col)
  * public int getRows()
  * public int getCols()
  * public SimulationDescriptor getDescriptor()
* Goals
  * Provide getters for other classes
  * Allow for animation to move forward (step())
* Contract
  * step() calls update on the grid, causing the simulation to advance
  * getters return requested value
* Services
  * Update the grid one step
  * Receive values (status, rows, cols, descriptor)

###Grid class

* Identified Classes/Methods
  * public Grid(int r, int c, GridBehavior behavior, GridEdge edge)
  * public void updateGrid()
  * public int getRows()
  * public int getColumns()
  * public int getStatus(int row, int col)
* Goals
  * Handle updates to grid 
* Contract
  * Getters return requested value
* Services
  * Create grid given values, behavior, and edge behavior
  * updateGrid() advances grid forward


## Internal

###Grid
* Identified Classes/Methods
  * protected List<List<Cell>> getGrid()
  * protected List<List<Cell>> copyGrid(List<List<Cell>> original)
  * protected List<Cell> findNeighbors(List<List<Cell>> grid, int row, int col)
  * protected abstract void fillGrid(List<List<Integer>> gridValues);
* Goals
  * Subclasses are able to use protected methods
* Contract
  * Find neighbors of a given cell
  * Fills grid given values
* Services
  * Subclasses are able to implement findNeighbors() in different ways
  * Grid can be filled

# Wish Simulation API

## External

###Grid
* Goals
  * Compatible with other types of simulations like falling sand
  * Allowing users to change states/parameters
* Contract
  * Generally same as above
* Services
  * updateGrid gets passed value in case of user input and updates accordingly

## Internal
###Grid
* Goals
  * Handle user input in between animation
  * Use something more abstract than List of Lists
* Contract
  * Same as above
* Services
  * Change grid without using List of Lists