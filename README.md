# Minesweeper-Java
Recreate Minesweeper in Java
This was a project for CS232 - Computer Programming II.

I have described each class in the order of lowest-level to highest-level along with the methods.

Ticker - This class is a count-up timer represented as minutes and seconds.
        start() - starts the Ticker.
        stop() - stops the Ticker.
        reset() - resets the Ticker to 00:00.
        isRunning() - returns if the Ticker has been started.
        getTime() - returns the value of Tickers in minutes and seconds as a String.
        actionPerformed(ActionEvent event) - increment seconds and update the text in the Ticker JLabel.

Location - This class represents a specific location on the Minesweeper grid. Also, contains the infomation: enum Type, mine, hint.
        getType() - returns if Location is Covered, Uncovered, or Flagged.
        hasMine() - returns if Location contains a mine.
        getHint() - returns number of nearby mines.
        reset() - reset the Location to it's default state.
        setters for each getter.

Grid - This class is a scalable two-dimensional array of Locations (default size is 8x8 with 10 mines).
        reset() - set all Locations to Uncovered and place new mines and hints.
        placeMines() - place mines randomly in Grid.
        placeHints() - place hints randomly in Grid.
        getNeighbor(int row, int col) - returns a List of nearby Locations to passed coordinates.
        isLegalIndex(int row, int col) - checks whether coordinates are in Grid.
        calculateHint(List<Location> neighbors) - returns number of mines in neighboring Locations.
        getResult() - checks if the game has been won.
        getMines() - returns number of mines in Grid.
        isFlagAt(int row, int col) - checks if flag is at passed coordinates.
        placeFlagAt(int row, int col) - places flag at passed coordinates.
        removeFlagAt(int row, int col) - removes flag at passed coordinates.
        uncoverAt(int row, int col) - sets Location at passed coordinates to Uncovered and checks for mine.
        isCovered(int row, int col) - checks passed coordinates if Location is set to Covered.
        isFlagged(int row, int col) - checks passed coordinates if Location is set to Flagged.
        getters and setters for Width, Height, and Location.
  
Minesweeper - This class is the GUI view of the Grid.
        reset() - resets the Grid and the GUI elements.
        update() - updates Grid and GUI depending on where user clicked.
        MouseClicked(MouseEvent event) - determines which action the user performed on the Location.
        findSourceIndex(MouseEvent event) - determines the location of the user's click.
        mousePressed(MouseEvent event) - unused.
        mouseReleased(MouseEvent event) - unused.
        mouseEntered(MouseEvent event) - unused.
        mouseExited(MouseEvent event) - unused.

Main - This class sets up Grid and Minesweeper.
