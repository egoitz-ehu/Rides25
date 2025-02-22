package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import domain.Driver;
import domain.Erreserba;

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

public class ErreserbakGestionatuGUI extends JFrame {

	private JPanel contentPane;
	
	private JComboBox<Integer> ridesComboBox;
	private DefaultComboBoxModel<Integer> ridesModel = new DefaultComboBoxModel<Integer>();
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel tableModel;

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

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblNewLabel = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		ridesComboBox = new JComboBox();
		ridesComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Erreserba> erreserbaList = WelcomeGUI.getBusinessLogic().lortuErreserbak((int) ridesModel.getSelectedItem());
				for(Erreserba err:erreserbaList) {
					//modeloErreserba.addElement(err.toString());
					Vector<Object> row = new Vector<Object>();
					row.add(err.getEskaeraNum());
					row.add(err.getPlazaKop());
					row.add(err.getErreserbaData());
					row.add(err.getEgoera());
					row.add(err.getBidaiaria().getEmail());
					tableModel.addRow(row);	
				}
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
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		
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
		List<Integer> ridesNumber = WelcomeGUI.getBusinessLogic().getAllRidesNumber(d.getEmail());
		ridesModel.addAll(ridesNumber);
		
		scrollPane.setViewportView(table);
	}

}
