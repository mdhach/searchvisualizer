import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Main grid GUI for search visualization.
 * 
 */
@SuppressWarnings("serial")
public class GridPanel extends JPanel {

	// declare enums
	private Enums.GridAction action;
	private Enums.NodeType nodeType;
	private Enums.MouseInputType mouse;
	private Enums.SearchType searchType;
	
	// JPanel and grid dimensions
	public static final int WIDTH = 808; // panel width
	public static final int HEIGHT = 608; // panel height
	public static final int TILE_SIZE = 25; // size of each tile
	public static final int ROWS = 24; // num of rows
	public static final int COLUMNS = 32; // num of columns
	
	// used to determine the correct coordinates of a node 
	// since the mouse input takes the entire window into consideration
	public static final int XOFFSET = 13; 
	public static final int YOFFSET = 36;
	
	private int x; // x coordinate of mouse input
	private int y; // y coordinate of mouse input
	private int col; // current column of the node being processed
	private int row; // current row of the node being processed
	
	private SearchManager search; // the object that will perform the searching process
	private Grid grid; // grid data structure to organize nodes
	
	// store start and goal for ease of access
	private Node start;
	private Node goal;
	
	// keeps track of whether the start or goal node have been set or not
	public boolean startSet = false;
	public boolean goalSet = false;
	
	/**
	 * Constructor method
	 * 
	 */
	public GridPanel() {
		start = null;
		goal = null;
		mouse = Enums.MouseInputType.IDLE;
		action = Enums.GridAction.INIT;
		nodeType = Enums.NodeType.PASSABLE;
		searchType = Enums.SearchType.ASTAR;
		grid = new Grid(ROWS, COLUMNS);
	}
	
