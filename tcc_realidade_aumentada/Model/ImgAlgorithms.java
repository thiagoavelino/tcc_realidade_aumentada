package Model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
					//does it belong  to any area?
					int iterator = 0;
					for(LabelArea labelArea: labeledAreas){
						Pixel pixel = new Pixel(x,y);
						if(labelArea.checkExistPixelAdjacent(pixel));
						//continuar aqui.
						
					}
					
				}
			}
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

}
