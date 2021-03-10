/**
 * Enum management class used to store multiple enums used
 * across the application
 * 
 */
public class Enums {
	
	public static enum NodeType {
		PASSABLE,
		IMPASSABLE,
		START,
		GOAL,
		PATH,
		VISITED
	}
	
	public static enum GridAction {
		INIT,
		IDLE,
		ADD,
		REMOVE,
		READY,
		SEARCHING,
		COMPLETE,
		SUCCESS,
		FAIL
	}
	
	public static enum PushActionType {
		IDLE,
		SEARCH,
		RESET,
		OPTION
	}
	
	public static enum MouseInputType {
		INIT,
		IDLE,
		LEFT_CLICK,
		RIGHT_CLICK,
		MIDDLE_CLICK,
		LEFT_HELD,
		RIGHT_HELD,
		RELEASED
	}
	
	public static enum SearchType {
		ASTAR,
		BFS,
		DFS
	}
	
}
