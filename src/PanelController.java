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
	private String init, searching, success, fail;
	private int delay = 10;
	
	public PanelController() {
		// init strings
		init = new String("Start placing some nodes!");
		searching = new String("Searching...");
		success = new String("Success! Path has been found.");
		fail = new String("Fail! Path cannot be determined.");
		gridPanel = new GridPanel();
		actionPanel = new ActionPanel();
		mouse = new GridMouseController();
		actionPanel.addPropertyChangeListener(this);
		mouse.addPropertyChangeListener(this);
		searchType = Enums.SearchType.ASTAR;
		actionPanel.setLabel(init);
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
						actionPanel.setLabel(searching);
						gridPanelSearch();
						break;
					case RESET:
						mouse.allowAction(true);
						gridPanel.reset();
						actionPanel.setLabel(init);
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
		mouse.allowAction(false);
		gridPanel.initSearch(searchType);
		
		Timer timer = new Timer(delay, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(gridPanel.getAction().equals(Enums.GridAction.SEARCHING) ||
						gridPanel.getAction().equals(Enums.GridAction.SUCCESS) ||
						gridPanel.getAction().equals(Enums.GridAction.INIT)) {
					gridPanel.iterateSearch();
				} else {
					if(gridPanel.getAction().equals(Enums.GridAction.COMPLETE)) {
						actionPanel.setLabel(success);
					} else if(gridPanel.getAction().equals(Enums.GridAction.FAIL)) {
						actionPanel.setLabel(fail);
					}
					((Timer)e.getSource()).stop();
				}
			}
		});
		timer.start();
	}
}
