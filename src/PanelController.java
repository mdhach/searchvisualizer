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
			System.out.println("Old property: " + evt.getOldValue() + " | New Property: " + evt.getNewValue());
		}
	}
	
	public JPanel getGrid() {
		return grid;
	}
	
	public JPanel getMenu() {
		return menu;
	}
	
	public MouseListener getGridAsMouseListener() {
		return grid.returnAsMouseListener();
	}
	
	public MouseMotionListener getGridAsMouseMotionListener() {
		return grid.returnAsMouseMotionListener();
	}
}
