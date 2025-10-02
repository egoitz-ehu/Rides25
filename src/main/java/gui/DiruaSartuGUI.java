package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import business_logic.BLFacade;
import domain.Traveler;
import domain.User;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

public class DiruaSartuGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	
	private JLabel labelTitle;
	private JLabel labelKop;
	private JLabel labelError;

	/**
	 * Create the frame.
	 */
	
	public DiruaSartuGUI(Traveler t) {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("DiruaSartu.Title"));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(5, 0, 0, 0));
		
		labelTitle = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DiruaSartuGUI.Title"));
		contentPane.add(labelTitle);
		
		labelKop = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DiruaSartuGUI.Kop"));
		contentPane.add(labelKop);
		
		textField = new JTextField();
		contentPane.add(textField);
		textField.setColumns(10);
		
		labelError = new JLabel("");
		contentPane.add(labelError);
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DiruaSartuGUI.botoi"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					double kop = Double.parseDouble(textField.getText());
					if(kop<0) throw new NumberFormatException();
					BLFacade facade = WelcomeGUI.getBusinessLogic();
					boolean b = facade.diruaSartu(t.getEmail(), kop);
					if(b)labelError.setText(kop+ResourceBundle.getBundle("Etiquetas").getString("DiruaSartuGUI.ondo"));
					else labelError.setText(kop+ResourceBundle.getBundle("Etiquetas").getString("DiruaSartuGUI.error"));
				} catch(NumberFormatException e1) {
					labelError.setText(ResourceBundle.getBundle("Etiquetas").getString("DiruaAteraGUI.gaizki"));
				}
			}
		});
		contentPane.add(btnNewButton);
	}

}
