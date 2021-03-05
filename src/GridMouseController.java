import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.SwingUtilities;

/**
 * This class is used to manage the input received and pushed to the
 * GridPanel class.
 * 
 */
public class GridMouseController implements MouseListener, MouseMotionListener {
	
	public static final String PROPERTY = "mouse";
	private PropertyChangeSupport PCS;
	private boolean allowAction;
	private Enums.MouseInputType oldAction;
	private int x;
	private int y;
	
	public GridMouseController() {
		allowAction = true;
		PCS = new PropertyChangeSupport(this);
	}

	@Override
	/**
	 * Updates the coordinates for the left mouse click
	 * and pushes the action to the PanelController
	 * 
	 */
	public void mouseClicked(MouseEvent e) {
		// get coords
		x = e.getX();
		y = e.getY();
		
		// update mouse input type enum
		if(SwingUtilities.isLeftMouseButton(e)) {
			pushAction(Enums.MouseInputType.LEFT_CLICK);
		} else if(SwingUtilities.isRightMouseButton(e)) {
			//mouse = Enums.MouseInputType.RIGHT_CLICK;
		} else {
			pushAction(Enums.MouseInputType.MIDDLE_CLICK);
		}
	}

	@Override
	/**
	 * Checks placement of right mouse pressed and pushes the
	 * pressed action to the PanelController
	 * 
	 */
	public void mousePressed(MouseEvent e) {
		if(SwingUtilities.isRightMouseButton(e)) {
			x = e.getX();
			y = e.getY();
			pushAction(Enums.MouseInputType.RIGHT_CLICK);
		}
		
	}

	@Override
	/**
	 * Checks when user has released the mouse button
	 * Pushes an action to the PanelController which
	 * notifies the GridPanel object to set the mouse
	 * type enum to IDLE
	 */
	public void mouseReleased(MouseEvent e) {
		pushAction(Enums.MouseInputType.RELEASED);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// nothing planned yet
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// nothing planned yet
		
	}
	
	@Override
	/**
	 * Similarly to mousePressed, this method allows the user
	 * to paint impassable nodes by holding and dragging
	 * the right mouse button
	 * 
	 */
	public void mouseDragged(MouseEvent e) {
		if(SwingUtilities.isRightMouseButton(e)) {
			x = e.getX();
			y = e.getY();
			pushAction(Enums.MouseInputType.RIGHT_HELD);
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
	
	/**
	 * Used to initialize a listener object; in this case
	 * it would be the PanelController object
	 * 
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		PCS.addPropertyChangeListener(listener);
	}
	
	/**
	 * Pushes an action to the listener object; PanelController
	 * 
	 */
	private void pushAction(Enums.MouseInputType newAction) {
		if(allowAction && isBounded()) {
			PropertyChangeEvent evt = new PropertyChangeEvent(this, PROPERTY, oldAction, newAction);
			PCS.firePropertyChange(evt);
		}
	}
	
	/**
	 * Checks if the mouse input is within bounds of the GridPanel
	 * 
	 */
	private boolean isBounded() {
		if(x < (GridPanel.WIDTH+GridPanel.XOFFSET) && y < (GridPanel.HEIGHT+GridPanel.YOFFSET)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Used by the PanelController to get the x coordinate of the mouse input
	 * 
	 * @returns int x
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * Used by the PanelController to get the y coordinate of the mouse input
	 * 
	 * @returns int y
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * Returns the GridMouseController object as a MouseListener object
	 * Used for JPanel initialization
	 * 
	 * @returns MouseListener GridMouseController mouse
	 */
	public MouseListener returnAsMouseListener() {
		return this;
	}
	
	/**
	 * Returns the GridMouseController object as a MouseMotionListener object
	 * Used for JPanel initialization
	 * 
	 * @returns MouseMotionListener GridMouseController mouse
	 */
	public MouseMotionListener returnAsMouseMotionListener() {
		return this;
	}
	
	/**
	 * Allows the PanelController to disable the mouse during searching
	 * 
	 */
	public void allowAction(boolean newAction) {
		allowAction = newAction;
	}
}
