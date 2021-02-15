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
 * GUI JPanel manager class
 * 
 */
@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener, MouseMotionListener {
	
	// declare enums
	private Mouse state;
	private Action action;
	private NodeType type;
	
	// variables for getting mouse input and node coordinates
	private int x; // x location of click
	private int y; // y location of click
	private int c; // column in relation to click location
	private int r; // row in relation to click location
	
	// variables to manage size of JPanel
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 600;
	private static final int TILE_SIZE = 25; // size of each tile
	private static final int ROWS = 24; // num of rows
	private static final int COLUMNS = 32; // num of columns
	
	// movement values
	private static final int horizontal = 25;
	private static final int diagonal = 35; // approximate value of diagonal movement
	
	// objects to manage nodes
	private Hashtable<List<Integer>, NodeType> hashtable = new Hashtable<>();
	private List<Integer> loc = new ArrayList<Integer>(); // location of current node

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
	enum Action {
		INIT,
		IDLE,
		ADD,
		REMOVE
	}
	
	/**
	 * Constructor method
	 * 
	 */
	public Board() {
		state = Mouse.IDLE;
		action = Action.INIT;
		type = NodeType.PASSABLE;
		
		// init hashtable with node locations
		for(int r=0; r<ROWS; r++) {
        	for(int c=0; c<COLUMNS; c++) {
        		hashtable.put(Arrays.asList(new Integer[] {c,r}), type);
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
		if(action == Action.INIT) {
			drawNode(g);
		}
		
		if(state != Mouse.IDLE) {
			drawNode(g);
			state = Mouse.IDLE; // reset mouse state
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
		Enumeration<List<Integer>> keys = hashtable.keys();
		while(keys.hasMoreElements()) {
			// iterates through hashtable keys
			List<Integer> key = keys.nextElement();
			
			// get location of node
			int col = key.get(0);
			int row = key.get(1);
			
			// set color based on node type
			switch(hashtable.get(key)) {
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
	 * Method to update the enum state based on mouse input
	 * 
	 */
	private void registerInput() {
		switch(state) {
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
		if(action == Action.ADD) {
			switch(type) {
			case PASSABLE:
				hashtable.replace(loc, NodeType.PASSABLE);
				break;
			case IMPASSABLE:
				hashtable.replace(loc, NodeType.IMPASSABLE);
				break;
			case START:
				hashtable.replace(loc, NodeType.START);
				break;
			case GOAL:
				hashtable.replace(loc, NodeType.GOAL);
				break;
			case PATH:
				hashtable.replace(loc, NodeType.PATH);
				break;
			case VISITED:
				hashtable.replace(loc, NodeType.VISITED);
				break;
		}
			repaint();
		} else if(action == Action.REMOVE) {
			hashtable.replace(loc, type);
			repaint();
		}
	}
	
	/**
	 * Sets Action enum
	 * 
	 */
	private void setAction() {
		setLoc();
		
		if(c < COLUMNS && r < TILE_SIZE) {
			if(hashtable.get(loc) == NodeType.PASSABLE) {
				action = Action.ADD;
			} else if(hashtable.get(loc) != NodeType.PASSABLE){
				action = Action.REMOVE;
			}
		}
	}
	
	/**
	 * Sets current node location
	 * 
	 * I'm not exactly sure why the 'x' value begins at 8,
	 * however, I'm guessing that the 'y' value begins at
	 * 32 because the JPanel is takes the size of the
	 * titlebar into consideration.
	 * 
	 */
	private void setLoc() {
		c = (x-8) / 25;
		r = (y-32) / 25;
		loc = Arrays.asList(new Integer[] {c,r});
	}
	
	/**
	 * Adds/removes start or goal node if neither
	 * have been registered to the grid
	 *
	 */
	private void leftClick() {
		if(action == Action.ADD) {
			if(!hashtable.containsValue(NodeType.START)) {
				type = NodeType.START;
				setNode();
			} else if(!hashtable.containsValue(NodeType.GOAL)) {
				type = NodeType.GOAL;
				setNode();
			}
		} else if(action == Action.REMOVE) {
			if(hashtable.get(loc) == NodeType.START || hashtable.get(loc) == NodeType.GOAL) {
				type = NodeType.PASSABLE;
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
		if(hashtable.get(loc) != NodeType.START && hashtable.get(loc) != NodeType.GOAL) {
			if(action == Action.ADD) {
				type = NodeType.IMPASSABLE;
				setNode();
			} else if(action == Action.REMOVE) {
				type = NodeType.PASSABLE;
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
		// get x and y locations
		x = e.getX();
		y = e.getY();
		
		// update enum based on mouse input
		if(SwingUtilities.isLeftMouseButton(e)) {
			state = Mouse.LEFT_CLICK;
		} else if(SwingUtilities.isRightMouseButton(e)) {
			//state = Mouse.RIGHT_CLICK;
		} else {
			state = Mouse.MIDDLE_CLICK;
		}
		
		setAction();
		registerInput();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(SwingUtilities.isRightMouseButton(e)) {
			x = e.getX();
			y = e.getY();
			state = Mouse.RIGHT_HELD;
			
			setAction();
			registerInput();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		action = Action.IDLE; // reset placement state
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
		if(SwingUtilities.isRightMouseButton(e)) {
			x = e.getX();
			y = e.getY();
			state = Mouse.RIGHT_HELD;
			
			setLoc();
			registerInput();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// nothing planned yet
	}
}