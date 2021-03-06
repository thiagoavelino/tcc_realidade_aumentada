package Model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Vector;

public class HoughTransform { 
	
    final int neighbourhoodSize = 4; 
    final int maxTheta = 180; 
    final double thetaStep = Math.PI / maxTheta; 
    protected int width, height; 
    protected int[][] houghArray; 
    protected float centerX, centerY; 
    protected int houghHeight; 
    protected int doubleHeight; 
    protected int numPoints; 
    private double[] sinCache; 
    private double[] cosCache; 
 
    public HoughTransform(int width, int height) { 
 
        this.width = width; 
        this.height = height; 
 
        initialise(); 
 
    } 
    public void initialise() { 
        houghHeight = (int) (Math.sqrt(2) * Math.max(height, width)) / 2; 
        doubleHeight = 2 * houghHeight; 
        houghArray = new int[maxTheta][doubleHeight]; 
        centerX = width / 2; 
        centerY = height / 2; 
        numPoints = 0; 
        sinCache = new double[maxTheta]; 
        cosCache = sinCache.clone(); 
        for (int t = 0; t < maxTheta; t++) { 
            double realTheta = t * thetaStep; 
            sinCache[t] = Math.sin(realTheta); 
            cosCache[t] = Math.cos(realTheta); 
        } 
    } 
 
    public void addPoints(BufferedImage image) { 
        for (int x = 0; x < image.getWidth(); x++) { 
            for (int y = 0; y < image.getHeight(); y++) { 
            	Color black = Color.BLACK;
                if (image.getRGB(x, y) == black.getRGB()) { 
                    addPoint(x, y); 
                } 
            } 
        } 
    } 
 
    public void addPoint(int x, int y) { 
        for (int t = 0; t < maxTheta; t++) { 
            int r = (int) (((x - centerX) * cosCache[t]) + ((y - centerY) * sinCache[t])); 
            r += houghHeight; 
            if (r < 0 || r >= doubleHeight) continue; 
            houghArray[t][r]++; 
        } 
        numPoints++; 
    } 
    public Vector<HoughLine> getLines(int threshold) { 
        Vector<HoughLine> lines = new Vector<HoughLine>(20); 
        if (numPoints == 0) return lines; 
        for (int t = 0; t < maxTheta; t++) { 
            loop: 
            for (int r = neighbourhoodSize; r < doubleHeight - neighbourhoodSize; r++) { 
                if (houghArray[t][r] > threshold) { 
 
                    int peak = houghArray[t][r]; 
                    for (int dx = -neighbourhoodSize; dx <= neighbourhoodSize; dx++) { 
                        for (int dy = -neighbourhoodSize; dy <= neighbourhoodSize; dy++) { 
                            int dt = t + dx; 
                            int dr = r + dy; 
                            if (dt < 0) dt = dt + maxTheta; 
                            else if (dt >= maxTheta) dt = dt - maxTheta; 
                            if (houghArray[dt][dr] > peak) { 
                                continue loop; 
                            } 
                        } 
                    } 
                    double theta = t * thetaStep; 
                    lines.add(new HoughLine(theta, r)); 
                } 
            } 
        } 
 
        return lines; 
    } 
 
    public int getHighestValue() { 
        int max = 0; 
        for (int t = 0; t < maxTheta; t++) { 
            for (int r = 0; r < doubleHeight; r++) { 
                if (houghArray[t][r] > max) { 
                    max = houghArray[t][r]; 
                } 
            } 
        } 
        return max; 
    } 
 
    public BufferedImage getHoughArrayImage() { 
        int max = getHighestValue(); 
        BufferedImage image = new BufferedImage(maxTheta, doubleHeight, BufferedImage.TYPE_INT_ARGB); 
        for (int t = 0; t < maxTheta; t++) { 
            for (int r = 0; r < doubleHeight; r++) { 
                double value = 255 * ((double) houghArray[t][r]) / max; 
                int v = 255 - (int) value; 
                int c = new Color(v, v, v).getRGB(); 
                image.setRGB(t, r, c); 
            } 
        } 
        return image; 
    } 
 
} 
