import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import java.util.*;

/**
 * Main grid GUI for search visualization.
 * 
 */
@SuppressWarnings("serial")
public class GridPanel extends JPanel {
	
	// allow user to modify grid
	// disabled if search is in progress
	public boolean allowAction;
	
	// declare enums
	private Enums.GridAction action;
	private Enums.NodeType nodeType;
	private Enums.MouseInputType mouse;
	
	// variables for getting mouse input and node coordinates
	private int x; // x location of click
	private int y; // y location of click
	private int c; // column in relation to click location
	private int r; // row in relation to click location
	
	// JPanel and grid dimensions
	private static final int WIDTH = 808;
	private static final int HEIGHT = 608;
	private static final int TILE_SIZE = 25; // size of each tile
	private static final int ROWS = 24; // num of rows
	private static final int COLUMNS = 32; // num of columns
	
	// movement values
	//private static final int horizontal = 25;
	//private static final int diagonal = 35; // approximate value of diagonal movement
	
	// objects to manage nodes
	private Hashtable<List<Integer>, Node> grid = new Hashtable<>();
	//private PriorityQueue<Node> openList;
	//private Set<Node> closedList;
	private List<Integer> loc = new ArrayList<Integer>(); // location of clicked node
	
	// keeps track of whether the start or goal node have been set or not
	private boolean startSet = false;
	private boolean goalSet = false;
	
	/**
	 * Enum to manage the different actions involving
	 * nodes
	 * 
	 */

	
	/**
	 * Constructor method
	 * 
	 */
	public GridPanel() {
		allowAction = true;
		
		mouse = Enums.MouseInputType.IDLE;
		action = Enums.GridAction.INIT;
		nodeType = Enums.NodeType.PASSABLE;
		
		// init hashtable with node locations
		for(int r=0; r<ROWS; r++) {
        	for(int c=0; c<COLUMNS; c++) {
        		grid.put(Arrays.asList(new Integer[] {c,r}), new Node(nodeType));
        	}
        }
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
		if(action == Enums.GridAction.INIT) {
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
		Enumeration<List<Integer>> keys = grid.keys();
		while(keys.hasMoreElements()) {
			// iterates through hashtable keys
			List<Integer> key = keys.nextElement();
			
			// get location of node
			int col = key.get(0);
			int row = key.get(1);
			
			// set color based on node type
			switch(grid.get(key).getType()) {
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

			g.fillRect(col*TILE_SIZE,
					row*TILE_SIZE,
					TILE_SIZE,
					TILE_SIZE);
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
					grid.get(loc).setType(nodeType);
					break;
				case IMPASSABLE:
					grid.get(loc).setType(nodeType);
					break;
				case START:
					grid.get(loc).setType(nodeType);
					break;
				case GOAL:
					grid.get(loc).setType(nodeType);
					break;
				case PATH:
					grid.get(loc).setType(nodeType);
					break;
				case VISITED:
					grid.get(loc).setType(nodeType);
					break;
		}
			repaint();
		} else if(action == Enums.GridAction.REMOVE) {
			grid.get(loc).setType(nodeType);
			repaint();
		}
	}
	
	/**
	 * Sets Action enum
	 * 
	 */
	private void setAction() {
		if(setLoc()) {
			if(grid.get(loc).getType() == Enums.NodeType.PASSABLE) {
				action = Enums.GridAction.ADD;
			} else if(grid.get(loc).getType() != Enums.NodeType.PASSABLE) {
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
		if(x > 13 && y > 36) {
			c = (x-14) / 25;
			r = (y-37) / 25;
			if(c < COLUMNS && r < ROWS && c > -1 && r > -1) {
				loc = Arrays.asList(new Integer[] {c,r});
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
				setNode();
			} else if(goalSet == false) {
				nodeType = Enums.NodeType.GOAL;
				goalSet = true;
				setNode();
			}
		} else if(action == Enums.GridAction.REMOVE) {
			if(grid.get(loc).getType() == Enums.NodeType.START) {
				nodeType = Enums.NodeType.PASSABLE;
				startSet = false;
				setNode();
			} else if(grid.get(loc).getType() == Enums.NodeType.GOAL) {
				nodeType = Enums.NodeType.PASSABLE;
				goalSet = false;
				setNode();
			}
		}
	}
	
	/**
	 * Adds/removes impassable nodes, but cannot replace
	 * start and goal node
	 * 
	 */
	private void rightClick() {
		if(grid.get(loc).getType() != Enums.NodeType.START 
				&& grid.get(loc).getType() != Enums.NodeType.GOAL) {
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
	
	private void rightHeld() {
		rightClick();
	}
	
	public void startSearch(Enums.SearchType newAction) {
		allowAction = false;
		switch(newAction) {
			case ASTAR:
				AstarSearch search = new AstarSearch(grid);
				grid = search.startSearch();
				break;
		}
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
}