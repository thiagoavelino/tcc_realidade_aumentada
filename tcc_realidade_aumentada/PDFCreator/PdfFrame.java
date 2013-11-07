package PDFCreator;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.itextpdf.text.DocumentException;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;

public class PdfFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldProfessor;
	private JTextField textFieldMateria;
	private JTextField textFieldAssunto;
	private JLabel lblLogo;
	private JTextField txtImagejpg;
	private JButton button;
	private JLabel lblConfiguraes;
	private JButton btnSalvar;
	private PDFCreator pdfCreator;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					PdfFrame frame = new PdfFrame(null,"Professor","Materia","Professor");
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public PdfFrame(PDFCreator pdfCreator_,String professor, String materia, String assunto) {
		this.pdfCreator = pdfCreator_;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		lblConfiguraes = new JLabel("Configura\u00E7\u00F5es");
		lblConfiguraes.setFont(new Font("Tahoma", Font.BOLD, 13));
		contentPane.add(lblConfiguraes, "6, 2, center, default");
		
		JLabel lblProfessor = new JLabel("Professor:");
		contentPane.add(lblProfessor, "4, 6");
		
		textFieldProfessor = new JTextField();
		contentPane.add(textFieldProfessor, "6, 6, left, default");
		textFieldProfessor.setColumns(20);
		
		JLabel lblMatria = new JLabel("Mat\u00E9ria:");
		contentPane.add(lblMatria, "4, 8");
		
		textFieldMateria = new JTextField();
		contentPane.add(textFieldMateria, "6, 8, left, default");
		textFieldMateria.setColumns(20);
		
		JLabel lblAssunto = new JLabel("Assunto:");
		contentPane.add(lblAssunto, "4, 10");
		
		textFieldAssunto = new JTextField();
		contentPane.add(textFieldAssunto, "6, 10, left, default");
		textFieldAssunto.setColumns(20);
		
		lblLogo = new JLabel("Logo:");
		contentPane.add(lblLogo, "4, 12");
		
		txtImagejpg = new JTextField();
		txtImagejpg.setText("image.jpg");
		contentPane.add(txtImagejpg, "6, 12, left, default");
		txtImagejpg.setColumns(20);
		
		button = new JButton("...");
		contentPane.add(button, "8, 12");
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				int retVal = fc.showSaveDialog(PdfFrame.this); //TODO - ALTERAR
				pdfCreator.saveConfig(new PdfConfig(textFieldProfessor.getText(),textFieldMateria.getText(),textFieldAssunto.getText()));
				
				if(retVal==JFileChooser.APPROVE_OPTION){
					try {
						String filepath = fc.getSelectedFile().getAbsolutePath();
						pdfCreator.CreatePDF(filepath);
					} catch (IOException
							| DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		contentPane.add(btnSalvar, "6, 18");
		
		this.textFieldProfessor.setText(professor);
		this.textFieldMateria.setText(materia);
		this.textFieldAssunto.setText(assunto);
	}
	
	public String getProfessor(){
		return textFieldProfessor.getText();
	}
	
	public String getMateria(){
		return textFieldMateria.getText();
	}
	
	public String getAssunto(){
		return textFieldAssunto.getText();
	}
}
