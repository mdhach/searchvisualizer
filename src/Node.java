/**
 * Node class to hold values for searching
 * 
 */
public class Node {
	
	private Node parent;
	private Enums.NodeType type;
	private int f, g, h;
	
	public Node() {
		type = null;
		parent = null;
	}
	
	/**
	 * Overload constructor
	 * Initialize Node with type
	 * 
	 */
	public Node(Enums.NodeType val) {
		type = val;
		parent = null;
	}
	
	public void setF() {
		f = g + h;
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
	
	public int getF() {
		return f;
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
}
