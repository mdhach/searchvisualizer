import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

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
	
	// variables for getting mouse input and node coordinates
	private int x; // x location of click
	private int y; // y location of click
	private int col; // column in relation to click location
	private int row; // row in relation to click location
	
	// JPanel and grid dimensions
	public static final int WIDTH = 808;
	public static final int HEIGHT = 608;
	public static final int TILE_SIZE = 25; // size of each tile
	public static final int ROWS = 24; // num of rows
	public static final int COLUMNS = 32; // num of columns
	public static final int XOFFSET = 13;
	public static final int YOFFSET = 36;
	public static int DELAY = 10;
	
	// objects to manage nodes
	private Grid grid;
	
	// store start and goal locs for quicker access
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
		
		// init node graphics
		if(action == Enums.GridAction.INIT || action == Enums.GridAction.SEARCHING ||
				action == Enums.GridAction.FAIL) {
			drawNode(g);
		}
		
		if(mouse != Enums.MouseInputType.IDLE) {
			drawNode(g);
		}
		
		drawGrid(g);
		
	}
	
	/**
	 * Creates a grid based on the constant variable sizes
	 * 
	 * @param Graphics object that is to be drawn
	 */
	private void drawGrid(Graphics g) {
        for(int r=0; r<ROWS; r++) {
        	for(int c=0; c<COLUMNS; c++) {
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
		for(int r=0; r<ROWS; r++) {
        	for(int c=0; c<COLUMNS; c++) {
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
	 * Begins search on currently assigned grid
	 * 
	 * @args newAction Enum.SearchType; the type of search to be performed
	 * @returns boolean true if search is complete; false otherwise
	 */
	public void startSearch(Enums.SearchType newType) {
		if(startSet && goalSet) {
			searchType = newType;
			action = Enums.GridAction.SEARCHING;
			iterateSearch();
		}
	}
	
	private void iterateSearch() {
		SearchManager search = new SearchManager(grid, start, goal, searchType);
		Timer timer = new Timer(DELAY, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switch(search.getAction()) {
				case SEARCHING:
					grid = search.search();
					action = search.getAction();
					repaint();
					break;
				case SUCCESS:
					grid = search.getFinalPath();
					action = search.getAction();
					repaint();
					break;
				case FAIL:
					action = search.getAction();
					repaint();
					((Timer)e.getSource()).stop();
					break;
				case COMPLETE:
					action = search.getAction();
					repaint();
					((Timer)e.getSource()).stop();
					break;
				default:
					repaint();
					((Timer)e.getSource()).stop();
					break;
				}
			}
		});
		timer.start();
	}
	
	public void reset() {
		start = null;
		goal = null;
		startSet = false;
		goalSet = false;
		action = Enums.GridAction.INIT;
		grid = new Grid(ROWS, COLUMNS);
		repaint();
	}
	
	public void setType(Enums.SearchType newType) {
		this.searchType = newType;
	}
	
	public void setMouse(Enums.MouseInputType newAction) {
		this.mouse = newAction;
	}
	
	public void setX(int val) {
		this.x = val;
	}
	
	public void setY(int val) {
		this.y = val;
	}
	
	public Enums.GridAction getAction() {
		return this.action;
	}
	
	public Node getStart() {
		return this.start;
	}
	
	public Node getGoal() {
		return this.goal;
	}
}