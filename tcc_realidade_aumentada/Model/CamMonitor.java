package Model;


import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;


import View.CamRAPanel;
import View.MainWindow;

import com.github.sarxos.webcam.Webcam;



public class CamMonitor implements Runnable{
	private MainWindow mainWindow;
	
	public CamMonitor(MainWindow mainWindow){
		this.mainWindow = mainWindow;
	}

	@Override
	public void run() {
		while(true){
			BufferedImage image = Webcam.getDefault().getImage();
			ImgAlgorithms imgAlgorithms = new ImgAlgorithms(image);
			imgAlgorithms.toBinary();
			CamRAPanel camRAPAnel = mainWindow.getRAPanel();
			camRAPAnel.setMaster(imgAlgorithms.getOutput());
			camRAPAnel.repaint();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
