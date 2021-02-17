import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.util.*;

/**
 * Main grid GUI for search visualization.
 * 
 */
@SuppressWarnings("serial")
public class NodeGrid extends JPanel implements MouseListener, MouseMotionListener {
	
	// allow user to modify grid
	// disabled if search is in progress
	public boolean allowAction;
	
	// declare enums
	private Mouse mouse;
	private NodeAction action;
	private NodeType nodeType;
	private PushActionType actionType;
	
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
	private Hashtable<List<Integer>, Node> board = new Hashtable<>();
	//private PriorityQueue<Node> openList;
	//private Set<Node> closedList;
	private List<Integer> loc = new ArrayList<Integer>(); // location of clicked node
	
	// keeps track of whether the start or goal node have been set or not
	private boolean startSet = false;
	private boolean goalSet = false;

	/**
	 * Enum to manage the mouse input
	 * 
	 */
	enum Mouse {
		IDLE,
		LEFT_CLICK,
		RIGHT_CLICK,
		MIDDLE_CLICK,
		LEFT_HELD,
		RIGHT_HELD
	}
	
	/**
	 * Enum to manage the different actions involving
	 * nodes
	 * 
	 */
	enum NodeAction {
		INIT,
		IDLE,
		ADD,
		REMOVE
	}
	
	/**
	 * Constructor method
	 * 
	 */
	public NodeGrid() {
		allowAction = true;
		
		mouse = Mouse.IDLE;
		action = NodeAction.INIT;
		nodeType = NodeType.PASSABLE;
		
		// init hashtable with node locations
		for(int r=0; r<ROWS; r++) {
        	for(int c=0; c<COLUMNS; c++) {
        		board.put(Arrays.asList(new Integer[] {c,r}), new Node(nodeType));
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
		if(action == NodeAction.INIT) {
			drawNode(g);
		}
		
		if(mouse != Mouse.IDLE) {
			drawNode(g);
			mouse = Mouse.IDLE; // reset mouse mouse
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
		Enumeration<List<Integer>> keys = board.keys();
		while(keys.hasMoreElements()) {
			// iterates through hashtable keys
			List<Integer> key = keys.nextElement();
			
			// get location of node
			int col = key.get(0);
			int row = key.get(1);
			
			// set color based on node type
			switch(board.get(key).getType()) {
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
	private void registerInput() {
		switch(mouse) {
			case LEFT_CLICK:
				leftClick();
				break;
			case RIGHT_CLICK:
				rightClick();
				break;	
			case MIDDLE_CLICK:
				middleClick();
				break;			
			case LEFT_HELD:
				leftHeld();
				break;			
			case RIGHT_HELD:
				rightHeld();
				break;	
			default:
				break;
		}
	}
	
	/**
	 * Adds or removes node based on Action enum
	 * 
	 */
	private void setNode() {
		if(action == NodeAction.ADD) {
			switch(nodeType) {
			case PASSABLE:
				board.get(loc).setType(nodeType);
				break;
			case IMPASSABLE:
				board.get(loc).setType(nodeType);
				break;
			case START:
				board.get(loc).setType(nodeType);
				break;
			case GOAL:
				board.get(loc).setType(nodeType);
				break;
			case PATH:
				board.get(loc).setType(nodeType);
				break;
			case VISITED:
				board.get(loc).setType(nodeType);
				break;
		}
			repaint();
		} else if(action == NodeAction.REMOVE) {
			board.get(loc).setType(nodeType);
			repaint();
		}
	}
	
	/**
	 * Sets Action enum
	 * 
	 */
	private void setAction() {
		if(setLoc()) {
			if(board.get(loc).getType() == NodeType.PASSABLE) {
				action = NodeAction.ADD;
			} else if(board.get(loc).getType() != NodeType.PASSABLE) {
				action = NodeAction.REMOVE;
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
		if(action == NodeAction.ADD) {
			if(startSet == false) {
				nodeType = NodeType.START;
				startSet = true;
				setNode();
			} else if(goalSet == false) {
				nodeType = NodeType.GOAL;
				goalSet = true;
				setNode();
			}
		} else if(action == NodeAction.REMOVE) {
			if(board.get(loc).getType() == NodeType.START) {
				nodeType = NodeType.PASSABLE;
				startSet = false;
				setNode();
			} else if(board.get(loc).getType() == NodeType.GOAL) {
				nodeType = NodeType.PASSABLE;
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
		if(board.get(loc).getType() != NodeType.START && board.get(loc).getType() != NodeType.GOAL) {
			if(action == NodeAction.ADD) {
				nodeType = NodeType.IMPASSABLE;
				setNode();
			} else if(action == NodeAction.REMOVE) {
				nodeType = NodeType.PASSABLE;
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
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(allowAction) {
			// get x and y locations
			x = e.getX();
			y = e.getY();
			
			// update enum based on mouse input
			if(SwingUtilities.isLeftMouseButton(e)) {
				mouse = Mouse.LEFT_CLICK;
			} else if(SwingUtilities.isRightMouseButton(e)) {
				// was causing some placement issues,
				// so right click relies on the
				// mousePressed event for now
				//mouse = Mouse.RIGHT_CLICK;
			} else {
				mouse = Mouse.MIDDLE_CLICK;
			}
			
			setAction();
			registerInput();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(allowAction) {
			if(SwingUtilities.isRightMouseButton(e)) {
				x = e.getX();
				y = e.getY();
				mouse = Mouse.RIGHT_HELD;
				
				setAction();
				registerInput();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		action = NodeAction.IDLE; // reset placement mouse
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// nothing planned yet
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// nothing planned yet
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(allowAction) {
			if(SwingUtilities.isRightMouseButton(e)) {
				x = e.getX();
				y = e.getY();
				mouse = Mouse.RIGHT_HELD;
				
				if(setLoc()) {
					registerInput();
				}
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// nothing planned yet
	}
	
	public MouseListener returnAsMouseListener() {
		return this;
	}
	
	public MouseMotionListener returnAsMouseMotionListener() {
		return this;
	}
	
	public void setAllowAction(boolean newAction) {
		allowAction = newAction;
	}
}