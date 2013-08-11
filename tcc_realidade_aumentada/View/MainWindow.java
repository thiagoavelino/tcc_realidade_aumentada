package View;

import javax.swing.JFrame;
import java.awt.SystemColor;
import javax.swing.JFileChooser;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

import java.awt.Dimension;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileFilter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
public class MainWindow implements Runnable{

	private JFrame frmAplicaoDeRealidade;
	public final String IMG_FORMAT = ".png";
	private CamRAPanel RAPanel;

	public MainWindow() {
		initialize();
	}
	
	private void initialize() {
		setFrmAplicaoDeRealidade(new JFrame());
		getFrmAplicaoDeRealidade().setTitle("Aplica\u00E7\u00E3o de Realidade Aumentada");
		getFrmAplicaoDeRealidade().getContentPane().setBackground(SystemColor.inactiveCaptionBorder);
		getFrmAplicaoDeRealidade().getContentPane().setLayout(null);
		camConfig();		
		setRAPanel(new CamRAPanel(Webcam.getDefault().getImage()));
		getFrmAplicaoDeRealidade().getContentPane().add(getRAPanel());
		getFrmAplicaoDeRealidade().setForeground(SystemColor.inactiveCaptionBorder);
		getFrmAplicaoDeRealidade().setResizable(false);
		getFrmAplicaoDeRealidade().setBounds(50, 50, 900, 540);
		getFrmAplicaoDeRealidade().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		getFrmAplicaoDeRealidade().setJMenuBar(menuBar);
		
		JMenu mnArquivo = new JMenu("Arquivo");
		menuBar.add(mnArquivo);
		
		JMenuItem mntmSalvarImagem = new JMenuItem("Salvar Imagem...");
		mntmSalvarImagem.addActionListener(actionListSalvarImg());
		mnArquivo.add(mntmSalvarImagem);
		
		JMenuItem mntmSair = new JMenuItem("Sair");
		mntmSair.addActionListener(closeFrame());
		mnArquivo.add(mntmSair);
	}

	public ActionListener closeFrame() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getFrmAplicaoDeRealidade().dispose();
			}
		};
	}

	public void camConfig() {
		Webcam webcam = Webcam.getDefault();
		webcam.setViewSize(new Dimension(640, 480));
		WebcamPanel panelCam = new WebcamPanel(webcam);
	}
	
	public ActionListener actionListSalvarImg() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Webcam.setAutoOpenMode(true);
				BufferedImage image = Webcam.getDefault().getImage();
				JFileChooser arquivo = new JFileChooser();
				setFileChooser(arquivo);
				int retorno = arquivo.showOpenDialog(null);
				if(retorno == JFileChooser.APPROVE_OPTION){
					String caminhoArquivo = arquivo.getSelectedFile().getAbsolutePath();
					Model.Cam cam = new Model.Cam();
					cam.saveImage(caminhoArquivo, image);
				}
			}
		};
	}
	
	public void setFileChooser(JFileChooser arquivo) {
		arquivo.setFileFilter(setFileFilter());
		arquivo.setApproveButtonText("Salvar");
	}
	
	public FileFilter setFileFilter () {
		return new javax.swing.filechooser.FileFilter(){
				public boolean accept(File f){
					return f.getName().toLowerCase().endsWith(IMG_FORMAT) || f.isDirectory();
				}
				public String getDescription() {
					return "Arquivos de imagem "+ IMG_FORMAT;
				}
			};
	}

	public JFrame getFrmAplicaoDeRealidade() {
		return frmAplicaoDeRealidade;
	}

	private void setFrmAplicaoDeRealidade(JFrame frmAplicaoDeRealidade) {
		this.frmAplicaoDeRealidade = frmAplicaoDeRealidade;
	}

	@Override
	public void run() {
		try {
			frmAplicaoDeRealidade.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CamRAPanel getRAPanel() {
		return RAPanel;
	}

	private void setRAPanel(CamRAPanel rAPanel) {
		RAPanel = rAPanel;
	}
}
