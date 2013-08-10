package Test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Model.ImgAlgorithms;

import com.github.sarxos.webcam.Webcam;

import java.awt.image.BufferedImage;

import org.opencv.core.Core;

public class UnitTestsImgAlgorithms {
	
	private Model.ImgAlgorithms imgAlgorithms;

	@Before
	public void setUp() throws Exception {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Webcam.setAutoOpenMode(true);
		BufferedImage image = Webcam.getDefault().getImage();
		this.imgAlgorithms = new Model.ImgAlgorithms(image);
	}

	@Test
	public void transformBwTest() {
		this.imgAlgorithms.transformBw();
	}

}
