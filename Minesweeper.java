import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


/**
 * This class represents a graphical view of the Minesweeper grid. It
 * has a two-dimensional array of JLabels for the locations, a Ticker
 * object for the count-up timer, and a JLabel to display the current
 * available flags.
 *
 * It implements the MouseListener to apply to each label in the grid
 * so that right-clicking places flags and left-clicking uncovers the
 * location. It also implements the Observer interface so that it can
 * be an observer of the grid. This means it receives the messages 
 * produced by the grid. Those messages are as follows:
 *     row:col:mine
 *     row:col:flag
 *     row:col:unflag
 *     row:col:<hint> (where <hint> is an integer)
 * The main updating of the interface should be done in response to
 * those messages.
 */
public class Minesweeper extends JPanel implements MouseListener, Observer {
    /**
     * These constants can be used as the icons on labels.
     */
    private final ImageIcon FLAG_ICON = new ImageIcon(getClass().getClassLoader().getResource("flag.png"));
    private final ImageIcon MINE_ICON = new ImageIcon(getClass().getClassLoader().getResource("mine.png"));
    
    private Grid grid;
    private int flags;
    private JLabel[][] tile;
    private JLabel flagLabel;
    private Ticker ticker;
    private boolean enabled;
	
	//my variables
	private JButton reset;
	private String template = "cell:%d:%d";
	private String nameIJ;
	private int mines;
        
    /**
     * This constructor should create a new 8-by-8 board with 10 mines.
     */
    public Minesweeper() {
		this(8, 8, 10);

    }
    
