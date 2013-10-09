package Test;

import ij.ImagePlus;
import ij.process.ImageProcessor;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.junit.Assert;
import org.junit.Test;

import Model.LinearHT;
import Model.HoughTransform;
import Model.HoughLine;

public class UnitTestHoughTransform {
	
	@Test
	public void testAddPixelAreaTest() throws IOException {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("test_hough_01.png"));
		} catch (IOException e) {
		}
		HoughTransform h = new HoughTransform(img.getWidth(), img.getHeight()); 
        h.addPoints(img); 
        Vector<HoughLine> lines = h.getLines(200); 
        for (int j = 0; j < lines.size(); j++) { 
            HoughLine line = lines.elementAt(j); 
            line.draw(img, Color.RED.getRGB()); 
        } 
        ImageIO.write(img, "PNG", new File("ttest.png"));
	}
}
