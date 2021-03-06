package Test;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import Model.ImgAlgorithms;

import com.github.sarxos.webcam.Webcam;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class UnitTestsImgAlgorithms {
	

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void labelingImageTestWith1Area2x2Image() {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("test_label_1_area.png"));
		} catch (IOException e) {
		}
		
		Model.ImgAlgorithms imgAlgorithms = new Model.ImgAlgorithms(img);
		imgAlgorithms.toBinary();
		imgAlgorithms.setImage(imgAlgorithms.getOutput());
		imgAlgorithms.labeling();
		Assert.assertSame(imgAlgorithms.getLabeledAreas().size(), 1);
	}
	
	@Test
	public void labelingImageTestWith2Areas5x5Image() {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("test_label_2_area.png"));
		} catch (IOException e) {
		}
		Model.ImgAlgorithms imgAlgorithms = new Model.ImgAlgorithms(img);
		imgAlgorithms.toBinary();
		imgAlgorithms.setImage(imgAlgorithms.getOutput());
		imgAlgorithms.labeling();
		Assert.assertSame(imgAlgorithms.getLabeledAreas().size(), 2);
	}

	@Test
	public void labelingImageTestWith5Areas39x18Image() {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("test_label.png"));
		} catch (IOException e) {
		}
		Model.ImgAlgorithms imgAlgorithms = new Model.ImgAlgorithms(img);
		imgAlgorithms.toBinary();
		imgAlgorithms.setImage(imgAlgorithms.getOutput());
		imgAlgorithms.labeling();
		Assert.assertSame(imgAlgorithms.getLabeledAreas().size(), 5);
	}
	
	@Test
	public void labelingImageTestWith1AreaMergeAreas() {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("test_label_1_merge_area.png"));
		} catch (IOException e) {
		}
		Model.ImgAlgorithms imgAlgorithms = new Model.ImgAlgorithms(img);
		imgAlgorithms.toBinary();
		imgAlgorithms.setImage(imgAlgorithms.getOutput());
		imgAlgorithms.labeling();
		Assert.assertSame(imgAlgorithms.getLabeledAreas().size(), 1);
	}
	
	@Test
	public void labelingImageTestWith1AreaAreaVazada() {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("test_label_area_vazada_2.png"));
		} catch (IOException e) {
		}
		Model.ImgAlgorithms imgAlgorithms = new Model.ImgAlgorithms(img);
		imgAlgorithms.toBinary();
		imgAlgorithms.setImage(imgAlgorithms.getOutput());
		imgAlgorithms.labeling();
		Assert.assertSame(imgAlgorithms.getLabeledAreas().size(), 1);
	}
	
	@Test
	public void labelingImageTestWith9AreaAreaVazada() {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("test_label_area_vazada.png"));
		} catch (IOException e) {
		}
		Model.ImgAlgorithms imgAlgorithms = new Model.ImgAlgorithms(img);
		imgAlgorithms.toBinary();
		imgAlgorithms.setImage(imgAlgorithms.getOutput());
		imgAlgorithms.labeling();
		Assert.assertSame(imgAlgorithms.getLabeledAreas().size(), 9);
	}
	
	@Test
	public void labelingImageTestWithRealImg() {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("test_img_real_graph.png"));
		} catch (IOException e) {
		}
		Model.ImgAlgorithms imgAlgorithms = new Model.ImgAlgorithms(img);
		imgAlgorithms.toBinary();
		imgAlgorithms.setImage(imgAlgorithms.getOutput());
		imgAlgorithms.labeling();
		Assert.assertEquals(imgAlgorithms.getLabeledAreas().size(), 14);
	}
	
	@Ignore @Test
	public void labelingImageTestPiorCaso() {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("test_pior_caso.png"));
		} catch (IOException e) {
		}
		Model.ImgAlgorithms imgAlgorithms = new Model.ImgAlgorithms(img);
		imgAlgorithms.toBinary();
		imgAlgorithms.setImage(imgAlgorithms.getOutput());
		imgAlgorithms.labeling();
		Assert.assertEquals(imgAlgorithms.getLabeledAreas().size(), 14);
	}
}
