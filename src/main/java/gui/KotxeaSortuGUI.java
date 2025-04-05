package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Driver;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

public class KotxeaSortuGUI extends JFrame {

	private JPanel contentPane;
	private JTextField fieldMatrikula;
	private JTextField fieldEserKop;
	private JLabel lblKolorea;
	private JTextField fieldKolorea;
	private JLabel lblMota;
	private JTextField fieldMota;
	private JButton btnSortu;
	private JButton btnItxi;
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 *//*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KotxeaSortuGUI frame = new KotxeaSortuGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public KotxeaSortuGUI(Driver d) {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblMatrikula = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateCar.Matrikula"));
		GridBagConstraints gbc_lblMatrikula = new GridBagConstraints();
		gbc_lblMatrikula.weighty = 1.0;
		gbc_lblMatrikula.gridwidth = 4;
		gbc_lblMatrikula.insets = new Insets(0, 0, 5, 5);
		gbc_lblMatrikula.gridx = 0;
		gbc_lblMatrikula.gridy = 1;
		contentPane.add(lblMatrikula, gbc_lblMatrikula);
		
		fieldMatrikula = new JTextField();
		GridBagConstraints gbc_fieldMatrikula = new GridBagConstraints();
		gbc_fieldMatrikula.insets = new Insets(0, 0, 5, 0);
		gbc_fieldMatrikula.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldMatrikula.gridx = 4;
		gbc_fieldMatrikula.gridy = 1;
		contentPane.add(fieldMatrikula, gbc_fieldMatrikula);
		fieldMatrikula.setColumns(10);
		
		JLabel lblEserKop = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateCar.EserKop"));
		GridBagConstraints gbc_lblEserKop = new GridBagConstraints();
		gbc_lblEserKop.weighty = 1.0;
		gbc_lblEserKop.gridwidth = 4;
		gbc_lblEserKop.insets = new Insets(0, 0, 5, 5);
		gbc_lblEserKop.gridx = 0;
		gbc_lblEserKop.gridy = 3;
		contentPane.add(lblEserKop, gbc_lblEserKop);
		
		fieldEserKop = new JTextField();
		GridBagConstraints gbc_fieldEserKop = new GridBagConstraints();
		gbc_fieldEserKop.insets = new Insets(0, 0, 5, 0);
		gbc_fieldEserKop.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldEserKop.gridx = 4;
		gbc_fieldEserKop.gridy = 3;
		contentPane.add(fieldEserKop, gbc_fieldEserKop);
		fieldEserKop.setColumns(10);
		
		lblKolorea = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateCar.Kolorea"));
		GridBagConstraints gbc_lblKolorea = new GridBagConstraints();
		gbc_lblKolorea.weighty = 1.0;
		gbc_lblKolorea.gridwidth = 4;
		gbc_lblKolorea.insets = new Insets(0, 0, 5, 5);
		gbc_lblKolorea.gridx = 0;
		gbc_lblKolorea.gridy = 5;
		contentPane.add(lblKolorea, gbc_lblKolorea);
		
		fieldKolorea = new JTextField();
		GridBagConstraints gbc_fieldKolorea = new GridBagConstraints();
		gbc_fieldKolorea.insets = new Insets(0, 0, 5, 0);
		gbc_fieldKolorea.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldKolorea.gridx = 4;
		gbc_fieldKolorea.gridy = 5;
		contentPane.add(fieldKolorea, gbc_fieldKolorea);
		fieldKolorea.setColumns(10);
		
		lblMota = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateCar.Model"));
		GridBagConstraints gbc_lblMota = new GridBagConstraints();
		gbc_lblMota.weighty = 1.0;
		gbc_lblMota.weightx = 0.6;
		gbc_lblMota.gridwidth = 4;
		gbc_lblMota.insets = new Insets(0, 0, 5, 5);
		gbc_lblMota.gridx = 0;
		gbc_lblMota.gridy = 7;
		contentPane.add(lblMota, gbc_lblMota);
		
		fieldMota = new JTextField();
		GridBagConstraints gbc_fieldMota = new GridBagConstraints();
		gbc_fieldMota.insets = new Insets(0, 0, 5, 0);
		gbc_fieldMota.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldMota.gridx = 4;
		gbc_fieldMota.gridy = 7;
		contentPane.add(fieldMota, gbc_fieldMota);
		fieldMota.setColumns(10);
		
		btnSortu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateCar.Sortu"));
		btnSortu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String matrikula = fieldMatrikula.getText();
					int eserKop = Integer.parseInt(fieldEserKop.getText());
					if(eserKop>0) {
						String kolorea = fieldKolorea.getText();
						String mota = fieldMota.getText();
						if(!matrikula.equals("") && !kolorea.equals("") && !mota.equals("")) {
							boolean b = WelcomeGUI.getBusinessLogic().sortuKotxea(matrikula,eserKop,kolorea,mota,d);
							if(b) {
								lblNewLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateCar.Create"));
							} else {
								lblNewLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateCar.Error"));
							}
						} else {
							System.out.println("Eremu guztiak bete behar dira");
							lblNewLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateCar.Empty"));
						}
					} else {
						System.out.println("Zenbaki oso positibo osoa izan behar du");
						lblNewLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateCar.DataError"));
					}
				} catch(NumberFormatException e1) {
					System.out.println("Eserleku kopurua zenbakia osoa izan behar da.");
					lblNewLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateCar.DataError"));
				}
			}
		});
		
		lblNewLabel = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 5;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 8;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		GridBagConstraints gbc_btnSortu = new GridBagConstraints();
		gbc_btnSortu.weighty = 1.0;
		gbc_btnSortu.insets = new Insets(0, 0, 0, 5);
		gbc_btnSortu.gridx = 2;
		gbc_btnSortu.gridy = 9;
		contentPane.add(btnSortu, gbc_btnSortu);
		
		btnItxi = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateCar.Itxi"));
		btnItxi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		GridBagConstraints gbc_btnItxi = new GridBagConstraints();
		gbc_btnItxi.gridx = 4;
		gbc_btnItxi.gridy = 9;
		contentPane.add(btnItxi, gbc_btnItxi);
	}

}
