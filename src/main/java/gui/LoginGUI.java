package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Driver;
import domain.Traveler;
import domain.User;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridLayout;
import java.util.ResourceBundle;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginGUI extends JFrame {

	private JPanel contentPane;
	private JTextField mailField;
	private JPasswordField passwordField;
	
	private JLabel labelTitle;
	private JLabel labelMail;
	private JLabel labelPassword;
	private JLabel errorLabel;
	
	private JButton buttonEnter;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginGUI() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(7,1));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Login.Title"));
		
		labelTitle = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Title"));
		labelTitle.setBounds(97, 11, 94, 39);
		contentPane.add(labelTitle);
		
		labelMail = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Mail"));
		labelMail.setBounds(97, 61, 148, 14);
		contentPane.add(labelMail);
		
		mailField = new JTextField();
		mailField.setBounds(97, 86, 236, 29);
		contentPane.add(mailField);
		mailField.setColumns(10);
		
		labelPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Password"));
		labelPassword.setBounds(97, 128, 148, 14);
		contentPane.add(labelPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(97, 153, 236, 29);
		contentPane.add(passwordField);
		
		errorLabel = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		contentPane.add(errorLabel);
		
		buttonEnter = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Enter"));
		buttonEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mail = mailField.getText();
				String pass = new String(passwordField.getPassword());
				if(mail.isBlank() || pass.isBlank()) {
					JOptionPane.showMessageDialog(contentPane, ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.ErrorDataBlank"), "Error", JOptionPane.ERROR_MESSAGE);
				}else {
					if(mail.equals("admin") && pass.equals("admin")) {
						AdminMenuGUI aM = new AdminMenuGUI();
						aM.setVisible(true);
					} else {
						BLFacade facade = WelcomeGUI.getBusinessLogic();
						User u = facade.login(mail, pass);
						if(u instanceof Driver) {
							DriverMenuGUI dMenu = new DriverMenuGUI((Driver) u);
							dMenu.setVisible(true);
						}
						else if(u instanceof Traveler) {
							PassengerMenuGUI tMenu = new PassengerMenuGUI((Traveler) u);
							tMenu.setVisible(true);
						}
						else{
							JOptionPane.showMessageDialog(contentPane, ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.NoUser"), "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		buttonEnter.setBounds(154, 193, 134, 29);
		contentPane.add(buttonEnter);
	}
}
