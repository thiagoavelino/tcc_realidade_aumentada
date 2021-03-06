package Model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

public class LabelArea {
	
	private ArrayList<Pixel> pixelsArea;
	private boolean [][] arrayAdjacents;
	private Pixel centroid;
	
	
	public LabelArea(){
		pixelsArea = new ArrayList<Pixel>();
		arrayAdjacents = new boolean [641][481];
		for (int y = 0; y < 481; y++)
			for (int x = 0; x < 641; x++){
				arrayAdjacents[x][y]=false;
			}
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
		if(pix.getX()>=0 & pix.getY()>=0){
				arrayAdjacents[pix.getX()][pix.getY()] = true;
		}
	}
	
	public boolean checkExistPixelAdjacent(Pixel pix){
		return arrayAdjacents[pix.getX()][pix.getY()];
	}
	
	public void mergeArea(ArrayList<Pixel> newPixelsArea){
		for(int i=0; i<newPixelsArea.size(); i++){
			Pixel pixel = newPixelsArea.get(i);
			this.addPixelArea(pixel);
		}
	}
	
	public Pixel calculateCentroide(){
		int SumX = 0;
		int SumY = 0;
		
		for(int i=0; i<this.pixelsArea.size(); i++){
			Pixel pix  = pixelsArea.get(i);
			SumX += pix.getX()+1;
			SumY += pix.getY()+1;
		}
		int x  = (int) Math.ceil(SumX/pixelsArea.size())-1;
		int y  = (int) Math.ceil(SumY/pixelsArea.size())-1;
		centroid = new Pixel(x,y);
		return centroid;
	}

	public ArrayList<Pixel> getPixelsArea() {
		return pixelsArea;
	}

}
