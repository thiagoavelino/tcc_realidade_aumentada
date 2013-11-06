package PDFCreator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

class PDFCreator{
	private Document document;
	private Phrase materia;
	private Phrase professor;
	private Phrase assunto;
	private ArrayList<ImStr> ItemsList;
	private PdfFrame pdfframe;
	
	public PDFCreator(ArrayList<ImStr> list) throws DocumentException, MalformedURLException, IOException{
		document = new Document(PageSize.A4, 50, 50, 50, 50);
		PdfConfig pdfcfg;
		ItemsList = list;
		
		
		pdfcfg=loadConfig();
		if(pdfcfg!=null){
			System.out.println("Config file loaded!");
			materia=new Phrase(pdfcfg.getMateria(),
				FontFactory.getFont(FontFactory.HELVETICA, 15, Font.BOLDITALIC));
			professor = new Phrase("Professor: "+pdfcfg.getProfessor());
			assunto = new Phrase("Aula: "+pdfcfg.getAssunto());
			pdfframe = new PdfFrame(this,pdfcfg.getProfessor(),pdfcfg.getMateria(), pdfcfg.getAssunto()); 
			pdfframe.setVisible(true);
		}
		else{
			pdfframe = new PdfFrame(this,"","","");
			pdfframe.setVisible(true);
		}
	}
	
	public  void CreatePDF(String pathpdf) throws MalformedURLException,
		IOException, DocumentException{
		
		materia=new Phrase(pdfframe.getMateria(),
				FontFactory.getFont(FontFactory.HELVETICA, 15, Font.BOLDITALIC));
		professor = new Phrase("Professor: "+pdfframe.getProfessor());
		assunto = new Phrase("Aula: "+pdfframe.getAssunto());
		
		PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(pathpdf));
		document.open();
		Paragraph p = new Paragraph();
		Image image2;
		PdfPTable t = new PdfPTable(2);
		t.setWidthPercentage(100);
		t.setSpacingBefore(0);
		t.setSpacingAfter(0);
		
		image2 = Image.getInstance("image.jpg");
		image2.scalePercent(25);
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
		
		this.InsertArrayOfItens(ItemsList);
	}
	
	private void InsertArrayOfItens(ArrayList<ImStr> list) throws MalformedURLException, IOException, DocumentException{
		Paragraph initP = new Paragraph();
		initP.add(new Phrase("\n\n"));
		document.add(initP);
		for(ImStr item: list){//foreach
			Paragraph paragraph = new Paragraph();
			Phrase pTitle = new Phrase(item.getText()+"\n",FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLDITALIC));
			Image pImage = Image.getInstance(item.getImage());
			pImage.scalePercent(40);
			Phrase pDesc = new Phrase(item.getDescription()+"\n");
			Phrase pnop = new Phrase("\n\n\n");
			paragraph.add(pTitle);
			paragraph.add(pImage);
			paragraph.add(pDesc);
			paragraph.add(pnop);
			
			document.add(paragraph);
		}
		document.close();
	}
	
	
	public static PdfConfig loadConfig(){
		try{
			FileInputStream fin = new FileInputStream("pdfconfig.cfg");
			ObjectInputStream oi = new ObjectInputStream(fin);
			return (PdfConfig)oi.readObject();
		}
		catch(Exception e){
			//e.printStackTrace();
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