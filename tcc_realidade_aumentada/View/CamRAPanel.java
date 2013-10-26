package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;

import KMeansPackage.Data;
import Model.HoughLine;
import Model.Pixel;

public class CamRAPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String algorithm;
	private BufferedImage master;
	private ArrayList<Pixel> centroids;
	private ArrayList<Color> colors;
	private ArrayList<Data> dataKmeans;
	private int axeX;
	private int axeY;
	
	
	public CamRAPanel(BufferedImage master) {
		setMaster(master);
		centroids = new ArrayList<Pixel>();
		dataKmeans = new ArrayList<Data>();
        Graphics2D g2d = getMaster().createGraphics();
        g2d.drawImage(getMaster(), 0, 0, null);
        g2d.dispose();
        setBounds(new Rectangle(5, 45, 640, 480));
        this.CreateArrayColors();
        algorithm = "";
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = new Dimension(getMaster().getWidth(), getMaster().getHeight());
        return size;
    }

    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	g.drawImage(getMaster(), 0, 0, null);
    	paintAxes(g);
    	switch(algorithm){
	    	case "kmeans":
	    		paintClusters(g);
	    		break;
	    	default:
	    		paintDots(g);
	    		break;
	    }
    }

	public void paintAxes(Graphics g) {
		g.setColor(Color.RED);
		g.drawLine(axeX, 0, axeX, this.getHeight());
		g.drawLine(0, axeY, this.getWidth(), axeY);
	}

	public void paintClusters(Graphics g) {
		for(int i=0; i< dataKmeans.size(); i++){
			Data data = dataKmeans.get(i);
			int xPix = (int)data.X(); 
			int yPix = (int)data.Y();
			int color = data.cluster();
			g.setColor(colors.get(color));
		    g.fillRect(xPix,yPix , 4, 4);
		}
	}

	public void paintDots(Graphics g) {
		for(int i=0; i<centroids.size();i++){
			g.setColor(Color.red);
		    g.fillRect(centroids.get(i).getX(),centroids.get(i).getY() , 4, 4);
		}
	}

	public BufferedImage getMaster() {
		return master;
	}

	public void setMaster(BufferedImage master) {
		this.master = master;
	}

	public ArrayList<Pixel> getCentroids() {
		return centroids;
	}

	public void setCentroids(ArrayList<Pixel> centroids) {
		this.centroids = centroids;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}
	
	public void CreateArrayColors(){
		colors = new ArrayList<Color>();
		int j = 255;
		for (int i= 1; i<8; i++){
			colors.add(new Color(0,0,j));
			colors.add(new Color(j,0,0));
			colors.add(new Color(0,j,0));
			colors.add(new Color(j,j,0));
			colors.add(new Color(0,j,j));
			colors.add(new Color(j,0,j));
			colors.add(new Color(j,j,j));
			j = j/(2*i);
		}
	}

	public ArrayList<Data> getDataKmeans() {
		return dataKmeans;
	}

	public void setDataKmeans(ArrayList<Data> dataKmeans) {
		this.dataKmeans = dataKmeans;
	}

	public int getAxeX() {
		return axeX;
	}

	public void setAxeX(int axeX) {
		this.axeX = axeX;
	}

	public int getAxeY() {
		return axeY;
	}

	public void setAxeY(int axeY) {
		this.axeY = axeY;
	}

}
