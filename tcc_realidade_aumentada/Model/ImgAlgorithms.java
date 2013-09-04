package Model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

public class ImgAlgorithms {
	
	private BufferedImage image;
	private BufferedImage output;
	private ArrayList<LabelArea> labeledAreas;
	private int threshold;
	
	public ImgAlgorithms(BufferedImage imageTemp){
		threshold =15;
		this.setImage(imageTemp);
		labeledAreas = new ArrayList<LabelArea>();
	}
	
	public void toGrayscale()  {  
		
		//Img img = new Img(getImage());
		//setOutput(img.getImageGreyScale());
        setOutput(new BufferedImage(image.getWidth(),  
                image.getHeight(), BufferedImage.TYPE_BYTE_GRAY));
        Graphics2D g2d = output.createGraphics();  
        g2d.drawImage(image, 0, 0, null);  
        g2d.dispose();
    } 
	
	public void toBinary() {
		int BLACK = Color.BLACK.getRGB();
		int WHITE = Color.WHITE.getRGB();

		output = new BufferedImage(image.getWidth(),
				image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		
		for (int y = 0; y < image.getHeight(); y++)
			for (int x = 0; x < image.getWidth(); x++) {
				Color pixel = new Color(image.getRGB(x, y));
				output.setRGB(x, y, pixel.getRed() < getThreshold() ? BLACK : WHITE);
			}

	}
	
	public void labeling(){
		for (int y = 0; y < image.getHeight(); y++)
			for (int x = 0; x < image.getWidth(); x++) {
				Color pixelColor = new Color(image.getRGB(x, y));
				if(recognizeElement(pixelColor)){
					Pixel pixel = new Pixel(x,y);
					
					// primeiro pixel encontrado
					if(labeledAreas.size() == 0) createNewArea(pixel);
					
					// a partir do segundo pixel encontrado
					else{
						//buscar index de areas que o pixel pertence.
						ArrayList<Integer> indexAreas = indexAreasPixelBelongs(pixel);
						if(indexAreas.size()==0){
							createNewArea(pixel);
						}
						if(indexAreas.size()==1){
							labeledAreas.get(indexAreas.get(0)).addPixelArea(pixel);
						}
						if(indexAreas.size()==2){
							LabelArea area01 = labeledAreas.get(indexAreas.get(0));
							LabelArea area02 = labeledAreas.get(indexAreas.get(1));
							area01.addPixelArea(pixel);
							area01.mergeArea(area02.getPixelsArea());
							labeledAreas.remove(area02);
						}
					}					
				}
			}
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

}
