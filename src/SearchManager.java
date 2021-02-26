import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Set;

public class SearchManager {
	
	private Grid grid;
	private PriorityQueue<Node> ASTAR_openList;
	private LinkedList<Node> BFS_openList;
	private Set<Node> closedList;
	private Node start;
	private Node goal;
	private boolean complete;
	private boolean pathing;
	private Node currentNode;
	private int move;
	private NodeComparator comparator;
	private Enums.SearchType searchType;
	
	public SearchManager(Grid grid, Node start, Node goal, Enums.SearchType searchType) {
		this.grid = grid;
		this.start = start;
		this.goal = goal;
		this.searchType = searchType;
		this.complete = false;
		this.pathing = false;
		
 		switch(searchType) {
	 		case ASTAR:
	 			move = 25;
	 			initAstar();
	 			break;
	 		case BFS:
	 			move = 1;
	 			initBFS();
	 			break;
 		}
	}
	
	/**
	 * Begins a search algorithm determined
	 * by the Enums.SearchType searchType
	 * 
	 */
	public void search() {
		switch(searchType) {
 		case ASTAR:
 			ASTAR();
 			break;
 		case BFS:
 			BFS();
 			break;
		}
	}
	
	/**
	 * Used to initialize variables used for the A*
	 * searching algorithm
	 * 
	 */
	private void initAstar() {
		comparator = new NodeComparator();
		ASTAR_openList = new PriorityQueue<>(11, comparator);
		closedList = new HashSet<Node>();
		start.setG(0);
		ASTAR_openList.add(start);
	}
	
	/**
	 * Used to initialize variables used for the
	 * Breadth First Search algorithm
	 * 
	 */
	private void initBFS() {
		BFS_openList = new LinkedList<Node>();
		closedList = new HashSet<Node>();
		BFS_openList.add(start);
	}
	
