package PDFCreator;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SavePdfSectionDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField TituloTextField;
	private JTextField DescTextField;
	private JTextField ImageTextField;
	private ArrayList<ImStr> itemList;
	private String imagePath;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SavePdfSectionDialog dialog = new SavePdfSectionDialog(null, null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SavePdfSectionDialog(ArrayList<ImStr> list, String image) {
		this.itemList = list;
		this.imagePath = image;
		setTitle("Salvar Imagem");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblTtulo = new JLabel("T\u00EDtulo");
			lblTtulo.setBounds(70, 13, 26, 14);
			contentPanel.add(lblTtulo);
		}
		{
			TituloTextField = new JTextField();
			TituloTextField.setBounds(106, 10, 281, 20);
			contentPanel.add(TituloTextField);
			TituloTextField.setColumns(10);
		}
		
		JLabel lblDescrio = new JLabel("Descri\u00E7\u00E3o");
		lblDescrio.setBounds(50, 50, 46, 14);
		contentPanel.add(lblDescrio);
		
		DescTextField = new JTextField();
		DescTextField.setBounds(106, 47, 281, 125);
		contentPanel.add(DescTextField);
		DescTextField.setColumns(10);
		
		JLabel lblImagem = new JLabel("Imagem");
		lblImagem.setBounds(50, 187, 46, 14);
		contentPanel.add(lblImagem);
		
		ImageTextField = new JTextField();
		ImageTextField.setEditable(false);
		ImageTextField.setBounds(106, 183, 281, 20);
		contentPanel.add(ImageTextField);
		ImageTextField.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						itemList.add(new ImStr(TituloTextField.getText(),imagePath,DescTextField.getText()));
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		ImageTextField.setText(image);
	}
}
