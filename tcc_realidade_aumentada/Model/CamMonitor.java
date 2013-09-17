package Model;


import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.util.ArrayList;


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
		int timeCallAlgorithm = 0;
		ArrayList<Pixel> centroidsTemp = new ArrayList<Pixel>();
		while(true){
			BufferedImage image = Webcam.getDefault().getImage();
			
			
			if(mainWindow.isAlgoritmoLigado()){
				ImgAlgorithms imgAlgorithms = new ImgAlgorithms(image);
				imgAlgorithms.setThreshold(mainWindow.getSliderThreshold().getValue());
				imgAlgorithms.toBinary();
				imgAlgorithms.erosion();
				imgAlgorithms.setCentroids(centroidsTemp);
				if(timeCallAlgorithm == 20){
					timeCallAlgorithm = 0;
					imgAlgorithms.setImage(imgAlgorithms.getOutput());
					imgAlgorithms.calculateArrayCentroides();
					centroidsTemp = imgAlgorithms.getCentroids();
				}
				imgAlgorithms.augumentedReality();
				CamRAPanel camRAPAnel = mainWindow.getRAPanel();
				camRAPAnel.setMaster(imgAlgorithms.getOutput());
				camRAPAnel.repaint();
				timeCallAlgorithm++;
			}else{
				ImgAlgorithms imgAlgorithms = new ImgAlgorithms(image);
				imgAlgorithms.setThreshold(mainWindow.getSliderThreshold().getValue());
				imgAlgorithms.toBinary();
				CamRAPanel camRAPAnel = mainWindow.getRAPanel();
				camRAPAnel.setMaster(imgAlgorithms.getImage());
				camRAPAnel.repaint();
			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
