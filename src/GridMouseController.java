import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.SwingUtilities;

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
	public void mousePressed(MouseEvent e) {
		if(SwingUtilities.isRightMouseButton(e)) {
			x = e.getX();
			y = e.getY();
			pushAction(Enums.MouseInputType.RIGHT_CLICK);
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		pushAction(Enums.MouseInputType.RELEASED);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if(SwingUtilities.isRightMouseButton(e)) {
			x = e.getX();
			y = e.getY();
			pushAction(Enums.MouseInputType.RIGHT_HELD);
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		PCS.addPropertyChangeListener(listener);
	}
	
	private void pushAction(Enums.MouseInputType newAction) {
		if(allowAction) {
			PropertyChangeEvent evt = new PropertyChangeEvent(this, PROPERTY, oldAction, newAction);
			PCS.firePropertyChange(evt);
		}
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public MouseListener returnAsMouseListener() {
		return this;
	}
	
	public MouseMotionListener returnAsMouseMotionListener() {
		return this;
	}
	
	public void allowAction(boolean newAction) {
		allowAction = newAction;
	}
}
