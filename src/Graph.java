import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Source: https://www.geeksforgeeks.org/implementing-generic-graph-in-java/
 */
public class Graph {

	private Map<Node, List<Node>> map;
	
	public Graph() {
		map = new HashMap<>();
	}
	
	/**
	 * Adds vertex to graph
	 * 
	 * @args Node vertex to be added
	 */
	public void addVertex(Node arg0) {
		Node vertex = arg0;
		
		if(!containsVertex(vertex)) {
			map.put(vertex, new LinkedList<Node>());
		}
	}
	
	/**
	 * Adds edge to vertex
	 * 
	 * @args Node vertex to add to
	 * @args Node edge to be added
	 * @args boolean true if edge is bidirectional; false otherwise
	 */
	public void addEdge(Node vertex, Node edge, boolean bidirectional) {
		
		// check if map already contains vertex and edge
		if(!containsVertex(vertex)) {
			addVertex(vertex);
		}
		if(!containsVertex(edge)) {
			addVertex(edge);
		}
		
		// add tail to head
		map.get(vertex).add(edge);
		
		// bidirectional, add the vertex
		// as an edge to the edge argument
		if(bidirectional) {
			map.get(edge).add(vertex);
		}
	}
	
	public List<Node> getVertices() {
		List<Node> vertices = new LinkedList<Node>();
		for(Node vertex : map.keySet()) {
			vertices.add(vertex);
		}
		return vertices;
	}
	
	public List<Node> getEdges(Node vertex) {
		List<Node> edges = new LinkedList<Node>();
		for(Node edge : map.get(vertex)) {
			edges.add(edge);
		}
		return edges;
	}
	
	/**
	 * Counts the numbers of vertices in graph
	 * 
	 * @returns int number of vertices
	 */
	public int getVerticesCount() {
		return map.keySet().size();
	}
	
	/**
	 * Counts the number of edges in graph
	 * 
	 * @args boolean true if bidirectional; false otherwise
	 * @returns int number of edges
	 */
	public int getEdgesCount(boolean bidirectional) {
		int count = 0;
		
		for(Node edge : map.keySet()) {
			count += map.get(edge).size();
		}
		if(bidirectional) {
			count /= 2;
		}
		return count;
	}
	
	/**
	 * Checks if graph contains vertex
	 * 
	 * @args Node vertex in question
	 * @returns boolean true if graph contains vertex; false otherwise
	 */
	public boolean containsVertex(Node vertex) {
		if(map.containsKey(vertex)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if vertex contains edge
	 * 
	 * @args Node vertex to be cheecked
	 * @args Node edge in question
	 * @returns boolean true if vertex contains edge; false otherwise
	 */
	public boolean containsEdge(Node vertex, Node edge) {
		if(map.get(vertex).contains(edge)) {
			return true;
		}
		return false;
	}
}
