package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.User;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.List;
import java.util.ResourceBundle;

public class ErreklamazioaSortuGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldKopurua;
	
	private DefaultComboBoxModel modelPerson;


	/**
	 * Create the frame.
	 */
	public ErreklamazioaSortuGUI(User u) {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{194, 194, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblPerson = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioaSortuGUI.Person"));
		GridBagConstraints gbc_lblPerson = new GridBagConstraints();
		gbc_lblPerson.anchor = GridBagConstraints.WEST;
		gbc_lblPerson.insets = new Insets(0, 0, 5, 5);
		gbc_lblPerson.gridx = 0;
		gbc_lblPerson.gridy = 0;
		contentPane.add(lblPerson, gbc_lblPerson);
		
		JComboBox comboBoxPerson = new JComboBox();
		GridBagConstraints gbc_comboBoxPerson = new GridBagConstraints();
		gbc_comboBoxPerson.gridwidth = 3;
		gbc_comboBoxPerson.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxPerson.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxPerson.gridx = 1;
		gbc_comboBoxPerson.gridy = 0;
		contentPane.add(comboBoxPerson, gbc_comboBoxPerson);
		
		modelPerson = new DefaultComboBoxModel();
		comboBoxPerson.setModel(modelPerson);
		
		JLabel lblKopurua = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioaSortuGUI.Kop"));
		GridBagConstraints gbc_lblKopurua = new GridBagConstraints();
		gbc_lblKopurua.anchor = GridBagConstraints.WEST;
		gbc_lblKopurua.insets = new Insets(0, 0, 5, 5);
		gbc_lblKopurua.gridx = 0;
		gbc_lblKopurua.gridy = 2;
		contentPane.add(lblKopurua, gbc_lblKopurua);
		
		textFieldKopurua = new JTextField();
		GridBagConstraints gbc_textFieldKopurua = new GridBagConstraints();
		gbc_textFieldKopurua.gridwidth = 3;
		gbc_textFieldKopurua.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldKopurua.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldKopurua.gridx = 1;
		gbc_textFieldKopurua.gridy = 2;
		contentPane.add(textFieldKopurua, gbc_textFieldKopurua);
		textFieldKopurua.setColumns(10);
		
		JLabel lblMezua = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioaSortuGUI.Mezua"));
		GridBagConstraints gbc_lblMezua = new GridBagConstraints();
		gbc_lblMezua.gridwidth = 5;
		gbc_lblMezua.insets = new Insets(0, 0, 5, 0);
		gbc_lblMezua.gridx = 0;
		gbc_lblMezua.gridy = 3;
		contentPane.add(lblMezua, gbc_lblMezua);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 5;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 4;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JButton btnSortu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioaSortuGUI.Sortu"));
		GridBagConstraints gbc_btnSortu = new GridBagConstraints();
		gbc_btnSortu.gridwidth = 5;
		gbc_btnSortu.gridx = 0;
		gbc_btnSortu.gridy = 5;
		contentPane.add(btnSortu, gbc_btnSortu);
		
		List<String> personList = WelcomeGUI.getBusinessLogic().lortuErabiltzaileEmailGuztiak();
		personList.remove(u.getEmail());
		modelPerson.addAll(personList);
	}
}
