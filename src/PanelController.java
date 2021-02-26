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
	private String searching, success, fail, resetting;
	
	public PanelController() {
		// init strings
		searching = new String("Searching...");
		success = new String("Success! Path has been found.");
		fail = new String("Failure! Path cannot be determined.");
		resetting = new String("Resetting...");
		gridPanel = new GridPanel();
		actionPanel = new ActionPanel();
		mouse = new GridMouseController();
		actionPanel.addPropertyChangeListener(this);
		mouse.addPropertyChangeListener(this);
		searchType = Enums.SearchType.ASTAR;
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
					case RESET:
						mouse.allowAction(true);
						gridPanel.reset();
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
	
	public JPanel getGrid() {
		return this.gridPanel;
	}
	
	public JPanel getMenu() {
		return this.actionPanel;
	}
	
	public MouseListener getMouseListener() {
		return this.mouse.returnAsMouseListener();
	}
	
	public MouseMotionListener getMouseMotionListener() {
		return this.mouse.returnAsMouseMotionListener();
	}
	
	private void gridPanelSearch() {
		actionPanel.setLabel(searching);
		mouse.allowAction(false);
		gridPanel.startSearch(searchType);
	}
}
