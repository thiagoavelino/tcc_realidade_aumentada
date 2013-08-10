package View;

import java.awt.EventQueue;

import javax.swing.SwingWorker;


public class StartApplication {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				final MainWindow mainWindow = new MainWindow();
				mainWindow.run();
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
					   @Override
					   protected Void doInBackground() throws Exception {
						   Model.CamMonitor camMonitor = new Model.CamMonitor(mainWindow);
						   camMonitor.run();
					    return null;
					   }
				};
				worker.execute();
			}
		});
	}
}