	@Override
	/**
	 * Overrides the Dimension method and reinitializes
	 * the window size of the JPanel
	 *
	 * @return Dimension object initialized with constants
	 */
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}
	
	/**
	 * Initializes the paint component from the JPanel library
	 * 
	 * @param Graphics object that is to be drawn
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawNode(g);
		drawGrid(g);
		
	}
	
	/**
	 * Creates a grid based on the constant variable sizes
	 * 
	 * @param Graphics object that is to be drawn
	 */
	private void drawGrid(Graphics g) {
        for(int r = 0; r < ROWS; r++) {
        	for(int c = 0; c < COLUMNS; c++) {
        		g.setColor(Color.BLACK);
            	g.drawRect(c*TILE_SIZE,
            			r*TILE_SIZE,
            			TILE_SIZE,
            			TILE_SIZE);
        	}
        }
    }
	
	/**
	 * Iterates through hashtable and draws
	 * nodes accordingly
	 * 
	 */
	private void drawNode(Graphics g) {
        for(int r = 0; r < ROWS; r++) {
        	for(int c = 0; c < COLUMNS; c++) {
        		switch(grid.getNode(r,c).getType()) {
    			case PASSABLE:
    				g.setColor(Color.LIGHT_GRAY);
    				break;
    			case IMPASSABLE:
    				g.setColor(Color.DARK_GRAY);
    				break;
    			case START:
    				g.setColor(Color.GREEN);
    				break;
    			case GOAL:
    				g.setColor(Color.RED);
    				break;
    			case PATH:
    				g.setColor(Color.CYAN);
    				break;
    			case VISITED:
    				g.setColor(Color.GRAY);
    				break;
    			}
    			g.fillRect(c*TILE_SIZE,
    					r*TILE_SIZE,
    					TILE_SIZE,
    					TILE_SIZE);
        	}
        }
	}
	
	/**
	 * Method to update the enum mouse based on mouse input
	 * 
	 */
	public void registerInput() {
		switch(mouse) {
			case LEFT_CLICK:
				setAction();
				leftClick();
				break;
			case RIGHT_CLICK:
				setAction();
				rightClick();
				break;	
			case MIDDLE_CLICK: // not used atm
				middleClick();
				break;			
			case LEFT_HELD: // not used atm
				leftHeld();
				break;			
			case RIGHT_HELD:
				setLoc();
				rightHeld();
				break;	
			case RELEASED:
				mouse = Enums.MouseInputType.IDLE;
				break;
			default:
				break;
		}
	}
	
	/**
	 * Adds or removes node based on Action enum
	 * 
	 */
	public void setNode() {
		if(action == Enums.GridAction.ADD) {
			switch(nodeType) {
				case PASSABLE:
					grid.getNode(row,col).setType(nodeType);
					break;
				case IMPASSABLE:
					grid.getNode(row,col).setType(nodeType);
					break;
				case START:
					grid.getNode(row,col).setType(nodeType);
					break;
				case GOAL:
					grid.getNode(row,col).setType(nodeType);
					break;
				case PATH:
					grid.getNode(row,col).setType(nodeType);
					break;
				case VISITED:
					grid.getNode(row,col).setType(nodeType);
					break;
		}
			repaint();
		} else if(action == Enums.GridAction.REMOVE) {
			grid.getNode(row,col).setType(nodeType);
			repaint();
		}
	}
	
	/**
	 * Sets Action enum
	 * 
	 */
	private void setAction() {
		if(setLoc()) {
			if(grid.getNode(row,col).getType().equals(Enums.NodeType.PASSABLE)) {
				action = Enums.GridAction.ADD;
			} else if(!grid.getNode(row,col).getType().equals(Enums.NodeType.PASSABLE)) {
				action = Enums.GridAction.REMOVE;
			}
		}
	}
	
	/**
	 * Sets current node location
	 * 
	 * @return boolean Returns true if coordinates are valid
	 * 							false otherwise
	 */
	private boolean setLoc() {
		if(x > XOFFSET && y > YOFFSET) {
			col = (x-(XOFFSET+1)) / 25;
			row = (y-(YOFFSET+1)) / 25;
			if(col < COLUMNS && row < ROWS && col > -1 && row > -1) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Adds/removes start or goal node if neither
	 * have been registered to the grid
	 *
	 */
	private void leftClick() {
		if(action == Enums.GridAction.ADD) {
			if(startSet == false) {
				nodeType = Enums.NodeType.START;
				startSet = true;
				start = grid.getNode(row,col);
				setNode();
			} else if(goalSet == false) {
				nodeType = Enums.NodeType.GOAL;
				goalSet = true;
				goal = grid.getNode(row,col);
				setNode();
			}
		} else if(action == Enums.GridAction.REMOVE) {
			if(grid.getNode(row,col).getType().equals(Enums.NodeType.START)) {
				nodeType = Enums.NodeType.PASSABLE;
				startSet = false;
				setNode();
				start = null;
			} else if(grid.getNode(row,col).getType().equals(Enums.NodeType.GOAL)) {
				nodeType = Enums.NodeType.PASSABLE;
				goalSet = false;
				setNode();
				goal = null;
			}
		}
	}
	
	/**
	 * Adds/removes impassable nodes, but cannot replace
	 * start and goal node
	 * 
	 */
	private void rightClick() {
		if(!grid.getNode(row,col).getType().equals(Enums.NodeType.START) 
				&& !grid.getNode(row,col).getType().equals(Enums.NodeType.GOAL)) {
			if(action == Enums.GridAction.ADD) {
				nodeType = Enums.NodeType.IMPASSABLE;
				setNode();
			} else if(action == Enums.GridAction.REMOVE) {
				nodeType = Enums.NodeType.PASSABLE;
				setNode();
			}
		}
	}
	
	private void middleClick() {
		// nothing planned yet
	}
	
	private void leftHeld() {
		// drag start or goal node around
	}
	
	/**
	 * Calls rightClick() method and updates location of cursor
	 * 
	 */
	private void rightHeld() {
		if(setLoc()) {
			rightClick();
		}	
	}
	
	/**
	 * Initializes grid if start and goal nodes have been set
	 * 
	 * @args newAction Enum.SearchType; the type of search to be performed
	 */
	public void initSearch(Enums.SearchType newType) {
		if(startSet && goalSet) {
			searchType = newType;
			action = Enums.GridAction.SEARCHING;
			search = new SearchManager(grid, start, goal, searchType);
		}
	}
	
	/**
	 * Iterates search algorithm one step at a time
	 * 
	 */
	public void iterateSearch() {
		if(search.getAction().equals(Enums.GridAction.SEARCHING)) {
			grid = search.search();
		} else if(search.getAction().equals(Enums.GridAction.SUCCESS)) {
			grid = search.getFinalPath();
		}
		action = search.getAction();
		repaint();
	}
	
	/**
	 * Reinitializes the GridPanel to initial state
	 * 
	 */
	public void reset() {
		start = null;
		goal = null;
		startSet = false;
		goalSet = false;
		action = Enums.GridAction.INIT;
		grid = new Grid(ROWS, COLUMNS);
		repaint();
	}
	
	/**
	 * Used to reset the grid to its initial state
	 * 
	 * All node types are set to Enums.NodeType.PASSABLE
	 * 
	 */
	public void resetSearch() {
		this.grid.resetSearch();
	}
	
	/**
	 * Sets the search type for this class
	 * 
	 * The type of search that will be performed on the grid
	 * 
	 */
	public void setType(Enums.SearchType newType) {
		this.searchType = newType;
	}
	
	/**
	 * Sets the mouse action type for this class
	 * 
	 * Used to determine what type of mouse input was
	 * initiated by the user (i.e. left click, right click, etc.)
	 * 
	 */
	public void setMouse(Enums.MouseInputType newAction) {
		this.mouse = newAction;
	}
	
	/**
	 * Sets the x coordinate of the mouse input
	 * 
	 */
	public void setX(int val) {
		this.x = val;
	}
	
	/**
	 * Sets the y coordinate of the mouse input
	 * 
	 */
	public void setY(int val) {
		this.y = val;
	}
	
	/**
	 * Returns the current action that is being performed
	 * 
	 */
	public Enums.GridAction getAction() {
		return this.action;
	}
	
	/**
	 * Returns true if the starting node has been set;
	 * false otherwise
	 * 
	 */
	public boolean getStartSet() {
		return this.startSet;
	}
	
	/**
	 * Returns true if the goal node has been set;
	 * false otherwise
	 * 
	 */
	public boolean getGoalSet() {
		return this.goalSet;
	}
}