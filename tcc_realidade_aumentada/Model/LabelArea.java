package Model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

public class LabelArea {
	
	private ArrayList<Pixel> pixelsArea;
	private ArrayList<Pixel> pixelsAdjacents;
	
	/*private int [][] arrayAdjacents;*/
	
	
	public LabelArea(){
		pixelsArea = new ArrayList<Pixel>();
		pixelsAdjacents = new ArrayList<Pixel>();
		/*arrayAdjacents = new int [640][480];
		for (int i = 0; i < 640; i++)
            for (int j = 0; j < 480; j++)
                    this.arrayAdjacents[i][j] = 0;*/
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
			/*arrayAdjacents[pix.getX()][pix.getY()] = 1;*/
		}
	}
	
	public boolean checkExistPixelAdjacent(Pixel pix){
		boolean operator = false;
		
		for(int i=pixelsAdjacents.size()-1; i>0; i--){
			Pixel pixelBusca = pixelsAdjacents.get(i);
			if(pixelBusca.getX() == pix.getX() && pixelBusca.getY() == pix.getY()){
				operator = true;
				break;
			}
		}
		return operator;
	}
	
	public void mergeArea(ArrayList<Pixel> newPixelsArea){
		for(int i=0; i<newPixelsArea.size(); i++){
			Pixel pixel = newPixelsArea.get(i);
			this.addPixelArea(pixel);
		}
	}

	public ArrayList<Pixel> getPixelsArea() {
		return pixelsArea;
	}
	
	public ArrayList<Pixel> getPixelsAdjacents() {
		return pixelsAdjacents;
	}

}
