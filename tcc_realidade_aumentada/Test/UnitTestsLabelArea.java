package Test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import Model.LabelArea;
import Model.Pixel;

public class UnitTestsLabelArea {

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testAddPixelAreaTest() {
		LabelArea labelArea = new LabelArea();
		Pixel pix = new Pixel(0,0);
		labelArea.addPixelArea(pix);
		Assert.assertSame(labelArea.getPixelsArea().get(0).getX(), pix.getX());
		Assert.assertSame(labelArea.getPixelsArea().get(0).getY(), pix.getY());
		Assert.assertSame(labelArea.getPixelsArea().size(), 1);
	}
	
	@Test
	public void testAddTwoPixelAreaTest() {
		LabelArea labelArea = new LabelArea();
		Pixel pix = new Pixel(0,0);
		labelArea.addPixelArea(pix);
		Pixel pix2 = new Pixel(1,1);
		labelArea.addPixelArea(pix2);
		Assert.assertSame(labelArea.getPixelsArea().get(1).getX(), pix2.getX());
		Assert.assertSame(labelArea.getPixelsArea().get(1).getY(), pix2.getY());
		Assert.assertSame(labelArea.getPixelsArea().size(), 2);
	}
	
	@Test
	public void checkExistPixelAdjacentTest() {
		LabelArea labelArea = new LabelArea();
		Pixel pix = new Pixel(0,0);
		labelArea.addPixelArea(pix);
		Assert.assertSame(labelArea.checkExistPixelAdjacent(pix), true);
	}
	
	@Test
	public void calculateCentroid3x3Test(){
		LabelArea labelArea = new LabelArea();
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 3; x++) {
				labelArea.addPixelArea(new Pixel(x,y));
			}
		Pixel centroid = labelArea.calculateCentroide();
		Assert.assertSame(centroid.getX(), 1);
		Assert.assertSame(centroid.getY(), 1);
	}
	@Test
	public void calculateCentroid5x5Test(){
		LabelArea labelArea = new LabelArea();
		for (int y = 0; y < 5; y++)
			for (int x = 0; x < 5; x++) {
				labelArea.addPixelArea(new Pixel(x,y));
			}
		Pixel centroid = labelArea.calculateCentroide();
		Assert.assertSame(centroid.getX(), 2);
		Assert.assertSame(centroid.getY(), 2);
	}
	@Test
	public void calculateCentroid2pix(){
		LabelArea labelArea = new LabelArea();
		labelArea.addPixelArea(new Pixel(0,0));
		labelArea.addPixelArea(new Pixel(0,1));
		Pixel centroid = labelArea.calculateCentroide();
		Assert.assertSame(centroid.getX(), 0);
		Assert.assertSame(centroid.getY(), 0);
	}
	

}
