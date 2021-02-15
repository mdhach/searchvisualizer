import javax.swing.*;

/**
 * This class is used to manage the main window created with
 * the Java Swing library
 * 
 */
public class Window {
	
	JFrame main;
	
	public Window() {
		main = new JFrame("Search Visualizer"); // init JFrame object
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // stop app after window is closed
		Board board = new Board(); // init GUI JPanel object
		main.add(board); // add the GUI JPanel object to the JFrame
		main.addMouseListener(board); // add mouse listener object
		main.addMouseMotionListener(board);
		main.setResizable(false); // disables resizable window feature
		main.pack(); // resizes frame to ensure stability across different platforms
		main.setLocationRelativeTo(null); // opens window at center of screen
		main.setVisible(true); // enable display
	}
}