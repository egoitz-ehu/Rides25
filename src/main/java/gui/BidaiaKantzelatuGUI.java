package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import domain.Driver;
import domain.Ride;
import domain.RideEgoera;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class BidaiaKantzelatuGUI extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel tableModel;
	
	private Ride selectedRide;
	
	private JButton buttonKantzelatu;
	private JButton buttonItxi;
	
	private String[] columnNamesRides = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("BidaiaKantzelatuGUI.Number"),
			ResourceBundle.getBundle("Etiquetas").getString("BidaiaKantzelatuGUI.From"),
			ResourceBundle.getBundle("Etiquetas").getString("BidaiaKantzelatuGUI.To"),
			ResourceBundle.getBundle("Etiquetas").getString("BidaiaKantzelatuGUI.Date"),
			ResourceBundle.getBundle("Etiquetas").getString("BidaiaKantzelatuGUI.State")
	};
	private JLabel lblNewLabel;
	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BidaiaKantzelatuGUI frame = new BidaiaKantzelatuGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/
	/**
	 * Create the frame.
	 */
	public BidaiaKantzelatuGUI(Driver d) {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] {0, 0, 0};
		gbl_contentPane.rowHeights = new int[] {220, 0, 30};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 1.0};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, 1.0};
		contentPane.setLayout(gbl_contentPane);
		
		JScrollPane scrollPane = new JScrollPane();

		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
				new Object[][] {
					{null, null, null, null, null},
				},columnNamesRides
			));
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = table.getSelectedRow();
				System.out.println("Selected row:"+selectedRow);
				if (selectedRow != -1) {
		            selectedRide = (Ride) tableModel.getValueAt(selectedRow, 5);
		            if(selectedRide.getEgoera().equals(RideEgoera.KANTZELATUTA)) {
		            	lblNewLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("BidaiaKantzelatuGUI.Arazoa"));
		            	buttonKantzelatu.setEnabled(false);
			            buttonItxi.setEnabled(false);
		            } else {
			            buttonKantzelatu.setEnabled(true);
			            buttonItxi.setEnabled(true);
			            lblNewLabel.setText("");
		            }
		        }
			}
		});

		tableModel = new DefaultTableModel(null,columnNamesRides);
		/*
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(30);
		table.getColumnModel().getColumn(2).setPreferredWidth(30);
		table.getColumnModel().getColumn(3).setPreferredWidth(90);
		table.getColumnModel().getColumn(4).setPreferredWidth(40);
		*/
		tableModel.setColumnCount(6);
		table.setModel(tableModel);
		
		table.getColumnModel().removeColumn(table.getColumnModel().getColumn(5));
		
		scrollPane.setViewportView(table);
		
		buttonKantzelatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BidaiaKantzelatuGUI.Kantzelatu"));
		buttonKantzelatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WelcomeGUI.getBusinessLogic().kantzelatuBidaia(selectedRide, d.getEmail());
				lblNewLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("BidaiaKantzelatuGUI.EzeztatuDa"));
				tableModel.getDataVector().removeAllElements();
				buttonKantzelatu.setEnabled(false);
	            buttonItxi.setEnabled(false);
				taulaOsatu(d);
			}
		});
		
		lblNewLabel = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 3;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 2;
		contentPane.add(buttonKantzelatu, gbc_btnNewButton);
		buttonKantzelatu.setEnabled(false);
		
		buttonItxi = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		buttonItxi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itxi(e);
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.gridx = 2;
		gbc_btnNewButton_1.gridy = 2;
		contentPane.add(buttonItxi, gbc_btnNewButton_1);
		buttonItxi.setEnabled(false);
		
		// Taula osatu
		taulaOsatu(d);
	}
	
	private void taulaOsatu(Driver d) {
		/*
		List<Ride> rideList = WelcomeGUI.getBusinessLogic().getRidesDriver(d);
		for(Ride r:rideList) {
			Vector<Object> row = new Vector<Object>();
			String eg = null;
			row.add(r.getRideNumber());
			row.add(r.getFrom());
			row.add(r.getTo());
			row.add(r.getDate());
			switch(r.getEgoera()){
			case KANTZELATUTA:
				eg=ResourceBundle.getBundle("Etiquetas").getString("BidaiaKantzelatuGUI.EgoeraKantzelatua");
				break;
			case MARTXAN:
				eg=ResourceBundle.getBundle("Etiquetas").getString("BidaiaKantzelatuGUI.EgoeraMartxan");
				break;
			}
			row.add(eg);
			row.add(r); //Informazio gordetzeko zutabea
			tableModel.addRow(row);
		}*/
	}
	
	private void itxi(ActionEvent e) {
		this.setVisible(false);
	}

}
