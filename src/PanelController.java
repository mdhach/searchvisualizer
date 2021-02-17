import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PanelController extends JPanel implements PropertyChangeListener {

	NodeGrid grid;
	ActionMenu menu;
	
	public PanelController() {
		
		grid = new NodeGrid();
		menu = new ActionMenu();
		menu.addPropertyChangeListener(this);
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
	
	public MouseListener getGridAsMouseListener() {
		return this.grid.returnAsMouseListener();
	}
	
	public MouseMotionListener getGridAsMouseMotionListener() {
		return this.grid.returnAsMouseMotionListener();
	}
}
