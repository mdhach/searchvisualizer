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
	
	public PanelController() {
		// init strings
		initMsg = new String("Start placing some nodes!");
		searchMsg = new String("Searching...");
		successMsg = new String("Success! Path has been found.");
		failMsg = new String("Fail! Path cannot be determined.");
		noStartMsg = new String("Please place a beginning node!");
		noGoalMsg = new String("Please place a goal node!");
		gridPanel = new GridPanel();
		actionPanel = new ActionPanel();
		mouse = new GridMouseController();
		actionPanel.addPropertyChangeListener(this);
		mouse.addPropertyChangeListener(this);
		searchType = Enums.SearchType.ASTAR;
		actionPanel.setLabel(initMsg);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
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
						actionPanel.setLabel(initMsg);
						break;
					case OPTION:
						searchType = actionPanel.getType();
						gridPanel.setType(searchType);
						break;
				}
			}
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
			actionPanel.setLabel(searchMsg);
			mouse.allowAction(false);
			gridPanel.initSearch(searchType);
			
			Timer timer = new Timer(delay, new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
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
						actionPanel.setLabel(successMsg);
						((Timer)e.getSource()).stop();
						break;
					case FAIL:
						actionPanel.setLabel(failMsg);
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
			if(!gridPanel.getStartSet()) {
				actionPanel.setLabel(noStartMsg);
			} else {
				actionPanel.setLabel(noGoalMsg);
			}
		}
	}
}
