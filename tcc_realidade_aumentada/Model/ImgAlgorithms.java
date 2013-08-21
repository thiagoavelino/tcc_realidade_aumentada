package Model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.DataBufferByte;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class ImgAlgorithms {
	
	private BufferedImage image;
	private BufferedImage output;
	private int threshold;
	
	public ImgAlgorithms(BufferedImage imageTemp){
		threshold =15;
		this.setImage(imageTemp);
	}
	
	public void toGrayscale()  {  
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
