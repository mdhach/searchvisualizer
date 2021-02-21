import java.util.PriorityQueue;
import java.util.Set;
import java.util.HashSet;

public class AstarSearch {
	
	private Grid grid;
	private PriorityQueue<Node> openList;
	private Set<Node> closedList;
	
	private Node start;
	private Node goal;
	
	private NodeComparator comparator;
	
	private final int move = 25;
	
	public AstarSearch(Grid arg0, Node arg1, Node arg2) {
		this.grid = arg0;
		this.start = arg1;
		this.goal = arg2;
		comparator = new NodeComparator();
		openList = new PriorityQueue<>(11, comparator);
		closedList = new HashSet<Node>();
		start.setG(0);
		openList.add(start);
	}
	
	public void startSearch() {
		Node currentNode;
		if(openList.peek() != null) {
			currentNode = openList.poll();
			closedList.add(currentNode);
			if(currentNode != start && currentNode != goal) {
				currentNode.setType(Enums.NodeType.VISITED);
			}
			if(currentNode == goal) {
				getFinalPath(currentNode);
				return;
			}
			for(Node neighbor : grid.getNeighbors(currentNode)) {
				if(neighbor.getType().equals(Enums.NodeType.PASSABLE) ||
						!closedList.contains(neighbor)) {
					int currentCost = currentNode.getG() + calculateH(currentNode, neighbor);
					
					if(currentCost < neighbor.getG() || !openList.contains(neighbor)) {
						neighbor.setG(currentCost);
						neighbor.setH(calculateH(neighbor, goal));
						neighbor.setParent(currentNode);
						if(neighbor != start && neighbor != goal) {
							neighbor.setType(Enums.NodeType.VISITED);
						}
						if(!openList.contains(neighbor)) {
							openList.add(neighbor);
						}
					}
				}
			}
			startSearch();
		}
	}
	
	private int calculateH(Node arg0, Node arg1) {
		int x = Math.abs(arg0.getCol() - arg1.getCol());
		int y = Math.abs(arg0.getRow() - arg1.getRow());
		return ((x+y)*move);
	}
	
	private void getFinalPath(Node arg0) {
		if(arg0.getParent() != start && arg0.getParent() != goal) {
			arg0.getParent().setType(Enums.NodeType.PATH);
			this.getFinalPath(arg0.getParent());
		}
	}
}
