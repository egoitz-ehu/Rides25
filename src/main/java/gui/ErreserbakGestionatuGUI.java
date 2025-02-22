package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Driver;
import domain.Erreserba;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ErreserbakGestionatuGUI extends JFrame {

	private JPanel contentPane;
	
	private JComboBox<Integer> ridesComboBox;
	private DefaultComboBoxModel<Integer> ridesModel = new DefaultComboBoxModel<Integer>();
	
	private JList<String> erreserbaList;
	private DefaultListModel<String> modeloErreserba = new DefaultListModel<String>();

	/**
	 * Launch the application.
	 *//*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ErreserbakGestionatuGUI frame = new ErreserbakGestionatuGUI();
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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(3, 0, 0, 0));
		
		ridesComboBox = new JComboBox();
		ridesComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Erreserba> erreserbaList = WelcomeGUI.getBusinessLogic().lortuErreserbak((int) ridesModel.getSelectedItem());
				for(Erreserba err:erreserbaList) {
					modeloErreserba.addElement(err.toString());
				}
			}
		});
		ridesComboBox.setModel(ridesModel);
		contentPane.add(ridesComboBox);
		
		erreserbaList = new JList<String>();
		erreserbaList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		contentPane.add(erreserbaList);
		erreserbaList.setModel(modeloErreserba);
		
		List<Integer> ridesNumber = WelcomeGUI.getBusinessLogic().getAllRidesNumber(d.getEmail());
		ridesModel.addAll(ridesNumber);
	}

}
