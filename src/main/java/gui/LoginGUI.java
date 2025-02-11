package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ResourceBundle;

import javax.swing.JPasswordField;
import javax.swing.JButton;

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
		
		labelTitle = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Title"));
		labelTitle.setFont(new Font("Tahoma", Font.PLAIN, 24));
		labelTitle.setBounds(97, 11, 94, 39);
		contentPane.add(labelTitle);
		
		labelMail = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Mail"));
		labelMail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelMail.setBounds(97, 61, 148, 14);
		contentPane.add(labelMail);
		
		mailField = new JTextField();
		mailField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		mailField.setBounds(97, 86, 236, 29);
		contentPane.add(mailField);
		mailField.setColumns(10);
		
		labelPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Password"));
		labelPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelPassword.setBounds(97, 128, 148, 14);
		contentPane.add(labelPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(97, 153, 236, 29);
		contentPane.add(passwordField);
		
		errorLabel = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		contentPane.add(errorLabel);
		
		buttonEnter = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Enter"));
		buttonEnter.setBounds(154, 193, 134, 29);
		contentPane.add(buttonEnter);
	}
}
