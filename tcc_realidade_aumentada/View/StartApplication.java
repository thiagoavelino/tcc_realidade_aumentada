package View;

import java.awt.EventQueue;


public class StartApplication {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Model.CamMonitor camMonitor = new Model.CamMonitor();
				MainWindow mainWindow = new MainWindow();
				mainWindow.run();
				//camMonitor.run();
			}
		});
	}
}
