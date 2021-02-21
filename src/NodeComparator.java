import java.util.Comparator;

/**
 * Used to override the comparator for PriorityQueue
 * 
 */
public class NodeComparator implements Comparator<Node> {

	@Override
	public int compare(Node arg0, Node arg1) {
		return Integer.compare(arg0.getF(), arg1.getF());
	}
}
