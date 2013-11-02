package View;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.SwingWorker;
import javax.swing.Timer;

import com.github.sarxos.webcam.Webcam;


public class StartApplication {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				SplashScreenSetaRA test = new SplashScreenSetaRA();				
			}
		});
	}
}
