package Model;


import java.awt.Color;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.util.ArrayList;
import java.util.Vector;

import WekaLib.Data;
import LinearRegression.LinearRegression;
import PolyRegression.PolynomialRegression;
import View.CamRAPanel;
import View.MainWindow;
import WekaLib.Wekalib;

import com.github.sarxos.webcam.Webcam;



public class CamMonitor extends Thread {
	public static final int GETIMAGETIMEMILI = 1000;
	public static final int NUMBERCYCLESGETINFORMATION = 5;
	private MainWindow mainWindow;
	private ArrayList<Pixel> centroidsTemp;
	private BufferedImage image;
	private Vector<HoughLine> linesTemp;
	private int linearXTemp;
	private int linearYTemp;
	private int timeCallAlgorithm;
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
			if(!mainWindow.getChckbxDesativarRa().isSelected()){
				ImgAlgorithms imgAlgorithms = imageSegmentation(image);
				imgAlgorithms.setCentroids(centroidsTemp);
				getInformation(imgAlgorithms);
				if(!mainWindow.getChckbxImgBinria().isSelected()){
					imgAlgorithms.setOutput(image);
				}
				CamRAPanel camRAPAnel = mainWindow.getRAPanel();
				String selectedAlgorithm = mainWindow.getAlgorithmSelected();
				camRAPAnel.setAlgorithm(selectedAlgorithm);
				camRAPAnel.setAxesOn(mainWindow.getChckbxEixos().isSelected());
				String spinnerValue;
				float spinnerFloatValue;
				switch(selectedAlgorithm){
					case"kmeans":
						spinnerValue = mainWindow.getSpinnerKmeans().getValue().toString();
						spinnerFloatValue = Float.parseFloat(spinnerValue);
						try {
							Wekalib simpleKmeans = new Wekalib(centroidsTemp,(int)spinnerFloatValue);
							camRAPAnel.setDataClusterAlgorithm(simpleKmeans.calculateKmeans());
							camRAPAnel.setAlgorithm(selectedAlgorithm);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						break;
					case"farthestfirst":
						spinnerValue = mainWindow.getSpinnerFarthestFirst().getValue().toString();
						spinnerFloatValue = Float.parseFloat(spinnerValue);
						try {
							Wekalib farthestFirst = new Wekalib(centroidsTemp,(int)spinnerFloatValue);
							camRAPAnel.setDataClusterAlgorithm(farthestFirst.calculateFarthestFirst());
							camRAPAnel.setAlgorithm(selectedAlgorithm);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						break;
					case"hierarchical":
						spinnerValue = mainWindow.getSpinnerHierarchical().getValue().toString();
						spinnerFloatValue = Float.parseFloat(spinnerValue);
						try {
							Wekalib hierarchical = new Wekalib(centroidsTemp,(int)spinnerFloatValue);
							hierarchical.setLinkType(mainWindow.getLinkTypesComboBox().getSelectedItem().toString());
							camRAPAnel.setDataClusterAlgorithm(hierarchical.calculateHierarchical());
							camRAPAnel.setAlgorithm(selectedAlgorithm);
						} catch (Exception e1) {
						e1.printStackTrace();
					}
					break;
					case"linear":
						if(centroidsTemp.size()>0){
							PolynomialRegression poliReg = calculatePolinomial(camRAPAnel,1);
							PolynomialRegression poliReal = calculatePolinomialRealFunction(1);
							mainWindow.getFunctionLinear().setText(poliReal.toString());
						}
						break;
					case"polinomial":
						if(centroidsTemp.size()>0){
							String spinnerPoly = mainWindow.getSpinnerPolinomio().getValue().toString();
							float spinnerPolyFloat = Float.parseFloat(spinnerPoly);
							PolynomialRegression poliRegValue = calculatePolinomial(camRAPAnel,(int)spinnerPolyFloat);
							PolynomialRegression poliReal = calculatePolinomialRealFunction((int)spinnerPolyFloat);
							mainWindow.getFunctionPolinomioValue().setText(poliReal.toString());
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
			}
			else{
				CamRAPanel camRAPAnel = mainWindow.getRAPanel();
				camRAPAnel.setAlgorithm("none");
				camRAPAnel.setMaster(image);
				camRAPAnel.revalidate();
				camRAPAnel.repaint();
			}
			
			try {
				Thread.sleep(GETIMAGETIMEMILI);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public PolynomialRegression calculatePolinomial(CamRAPanel camRAPAnel, int degree) {
		double[] xPoly = new double[centroidsTemp.size()];
		double[] yPoly = new double[centroidsTemp.size()];
		for(int i=0; i<centroidsTemp.size(); i++){
			xPoly[i] = centroidsTemp.get(i).getX();
			yPoly[i] = image.getHeight() - centroidsTemp.get(i).getY();
		}
		PolynomialRegression pReg = new PolynomialRegression(xPoly, yPoly, degree);
		ArrayList<Data> dataPolinomial = new ArrayList<Data>();
		for(int x=0; x<image.getWidth(); x++){
			double yCalc = pReg.calculatePoly(x);
			if(yCalc<image.getHeight())	dataPolinomial.add(new Data(x,Math.abs(yCalc-image.getHeight())));
			else dataPolinomial.add(new Data(x,Math.abs(yCalc+image.getHeight())));
		}
		camRAPAnel.setDataPolinomial(dataPolinomial);
		return pReg;
	}
	
	public PolynomialRegression calculatePolinomialRealFunction(int degree) {
		double[] xPoly = new double[centroidsTemp.size()];
		double[] yPoly = new double[centroidsTemp.size()];
		int yReal = image.getHeight() - this.linearYTemp;
		for(int i=0; i<centroidsTemp.size(); i++){
			xPoly[i] = centroidsTemp.get(i).getX() - this.linearXTemp;
			yPoly[i] = image.getHeight() - centroidsTemp.get(i).getY();
			yPoly[i] = yPoly[i] - yReal;
		}
		PolynomialRegression pReg = new PolynomialRegression(xPoly, yPoly, degree);
		return pReg;
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
