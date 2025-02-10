package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;

public class WelcomeWindow extends JFrame {
	
	private JButton registerButton;
	private JButton loginButton;
	private JButton guestButton;
	private JLabel titleLabel;
		
	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomeWindow frame = new WelcomeWindow();
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
	public WelcomeWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		registerButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("WelcomeGUI.Register"));
		registerButton.setBounds(96, 80, 252, 45);
		contentPane.add(registerButton);
		
		loginButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("WelcomeGUI.Login"));
		loginButton.setBounds(96, 136, 252, 45);
		contentPane.add(loginButton);
		
		JButton btnNewButton_2 = new JButton(ResourceBundle.getBundle("Etiquetas").getString("WelcomeGUI.Login"));
		btnNewButton_2.setBounds(96, 192, 252, 45);
		contentPane.add(btnNewButton_2);
		
		titleLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("WelcomeGUI.Welcome"));
		titleLabel.setBounds(10, 11, 274, 28);
		contentPane.add(titleLabel);
	}
}
