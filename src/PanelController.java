import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PanelController extends JPanel implements PropertyChangeListener {

	GridPanel gridPanel;
	ActionPanel menuPanel;
	GridMouseController mouse;
	
	public PanelController() {
		gridPanel = new GridPanel();
		menuPanel = new ActionPanel();
		mouse = new GridMouseController();
		menuPanel.addPropertyChangeListener(this);
		mouse.addPropertyChangeListener(this);
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
						if(gridPanel.startSearch(Enums.SearchType.ASTAR)) {
							mouse.allowAction(true);
						}
						break;
					case RESET:
						gridPanel.reset();
						break;
					case ERROR:
						// stuff
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
		return this.menuPanel;
	}
	
	public MouseListener getMouseListener() {
		return this.mouse.returnAsMouseListener();
	}
	
	public MouseMotionListener getMouseMotionListener() {
		return this.mouse.returnAsMouseMotionListener();
	}
}
