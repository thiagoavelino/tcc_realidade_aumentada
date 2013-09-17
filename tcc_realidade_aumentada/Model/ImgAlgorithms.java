package Model;

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
	
	public ImgAlgorithms(BufferedImage imageTemp){
		threshold =15;
		this.setImage(imageTemp);
		labeledAreas = new ArrayList<LabelArea>();
		centroids = new ArrayList<Pixel>();
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
		for(int i=0; i<this.labeledAreas.size(); i++){
			centroids.add(labeledAreas.get(i).calculateCentroide());
		}
	}
	
	public void augumentedReality(){
		for(int i=0; i<this.centroids.size(); i++){
			Pixel centroid = centroids.get(i);
			int xPix = centroid.getX(); 
			int yPix =centroid.getY();
			
			int color = Color.DARK_GRAY.getRGB();
			int tam = 2;
			paintPoint(xPix, yPix, color, tam);
			color = Color.YELLOW.getRGB();
			tam = 1;
			paintPoint(xPix, yPix, color, tam);
			
		}
	}

	public void paintPoint(int xPix, int yPix, int color, int tam) {
		for (int y = yPix-tam ; y <= yPix+tam; y++)
			for (int x = xPix-tam ; x <= xPix+tam; x++) {
				if(y>0 && y<=WIDTH_SCREEN && x>0 && x<=HEIGHT_SCREEN)
				output.setRGB(x, y, color);
			}
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
		output = ip.getBufferedImage();
		//ImageProcessor ip = IJ.openImage("/path/to/image.tif");
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

}
