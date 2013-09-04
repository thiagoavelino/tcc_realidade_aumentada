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
		Assert.assertSame(labelArea.getPixelsAdjacents().get(0).getX(), -1);
		Assert.assertSame(labelArea.getPixelsAdjacents().get(0).getY(), -1);
		Assert.assertSame(labelArea.getPixelsAdjacents().size(), 9);
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
		Assert.assertSame(labelArea.getPixelsAdjacents().get(9).getX(), 2);
		Assert.assertSame(labelArea.getPixelsAdjacents().get(9).getY(), 0);
		Assert.assertSame(labelArea.getPixelsAdjacents().size(), 14);
	}
	
	@Test
	public void checkExistPixelAdjacentTest() {
		LabelArea labelArea = new LabelArea();
		Pixel pix = new Pixel(0,0);
		labelArea.addPixelArea(pix);
		Assert.assertSame(labelArea.checkExistPixelAdjacent(pix), true);
	}
	

}