    /**
     * This constructor creates a new board of the specified size and
     * mines. The number of initial flags should be the same as the 
     * number of mines. The labels (if covered) should have raised
     * bevel borders and should be size 50-by-50. Ideally, the layout
     * of the interface should look something like the following:
     *
     *     --------------------------------------
     *     |  Flags [ 8 ]       Time [ 00:27 ]  |
     *     |                                    |
     *     |  --- --- --- --- --- --- --- ---   |
     *     | |   |   |   |   | 1 |///|///|///|  |
     *     |  --- --- --- --- --- --- --- ---   |
     *     | |   |   |   | 1 | 2 |///|///|///|  |
     *     |  --- --- --- --- --- --- --- ---   |
     *     | |   |   |   | 1 | % |///|///|///|  |
     *     |  --- --- --- --- --- --- --- ---   |
     *     | |   | 1 | 1 | 2 |///|///|///|///|  |
     *     |  --- --- --- --- --- --- --- ---   |
     *     | |   | 1 | % |///|///|///|///|///|  |
     *     |  --- --- --- --- --- --- --- ---   |
     *     | | 1 | 2 |///|///|///|///|///|///|  |
     *     |  --- --- --- --- --- --- --- ---   |
     *     | |///|///|///|///|///|///|///|///|  |
     *     |  --- --- --- --- --- --- --- ---   |
     *     | |///|///|///|///|///|///|///|///|  |
     *     |  --- --- --- --- --- --- --- ---   |
     *     |                                    |
     *     --------------------------------------
     *
     * Each of the `tile` labels should have their names set
     * to "cell:i:j" where `i` is the row and `j` is the 
     * column. For instance, the 1 in the top row of the 
     * diagram would have the name of "cell:0:4".
     * 
     * The `flagLabel` should have its name set to "flags".
     * 
     * The `enabled` class variable should be initialized
     * to true.
     * 
     * @param width 
     * @param height 
     * @param mines 
     */
    public Minesweeper(int width, int height, int mines) {
		//Class Variables
		grid = new Grid(width, height, mines);
		flags = mines;
		tile = new JLabel[height][width];
		flagLabel = new JLabel("");
		ticker  = new Ticker();
		enabled = true;
		
		reset = new JButton("New Game");
		this.mines = mines;
		
		
		//Local(?) variables
		JPanel panel = new JPanel();
		JPanel widgets = new JPanel();
		JPanel mineField = new JPanel();
		
		//setLayouts	
		panel.setLayout(new BorderLayout());
		widgets.setLayout(new GridLayout(1, 3));
		mineField.setLayout(new GridLayout(height, width));
		
		//add ALL the things
		this.add(panel);
		widgets.add(flagLabel);
		widgets.add(reset);
		widgets.add(ticker);
		panel.add(widgets, BorderLayout.NORTH);
		panel.add(mineField, BorderLayout.PAGE_END);
        grid.addObserver(this);
		
		//do all the non tile widgety things (set name, text, etc...)
		flagLabel.setName("flags");
		flagLabel.setText("" + flags);
		reset.setName("reset");
		reset.addMouseListener(this);
		ticker.setHorizontalAlignment(SwingConstants.RIGHT);


		//for each tile, do the things and ADD them
		for(int i = 0; i < grid.getHeight(); i++){
			for(int j = 0; j < grid.getWidth(); j++){
				nameIJ = String.format(template, i, j);
				System.out.println();
				JLabel t ; 
				t = new JLabel();
				t.setName(nameIJ);
				t.setToolTipText("covered");
				t.setPreferredSize(new Dimension(50, 50));
				t.setBorder(BorderFactory.createRaisedBevelBorder());
				t.setHorizontalTextPosition(JLabel.CENTER);
				t.setVerticalTextPosition(JLabel.CENTER);
				t.setHorizontalAlignment(JLabel.CENTER);
				t.setVerticalAlignment(JLabel.CENTER);
				t.addMouseListener(this);
				tile[i][j] = t;
				mineField.add(tile[i][j]);

			}
		}
    }
    public void reset(){
		// do all the widgety RESET ONLY things
		grid.reset();
		flags = mines;
		flagLabel.setText("" + flags);
		ticker.reset();
		enabled = true;
		
		//for each tile, do the RESET ONLY things 
		for(int i = 0; i < grid.getHeight(); i++){
			for(int j = 0; j < grid.getWidth(); j++){
				nameIJ = String.format(template, i, j);
				System.out.println(); 
				tile[i][j].setToolTipText("covered");
				tile[i][j].setText("");
				tile[i][j].setIcon(null);
				tile[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
			}//perhaps consider next time making a template cell in the beginning.
		}
		
	}
    /**
     * This method should interpret the message from the grid.
     *     String message = (String)arg;
     * It should accomplish this by splitting the message at the 
     * colon delimiters. Depending on the message, the interface 
     * should be updated.
     * 
     * The tooltip text should be updated on the cells to reflect
     * their current state (uncovered, covered, flagged, or mine).
     * 
     * @param o the observable (Grid object)
     * @param arg (the message, which is actually a String)
     */
    public void update(Observable o, Object arg) {
		String s = (String)arg;
        Scanner scanner = new Scanner(s);
        scanner.useDelimiter(":");
        int row = scanner.nextInt();
        int col = scanner.nextInt();
        String info = scanner.next();
		
		//Location l = grid.getLocation(row, col);
		JLabel t = tile[row][col];
		
		if(info.equals("mine")){
			t.setBorder(null);
			t.setToolTipText("mine");
			t.setIcon(MINE_ICON);
		}
		
		if(info.equals("flag")){
			t.setToolTipText("flagged");
			t.setText("");
			t.setIcon(FLAG_ICON);
            flags--;
            flagLabel.setText("" + flags);
		}
		
		if(info.equals("unflag")){
			t.setToolTipText("covered");
			t.setText("");
			t.setBorder(BorderFactory.createRaisedBevelBorder());
			t.setIcon(null);
            flags++;
            flagLabel.setText("" + flags);
		}
		
		if(info.matches(".*\\d.*")) {
			t.setBorder(null);
			t.setToolTipText("uncovered");
			if(!info.equals("0") && !info.equals("mine")) t.setText(info);
		tile[row][col] = t;
		}
		if(grid.getResult() != Grid.Result.NONE){
			ticker.stop();
			enabled = false;
			JFrame frame = new JFrame("RESULTS");
			if(grid.getResult() == Grid.Result.WIN) JOptionPane.showMessageDialog(frame, "YOU WIN!\n Elapsed Time: " + ticker.getTime());
			if(grid.getResult() == Grid.Result.LOSE) JOptionPane.showMessageDialog(frame, "GAME OVER");
			for(int i = 0; i < grid.getHeight(); i++){
				for(int j = 0; j < grid.getWidth(); j++){
					if(grid.getLocation(i, j).hasMine() && !grid.isFlagAt(i, j)) tile[i][j].setIcon(MINE_ICON);
				}
			}
		}
	}


    /**
     * This method shoud handle the left- and right-clicks on the
     * labels. Remember that the very first time that a label is 
     * clicked (left or right), the count-up timer should start.
     * 
     * @param event the clicking mouse event
     */
    public void mouseClicked(MouseEvent event) {
		
		Component c = (Component)event.getSource();
		
		
		if(c.getName().equals("reset")) {
			grid.reset();
			reset();
		}	
		if(enabled) {
			if(!ticker.isRunning() && !c.getName().equals("reset")) ticker.start();
				
			Point p = findSourceIndex(event);
			
			if(event.getButton() == MouseEvent.BUTTON1) grid.uncoverAt(p.x, p.y);
			
			if(event.getButton() == MouseEvent.BUTTON3) {
				if(grid.getLocation(p.x, p.y).getType() == Location.Type.COVERED && flags > 0)grid.placeFlagAt(p.x, p.y);
				else if(grid.getLocation(p.x, p.y).getType() == Location.Type.FLAGGED)grid.removeFlagAt(p.x, p.y);
			}
		}	

    }   
    
    /**
     * This is a private convenience method that returns the point
     * (x and y) representing the row and column of the label that
     * was the source of the event. It should return null if no such
     * label can be found (which should not be able to happen, but
     * the compiler demands a return in all cases).
     * 
     * @param event the mouse clicked event on a label
     * @return the point (x is row, y is column) of the label
     */
    private Point findSourceIndex(MouseEvent event) {
        Point p = new Point(-1, -1);
		Component c = (Component)event.getSource();
		if(c.getName().startsWith("cell")) {
			String s = c.getName();
			Scanner scanner = new Scanner(s);
			scanner.useDelimiter(":");
			scanner.next();
			p.x = scanner.nextInt();
			p.y = scanner.nextInt();
		}
		return p;
    }
    
    
    /**
     * These methods do not need true implementations.
     */
    public void mousePressed(MouseEvent event) {}
    public void mouseReleased(MouseEvent event) {}
    public void mouseEntered(MouseEvent event) {}
    public void mouseExited(MouseEvent event) {}
}