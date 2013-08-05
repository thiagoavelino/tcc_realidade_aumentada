package Model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Cam {
	public final String IMG_FORMAT = ".png";
	
	public void saveImage(String path, BufferedImage image) {
		try {
			ImageIO.write(image, "PNG", new File(path+IMG_FORMAT));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}

}