	/**
	 * The logic for finding a path using A*
	 * 
	 */
	public void ASTAR() {
		if(ASTAR_openList.peek() != null) {
			
			// set the current node to the top of the ASTAR_openList.
			// The ASTAR_openList utilizes the PriorityQueue class,
			// and with the overridden comparator (which can be found in the
			// NodeComparator class) the PriorityQueue organizes the nodes
			// from least to greatest (first in, first out) based on their
			// current F value. This node is then added to the closedList,
			// which means that the node has already been checked
			currentNode = ASTAR_openList.poll();
			closedList.add(currentNode);
			
			// iterate through the neighbors surrounding the current node
			// this does not take diagonal nodes into consideration
			for(Node neighbor : grid.getNeighbors(currentNode)) {
				if(neighbor.getType().equals(Enums.NodeType.PASSABLE) ||
						!closedList.contains(neighbor)) {
					
					// checks if any of the neighboring nodes is the goal node
					if(neighbor == goal) {
						getFinalPath();
						complete = true;
						pathing = true;
					}
					
					// the current cost for a movement between the current node
					// and a neighboring node. This cost, G + H, is essentially the F value.
					// This value is used to organize the nodes in the PriorityQueue (ASTAR_openList).
					// The G value is basically the distance between the starting node and the
					// neighboring node. When added with the H value, this helps determine
					// the shortest path between the starting node and the goal node.
					int currentCost = currentNode.getG() + calculateH(currentNode, neighbor);
					
					// if the current cost of a neighboring node is less than current node OR
					// if the neighboring node is NOT in the ASTAR_openList queue,
					// set the neighboring nodes G cost to the current cost,
					// calculate and set the H value for the neighboring node,
					// and set current node as the parent of the neighboring node
					// This ensures that only the best nodes are kept during the search.
					if(currentCost < neighbor.getG() || !ASTAR_openList.contains(neighbor)) {
						neighbor.setG(currentCost);
						neighbor.setH(calculateH(neighbor, goal));
						neighbor.setParent(currentNode);
						
						// if the neighboring node is neither the start or the goal node,
						// set the type of the neighboring node to Enums.NodeType.VISITED.
						// While the node type VISITED is not necessary for the search or pathing logic
						// since they are being tracked in the closed list, it is simply used in the 
						// GridPanel class to illustrate which nodes were searched,
						// or VISITED, prior to finding the goal node.
						if(neighbor != start && neighbor != goal) {
							neighbor.setType(Enums.NodeType.VISITED);
						}
						
						// if the neighboring node is not in the ASTAR_openList,
						// add it to the ASTAR_openList. Only neighboring nodes with a 
						// better G cost are added to the queue, while all neighboring 
						// nodes are added to the queue that is used in the Breadth First Search
						if(!ASTAR_openList.contains(neighbor)) {
							ASTAR_openList.add(neighbor);
						}
					}
				}
			}
		} else {
			// If the open list is empty, then the search has completed,
			// however, the goal has not been found.
			complete = true;
		}
	}
	
	
	/**
	 * The logic for finding a path using Breadth First Searchj
	 * 
	 */
	public void BFS() {
		if(BFS_openList.peek() != null) {
			// sets the current node to the top of the open list queue
			// this node is then added to the closed list to identify that
			// it has been searched
			currentNode = BFS_openList.poll();
			closedList.add(currentNode);
			
			// iterate through the neighbors surrounding the current node
			// this does not take diagonal nodes into consideration
			for(Node neighbor : grid.getNeighbors(currentNode)) {
				if(neighbor.getType().equals(Enums.NodeType.PASSABLE) ||
						!closedList.contains(neighbor)) {
					
					// calculates the distance between the starting node and a neighboring node
					// and sets the H value (heuristic) to that value
					neighbor.setH((calculateH(start, neighbor)));
					
					// if the H value of a neighboring node is less than current node OR
					// if the neighboring node is NOT in the BFS_openList queue,
					// set the current node as the neighboring nodes parent.
					if(neighbor.getH() < currentNode.getH() || !BFS_openList.contains(neighbor)) {
						neighbor.setParent(currentNode);
					}
					
					// checks if a neighboring node is the goal node
					if(neighbor.getType().equals(Enums.NodeType.GOAL)) {
						getFinalPath();
						complete = true;
						pathing = true;
						return;
					} 
					
					// if the neighboring node is neither the start or the goal node,
					// set the type of the neighboring node to Enums.NodeType.VISITED.
					// While the node type VISITED is not necessary for the search or pathing logic
					// since they are being tracked in the closed list, it is simply used in the 
					// GridPanel class to illustrate which nodes were searched,
					// or VISITED, prior to finding the goal node.
					if(neighbor != start && neighbor != goal) {
						neighbor.setType(Enums.NodeType.VISITED);
					}
					
					// if the neighboring node is not in the BFS_openList,
					// add it to the BFS_openList. In comparison to the A* search,
					// all neighboring nodes (if the goal has not been found) are added
					// to this queue, whereas for the A* search, only neighboring nodes
					// with a better or less G value are added.
					if(!BFS_openList.contains(neighbor)) {
						BFS_openList.add(neighbor);
					}
				}
			}
		} else {
			// If the open list is empty, then the search has completed,
			// however, the goal has not been found.
			complete = true;
		}
	}
	
	/**
	 * Finds the Manhattan distance between two nodes
	 * 
	 * @args Node the first node
	 * @args Node the second node
	 * @returns int the heuristic (Manhattan distance)
	 */
	private int calculateH(Node arg0, Node arg1) {
		int x = Math.abs(arg0.getCol() - arg1.getCol());
		int y = Math.abs(arg0.getRow() - arg1.getRow());
		return ((x+y)*move);
	}
	
	/**
	 * Used for iterating each step of the search and
	 * pathing process
	 * 
	 * @returns Grid current Grid object at the nth iteration
	 */
	public Grid getGrid() {
		return this.grid;
	}
	
	/**
	 * Used to determine if the search process is complete
	 * 
	 * @returns boolean
	 */
	public boolean isComplete() {
		return complete;
	}
	
	/**
	 * Used to determine if the pathing process is complete
	 * 
	 * @returns boolean
	 */
	public boolean isPathing() {
		return this.pathing;
	}
	
	/**
	 * Used to iterate through the path determined
	 * by a search algorithm
	 * 
	 * @returns boolean if the pathing is complete
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
