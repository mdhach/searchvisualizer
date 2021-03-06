import java.util.ArrayList;
import java.util.List;

public class Grid {
	
	private Node[][] grid;
	private int ROWS;
	private int COLUMNS;
	private int[] idx;
	
	public Grid(int arg0, int arg1) {
		ROWS = arg0;
		COLUMNS = arg1;
		idx = new int[] {-1,1};
		initGrid();
	}
	
	/**
	 * Initializes the grid to a specified size;
	 * the nodes are initialized with their respective
	 * coordinates and are set to Enums.NodeType.PASSABLE
	 * 
	 * 
	 */
	private void initGrid() {
		grid = new Node[ROWS][COLUMNS];
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLUMNS; j++) {
				grid[i][j] = new Node(i, j, Enums.NodeType.PASSABLE);
			}
		}
	}
	
	/**
	 * Checks to see if the given coordinates are within
	 * the grid dimensions
	 * 
	 */
	private boolean isBounded(int r, int c) {
		if(r < ROWS && c < COLUMNS && r > -1 && c > -1) {
			if(!grid[r][c].getType().equals(Enums.NodeType.IMPASSABLE)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Resets the GridPanel by setting the type of
	 * each node to Enums.NodeType.PASSABLE
	 * 
	 */
	public void resetSearch() {
		for(Node[] row : grid) {
			for(Node node : row) {
				if(node.getType().equals(Enums.NodeType.VISITED) ||
						node.getType().equals(Enums.NodeType.PATH)) {
					grid[node.getRow()][node.getCol()].setType(node.getType());
				}
			}
		}
	}
	
	/**
	 * Returns the node at a given index
	 * 
	 */
	public Node getNode(int r, int c) {
		return grid[r][c];
	}
	
	/**
	 * Gets the horizontal and vertical neighbors of a given node
	 * 
	 */
	public List<Node> getNeighbors(Node arg0) {
		int row = arg0.getRow();
		int col = arg0.getCol();
		List<Node> neighbors = new ArrayList<>();
		for(int i : idx) {
			if(isBounded(row+i, col)) { neighbors.add(grid[row+i][col]); }
			if(isBounded(row, col+i)) { neighbors.add(grid[row][col+i]); }
			
			// Uncomment to enable corner checking
			//if(isBounded(row+i, col+i)) { neighbors.add(grid[row+i][col+i]); }
			//if(isBounded(row+i, col-i)) { neighbors.add(grid[row+i][col-i]); }
		}
		return neighbors;
	}
	
	/**
	 * Used for debugging purposes;
	 * print the contents of the grid to a string
	 * 
	 */
	public void gridToString() {
		for(Node[] rows : grid) {
			for(Node node : rows) {
				System.out.println(grid[node.getRow()][node.getCol()]);
			}
		}
	}
}
