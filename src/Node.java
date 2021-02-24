/**
 * Node class to hold values for searching
 * 
 */
public class Node {
	
	private Node parent;
	private Enums.NodeType type;
	private int g, h, col, row;
	
	public Node(int arg0, int arg1) {
		row = arg0;
		col = arg1;
		type = null;
		parent = null;
	}
	
	/**
	 * Overload constructor;
	 * pass Enums.NodeType with coordinates
	 * 
	 */
	public Node(int arg0, int arg1, Enums.NodeType arg2) {
		row = arg0;
		col = arg1;
		type = arg2;
		parent = null;
	}
	
	public void setG(int val) {
		g = val;
	}
	
	public void setH(int val) {
		h = val;
	}
	
	public void setParent(Node val) {
		parent = val;
	}
	
	public void setType(Enums.NodeType val) {
		type = val;
	}
	
	public void setCol(int val) {
		col = val;
	}
	
	public void setRow(int val) {
		row = val;
	}
	
	public int getF() {
		return g + h;
	}
	
	public int getG() {
		return g;
	}
	
	public int getH() {
		return h;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public Enums.NodeType getType() {
		return type;
	}
	
	public int getCol() {
		return col;
	}
	
	public int getRow() {
		return row;
	}
	
	public String toString() {
		return ("{TYPE: [" + type + "], LOC: [" + row + ", " + col + "]");
	}
}
