package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Driver;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class ErreserbakGestionatuGUI extends JFrame {

	private JPanel contentPane;
	
	private JComboBox<Integer> ridesComboBox;
	private DefaultComboBoxModel<Integer> ridesModel = new DefaultComboBoxModel<Integer>();

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
		ridesComboBox.setModel(ridesModel);
		contentPane.add(ridesComboBox);
		
		List<Integer> ridesNumber = WelcomeGUI.getBusinessLogic().getAllRidesNumber(d.getEmail());
		ridesModel.addAll(ridesNumber);
	}

}
