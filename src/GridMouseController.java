import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.SwingUtilities;

public class GridMouseController implements MouseListener, MouseMotionListener {
	
	public static final String PROPERTY = "mouse";
	private PropertyChangeSupport PCS;
	private boolean allowAction;
	private Enums.MouseInputType mouse;
	private int x;
	private int y;
	private Hashtable<List<Integer>, Enums.MouseInputType> gridInput = new Hashtable<>();
	
	public GridMouseController() {
		allowAction = true;
		mouse = Enums.MouseInputType.IDLE;
		PCS = new PropertyChangeSupport(this);
		gridInput.put(Arrays.asList(new Integer[] {10000,10000}), Enums.MouseInputType.IDLE);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(allowAction) {
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
			
			//setAction();
			//registerInput();
		}
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(allowAction) {
			if(SwingUtilities.isRightMouseButton(e)) {
				x = e.getX();
				y = e.getY();
				pushAction(Enums.MouseInputType.RIGHT_CLICK);
				
				//setAction();
				//registerInput();
			}
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
		if(allowAction) {
			if(SwingUtilities.isRightMouseButton(e)) {
				x = e.getX();
				y = e.getY();
				pushAction(Enums.MouseInputType.RIGHT_HELD);
				
//				if(setLoc()) {
//					registerInput();
//				}
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		PCS.addPropertyChangeListener(listener);
	}
	
	public void pushAction(Enums.MouseInputType newAction) {
		this.mouse = newAction;
		Hashtable<List<Integer>, Enums.MouseInputType> oldAction = gridInput;
		Hashtable<List<Integer>, Enums.MouseInputType> pushAction = new Hashtable<>();
		pushAction.put(Arrays.asList(new Integer[] {x,y}), mouse);
		gridInput = pushAction;
		PropertyChangeEvent evt = new PropertyChangeEvent(this, PROPERTY, oldAction, pushAction);
		PCS.firePropertyChange(evt);
	}
	
	public MouseListener returnAsMouseListener() {
		return this;
	}
	
	public MouseMotionListener returnAsMouseMotionListener() {
		return this;
	}
	
	public void setAllowAction(boolean newAction) {
		allowAction = newAction;
	}
}
