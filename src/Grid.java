import java.util.ArrayList;
import java.util.List;

public class Grid {
	
	private Node[][] grid;
	private Enums.NodeType nodeType;
	private int ROWS;
	private int COLUMNS;
	
	public Grid(int arg0, int arg1) {
		ROWS = arg0;
		COLUMNS = arg1;
		nodeType = Enums.NodeType.PASSABLE;
		initGrid();
	}
	
	private void initGrid() {
		grid = new Node[ROWS][COLUMNS];
		for(int r = 0; r < ROWS; r++) {
			for(int c = 0; c < COLUMNS; c++) {
				grid[r][c] = new Node(r, c, nodeType);
			}
		}
	}
	
	private boolean isBounded(int r, int c) {
		if(r < ROWS && c < COLUMNS && r > -1 && c > -1) {
			if(!grid[r][c].getType().equals(Enums.NodeType.IMPASSABLE)) {
				return true;
			}
		}
		return false;
	}
	
	public Node getNode(int r, int c) {
		return grid[r][c];
	}
	
	public List<Node> getNeighbors(Node arg0) {
		int row = arg0.getRow();
		int col = arg0.getCol();
		List<Node> neighbors = new ArrayList<>();
		for(int i = -1; i < 2; i+=2) {
			if(isBounded(row+i, col)) {
				neighbors.add(grid[row+i][col]);
			}
			if(isBounded(row, col+i)) {
				neighbors.add(grid[row][col+i]);
			}
			// check corners
//			if(isBounded(row+i, col+i)) {
//				neighbors.add(grid[row+i][col+i]);
//			}
//			if(isBounded(row+i, col-i)) {
//				neighbors.add(grid[row+i][col-i]);
//			}
		}
		return neighbors;
	}
	
	public void gridToString() {
		for(int r = 0; r < ROWS; r++) {
			for(int c = 0; c < COLUMNS; c++) {
				System.out.println(grid[r][c]);
			}
		}
	}
}
