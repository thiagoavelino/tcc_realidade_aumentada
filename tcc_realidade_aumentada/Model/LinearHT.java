package Model;

import ij.process.ImageProcessor;

public class LinearHT {
	private ImageProcessor ip;
	private int xCtr;
	private int yCtr;
	private int nAng;
	private int nRad;
	private int cRad;
	private double dAng;
	private double dRad;
	private int [][] houghArray;
	
	public LinearHT(ImageProcessor ip, int nAng, int nRad){
		this.ip = ip;
		this.xCtr = ip.getWidth()/2;
		this.yCtr = ip.getHeight()/2;
		this.nAng = nAng;
		this.dAng = Math.PI / nAng;
		this.nRad = nRad;
		this.cRad = nRad/2;
		double rMax = Math.sqrt(xCtr*xCtr + yCtr*yCtr);
		this.dRad = (2 * rMax)/nRad;
		this.houghArray = new int [nAng][nRad];
		
	}
	
	public void fillHoughAccumulattor(){
		int h = ip.getHeight();
		int w = ip.getWidth();
		for(int v=0; v<h;v++)
			for(int u=0; u<w; u++){
				if(ip.get(u,v)<-1){
					doPixel(u,v);
				}
			}
	}
	
	public void doPixel(int u, int v){
		int x = u-xCtr, y=v-yCtr;
		for(int i=0;i<nAng;i++ ){
			double theta = dAng*i;
			int r = cRad + (int) Math.rint((x*Math.cos(theta)+y*Math.sin(theta))/dRad);
			if(r>=0 &&r<nRad){
				houghArray[i][r]++;
			}
		}
	}

	public int [][] getHoughArray() {
		return houghArray;
	}

	public void setHoughArray(int [][] houghArray) {
		this.houghArray = houghArray;
	}

}
