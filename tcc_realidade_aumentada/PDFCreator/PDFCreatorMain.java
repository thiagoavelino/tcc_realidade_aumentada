package PDFCreator;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import com.itextpdf.*;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.TabStop.Alignment;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfDate;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


//NAO MEXER
public class PDFCreatorMain {

	/**
	 * @param args
	 * @throws DocumentException
	 * @throws IOException
	 * @throws MalformedURLException
	 */

	public static void main(String[] args) throws DocumentException,
	MalformedURLException, IOException {
		//PDFCreatorClass.CreatePDF(null);
		ArrayList<ImStr> arrayOfItems = new ArrayList<ImStr>();
		arrayOfItems.add(new ImStr("Gavião","tmp/image01.jpg","Sparrowhawk with its prey"));
		arrayOfItems.add(new ImStr("Zebra","tmp/image02.jpg","Vyborg Castle in Vyborg, Russia. "));
		arrayOfItems.add(new ImStr("Naica Cave","tmp/image03.jpg","Gypsum crystals of the Naica cave. Note person for scale."));
		arrayOfItems.add(new ImStr("Black bird","tmp/image04.jpg","Common Blackbird having a delicious meal"));
		arrayOfItems.add(new ImStr("Bald Eagle","tmp/image05.jpg","Head of Bald Eagle. peneee"));
		PDFCreator pdf = new PDFCreator(arrayOfItems);
	}
}





