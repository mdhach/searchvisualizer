import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ActionPanel extends JPanel implements ActionListener {
	
	public static final String PROPERTY = "action";
	private PropertyChangeSupport PCS;
	
	private Enums.PushActionType actionType;
	
	private static final int WIDTH = 200;
	private static final int HEIGHT = 600;
	
	protected JButton searchButton, reset;
	protected JLabel label;
	//private String searching, success, fail, resetting;
	
	public ActionPanel() {
		PCS = new PropertyChangeSupport(this);
		
		actionType = Enums.PushActionType.IDLE;
		
		// init strings
//		searching = new String("Seaching...");
//		success = new String("Success! Path has been found.");
//		fail = new String("Failure! Path cannot be determined.");
//		resetting = new String("Resetting...");
		
		// init buttons and label
		searchButton = new JButton("Start Search");
		reset = new JButton("Reset");
		
		// create empty label with size and bounds
		label = new JLabel();
		
		// set action listeners
		searchButton.addActionListener(this);
		reset.addActionListener(this);
		
		// set action commands
		searchButton.setActionCommand("startSearch");
		reset.setActionCommand("reset");
		
		setBorder(BorderFactory.createLineBorder(Color.black));
		
		// add buttons/labels to main frame
		add(searchButton);
		add(reset);
		add(label);
	}
	
	@Override
	/**
	 * Overrides the Dimension method and reinitializes
	 * the window size of the JPanel
	 *
	 * @return Dimension object initialized with constants
	 */
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}
	
	/**
	 * Initializes the paint component from the JPanel library
	 * 
	 * @param Graphics object that is to be drawn
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if("startSearch".equals(e.getActionCommand())) {
			Enums.PushActionType newAction = Enums.PushActionType.SEARCH;
			setAction(newAction);
		} else if("reset".equals(e.getActionCommand())) {
			Enums.PushActionType newAction = Enums.PushActionType.RESET;
			setAction(newAction);
		}
	}
	
	/**
	 * Sets the PanelController class as a PropertyChangeListener object
	 * 
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		PCS.addPropertyChangeListener(listener);
	}
	
	/**
	 * Used to update the action
	 * 
	 */
	public void setAction(Enums.PushActionType newAction) {
		Enums.PushActionType oldAction = this.actionType;
		this.actionType = newAction;
		PropertyChangeEvent evt = new PropertyChangeEvent(this, PROPERTY, oldAction, newAction);
		PCS.firePropertyChange(evt);
	}
}