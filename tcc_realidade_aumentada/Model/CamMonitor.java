package Model;


import java.awt.Color;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.util.ArrayList;
import java.util.Vector;

import LinearRegression.LinearRegression;
import View.CamRAPanel;
import View.MainWindow;

import com.github.sarxos.webcam.Webcam;



public class CamMonitor extends Thread {
	public static final int GETIMAGETIMEMILI = 500;
	public static final int NUMBERCYCLESGETINFORMATION = 10;
	private MainWindow mainWindow;
	private ArrayList<Pixel> centroidsTemp;
	private BufferedImage image;
	private BufferedImage imageCamera;
	private Vector<HoughLine> linesTemp;
	private int linearXTemp;
	private int linearYTemp;
	private int timeCallAlgorithm;
	private double[]regressionArray;
	private Pixel linearRegressionInit;
	private Pixel linearRegressionFinal;
	public static final double THRESHOLANGLE = 0.2;
	public static final double ANGLEX = Math.PI;
	public static final double ANGLEY = Math.PI/2;
	
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
				case"linear":
					LinearRegression lr = new LinearRegression();
					double[] xp = new double[centroidsTemp.size()];
					double[] yp = new double[centroidsTemp.size()];
					for(int i=0; i<centroidsTemp.size(); i++){
						xp[i] = centroidsTemp.get(i).getX();
						yp[i] = image.getHeight() - centroidsTemp.get(i).getY();
					}
					try {
						regressionArray = lr.getRegression(xp, yp);
						double x1 = 0;
						double y1 = x1*regressionArray[3] + regressionArray[4];
						y1 = Math.abs(y1-image.getHeight());
						
						double x2 = image.getWidth()-1;
						double y2 = x2*regressionArray[3] + regressionArray[4];
						y2 = Math.abs(y2-image.getHeight());
						camRAPAnel.setLinearRegressionInit(new Pixel((int)x1,(int)y1));
						camRAPAnel.setLinearRegressionFinal(new Pixel((int)x2,(int)y2));
						mainWindow.getFunctionLinear().setText("y = "+ Double.toString(regressionArray[3]) + "x + " 
																+ Double.toString(regressionArray[4]));
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
					
					break;
				default:
					camRAPAnel.setCentroids(centroidsTemp);
					break;
			}
			
			camRAPAnel.setAxeX(linearXTemp);
			camRAPAnel.setAxeY(linearYTemp);
			camRAPAnel.setMaster(imgAlgorithms.getOutput());
			camRAPAnel.revalidate();
			camRAPAnel.repaint();
			
			try {
				Thread.sleep(GETIMAGETIMEMILI);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public void defineAxes() {
		boolean selectedAxeX = false;
		boolean selectedAxeY = false;
		for(int i=0;i<linesTemp.size();i++){
			HoughLine selected = linesTemp.get(i);
			if( (selected.getTheta()<(ANGLEX+THRESHOLANGLE) && selected.getTheta()>(ANGLEX-THRESHOLANGLE))
					|| (selected.getTheta()<(THRESHOLANGLE) && selected.getTheta()>(-THRESHOLANGLE)) ){
				if(!selectedAxeX){
					selectedAxeX = true;
					linearXTemp = selected.getVertical(image.getWidth(), image.getHeight());
				}
			}
			if( selected.getTheta()<(ANGLEY+THRESHOLANGLE) && selected.getTheta()>(ANGLEY-THRESHOLANGLE)){
				if(!selectedAxeY){
					selectedAxeY = true;
					linearYTemp = selected.getHorizontal(image.getWidth(), image.getHeight());
				}
			}
		}
	}

	public void getInformation(ImgAlgorithms imgAlgorithms) {
		if(timeCallAlgorithm == NUMBERCYCLESGETINFORMATION){
			timeCallAlgorithm = 0;
			calculateCentroids(imgAlgorithms);
			linesTemp = houghTransform(imgAlgorithms); 
			defineAxes();
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
