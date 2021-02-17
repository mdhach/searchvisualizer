import javax.swing.SwingUtilities;

/**
 * Main class
 * 
 */
public class Application {
	
	private static void initWindow() {
		Window window = new Window();
	}
	
	public static void main(String[] args) {
		// creates an instance of the window and makes it visible
		// main is only called once
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initWindow();
			}
		});
	}
}