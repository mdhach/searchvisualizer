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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
	protected JLabel label;
	protected JComboBox<String> dropDownMenu;
	private String[] options;
	
	public ActionPanel() {
		PCS = new PropertyChangeSupport(this);
		
		actionType = Enums.PushActionType.IDLE;
		searchType = Enums.SearchType.ASTAR;
		
		
		options = new String[]{"A*", "Breadth First Search"};
		
		// init buttons
		searchButton = new JButton("Start Search");
		reset = new JButton("Reset");
		dropDownMenu = new JComboBox<String>(options);
		
		// create empty label with size and bounds
		label = new JLabel();
		
		// set action listeners
		searchButton.addActionListener(this);
		reset.addActionListener(this);
		dropDownMenu.addActionListener(this);
		
		// set action commands
		searchButton.setActionCommand("startSearch");
		reset.setActionCommand("reset");
		dropDownMenu.setActionCommand("option");
		
		
		setBorder(BorderFactory.createLineBorder(Color.black));
		
		// add buttons/labels to main frame
		add(searchButton);
		add(reset);
		add(label);
		add(dropDownMenu);
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
	 * Used to update the action
	 * 
	 */
	public void setAction(Enums.PushActionType newAction) {
		this.actionType = newAction;
		PropertyChangeEvent evt = new PropertyChangeEvent(this, PROPERTY, null, actionType);
		PCS.firePropertyChange(evt);
	}
	
	public Enums.SearchType getType() {
		return searchType;
	}
	
	public void setLabel(String text) {
		label.setText(text);
		label.setVisible(true);
		repaint();
	}
}