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
	private ArrayList<Pixel> centroidsTemp;
	
	public CamMonitor(MainWindow mainWindow){
		this.mainWindow = mainWindow;
	}

	@Override
	public void run() {
		
		int timeCallAlgorithm = 0;
		centroidsTemp = new ArrayList<Pixel>();
		Vector<HoughLine> linesTemp = new Vector<HoughLine>();
		
		while(true){
			BufferedImage image = Webcam.getDefault().getImage();
			
			if(mainWindow.isAlgoritmoLigado()){
				ImgAlgorithms imgAlgorithms = imageSegmentation(image);
				
				if(timeCallAlgorithm == 20){
					timeCallAlgorithm = 0;
					calculateCentroids(imgAlgorithms);
					linesTemp = houghTransform(imgAlgorithms); 
				}
				imgAlgorithms.setCentroids(centroidsTemp);
				String spinnerValue = mainWindow.getSpinnerKmeans().getValue().toString();
				float spinnerFloatValue = Float.parseFloat(spinnerValue);
				imgAlgorithms.setNumberClustersKmeans((int)spinnerFloatValue);
				imgAlgorithms.centroidsPainting(mainWindow.getAlgorithmSelected());
				
				PaintHougLines(linesTemp, imgAlgorithms);
				
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

	public void PaintHougLines(Vector<HoughLine> linesTemp,	ImgAlgorithms imgAlgorithms) {
		for (int j = 0; j < linesTemp.size(); j++) { 
		    HoughLine line = linesTemp.elementAt(j); 
		    line.draw(imgAlgorithms.getOutput(), Color.RED.getRGB()); 
		}
	}

	public Vector<HoughLine> houghTransform(ImgAlgorithms imgAlgorithms) {
		Vector<HoughLine> linesTemp;
		HoughTransform h = new HoughTransform(imgAlgorithms.getOutput().getWidth(), imgAlgorithms.getOutput().getHeight()); 
		h.addPoints(imgAlgorithms.getOutput()); 
		linesTemp = h.getLines(200);
		return linesTemp;
	}

	public void calculateCentroids(ImgAlgorithms imgAlgorithms) {
		imgAlgorithms.setImage(imgAlgorithms.getOutput());
		imgAlgorithms.calculateArrayCentroides();
		centroidsTemp = imgAlgorithms.getCentroids();
	}

	public ImgAlgorithms imageSegmentation(BufferedImage image) {
		ImgAlgorithms imgAlgorithms = new ImgAlgorithms(image);
		imgAlgorithms.setThreshold(mainWindow.getSliderThreshold().getValue());
		imgAlgorithms.toBinary();
		imgAlgorithms.erosion();
		return imgAlgorithms;
	}

	public ArrayList<Pixel> getCentroidsTemp() {
		return centroidsTemp;
	}

	public void setCentroidsTemp(ArrayList<Pixel> centroidsTemp) {
		this.centroidsTemp = centroidsTemp;
	}

}
