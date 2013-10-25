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



public class CamMonitor extends Thread {
	private MainWindow mainWindow;
	private ArrayList<Pixel> centroidsTemp;
	private BufferedImage image;
	private BufferedImage imageCamera;
	private Vector<HoughLine> linesTemp;
	private int timeCallAlgorithm;
	
	public CamMonitor(MainWindow mainWindow){
		this.mainWindow = mainWindow;
		timeCallAlgorithm = 0;
		centroidsTemp = new ArrayList<Pixel>();
		linesTemp = new Vector<HoughLine>();
	}

	@Override
	public void run() {
		
		while(true){
			image = Webcam.getDefault().getImage();
			ImgAlgorithms imgAlgorithms = imageSegmentation(image);
			imgAlgorithms.setCentroids(centroidsTemp);
			getInformation(imgAlgorithms);
			if(!mainWindow.isAlgoritmoLigado()){
				imgAlgorithms.setOutput(image);
				//PaintHougLines(linesTemp, imgAlgorithms);
			}
			CamRAPanel camRAPAnel = mainWindow.getRAPanel();
			String selectedAlgorithm = mainWindow.getAlgorithmSelected();
			camRAPAnel.setAlgorithm(selectedAlgorithm);
			switch(selectedAlgorithm){
				case"kmeans":
					String spinnerValue = mainWindow.getSpinnerKmeans().getValue().toString();
					float spinnerFloatValue = Float.parseFloat(spinnerValue);
					imgAlgorithms.setNumberClustersKmeans((int)spinnerFloatValue);
					imgAlgorithms.calculateKmeans();
					camRAPAnel.setDataKmeans(imgAlgorithms.getDataKmeans());
					camRAPAnel.setAlgorithm(selectedAlgorithm);
					break;
				default:
					camRAPAnel.setCentroids(centroidsTemp);
					break;
			}
			
			getInformation(imgAlgorithms);
			camRAPAnel.setMaster(imgAlgorithms.getOutput());
			camRAPAnel.revalidate();
			camRAPAnel.repaint();
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public void getInformation(ImgAlgorithms imgAlgorithms) {
		if(timeCallAlgorithm == 10){
			timeCallAlgorithm = 0;
			calculateCentroids(imgAlgorithms);
			linesTemp = houghTransform(imgAlgorithms); 
		}
		timeCallAlgorithm++;
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
