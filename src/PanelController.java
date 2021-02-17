import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PanelController extends JPanel implements PropertyChangeListener {

	NodeGrid grid;
	ActionMenu menu;
	GridMouseController mouse;
	
	public PanelController() {
		
		grid = new NodeGrid();
		menu = new ActionMenu();
		mouse = new GridMouseController();
		menu.addPropertyChangeListener(this);
		mouse.addPropertyChangeListener(grid);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals(ActionMenu.PROPERTY)) {
			if(evt.getNewValue() instanceof Enums.PushActionType) {
				Enums.PushActionType temp = (Enums.PushActionType) evt.getNewValue();
				switch(temp) {
					case IDLE:
						// stuff
						break;
					case SEARCH:
						System.out.println(evt.getNewValue());
						break;
					case RESET:
						System.out.println(evt.getNewValue());
						break;
					case ERROR:
						// stuff
						break;
				}
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
