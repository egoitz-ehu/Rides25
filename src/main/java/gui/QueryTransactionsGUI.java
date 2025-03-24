package gui;


import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import java.awt.GridLayout;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import domain.Mugimendua;
import domain.User;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;

public class QueryTransactionsGUI extends JFrame {

	private JPanel contentPane;
	private JTable table;
	
	private DefaultTableModel tableModelMugimenduak;
	
	private String[] columnNamesRides = new String[] {
			"Id",
			ResourceBundle.getBundle("Etiquetas").getString("QueryTransactionGUI.Date"),
			ResourceBundle.getBundle("Etiquetas").getString("QueryTransactionGUI.Amount"),
			ResourceBundle.getBundle("Etiquetas").getString("QueryTransactionGUI.Description")
	};

	/**
	 * Launch the application.
	 *//*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QueryTransactionsGUI frame = new QueryTransactionsGUI();
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
	public QueryTransactionsGUI(User u) {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[] {0, 30};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0};
		contentPane.setLayout(gbl_contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("QueryTransactionGUI.Close"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButton2_actionPerformed(e);
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 1;
		contentPane.add(btnNewButton, gbc_btnNewButton);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
			},columnNamesRides
		));
		tableModelMugimenduak = new DefaultTableModel(null, columnNamesRides);
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(90);
		table.getColumnModel().getColumn(2).setPreferredWidth(60);
		scrollPane.setViewportView(table);
		
				table.setModel(tableModelMugimenduak);
		
		
		List<Mugimendua> mugimenduList = u.getMugimenduak();
		if(!mugimenduList.isEmpty())
		for (Mugimendua m:mugimenduList){
			Vector<Object> row = new Vector<Object>();
			row.add(m.getId());
			row.add(m.getData());
			row.add(m.getKopurua());
			switch(m.getType()) {
			case DIRU_SARRERA:
				row.add(ResourceBundle.getBundle("Etiquetas").getString("QueryTransactionGUI.Sarrera"));	
				break;
			case BIDAIA_KANTZELATU:
				break;
			case DIRU_IRTEERA:
				break;
			case ERRESERBA_ONARTU:
				break;
			case ERRESERBA_SORTU:
				break;
			case ERRESERBA_UKATU:
				break;
			default:
				break;
			}
			tableModelMugimenduak.addRow(row);
		}
	}
	
	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
