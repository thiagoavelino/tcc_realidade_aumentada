package Model;


import java.awt.Color;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.util.ArrayList;
import java.util.Vector;


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
		Vector<HoughLine> linesTemp = new Vector<HoughLine>();
		
		while(true){
			BufferedImage image = Webcam.getDefault().getImage();
			
			if(mainWindow.isAlgoritmoLigado()){
				ImgAlgorithms imgAlgorithms = imageSegmentation(image);
				imgAlgorithms.setCentroids(centroidsTemp);
				if(timeCallAlgorithm == 20){
					timeCallAlgorithm = 0;
					centroidsTemp = calculateCentroids(imgAlgorithms);
					linesTemp = houghTransform(imgAlgorithms); 
				}
				String spinnerValue = mainWindow.getSpinnerKmeans().getValue().toString();
				float spinnerFloatValue = Float.parseFloat(spinnerValue);
				imgAlgorithms.setNumberClustersKmeans((int)spinnerFloatValue);
				imgAlgorithms.centroidsPainting(mainWindow.getAlgorithmSelected());
				//houghlines
				for (int j = 0; j < linesTemp.size(); j++) { 
		            HoughLine line = linesTemp.elementAt(j); 
		            line.draw(imgAlgorithms.getOutput(), Color.RED.getRGB()); 
		        }
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

	public Vector<HoughLine> houghTransform(ImgAlgorithms imgAlgorithms) {
		Vector<HoughLine> linesTemp;
		HoughTransform h = new HoughTransform(imgAlgorithms.getOutput().getWidth(), imgAlgorithms.getOutput().getHeight()); 
		h.addPoints(imgAlgorithms.getOutput()); 
		linesTemp = h.getLines(200);
		return linesTemp;
	}

	public ArrayList<Pixel> calculateCentroids(ImgAlgorithms imgAlgorithms) {
		ArrayList<Pixel> centroidsTemp;
		imgAlgorithms.setImage(imgAlgorithms.getOutput());
		imgAlgorithms.calculateArrayCentroides();
		centroidsTemp = imgAlgorithms.getCentroids();
		return centroidsTemp;
	}

	public ImgAlgorithms imageSegmentation(BufferedImage image) {
		ImgAlgorithms imgAlgorithms = new ImgAlgorithms(image);
		imgAlgorithms.setThreshold(mainWindow.getSliderThreshold().getValue());
		imgAlgorithms.toBinary();
		imgAlgorithms.erosion();
		return imgAlgorithms;
	}

}
