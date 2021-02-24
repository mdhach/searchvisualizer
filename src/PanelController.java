import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

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
	
	public PanelController() {
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
						mouse.allowAction(false);
						if(gridPanel.startSearch(searchType)) {
							mouse.allowAction(true);
						}
						break;
					case RESET:
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
}
