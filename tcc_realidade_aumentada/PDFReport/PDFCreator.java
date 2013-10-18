package PDFReport;

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
public class PDFCreator {

	/**
	 * @param args
	 * @throws DocumentException
	 * @throws IOException
	 * @throws MalformedURLException
	 */

	public static void main(String[] args) throws DocumentException,
	MalformedURLException, IOException {
		//PDFCreatorClass.CreatePDF(null);
		PDFCreatorClass.CreatePDF2();
	}
}

class PDFCreatorClass{
	public PDFCreatorClass(){
	}

	public static void CreatePDF(ArrayList<ImStr> content) throws
	DocumentException, MalformedURLException, IOException{
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		PdfWriter writer = PdfWriter.getInstance(document,new
		FileOutputStream(".\\ITextTest.pdf"));
		document.open();
		Anchor anchorTarget = new Anchor("First page of the document.");
		anchorTarget.setName("BackToTop");
		Paragraph paragraph1 = new Paragraph();
		paragraph1.setSpacingBefore(50);
		Image image2 = Image.getInstance("image.jpg");
		image2.scalePercent(25);
		paragraph1.add(image2);
		paragraph1.add(anchorTarget);
		document.add(paragraph1);
		document.close();
	}
	
	
	public static void CreatePDF2() throws MalformedURLException,
		IOException, DocumentException{
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(".\\ITextTest.pdf"));
		document.open();
		Paragraph p = new Paragraph();
		Image image2;
		PdfPTable t = new PdfPTable(2);
		t.setWidthPercentage(100);
		t.setSpacingBefore(0);
		t.setSpacingAfter(0);
		PdfConfig pdfcfg;
		pdfcfg=loadConfig();
		Phrase materia;
		Phrase professor;
		Phrase assunto;
		if(pdfcfg!=null){
			System.out.println("Config file loaded!");
			materia=new Phrase(pdfcfg.getMateria(),
				FontFactory.getFont(FontFactory.HELVETICA, 15, Font.BOLDITALIC));
			professor = new Phrase("Professor: "+pdfcfg.getProfessor());
			assunto = new Phrase("Aula: "+pdfcfg.getAssunto());
			ImageIcon imIconTmp = pdfcfg.getLogo();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write((RenderedImage) imIconTmp,"jpg" , baos);	
			image2 = Image.getInstance(baos.toByteArray(),true);
		}
		else{
			System.out.println("Config file not found!");
			pdfcfg = new PdfConfig();
			pdfcfg.setAssunto("Processamento digital de imagens");
			pdfcfg.setProfessor("Gustavo Borba");
			pdfcfg.setAssunto("Segmentação");
			image2 = Image.getInstance("C:\\Users\\FARINHAKI\\image_preview.jpg");
			image2.scalePercent(25);
			pdfcfg.setLogo(image2);
			materia=new Phrase("Processamento digital de imagens",
				FontFactory.getFont(FontFactory.HELVETICA, 15, Font.BOLDITALIC));
			professor = new Phrase("Professor: Gustavo Borba");
			assunto = new Phrase("Aula: Segmentação");
			saveConfig(pdfcfg);
		}
		Date data = (new Date(System.currentTimeMillis()));
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		Phrase dataPhrase = new Phrase(formatador.format(data));
		PdfPCell c1 = new PdfPCell();
		float array[]={75,25};
		t.setWidths(array);
		c1.addElement(materia);
		c1.addElement(professor);
		c1.addElement(assunto);
		c1.addElement(dataPhrase);
		c1.setBorderWidth(0);
		t.addCell(c1);
		PdfPCell c2 = new PdfPCell();
		c2.addElement(image2);
		c2.setBorderWidth(0);
		c2.setHorizontalAlignment(Element.ALIGN_MIDDLE);
		t.addCell(c2);
		p.add(t);
		document.add(p);
		document.close();
	}
	public static PdfConfig loadConfig(){
		try{
			FileInputStream fin = new FileInputStream("pdfconfig.cfg");
			ObjectInputStream oi = new ObjectInputStream(fin);
			return (PdfConfig)oi.readObject();
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public static boolean saveConfig(PdfConfig cfgFile){
		try{
			FileOutputStream fos = new FileOutputStream("pdfconfig.cfg");
			ObjectOutputStream oo=new ObjectOutputStream(fos);
			oo.writeObject(cfgFile);
			return true;
		}
		catch(Exception e){
			//e.printStackTrace();
			return false;
		}
	}
}


class ImStr{
	private String text;
	private Image image;
	public ImStr(String tx, Image Im){
		this.text=tx;
		this.image=Im;
	}
	public String getText(){
		return text;
	}
	public Object getImage(){
		return image;
	}
}


class PdfConfig implements Serializable{
	private String materia;
	private String professor;
	private String assunto;
	private ImageIcon logo;
	public String getMateria() {
		return materia;
	}
	public void setMateria(String materia) {
		this.materia = materia;
	}
	public String getProfessor() {
		return professor;
	}
	public void setProfessor(String professor) {
		this.professor = professor;
	}
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	//TODO - ARRUMAR
	public ImageIcon getLogo() {
		return  logo;
	}
	public void setLogo(Image plogo) {
		this.logo = new ImageIcon(plogo.getRawData());
	}
}
