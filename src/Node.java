import java.util.*;

public class Node {
	
	private Node parent;
	private List<Integer> location = new ArrayList<Integer>();
	private NodeType type;
	private int f, g, h;
	
	public Node(List<Integer> loc, NodeType val) {
		location = Arrays.asList(new Integer[] {loc.get(0), loc.get(1)});
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
	
	public void setType(NodeType val) {
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
	
	public List<Integer> getLoc() {
		return Arrays.asList(new Integer[] {location.get(0), location.get(1)});
	}
	
	public NodeType getType() {
		return type;
	}
	
	public boolean equals(Object val) {
		Node node = (Node) val;
		
		return location == node.getLoc();
	}
	
	public String toString() {
		return "Node: " + "(" + this.getLoc() + ")";
	}
	
}
