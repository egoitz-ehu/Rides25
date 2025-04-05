package gui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import domain.Driver;
import domain.Erreserba;
import domain.ErreserbaEgoera;
import domain.Ride;
import domain.TravelerErreserbaConatainer;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ErreserbakGestionatuGUI extends JFrame {

	private JPanel contentPane;
	
	private JComboBox<Ride> ridesComboBox;
	private DefaultComboBoxModel<Ride> ridesModel = new DefaultComboBoxModel<Ride>();
	//private JScrollPane scrollPane = new JScrollPane();
	private JTable table;
	private DefaultTableModel tableModel;
	
	private JButton onartuButton;
	private JButton ukatuButton;
	
	private int selectedErreserbaNumber;
	private Ride selectedRide;
	
	private JLabel messageLabel;

	/**
	 * Launch the application.
	 *//*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ErreserbakGestionatu frame = new ErreserbakGestionatu();
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
	public ErreserbakGestionatuGUI(Driver d) {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("ErreserbakGestionatu.Title"));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErreserbakGestionatuGUI.BidaiaNum"));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		ridesComboBox = new JComboBox();
		ridesComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ezarriErreserbak();
			}
		});
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 2;
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 1;
		contentPane.add(ridesComboBox, gbc_comboBox);
		ridesComboBox.setModel(ridesModel);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = table.getSelectedRow();
				if(selectedRow != -1) {
					selectedErreserbaNumber = (int) tableModel.getValueAt(selectedRow, 0);
					onartuButton.setEnabled(true);
					ukatuButton.setEnabled(true);
				} else {
					onartuButton.setEnabled(false);
					ukatuButton.setEnabled(false);
				}
			}
		});
		
		tableModel = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						ResourceBundle.getBundle("Etiquetas").getString("ErreserbakGestionatuGUI.ErreserbaNum"),
						ResourceBundle.getBundle("Etiquetas").getString("ErreserbakGestionatuGUI.EserKop"),
						ResourceBundle.getBundle("Etiquetas").getString("ErreserbakGestionatuGUI.Data"),
						ResourceBundle.getBundle("Etiquetas").getString("ErreserbakGestionatuGUI.Egoera"),
						ResourceBundle.getBundle("Etiquetas").getString("ErreserbakGestionatuGUI.Email")
				});
		table.setModel(tableModel);
		List<Ride> driverRides = WelcomeGUI.getBusinessLogic().getDriverAllRides(d.getEmail());
		//List<Ride> driverRides = d.getRides();
		ridesModel.addAll(driverRides);
		
		scrollPane.setViewportView(table);
		
		onartuButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ErreserbakGestionatuGUI.Onartu"));
		onartuButton.setEnabled(false);
		onartuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WelcomeGUI.getBusinessLogic().onartuErreserba(selectedErreserbaNumber, d);
				ezarriErreserbak();
				onartuButton.setEnabled(false);
				ukatuButton.setEnabled(false);
				messageLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("ErreserbakGestionatuGUI.MezuaOnartu"));
				ridesComboBox.removeAllItems();
				List<Ride> driverRides = WelcomeGUI.getBusinessLogic().getDriverAllRides(d.getEmail());
				ridesModel.addAll(driverRides);
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 3;
		contentPane.add(onartuButton, gbc_btnNewButton);
		
		ukatuButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ErreserbakGestionatuGUI.Ukatu"));
		ukatuButton.setEnabled(false);
		ukatuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WelcomeGUI.getBusinessLogic().ukatuErreserba(selectedErreserbaNumber, selectedRide);
				ezarriErreserbak();
				onartuButton.setEnabled(false);
				ukatuButton.setEnabled(false);
				messageLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("ErreserbakGestionatuGUI.MezuaUkatu"));
				ridesComboBox.removeAllItems();
				List<Ride> driverRides = WelcomeGUI.getBusinessLogic().getDriverAllRides(d.getEmail());
				ridesModel.addAll(driverRides);
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_1.gridx = 3;
		gbc_btnNewButton_1.gridy = 3;
		contentPane.add(ukatuButton, gbc_btnNewButton_1);
		
		messageLabel = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		messageLabel.setForeground(Color.BLUE);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.gridwidth = 4;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 4;
		contentPane.add(messageLabel, gbc_lblNewLabel_1);
	}
	
	private void ezarriErreserbak() {
		String egoera = "";
		tableModel.getDataVector().removeAllElements();
		//List<Erreserba> erreserbaList = WelcomeGUI.getBusinessLogic().lortuErreserbak(((Ride) ridesModel.getSelectedItem()).getRideNumber());
		selectedRide =  (Ride) ridesModel.getSelectedItem();
		if(selectedRide != null) {
			List<TravelerErreserbaConatainer> containerList =WelcomeGUI.getBusinessLogic().getErreserbaTravelerContainers(selectedRide);
			if(containerList.isEmpty()) {
				messageLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("ErreserbakGestionatuGUI.MezuaGabe"));
			} else {
				messageLabel.setText("");
				for(TravelerErreserbaConatainer c:containerList) {
					//modeloErreserba.addElement(err.toString());
					Erreserba err = c.getErreserba();
					if(err.getEgoera()==ErreserbaEgoera.ZAIN) {
						Vector<Object> row = new Vector<Object>();
						row.add(err.getEskaeraNum());
						row.add(err.getPlazaKop());
						row.add(err.getErreserbaData());
						switch(err.getEgoera()) {
						case ZAIN:
							egoera = ResourceBundle.getBundle("Etiquetas").getString("ErreserbakGestionatuGUI.ZAIN");
							break;
						case UKATUA:
							egoera = ResourceBundle.getBundle("Etiquetas").getString("ErreserbakGestionatuGUI.UKATUA");
							break;
						case ONARTUA:
							egoera = ResourceBundle.getBundle("Etiquetas").getString("ErreserbakGestionatuGUI.ONARTUA");
						}
						row.add(egoera);
						row.add(c.getTraveler().getEmail());
						tableModel.addRow(row);	
					}
				}
			}
		}
	}
}
