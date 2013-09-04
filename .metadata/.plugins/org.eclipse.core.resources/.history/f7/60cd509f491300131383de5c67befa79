package Model;

import java.awt.Color;
import java.util.ArrayList;

public class LabelArea {
	
	private ArrayList<Pixel> pixelsArea;
	private ArrayList<Pixel> pixelsAdjacents;
	
	
	public LabelArea(){
		pixelsArea = new ArrayList<Pixel>();
		pixelsAdjacents = new ArrayList<Pixel>();
	}
	
	public void addPixelArea(Pixel pix){
		pixelsArea.add(pix);
		for (int y = -1; y < 2; y++)
			for (int x = -1; x < 2; x++) {
				int xPixel = pix.getX() + x;
				int yPixel = pix.getY() + y;
				this.addPixelAdjacent(new Pixel(xPixel,yPixel));
			}
	}
	
	public void addPixelAdjacent(Pixel pix){
		if(!checkExistPixelAdjacent(pix)){
			pixelsAdjacents.add(pix);
		}
	}
	
	public boolean checkExistPixelAdjacent(Pixel pix){
		boolean operator = false;
		for(Pixel i: pixelsAdjacents){
			if(i.getX() == pix.getX() && i.getY() == pix.getY()){
				operator = true;
				break;
			}
		}
		return operator;
	}

	public ArrayList<Pixel> getPixelsArea() {
		return pixelsArea;
	}
	
	public ArrayList<Pixel> getPixelsAdjacents() {
		return pixelsAdjacents;
	}

}
