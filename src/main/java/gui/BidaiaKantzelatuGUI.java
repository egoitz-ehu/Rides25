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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BidaiaKantzelatuGUI extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel tableModel;
	
	private Ride selectedRide;
	
	private JButton buttonKantzelatu;
	private JButton buttonItxi;
	
	private String[] columnNamesRides = new String[] {
			"Number", "From", "To", "Date", "Egoera"
	};
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
		gbl_contentPane.columnWidths = new int[] {0, 0};
		gbl_contentPane.rowHeights = new int[] {220, 30};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0};
		gbl_contentPane.rowWeights = new double[]{1.0, 1.0};
		contentPane.setLayout(gbl_contentPane);
		
		JScrollPane scrollPane = new JScrollPane();

		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
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
		            buttonKantzelatu.setEnabled(true);
		            buttonItxi.setEnabled(true);
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
		
		scrollPane.setViewportView(table);
		
		buttonKantzelatu = new JButton("New button");
		buttonKantzelatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WelcomeGUI.getBusinessLogic().kantzelatuBidaia(selectedRide, d);
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 1;
		contentPane.add(buttonKantzelatu, gbc_btnNewButton);
		buttonKantzelatu.setEnabled(false);
		
		buttonItxi = new JButton("New button");
		buttonItxi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itxi(e);
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 1;
		contentPane.add(buttonItxi, gbc_btnNewButton_1);
		buttonItxi.setEnabled(false);
		
		// Taula osatu
		taulaOsatu(d);
	}
	
	private void taulaOsatu(Driver d) {
		List<Ride> rideList = d.getRides();
		for(Ride r:rideList) {
			Vector<Object> row = new Vector<Object>();
			row.add(r.getRideNumber());
			row.add(r.getFrom());
			row.add(r.getTo());
			row.add(r.getDate());
			row.add(r.getEgoera());
			row.add(r); //Informazio gordetzeko zutabea
			tableModel.addRow(row);
		}
	}
	
	private void itxi(ActionEvent e) {
		this.setVisible(false);
	}

}
