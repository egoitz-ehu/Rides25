package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.User;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

public class DiruaAtera extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 *//*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DiruaAtera frame = new DiruaAtera();
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
	
	public DiruaAtera(User u) {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(5, 0, 0, 0));
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DiruaAteraGUI.Title"));
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DiruaAteraGUI.Kop"));
		contentPane.add(lblNewLabel_2);
		
		textField = new JTextField();
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("");
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DiruaAteraGUI.botoi"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					double kop = Double.parseDouble(textField.getText());
					if(kop<0) throw new NumberFormatException();
					BLFacade facade = WelcomeGUI.getBussinessLogic();
					boolean b = facade.diruaAtera(u, kop);
					if(b) {
						lblNewLabel_1.setText(kop+ResourceBundle.getBundle("Etiquetas").getString("DiruaAteraGUI.ondo"));
					} else {
						lblNewLabel_1.setText(ResourceBundle.getBundle("Etiquetas").getString("DiruaAteraGUI.gutxi"));
					}
				} catch(NumberFormatException e1) {
					lblNewLabel_1.setText(ResourceBundle.getBundle("Etiquetas").getString("DiruaAteraGUI.gaizki"));
				}
			}
		});
		contentPane.add(btnNewButton);
	}

}
