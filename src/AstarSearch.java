import java.util.Hashtable;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.HashSet;

public class AstarSearch {
	
	private Hashtable<List<Integer>, Node> grid;
	private PriorityQueue<Node> openList;
	private Set<Node> closedList;
	private NodeComparator comparator;
	
	public AstarSearch(Hashtable<List<Integer>, Node> val) {
		grid = val;
		comparator = new NodeComparator();
		openList = new PriorityQueue<Node>(11, comparator);
		closedList = new HashSet<Node>();
	}
	
	public Hashtable<List<Integer>, Node> startSearch() {
		return this.grid;
	}
	
}
