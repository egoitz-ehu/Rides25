package gui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import domain.Driver;
import domain.Erreserba;
import domain.ErreserbaEgoera;
import domain.Ride;
import domain.RideErreserbaContainer;
import domain.Traveler;

public class ErreserbakKontsultatuGUI extends JFrame {
private JPanel contentPane;
	//private JScrollPane scrollPane = new JScrollPane();
	private JTable table;
	private DefaultTableModel tableModel;
	
	private JButton onartuButton;
	private JButton ukatuButton;
	
	private RideErreserbaContainer selectedErreserba;
	
	private JLabel messageLabel;
	private JLabel lblMessage;

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
	public ErreserbakKontsultatuGUI(Traveler t) {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("ErreserbakGestionatu.Title"));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridwidth = 5;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = table.getSelectedRow();
				if(selectedRow != -1) {
					selectedErreserba = (RideErreserbaContainer) tableModel.getValueAt(selectedRow, 6);
					//System.out.println("Selected a new erreserba:"+selectedErreserba);
					if(selectedErreserba!=null && selectedErreserba.getErreserba().getEgoera().equals(ErreserbaEgoera.ONARTUA)) {
						onartuButton.setEnabled(true);
						ukatuButton.setEnabled(true);
						lblMessage.setText("");
					} else {
						if(selectedErreserba.getErreserba().getEgoera().equals(ErreserbaEgoera.ZAIN)) {
							lblMessage.setText(ResourceBundle.getBundle("Etiquetas").getString("ErreserbakKontsultatuGUI.MessageZain"));
						} else if(selectedErreserba.getErreserba().getEgoera().equals(ErreserbaEgoera.BAIEZTATUA)) {
							lblMessage.setText(ResourceBundle.getBundle("Etiquetas").getString("ErreserbakKontsultatuGUI.MessageBaieztatua"));
						}
						onartuButton.setEnabled(false);
						ukatuButton.setEnabled(false);
					}
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
						ResourceBundle.getBundle("Etiquetas").getString("ErreserbakKontsultatuGUI.Zenbakia"),
						ResourceBundle.getBundle("Etiquetas").getString("ErreserbakKontsultatuGUI.Egoera"),
						ResourceBundle.getBundle("Etiquetas").getString("ErreserbakKontsultatuGUI.Data"),
						ResourceBundle.getBundle("Etiquetas").getString("ErreserbakKontsultatuGUI.Kop"),
						ResourceBundle.getBundle("Etiquetas").getString("ErreserbakKontsultatuGUI.From"),
						ResourceBundle.getBundle("Etiquetas").getString("ErreserbakKontsultatuGUI.To")
				});
		tableModel.setColumnCount(7);
		table.setModel(tableModel);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(30);
		table.getColumnModel().getColumn(2).setPreferredWidth(30);
		table.getColumnModel().getColumn(3).setPreferredWidth(30);
		table.getColumnModel().getColumn(4).setPreferredWidth(30);
		table.getColumnModel().getColumn(5).setPreferredWidth(30);
		table.getColumnModel().removeColumn(table.getColumnModel().getColumn(6));
		
		scrollPane.setViewportView(table);
		
		onartuButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ErreserbakGestionatuGUI.Onartu"));
		onartuButton.setEnabled(false);
		onartuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WelcomeGUI.getBusinessLogic().erreserbaBaieztatu(selectedErreserba);
				onartuButton.setEnabled(false);
				ukatuButton.setEnabled(false);
				selectedErreserba=null;
				ezarriErreserbak(t);
			}
		});
		
		lblMessage = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 5;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 3;
		contentPane.add(lblMessage, gbc_lblNewLabel);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 4;
		contentPane.add(onartuButton, gbc_btnNewButton);
		
		ukatuButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ErreserbakGestionatuGUI.Ukatu"));
		ukatuButton.setEnabled(false);
		ukatuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WelcomeGUI.getBusinessLogic().erreserbaEzeztatu(selectedErreserba,t.getEmail());
				onartuButton.setEnabled(false);
				ukatuButton.setEnabled(false);
				selectedErreserba=null;
				ezarriErreserbak(t);
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_1.gridx = 4;
		gbc_btnNewButton_1.gridy = 4;
		contentPane.add(ukatuButton, gbc_btnNewButton_1);
		
		messageLabel = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		messageLabel.setForeground(Color.BLUE);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.gridwidth = 5;
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 5;
		contentPane.add(messageLabel, gbc_lblNewLabel_1);
		
		ezarriErreserbak(t);
	}
	
	private void ezarriErreserbak(Traveler t) {
		String egoera = "";
		tableModel.getDataVector().removeAllElements();
			//List<Erreserba> erreserbaList = t.getBookedRides();
			List<RideErreserbaContainer> erreserbaList = WelcomeGUI.getBusinessLogic().getRideErreserbaContainers(t);
			//System.out.println(erreserbaList);
			if(erreserbaList.isEmpty()) {
				messageLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("ErreserbakGestionatuGUI.MezuaGabe"));
			} else {
				messageLabel.setText("");
				for(RideErreserbaContainer c:erreserbaList) {
					//modeloErreserba.addElement(err.toString());
					Vector<Object> row = new Vector<Object>();
					Erreserba err = c.getErreserba();
					row.add(err.getEskaeraNum());
					switch(err.getEgoera()) {
					case ZAIN:
						egoera = ResourceBundle.getBundle("Etiquetas").getString("ErreserbakGestionatuGUI.ZAIN");
						break;
					case UKATUA:
						egoera = ResourceBundle.getBundle("Etiquetas").getString("ErreserbakGestionatuGUI.UKATUA");
						break;
					case ONARTUA:
						egoera = ResourceBundle.getBundle("Etiquetas").getString("ErreserbakGestionatuGUI.ONARTUA");
						break;
					case BAIEZTATUA:
						egoera = ResourceBundle.getBundle("Etiquetas").getString("ErreserbakGestionatuGUI.Baieztatua");
						break;
					case EZEZTATUA:
						egoera = ResourceBundle.getBundle("Etiquetas").getString("ErreserbakGestionatuGUI.Ezeztatua");
						break;
					case KANTZELATUA:
						egoera = ResourceBundle.getBundle("Etiquetas").getString("ErreserbakGestionatuGUI.Kantzelatua");
					default:
						break;
					}
					row.add(egoera);
					row.add(err.getErreserbaData());
					row.add(err.getPlazaKop());
					row.add(err.getFrom());
					row.add(err.getTo());
					row.add(c);
					tableModel.addRow(row);	
					}
				}
	}
}
