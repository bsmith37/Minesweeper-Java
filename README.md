# Minesweeper-Java
Recreate Minesweeper in Java
This was a project for CS232 - Computer Programming II.

The goal of Minesweeper is to uncover all the boxes in the grid without uncovering a mine. Flags should be placed over mines.
If a mine is uncovered, the game is lost.

Ticker extends JLabel and implements ActionListener. Ticker is constructed by a Timer, an int representing seconds passed since
the ticker started, and a boolean to for checking if the Ticker is still running. Ticker counts up by 1 for every second that
passes. The Ticker can be started, stopped, and reset by using: start(), stop(), and reset() respectively. Ticker and also
check if it is running and get time by using: isRunning() and getTime() respectively. Ticker will keep track of how long the
current game of Minesweeper has been going on for.

Location contains an enum Type which can be: COVERED, UNCOVERED, or FLAGGED; a boolean mine to signal if the Location contains
a mine; and a hint which shows the number of adjacent mines in the Grid. Location can be reset by using reset().

Grid extends Observable. Grid is a two-dimensional array of Locations. Grid contains an enum Result for the status of the
current game -- the Results are NONE, WIN, and LOSE. Grid also keeps up with the number of mines. The default Grid is an 8x8
grid with 10 mines. Grid will construct the array of Location arrays, place the mines in the arrays at random, and set the
hints for each Location. Grid can be reset using reset(). Grid also handles placing flags in a Location and uncovering the
covered Locations once the Location is clicked.

Minesweeper extends JPanel and implements MouseListener and Observer. Minesweeper contains a Grid, an int for number of
available flags, a JLabel tile for each Location in Grid using an array of JLabel arrays, a JLabel to show the int for flags,
and a Ticker. Minesweeper loads in the images used for flags and mines. Minesweeper initializes a Grid (default: 8x8 with 10
mines), sets the number of flags to the number of mines, initializes all the JLabels and Ticker, and uses all of those to build
the JPanel where the game will be played. The constructed Minesweeper should look like the example below. Minesweeper will
register all left mouse clicks as the user uncovering a Location and all right mouse clicks as the user flagging a location.
        Example of the Minesweeper window:

        --------------------------------------
        |  Flags [ 8 ]       Time [ 00:27 ]  |          Remaining flags should appear in top left.
        |                                    |          Time elapsed should appear in top right.
        |  --- --- --- --- --- --- --- ---   |
        | |   |   |   |   | 1 |///|///|///|  |
        |  --- --- --- --- --- --- --- ---   |
        | |   |   |   | 1 | 2 |///|///|///|  |          Blank tiles should be uncovered.
        |  --- --- --- --- --- --- --- ---   |          Shaded tiles should be covered.
        | |   |   |   | 1 | % |///|///|///|  |          Number in tiles are the hints of uncovered tiles/Locations.
        |  --- --- --- --- --- --- --- ---   |          % are mines. Each tile should list the number of neighboring mines.
        | |   | 1 | 1 | 2 |///|///|///|///|  |
        |  --- --- --- --- --- --- --- ---   |
        | |   | 1 | % |///|///|///|///|///|  |
        |  --- --- --- --- --- --- --- ---   |
        | | 1 | 2 |///|///|///|///|///|///|  |
        |  --- --- --- --- --- --- --- ---   |
        | |///|///|///|///|///|///|///|///|  |
        |  --- --- --- --- --- --- --- ---   |
        | |///|///|///|///|///|///|///|///|  |
        |  --- --- --- --- --- --- --- ---   |
        |                                    |
        --------------------------------------

Main simply sets up Minesweeper with the default arguments and makes the window visible as well as closable.


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
