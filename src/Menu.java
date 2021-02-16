import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Menu extends JPanel implements ActionListener {
	
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	protected JButton startSearch, reset;
	
	public Menu() {
		startSearch = new JButton("Start Search");
		reset = new JButton("Reset");
		add(startSearch);
		add(reset);
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
	}
}
