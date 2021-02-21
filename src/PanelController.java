import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PanelController extends JPanel implements PropertyChangeListener {

	GridPanel grid;
	ActionPanel menu;
	GridMouseController mouse;
	
	public PanelController() {
		grid = new GridPanel();
		menu = new ActionPanel();
		mouse = new GridMouseController();
		menu.addPropertyChangeListener(this);
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
						if(grid.startSearch(Enums.SearchType.ASTAR)) {
							mouse.allowAction(true);
						}
						break;
					case RESET:
						grid.reset();
						break;
					case ERROR:
						// stuff
						break;
				}
			}
		} else if(evt.getPropertyName().equals(GridMouseController.PROPERTY)) {
			if(evt.getNewValue() instanceof Enums.MouseInputType) {
				grid.setX(mouse.getX());
				grid.setY(mouse.getY());
				grid.setMouse((Enums.MouseInputType) evt.getNewValue());
				grid.registerInput();
			}
		}
	}
	
	public JPanel getGrid() {
		return this.grid;
	}
	
	public JPanel getMenu() {
		return this.menu;
	}
	
	public MouseListener getMouseListener() {
		return this.mouse.returnAsMouseListener();
	}
	
	public MouseMotionListener getMouseMotionListener() {
		return this.mouse.returnAsMouseMotionListener();
	}
}
