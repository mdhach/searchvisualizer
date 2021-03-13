import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * This class is used to manage two JPanels:
 * - GridPanel
 * - ActionPanel
 * 
 */
@SuppressWarnings("serial")
public class PanelController extends JPanel implements PropertyChangeListener {

	GridPanel gridPanel;
	ActionPanel actionPanel;
	GridMouseController mouse;
	private Enums.SearchType searchType;
	private String initMsg, searchMsg, successMsg, failMsg, noStartMsg, noGoalMsg;
	private int delay = 10; // iteration speed used in timer object; unit: ms
	private long timeScale = 1000000000; // convert nano seconds to seconds
	
	public PanelController() {
		// init strings
		initMsg = new String("Start placing some nodes!");
		searchMsg = new String("Searching...");
		successMsg = new String("Success! Path has been found.");
		failMsg = new String("Fail! Path cannot be determined.");
		noStartMsg = new String("Please place a beginning node!");
		noGoalMsg = new String("Please place a goal node!");
		
		// init JPanel objects
		gridPanel = new GridPanel();
		actionPanel = new ActionPanel();
		
		// init mouse controller and set this object as a listener
		mouse = new GridMouseController();
		actionPanel.addPropertyChangeListener(this);
		mouse.addPropertyChangeListener(this);
		
		// init the search type to ASTAR and the ActionPanel label
		searchType = Enums.SearchType.ASTAR;
		actionPanel.setStatus(initMsg);
	}
	
	@Override
	/**
	 * Listens for a property change from either the GridMouseController
	 * or the ActionPanel
	 * Makes changes accordingly based on the push action
	 * 
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		
		// listens for changes from the ActionPanel object
		if(evt.getPropertyName().equals(ActionPanel.PROPERTY)) {
			if(evt.getNewValue() instanceof Enums.PushActionType) {
				switch((Enums.PushActionType) evt.getNewValue()) {
					case IDLE:
						// stuff
						break;
					case SEARCH:
						gridPanelSearch();
						break;
					case RESET:
						mouse.allowAction(true);
						gridPanel.reset();
						actionPanel.setStatus(initMsg);
						break;
					case OPTION:
						searchType = actionPanel.getType();
						gridPanel.setType(searchType);
						break;
				}
			}
			
			// listens for change from the GridMouseController object
		} else if(evt.getPropertyName().equals(GridMouseController.PROPERTY)) {
			if(evt.getNewValue() instanceof Enums.MouseInputType) {
				gridPanel.setX(mouse.getX());
				gridPanel.setY(mouse.getY());
				gridPanel.setMouse((Enums.MouseInputType) evt.getNewValue());
				gridPanel.registerInput();
			}
		}
	}
	
	/**
	 * Returns the gridPanel as a JPanel object
	 * Used for JFrame initilization
	 * 
	 * @returns JPanel GridPanel gridPanel
	 */
	public JPanel getGrid() {
		return this.gridPanel;
	}
	
	/**
	 * Returns the actionPanel as a JPanel object
	 * Used for JFrame initialization
	 * 
	 * @returns JPanel ActionPanel actionPanel
	 */
	public JPanel getMenu() {
		return this.actionPanel;
	}
	
	/**
	 * Returns the GridMouseController object as a MouseListener object
	 * Used for JPanel initialization
	 * 
	 * @returns MouseListener GridMouseController mouse
	 */
	public MouseListener getMouseListener() {
		return this.mouse.returnAsMouseListener();
	}
	
	/**
	 * Returns the GridMouseController object as a MouseMotionListener object
	 * Used for JPanel initialization
	 * 
	 * @returns MouseMotionListener GridMouseController mouse
	 */
	public MouseMotionListener getMouseMotionListener() {
		return this.mouse.returnAsMouseMotionListener();
	}
	
	/**
	 * Iterates through the gridPanel object based on the 'delay'
	 * variable
	 * 
	 */
	private void gridPanelSearch() {
		
		if(gridPanel.getStartSet() && gridPanel.getGoalSet()) {
			actionPanel.showTime(false);
			if(gridPanel.getAction().equals(Enums.GridAction.COMPLETE)) {
				gridPanel.resetSearch();
			}
			actionPanel.setStatus(searchMsg);
			mouse.allowAction(false);
			gridPanel.initSearch(searchType);
			double startTime = System.nanoTime();
			
			Timer timer = new Timer(delay, new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					double stopTime = 0;
					switch(gridPanel.getAction()) {
					case INIT:
						gridPanel.iterateSearch();
						break;
					case SEARCHING:
						gridPanel.iterateSearch();
						break;
					case SUCCESS:
						gridPanel.iterateSearch();
						break;
					case COMPLETE:
						actionPanel.setStatus(successMsg);
						stopTime = System.nanoTime();
						actionPanel.setTime((stopTime-startTime)/timeScale);
						((Timer)e.getSource()).stop();
						break;
					case FAIL:
						actionPanel.setStatus(failMsg);
						stopTime = System.nanoTime();
						actionPanel.setTime((stopTime-startTime)/timeScale);
						((Timer)e.getSource()).stop();
						break;
					default:
						((Timer)e.getSource()).stop();
						break;
					}
				}
			});
			timer.start();
		} else {
			// set labels accordingly to notify user of missing essential nodes;
			// the start or goal node
			if(!gridPanel.getStartSet()) {
				actionPanel.setStatus(noStartMsg);
			} else {
				actionPanel.setStatus(noGoalMsg);
			}
		}
	}
}
