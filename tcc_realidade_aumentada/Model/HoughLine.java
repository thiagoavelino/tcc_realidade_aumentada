package Model;

import java.awt.image.BufferedImage;

public class HoughLine { 
	 
    private double theta; 
    private double r; 
 
    public HoughLine(double theta, double r) { 
        this.theta = theta; 
        this.r = r; 
    } 

    public void draw(BufferedImage image, int color) { 
 
        int height = image.getHeight(); 
        int width = image.getWidth(); 
 
        // During processing h_h is doubled so that -ve r values 
        int houghHeight = (int) (Math.sqrt(2) * Math.max(height, width)) / 2; 
 
        // Find edge points and vote in array 
        float centerX = width / 2; 
        float centerY = height / 2; 
 
        // Draw edges in output array 
        double tsin = Math.sin(theta); 
        double tcos = Math.cos(theta); 
 
        if (theta < Math.PI * 0.25 || theta > Math.PI * 0.75) { 
            // Draw vertical-ish lines 
            for (int y = 0; y < height; y++) { 
                int x = (int) ((((r - houghHeight) - ((y - centerY) * tsin)) / tcos) + centerX); 
                if (x < width && x >= 0) { 
                    image.setRGB(x, y, color); 
                } 
            } 
        } else { 
            // Draw horizontal-sh lines 
            for (int x = 0; x < width; x++) { 
                int y = (int) ((((r - houghHeight) - ((x - centerX) * tcos)) / tsin) + centerY); 
                if (y < height && y >= 0) { 
                    image.setRGB(x, y, color); 
                } 
            } 
        } 
    }
    
    public int getHorizontal(int width, int height) { 
        
        int houghHeight = (int) (Math.sqrt(2) * Math.max(height, width)) / 2; 
        float centerX = width / 2; 
        float centerY = height / 2; 
        
        double tsin = Math.sin(theta); 
        double tcos = Math.cos(theta); 
 
    	int x1 = 0;
        int y1 = (int) ((((r - houghHeight) - ((x1 - centerX) * tcos)) / tsin) + centerY); 
        int x2 = width-1;
        int y2 = (int) ((((r - houghHeight) - ((x2 - centerX) * tcos)) / tsin) + centerY);
        
        return (y1+y2)/2;
  
    }
    
    public int getVertical(int width, int height) { 
        
        int houghHeight = (int) (Math.sqrt(2) * Math.max(height, width)) / 2; 
        float centerX = width / 2; 
        float centerY = height / 2; 
        
        double tsin = Math.sin(theta); 
        double tcos = Math.cos(theta); 
 
    	int y1 = 0;
    	int x1 = (int) ((((r - houghHeight) - ((y1 - centerY) * tsin)) / tcos) + centerX); 
        int y2 = height-1;
        int x2 = (int) ((((r - houghHeight) - ((y2 - centerY) * tsin)) / tcos) + centerX); 
        
        return (x1+x2)/2;
  
    }


	public double getTheta() {
		return theta;
	}


	public void setTheta(double theta) {
		this.theta = theta;
	}


	public double getR() {
		return r;
	}


	public void setR(double r) {
		this.r = r;
	} 
} 
