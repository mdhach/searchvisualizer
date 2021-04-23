import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class SearchManager {
	
	private Grid grid; // grid data structure to manage nodes
	
	// open and closed lists used to keep track of
	// what has or hasn't been searched
	private PriorityQueue<Node> ASTAR_openList;
	private LinkedList<Node> BFS_openList;
	private Stack<Node> DFS_openList;
	private Set<Node> closedList;
	private Stack<Node> pathList; // the final path of a search algorithm
	private Node start;
	private Node goal;
	private Node currentNode;
	private int move; // cost of movement
	private NodeComparator comparator; // used to override the comparator for the PriorityQueue data structure
	private Enums.SearchType searchType;
	private Enums.GridAction action;
	
	public SearchManager(Grid grid, Node start, Node goal, Enums.SearchType searchType) {
		this.grid = grid;
		this.start = start;
		this.goal = goal;
		this.searchType = searchType;
		this.action = Enums.GridAction.SEARCHING;
		
		// init search type to the user specified option (in the ActionPanel)
 		switch(searchType) {
	 		case ASTAR:
	 			move = 25;
	 			initAstar();
	 			break;
	 		case BFS:
	 			move = 1;
	 			initBFS();
	 			break;
	 		case DFS:
	 			move = 1;
	 			initDFS();
	 			break;
 		}
	}
	
	/**
	 * Begins a search algorithm and returns the current
	 * iteration of the Grid
	 * 
	 */
	public Grid search() {
		switch(searchType) {
 		case ASTAR:
 			ASTAR();
 			break;
 		case BFS:
 			BFS();
 			break;
 		case DFS:
 			DFS();
 			break;
		}
		return this.grid;
	}
	
	/**
	 * Initialize objects used for the A* searching algorithm
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
	 * Initialize objects used for the Breadth First Search algorithm
	 * 
	 */
	private void initBFS() {
		BFS_openList = new LinkedList<Node>();
		closedList = new HashSet<Node>();
		BFS_openList.add(start);
	}
	
	/**
	 * Initialize objects used for the Depth First Search algorithm
	 * 
	 */
	private void initDFS() {
		DFS_openList = new Stack<Node>();
		closedList = new HashSet<Node>();
		DFS_openList.add(start);
	}
	
	/**
	 * 
	 *                      The A* Search Pathfinding Logic
	 * -------------------------------------------------------------------------------
	 * 
	 * The currentNode is popped from the PriorityQueue and added to the
	 * closedList Set.
	 * 
	 * The ASTAR_openList utilizes the PriorityQueue data structure
	 * which organizes the nodes from least to greatest based on their current 
	 * F value.
	 * 
	 * The currentCost is calculated by determining the distance between the
	 * currentNode and a neighbor. This is the F value, which is used to sift
	 * the PriorityQueue data structure.
	 * 
	 * The G cost is basically the distance between the starting node and a
	 * neighboring node. When added with the H value, this helps determine the
	 * shortest path between the start and goal node.
	 * 
	 * The if statement that compares the currentCost and the G value for a neighbor
	 * helps ensure that only the best nodes are kept during the search.
	 * 
	 * Nodes that have been searched have their types set to Enums.NodeType.VISITED,
	 * which is simply used in the GridPanel class to visualize which nodes have been
	 * searched/visited.
	 * 
	 * Only nodes with a greater G cost are added to the ASTAR_openList.
	 * 
	 */
	public void ASTAR() {
		if(ASTAR_openList.peek() != null) {
			currentNode = ASTAR_openList.poll();
			closedList.add(currentNode);
			
			// iterate through the neighbors surrounding the current node
			for(Node neighbor : grid.getNeighbors(currentNode)) {
				if(neighbor.getType().equals(Enums.NodeType.PASSABLE) ||
						!closedList.contains(neighbor)) {
					
					// checks if any of the neighboring nodes is the goal node
					if(neighbor == goal) {
						reversePath();
						getFinalPath();
						action = Enums.GridAction.SUCCESS;
						return;
					}
					
					int currentCost = currentNode.getG() + calculateH(currentNode, neighbor);
					
					if(currentCost < neighbor.getG() || !ASTAR_openList.contains(neighbor)) {
						neighbor.setG(currentCost);
						neighbor.setH(calculateH(neighbor, goal));
						neighbor.setParent(currentNode);
						
						if(neighbor != start && neighbor != goal) {
							neighbor.setType(Enums.NodeType.VISITED);
						}
						
						if(!ASTAR_openList.contains(neighbor)) {
							ASTAR_openList.add(neighbor);
						}
					}
				}
			}
		} else {
			// all nodes have been searched but the goal
			// could not be determined
			action = Enums.GridAction.FAIL;
			
		}
	}
	
	/**
	 * 
	 *                     The Breadth First Search Pathfinding Logic
	 * -------------------------------------------------------------------------------
	 * 
	 * The currentNode is polled from the BFS_openList queue data
	 * structure.
	 * 
	 * The BFS_openList is a queue which simply allows data to be accessed via
	 * first-in-first-out. It does not make any organize or compare the added nodes.
	 * 
	 * Each neighbor has their heuristic (H) value calculated by finding
	 * the Manhattan distance between the starting node and itself.
	 * 
	 * The H value is used to determine the shortest path between the start
	 * and goal node. If the H value of a neighboring node is less than the
	 * currentNode OR if the neighbor is NOT in the BFS_openList, the currentNode
	 * is set as the neighbors parent.
	 * 
	 * Nodes that have been searched have their types set to Enums.NodeType.VISITED,
	 * which is simply used in the GridPanel class to visualize which nodes have been
	 * searched/visited.
	 * 
	 * In comparison to the A* Search algorithm, the Breadth First Search adds all
	 * neighboring nodes into the open list, whereas, the A* algorithm compares
	 * their G values.
	 * 
	 */
	public void BFS() {
		if(BFS_openList.peek() != null) {
			currentNode = BFS_openList.poll();
			closedList.add(currentNode);
			
			for(Node neighbor : grid.getNeighbors(currentNode)) {
				if(neighbor.getType().equals(Enums.NodeType.PASSABLE) ||
						!closedList.contains(neighbor)) {
					
					neighbor.setH((calculateH(start, neighbor)));
					
					if(neighbor.getH() < currentNode.getH() || !BFS_openList.contains(neighbor)) {
						neighbor.setParent(currentNode);
					}
					
					// checks if a neighboring node is the goal node
					if(neighbor.getType().equals(Enums.NodeType.GOAL)) {
						reversePath();
						getFinalPath();
						action = Enums.GridAction.SUCCESS;
						return;
					} 
					
					if(neighbor != start && neighbor != goal) {
						neighbor.setType(Enums.NodeType.VISITED);
					}
					
					if(!BFS_openList.contains(neighbor)) {
						BFS_openList.add(neighbor);
					}
				}
			}
		} else {
			// all nodes have been searched but the goal
			// could not be determined
			action = Enums.GridAction.FAIL;
		}
	}
	
	/**
	 * 
	 *                     The Depth First Search Pathfinding Logic
	 * -------------------------------------------------------------------------------
	 * 
	 * The currentNode is polled from the DFS_openList stack. This data structure
	 * allows the nodes to be accessed via last-in-first-out.
	 * 
	 * The Depth First Search and Breadth First Search algorithms are nearly identical
	 * in implementation, however, the only difference is the data structure that is
	 * used to contain the nodes.
	 * 
	 * The difference in data structures drastically changes the behaviour of both
	 * algorithms. The BFS algorithm will search all surrounding nodes whereas the 
	 * DFS algorithm will search in a particular direction in order to reach the 
	 * farthest node and back.
	 * 
	 */
	public void DFS() {
		if(!DFS_openList.isEmpty()) {
			currentNode = DFS_openList.pop();
			closedList.add(currentNode);
			
			for(Node neighbor : grid.getNeighbors(currentNode)) {
				if(neighbor.getType().equals(Enums.NodeType.PASSABLE) ||
						!closedList.contains(neighbor)) {
					
					neighbor.setH((calculateH(start, neighbor)));
					
					if(neighbor.getH() < currentNode.getH() || !DFS_openList.contains(neighbor)) {
						neighbor.setParent(currentNode);
					}
					
					// checks if a neighboring node is the goal node
					if(neighbor.getType().equals(Enums.NodeType.GOAL)) {
						reversePath();
						getFinalPath();
						action = Enums.GridAction.SUCCESS;
						return;
					} 
					
					if(neighbor != start && neighbor != goal) {
						neighbor.setType(Enums.NodeType.VISITED);
					}
					
					if(!DFS_openList.contains(neighbor)) {
						DFS_openList.add(neighbor);
					}
				}
			}
		} else {
			// all nodes have been searched but the goal
			// could not be determined
			action = Enums.GridAction.FAIL;
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
	 * Reverses the final path by appending each parent node
	 * to a stack
	 * 
	 */
	private void reversePath() {
		pathList = new Stack<Node>();
		while(currentNode != start) {
			pathList.push(currentNode);
			currentNode = currentNode.getParent();
		}
	}
	
	/**
	 * Sets the nodes along the final path to
	 * Enums.NodeType.PATH and returns the current
	 * grid
	 * 
	 * @returns Grid current grid during pathing
	 */
	public Grid getFinalPath() {
		if(!pathList.empty()) {
			currentNode = pathList.pop();
			currentNode.setType(Enums.NodeType.PATH);
			return this.grid;
		} else {
			action = Enums.GridAction.COMPLETE;
			return this.grid;
		}
	}
	
	/**
	 * Returns the current grid action
	 * 
	 */
	public Enums.GridAction getAction() {
		return this.action;
	}
}
