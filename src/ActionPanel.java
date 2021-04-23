import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.BoxLayout;

/**
 * This JPanel is used to allow the search to begin
 * 
 */
@SuppressWarnings("serial")
public class ActionPanel extends JPanel implements ActionListener {
	
	public static final String PROPERTY = "action";
	private PropertyChangeSupport PCS;
	
	private Enums.PushActionType actionType;
	private Enums.SearchType searchType;
	
	private static final int WIDTH = 200;
	private static final int HEIGHT = 600;
	
	protected JButton searchButton, reset;
	protected JLabel statusLabel, timeLabel;
	protected JComboBox<String> dropDownMenu;
	private String[] options;
	private static DecimalFormat decimalFormat = new DecimalFormat("#.##");
	
	public ActionPanel() {
		PCS = new PropertyChangeSupport(this);
		
		actionType = Enums.PushActionType.IDLE;
		searchType = Enums.SearchType.ASTAR;
		
		options = new String[]{"A*", "Breadth First Search", "Depth First Search"};
		
		// init buttons
		searchButton = new JButton("Start Search");
		reset = new JButton("Reset");
		dropDownMenu = new JComboBox<String>(options);
		
		// create empty label with size and bounds
		statusLabel = new JLabel();
		statusLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
		timeLabel = new JLabel();
		timeLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
		
		// set action listeners
		searchButton.addActionListener(this);
		reset.addActionListener(this);
		dropDownMenu.addActionListener(this);
		
		// set action commands
		searchButton.setActionCommand("startSearch");
		reset.setActionCommand("reset");
		dropDownMenu.setActionCommand("option");
		
		// set size of drop down menu
		dropDownMenu.setMinimumSize(new Dimension(200,25));
		dropDownMenu.setPreferredSize(new Dimension(200,25));
		dropDownMenu.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		// add buttons/labels to main frame
		// create rigid areas for button spacing
		add(Box.createRigidArea(new Dimension(0,0)));
		add(searchButton);
		add(reset);
		add(dropDownMenu);
		add(Box.createRigidArea(new Dimension(0,400)));
		add(statusLabel);
		add(timeLabel);
		add(Box.createGlue());
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
	/**
	 * Listens for an action that has been performed by the user
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		
		// search action
		if("startSearch".equals(e.getActionCommand())) {
			Enums.PushActionType newAction = Enums.PushActionType.SEARCH;
			setAction(newAction);
		
		// reset action
		} else if("reset".equals(e.getActionCommand())) {
			Enums.PushActionType newAction = Enums.PushActionType.RESET;
			timeLabel.setVisible(false); // hides time label after reset
			setAction(newAction);
			
		// allows user to choose between multiple search type options
		} else if("option".equals(e.getActionCommand())) {
			JComboBox<?> cb = (JComboBox<?>)e.getSource();
			String algo = (String)cb.getSelectedItem();
			switch(algo) {
				case "A*":
					searchType = Enums.SearchType.ASTAR;
					break;
				case "Breadth First Search":
					searchType = Enums.SearchType.BFS;
					break;
				case "Depth First Search":
					searchType = Enums.SearchType.DFS;
					break;
			}
			Enums.PushActionType newAction = Enums.PushActionType.OPTION;
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
	 * Notifies the PanelController class which action has been performed
	 * 
	 */
	public void setAction(Enums.PushActionType newAction) {
		this.actionType = newAction;
		PropertyChangeEvent evt = new PropertyChangeEvent(this, PROPERTY, null, actionType);
		PCS.firePropertyChange(evt);
	}
	
	/**
	 * Returns the search type
	 * 
	 */
	public Enums.SearchType getType() {
		return searchType;
	}
	
	/**
	 * Displays the execution time of the search algorithm
	 * 
	 */
	public void setTime(double time) {
		timeLabel.setText("Time Finished: " + decimalFormat.format(time) + "s");
		timeLabel.setVisible(true);
		repaint();
	}
	
	/**
	 * Displays the current status of the application
	 * 
	 */
	public void setStatus(String text) {
		statusLabel.setText(text);
		statusLabel.setVisible(true);
		repaint();
	}
}