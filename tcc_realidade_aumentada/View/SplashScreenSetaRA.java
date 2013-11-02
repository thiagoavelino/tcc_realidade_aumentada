package View;

import java.awt.*;
import java.awt.event.*;

import javax.swing.SwingWorker;

public class SplashScreenSetaRA extends Frame implements ActionListener {
    static void renderSplashFrame(Graphics2D g) {
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(120,140,200,40);
        g.setPaintMode();
        g.setColor(Color.WHITE);
        g.drawString("Loading Application...", 350, 300);
    }
    public SplashScreenSetaRA() {
        final SplashScreen splash = SplashScreen.getSplashScreen();
        if (splash == null) {
            System.out.println("SplashScreen.getSplashScreen() returned null");
            return;
        }
        Graphics2D g = splash.createGraphics();
        if (g == null) {
            System.out.println("g is null");
            return;
        }
      
        renderSplashFrame(g);
        splash.update();     
        
        final MainWindow mainWindow = new MainWindow();
		mainWindow.run();
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			   @Override
			   protected Void doInBackground() throws Exception {
				   Model.CamMonitor camMonitor = new Model.CamMonitor(mainWindow);
				   camMonitor.run();
				   splash.close();
			    return null;
			   }
		};
		worker.execute();
		
    }
    public void actionPerformed(ActionEvent ae) {
        System.exit(0);
    }
    
    private static WindowListener closeWindow = new WindowAdapter(){
        public void windowClosing(WindowEvent e){
            e.getWindow().dispose();
        }
    };
}
