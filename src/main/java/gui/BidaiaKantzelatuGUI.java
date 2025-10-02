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
import domain.ErreserbaEgoera;
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
			ResourceBundle.getBundle("Etiquetas").getString("BidaiaKantzelatuGUI.Date"),
			ResourceBundle.getBundle("Etiquetas").getString("BidaiaKantzelatuGUI.State")
	};
	private JLabel lblNewLabel;
	/**
	 * Create the frame.
	 */
	public BidaiaKantzelatuGUI(Driver d) {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gblContentPane = new GridBagLayout();
		gblContentPane.columnWidths = new int[] {0, 0, 0};
		gblContentPane.rowHeights = new int[] {220, 0, 30};
		gblContentPane.columnWeights = new double[]{1.0, 0.0, 1.0};
		gblContentPane.rowWeights = new double[]{1.0, 0.0, 1.0};
		contentPane.setLayout(gblContentPane);
		
		JScrollPane scrollPane = new JScrollPane();

		GridBagConstraints gbcScrollPane = new GridBagConstraints();
		gbcScrollPane.gridwidth = 3;
		gbcScrollPane.insets = new Insets(0, 0, 5, 0);
		gbcScrollPane.fill = GridBagConstraints.BOTH;
		gbcScrollPane.gridx = 0;
		gbcScrollPane.gridy = 0;
		contentPane.add(scrollPane, gbcScrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
				new Object[][] {
					{null, null, null},
				},columnNamesRides
			));
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
		            selectedRide = (Ride) tableModel.getValueAt(selectedRow, 3);
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

		tableModel.setColumnCount(4);
		table.setModel(tableModel);
		
		table.getColumnModel().removeColumn(table.getColumnModel().getColumn(3));
		
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
		GridBagConstraints gbcLblNewLabel = new GridBagConstraints();
		gbcLblNewLabel.gridwidth = 3;
		gbcLblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbcLblNewLabel.gridx = 0;
		gbcLblNewLabel.gridy = 1;
		contentPane.add(lblNewLabel, gbcLblNewLabel);
		GridBagConstraints gbcBtnNewButton = new GridBagConstraints();
		gbcBtnNewButton.insets = new Insets(0, 0, 0, 5);
		gbcBtnNewButton.gridx = 0;
		gbcBtnNewButton.gridy = 2;
		contentPane.add(buttonKantzelatu, gbcBtnNewButton);
		buttonKantzelatu.setEnabled(false);
		
		buttonItxi = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		buttonItxi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itxi(e);
			}
		});
		GridBagConstraints gbcBtnNewButton_1 = new GridBagConstraints();
		gbcBtnNewButton_1.gridx = 2;
		gbcBtnNewButton_1.gridy = 2;
		contentPane.add(buttonItxi, gbcBtnNewButton_1);
		buttonItxi.setEnabled(false);
		
		// Taula osatu
		taulaOsatu(d);
	}
	
	private void taulaOsatu(Driver d) {
		List<Ride> rideList = WelcomeGUI.getBusinessLogic().getRidesDriver(d);
		for(Ride r:rideList) {
			Vector<Object> row = new Vector<Object>();
			String eg = null;
			row.add(r.getRideNumber());
			row.add(r.getDate());
			if(r.getEgoera()==RideEgoera.KANTZELATUTA){
				eg=ResourceBundle.getBundle("Etiquetas").getString("BidaiaKantzelatuGUI.EgoeraKantzelatua");
			}
			else if(r.getEgoera()==RideEgoera.MARTXAN) {
				eg=ResourceBundle.getBundle("Etiquetas").getString("BidaiaKantzelatuGUI.EgoeraMartxan");
			}
			row.add(eg);
			row.add(r); //Informazio gordetzeko zutabea
			tableModel.addRow(row);
		}
	}
	
	private void itxi(ActionEvent e) {
		this.setVisible(false);
	}

}
