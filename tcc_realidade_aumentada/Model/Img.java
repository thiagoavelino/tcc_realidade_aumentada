package Model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Img {
	
	private BufferedImage image;
	private BufferedImage imageGreyScale;
	private ArrayList<PixelGrey> arrayGreyPixels;
	
	public Img(BufferedImage image){
		this.setImage(image);
		setArrayGreyPixels(new ArrayList<PixelGrey>());
		trasnformRGBGrayScaleArray();
		imageGreyScale = new BufferedImage(image.getWidth(),
				image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		transfGrayBufferedImg();
	}

	public void transfGrayBufferedImg() {
		for(PixelGrey pix: getArrayGreyPixels() ){
			imageGreyScale.setRGB(pix.getX(), pix.getY(), pix.getColor());
		}
	}

	public void trasnformRGBGrayScaleArray() {
		for (int y = 0; y < getImage().getHeight(); y++)
			for (int x = 0; x < getImage().getWidth(); x++) {
				Color pixel = new Color(getImage().getRGB(x, y));
				int color = (int) calculateGrey(pixel) ;
				getArrayGreyPixels().add(new PixelGrey(x, y, color+100));
			}
	}

	public double calculateGrey(Color pixel) {
		return 0.299*pixel.getRed() + 0.587*pixel.getGreen() + 0.114*pixel.getBlue();
	}

	public ArrayList<PixelGrey> getArrayGreyPixels() {
		return arrayGreyPixels;
	}

	public void setArrayGreyPixels(ArrayList<PixelGrey> arrayGreyPixels) {
		this.arrayGreyPixels = arrayGreyPixels;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public BufferedImage getImageGreyScale() {
		return imageGreyScale;
	}

	public void setImageGreyScale(BufferedImage imageGreyScale) {
		this.imageGreyScale = imageGreyScale;
	}

}
