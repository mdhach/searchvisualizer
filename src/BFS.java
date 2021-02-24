import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class BFS {
	
	private Grid grid;
	private LinkedList<Node> openList;
	private Set<Node> closedList;
	private Node start;
	private Node goal;
	private boolean complete;
	private boolean pathing;
	private Node currentNode;
	private final int move = 25;
	
	public BFS(Grid arg0, Node arg1, Node arg2) {
		openList = new LinkedList<Node>();
		closedList = new HashSet<Node>();
		this.grid = arg0;
		this.start = arg1;
		this.goal = arg2;
		this.complete = false;
		this.pathing = false;
		openList.add(start);
	}
	
	public void startSearch() {
		if(openList.peek() != null) {
			currentNode = openList.poll();
			closedList.add(currentNode);
			
			for(Node neighbor : grid.getNeighbors(currentNode)) {
				if(neighbor.getType().equals(Enums.NodeType.PASSABLE) ||
						!closedList.contains(neighbor)) {
					
					neighbor.setH((calculateH(start, neighbor)));
					
					if(neighbor.getH() < currentNode.getH() || !openList.contains(neighbor)) {
						neighbor.setParent(currentNode);
					}
					
					if(neighbor.getType().equals(Enums.NodeType.GOAL)) {
						getFinalPath();
						complete = true;
						pathing = true;
						return;
					} else {
						if(neighbor != start && neighbor != goal) {
							neighbor.setType(Enums.NodeType.VISITED);
						}
						if(!openList.contains(neighbor)) {
							openList.add(neighbor);
						}
					}
				}
			}
		}
	}
	
	private int calculateH(Node arg0, Node arg1) {
		int x = Math.abs(arg0.getCol() - arg1.getCol());
		int y = Math.abs(arg0.getRow() - arg1.getRow());
		return ((x+y)*move);
	}
	
	public Grid getGrid() {
		return this.grid;
	}
	
	public boolean isComplete() {
		return complete;
	}
	
	/**
	 * Recursively finds the path by setting arg0's parents type
	 * to Enums.NodeType.PATH
	 * 
	 */
	public boolean getFinalPath() {
		if(currentNode != start) {
			currentNode.setType(Enums.NodeType.PATH);
			currentNode = currentNode.getParent();
			return pathing;
		} else {
			pathing = false;
			return pathing;
		}
	}
}