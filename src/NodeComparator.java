import java.util.Comparator;

/**
 * Used to override the comparator for PriorityQueue
 * 
 */
public class NodeComparator implements Comparator<Node> {

	@Override
	public int compare(Node o1, Node o2) {
		if(o1.getF() > o2.getF()) {
			return 1;
		} else if(o1.getF() < o2.getF()) {
			return -1;
		} else {
			return 0;
		}
	}

}
