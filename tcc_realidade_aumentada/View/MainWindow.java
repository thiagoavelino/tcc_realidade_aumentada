package View;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.JFileChooser;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.ScrollPane;
import javax.swing.JSlider;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JToggleButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextPane;
public class MainWindow implements Runnable{

	private JFrame frmAplicaoDeRealidade;
	public final String IMG_FORMAT = ".png";
	private CamRAPanel RAPanel;
	private JToggleButton tglbtnAcionarAlgoritmo;
	private JSlider sliderThreshold;
	private boolean algoritmoLigado;
	private boolean binaryImage;
	private JToolBar toolBar;
	private JButton buttonKmeans;
	private JButton buttonLinear;
	private JButton buttonPolinomial;
	private JPanel panelConfig;
	private String algorithmSelected;
	private JSpinner spinnerKmeans;

	public MainWindow() {
		initialize();
	}
	
	private void initialize() {
		
		algoritmoLigado = false;
		algorithmSelected = "";
		setFrmAplicaoDeRealidade(new JFrame());
		getFrmAplicaoDeRealidade().setPreferredSize(new Dimension(1000, 600));
		getFrmAplicaoDeRealidade().pack();
		getFrmAplicaoDeRealidade().setTitle("Aplica\u00E7\u00E3o de Realidade Aumentada");
		getFrmAplicaoDeRealidade().getContentPane().setLayout(null);
		camConfig();		
		setRAPanel(new CamRAPanel(Webcam.getDefault().getImage()));
		getFrmAplicaoDeRealidade().getContentPane().add(getRAPanel());
		
		getFrmAplicaoDeRealidade().setForeground(SystemColor.inactiveCaptionBorder);
		
		getFrmAplicaoDeRealidade().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Tool Bar
		createToolBar();
		
		setSliderThreshold(new JSlider(JSlider.HORIZONTAL, 0, 255, 110));
		getSliderThreshold().setMinorTickSpacing(5);
		getSliderThreshold().setMajorTickSpacing(20);
		getSliderThreshold().setBounds(655, 31, 300, 40);
		getSliderThreshold().setPaintTicks(true);
		getSliderThreshold().setPaintLabels(true);
		getSliderThreshold().setFont(new Font("Tahoma", Font.PLAIN, 11));
		frmAplicaoDeRealidade.getContentPane().add(getSliderThreshold());
		
		JLabel lblNewLabel = new JLabel("Threshold: ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(661, 11, 136, 23);
		frmAplicaoDeRealidade.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel2 = new JLabel("Defina o limite de limiariza��o da imagem.");
		lblNewLabel2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel2.setBounds(730, 11, 270, 23);
		frmAplicaoDeRealidade.getContentPane().add(lblNewLabel2);
		
		tglbtnAcionarAlgoritmo = new JToggleButton("Visualizar Algoritmo");
		tglbtnAcionarAlgoritmo.setFont(new Font("Tahoma", Font.PLAIN, 9));
		tglbtnAcionarAlgoritmo.setBounds(665, 82, 117, 19);
		tglbtnAcionarAlgoritmo.addChangeListener(new ChangeListener( ) {
		      public void stateChanged(ChangeEvent ev) {
		    	  if(algoritmoLigado) algoritmoLigado=false;
		    	  else algoritmoLigado=true;
		      }
		    });
		frmAplicaoDeRealidade.getContentPane().add(tglbtnAcionarAlgoritmo);
		
		generatePainelInitial();
		
		
		JLabel lblPainelAlgoritmo = new JLabel("Painel Algoritmo:");
		lblPainelAlgoritmo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPainelAlgoritmo.setBounds(665, 115, 117, 14);
		frmAplicaoDeRealidade.getContentPane().add(lblPainelAlgoritmo);
		
		
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

	public void generatePainelInitial() {
		panelConfig = new JPanel();
		panelConfig.setBorder(new LineBorder(Color.GRAY, 1, true));
		panelConfig.setBounds(666, 140, 309, 385);
		frmAplicaoDeRealidade.getContentPane().add(panelConfig);
		JLabel initialText = new JLabel("Selecione um algoritmo na barra acima da image.");
		initialText.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelConfig.add(initialText);
	}

	public void createToolBar() {
		toolBar = new JToolBar();
		buttonKmeans = new JButton();
		buttonKmeans.setIcon(new ImageIcon("icons/kmeans.jpg"));
		buttonKmeans.addActionListener(this.buttonKmeansAction());
		toolBar.add(buttonKmeans);
		buttonLinear = new JButton();
		buttonLinear.setIcon(new ImageIcon("icons/linear.jpg"));
		buttonLinear.addActionListener(this.buttonLinearAction());
		toolBar.add(buttonLinear);
		buttonPolinomial = new JButton();
		buttonPolinomial.setIcon(new ImageIcon("icons/polinomial.jpg"));
		buttonPolinomial.addActionListener(this.buttonPolinomialAction());
		toolBar.add(buttonPolinomial);
		toolBar.setBounds(0, 0, 645, 40);
		toolBar.setFloatable(false);
		frmAplicaoDeRealidade.getContentPane().add(toolBar);
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
	
	public ActionListener buttonKmeansAction() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				algorithmSelected = "kmeans";
				panelConfig.removeAll();
				JLabel kmeans = new JLabel("K-means");
				kmeans.setFont(new Font("Tahoma", Font.BOLD, 11));
				panelConfig.add(kmeans);
				ImageIcon imageKmeans = new ImageIcon("textos/kmeans.jpg"); 
				JLabel imageKmeansLabel = new JLabel(); 
				imageKmeansLabel.setIcon(imageKmeans); 
				panelConfig.add(imageKmeansLabel);
				JLabel numeroPontos = new JLabel("N�mero de pontos detectados: ");
				numeroPontos.setFont(new Font("Tahoma", Font.PLAIN, 11));
				panelConfig.add(numeroPontos);
				JLabel numeroPontosField = new JLabel("1");
				numeroPontosField.setFont(new Font("Tahoma", Font.PLAIN, 11));
				panelConfig.add(numeroPontosField);
				JLabel numeroClustersField = new JLabel("Defina o n�mero de clusters:");
				numeroClustersField.setFont(new Font("Tahoma", Font.PLAIN, 11));
				panelConfig.add(numeroClustersField);
				SpinnerNumberModel model = new SpinnerNumberModel(1.0, 1.0, 30.0, 1.0);  
				spinnerKmeans = new JSpinner(model);
				JFormattedTextField tf = ((JSpinner.DefaultEditor)spinnerKmeans.getEditor())
						.getTextField();
						  tf.setEditable(false);
				panelConfig.add(spinnerKmeans);
				panelConfig.revalidate();
				panelConfig.repaint();
				
			}
		};
	}
	public ActionListener buttonLinearAction() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				algorithmSelected = "linear";
				panelConfig.removeAll();
				JLabel kmeans = new JLabel("Linear");
				kmeans.setFont(new Font("Tahoma", Font.BOLD, 11));
				panelConfig.add(kmeans);
				panelConfig.revalidate();
				panelConfig.repaint();
				
			}
		};
	}
	public ActionListener buttonPolinomialAction() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				algorithmSelected = "polinomial";
				panelConfig.removeAll();
				JLabel kmeans = new JLabel("Polinomial");
				kmeans.setFont(new Font("Tahoma", Font.BOLD, 11));
				panelConfig.add(kmeans);
				panelConfig.revalidate();
				panelConfig.repaint();
				
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
		RAPanel.setSize(640, 480);
	}

	public JSlider getSliderThreshold() {
		return sliderThreshold;
	}

	public void setSliderThreshold(JSlider sliderThreshold) {
		this.sliderThreshold = sliderThreshold;
	}

	public JToggleButton getTglbtnAcionarAlgoritmo() {
		return tglbtnAcionarAlgoritmo;
	}

	public void setTglbtnAcionarAlgoritmo(JToggleButton tglbtnAcionarAlgoritmo) {
		this.tglbtnAcionarAlgoritmo = tglbtnAcionarAlgoritmo;
	}

	public boolean isAlgoritmoLigado() {
		return algoritmoLigado;
	}

	public void setAlgoritmoLigado(boolean algoritmoLigado) {
		this.algoritmoLigado = algoritmoLigado;
	}

	public String getAlgorithmSelected() {
		return algorithmSelected;
	}

	public void setAlgorithmSelected(String algorithmSelected) {
		this.algorithmSelected = algorithmSelected;
	}

	public JSpinner getSpinnerKmeans() {
		return spinnerKmeans;
	}

	public void setSpinnerKmeans(JSpinner spinnerKmeans) {
		this.spinnerKmeans = spinnerKmeans;
	}
}
