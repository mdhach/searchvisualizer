import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.*;

/**
 * This class is used to manage the main window created with
 * the Java Swing library
 * 
 */
public class Window {
	
	JFrame main;
	
	public Window() {
		main = new JFrame("Search Visualizer");
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // stop app after window is closed
		Board board = new Board();
		Menu menu = new Menu();
		
		// add JPanels to frame
		main.add(board);
		main.add(menu);
		main.addMouseListener(board); // add board as a mouse listener object
		main.addMouseMotionListener(board); // add board as a mouse motion listener object
		
		// change frame layout
		Container contentPane = main.getContentPane();
		contentPane.setLayout(new FlowLayout());
		
		// update frame
		main.setResizable(false); // disables resizable window feature
		main.pack(); // resizes frame to ensure stability across different platforms
		main.setLocationRelativeTo(null); // opens window at center of screen
		main.setVisible(true); // enable display
	}
}