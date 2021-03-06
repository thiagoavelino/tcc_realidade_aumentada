package Model;

import KMeansPackage.*;
import ij.ImagePlus;
import ij.process.ImageProcessor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

public class ImgAlgorithms {
	
	public final int WIDTH_SCREEN = 640;
	public final int HEIGHT_SCREEN = 480;
	private BufferedImage image;
	private BufferedImage output;
	private ArrayList<LabelArea> labeledAreas;
	private ArrayList<Pixel> centroids;
	private int threshold;
	private ArrayList<Color> colors;
	private int numberClustersKmeans;
	private ArrayList<Ponto> dataKmeans;
	
	
	public ImgAlgorithms(BufferedImage imageTemp){
		numberClustersKmeans = 1;
		threshold =15;
		this.setImage(imageTemp);
		labeledAreas = new ArrayList<LabelArea>();
		centroids = new ArrayList<Pixel>();
		dataKmeans = new ArrayList<Ponto>();
		this.CreateArrayColors();
	}
	
	public void toGrayscale()  {  
        setOutput(new BufferedImage(image.getWidth(),  
                image.getHeight(), BufferedImage.TYPE_INT_RGB));
        Graphics2D g2d = output.createGraphics();  
        g2d.drawImage(image, 0, 0, null);  
        g2d.dispose();
    } 
	
	public void toBinary() {
		int BLACK = Color.BLACK.getRGB();
		int WHITE = Color.WHITE.getRGB();
		output = new BufferedImage(image.getWidth(),
				image.getHeight(), BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < image.getHeight(); y++)
			for (int x = 0; x < image.getWidth(); x++) {
				Color pixel = new Color(image.getRGB(x, y));
				output.setRGB(x, y, pixel.getRed() < getThreshold() ? BLACK : WHITE);
			}
	}
	
	public void calculateArrayCentroides(){
		this.labeling();
		centroids.clear();
		removeBiggerArea();
		for(int i=0; i<this.labeledAreas.size(); i++){
			centroids.add(labeledAreas.get(i).calculateCentroide());
		}
	}

	public void removeBiggerArea() {
		int biggerLabeledArea = 0;
		int idBiggerlabeledArea = 0;
		for(int i=0; i<this.labeledAreas.size(); i++){
			int sizeArea = labeledAreas.get(i).getPixelsArea().size();
			if(sizeArea>biggerLabeledArea){
				biggerLabeledArea = sizeArea;
				idBiggerlabeledArea = i;
			}
		}
		labeledAreas.remove(idBiggerlabeledArea);
	}
	
	public void calculateKmeans(){
		ArrayList<Ponto> dataList = new ArrayList<Ponto>();
		for(int i=0; i<this.centroids.size(); i++){
			Pixel dataPixel  = centroids.get(i);
			dataList.add(new Ponto(dataPixel.getX(),dataPixel.getY()));
		}
		kMeans kmeans = new kMeans(dataList);
		kmeans.ExecutaKMeans(this.numberClustersKmeans);
		dataKmeans.clear();
		dataKmeans = kmeans.getProcessedData();
	}
	
	
	public void labeling(){
		labeledAreas.clear();
		for (int y = 0; y < image.getHeight(); y++)
			for (int x = 0; x < image.getWidth(); x++) {
				Color pixelColor = new Color(image.getRGB(x, y));
				if(recognizeElement(pixelColor)){
					Pixel pixel = new Pixel(x,y);
					if(firstArea()) createNewArea(pixel);
					else{
						ArrayList<Integer> indexAreas = indexAreasPixelBelongs(pixel);
						if(doesntBelongtoAnyArea(indexAreas)){
							createNewArea(pixel);
						}
						if(belongsToOneArea(indexAreas)){
							labeledAreas.get(indexAreas.get(0)).addPixelArea(pixel);
						}
						if(belongsToTwoAreas(indexAreas)){
							mergeAreas(pixel, indexAreas);
						}
					}					
				}
			}
	}

	public void mergeAreas(Pixel pixel, ArrayList<Integer> indexAreas) {
		LabelArea area01 = labeledAreas.get(indexAreas.get(0));
		LabelArea area02 = labeledAreas.get(indexAreas.get(1));
		area01.addPixelArea(pixel);
		area01.mergeArea(area02.getPixelsArea());
		labeledAreas.remove(area02);
	}

	public boolean belongsToTwoAreas(ArrayList<Integer> indexAreas) {
		return indexAreas.size()==2;
	}

	public boolean belongsToOneArea(ArrayList<Integer> indexAreas) {
		return indexAreas.size()==1;
	}

	public boolean doesntBelongtoAnyArea(ArrayList<Integer> indexAreas) {
		return indexAreas.size()==0;
	}

	public boolean firstArea() {
		return labeledAreas.size() == 0;
	}

	public ArrayList<Integer> indexAreasPixelBelongs(Pixel pixel) {
		ArrayList<Integer> indexAreas = new ArrayList<Integer>();
		for(int i = 0; i < labeledAreas.size(); i++){
			LabelArea labelArea = labeledAreas.get(i);
			if(labelArea.checkExistPixelAdjacent(pixel)){
				indexAreas.add(i);
			}
		}
		return indexAreas;
	}

	public void createNewArea(Pixel pixel) {
		LabelArea newArea = new LabelArea();
		newArea.addPixelArea(pixel);
		labeledAreas.add(newArea);
	}

	public boolean recognizeElement(Color pixel) {
		return pixel.getRGB() == Color.BLACK.getRGB();
	}
	
	public void erosion(){
		ImagePlus implus = new ImagePlus("cam",output);
		ij.process.ImageProcessor ip = implus.getProcessor();
		ip.erode();
		ip.dilate();
		output = ip.getBufferedImage();
	}
	
	public void CreateArrayColors(){
		colors = new ArrayList<Color>();
		int j = 255;
		for (int i= 1; i<8; i++){
			colors.add(new Color(0,0,j));
			colors.add(new Color(j,0,0));
			colors.add(new Color(0,j,0));
			colors.add(new Color(j,j,0));
			colors.add(new Color(0,j,j));
			colors.add(new Color(j,0,j));
			colors.add(new Color(j,j,j));
			j = j/(2*i);
		}
	}
		
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public BufferedImage getOutput() {
		return output;
	}

	public void setOutput(BufferedImage output) {
		this.output = output;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public ArrayList<LabelArea> getLabeledAreas() {
		return labeledAreas;
	}

	public void setLabeledAreas(ArrayList<LabelArea> labeledAreas) {
		this.labeledAreas = labeledAreas;
	}

	public ArrayList<Pixel> getCentroids() {
		return centroids;
	}

	public void setCentroids(ArrayList<Pixel> centroids) {
		this.centroids = centroids;
	}

	public int getNumberClustersKmeans() {
		return numberClustersKmeans;
	}

	public void setNumberClustersKmeans(int numberClustersKmeans) {
		this.numberClustersKmeans = numberClustersKmeans;
	}

	public ArrayList<Ponto> getDataKmeans() {
		return dataKmeans;
	}

	public void setDataKmeans(ArrayList<Ponto> dataKmeans) {
		this.dataKmeans = dataKmeans;
	}

}
