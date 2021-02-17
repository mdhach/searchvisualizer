import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.*;

/**
 * This class is used to manage the main window created with
 * the Java Swing library
 * 
 */
public class Window {
	
	JFrame main;
	PanelController controller;
	
	public Window() {
		main = new JFrame("Search Visualizer");
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // stop app after window is closed
		
		controller = new PanelController();
		
		// add panels from controller object
		main.add(controller.getGrid());
		main.add(controller.getMenu());
		main.addMouseListener(controller.getMouseListener());
		main.addMouseMotionListener(controller.getMouseMotionListener());
		
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