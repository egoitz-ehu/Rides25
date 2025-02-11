package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WelcomeGUI extends JFrame {
	
	private JButton registerButton;
	private JButton loginButton;
	private JButton guestButton;
	private JLabel titleLabel;
		
	
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JComboBox<String> comboBoxLanguages;
	private DefaultComboBoxModel<String> hizkuntzak = new DefaultComboBoxModel<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomeGUI frame = new WelcomeGUI();
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
	public WelcomeGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("WelcomeGUI.Languages")); //$NON-NLS-1$ //$NON-NLS-2$
		contentPane.add(lblNewLabel);
		
		registerButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("WelcomeGUI.Register"));
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame registerWindow = new RegisterGUI();
				registerWindow.setVisible(true);;
			}
		});
		registerButton.setBounds(96, 80, 252, 45);
		//contentPane.add(registerButton);
		
		loginButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("WelcomeGUI.Login"));
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame loginWindow = new LoginGUI();
				loginWindow.setVisible(true);;
			}
		});
		loginButton.setBounds(96, 136, 252, 45);
		//contentPane.add(loginButton);
		
		guestButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("WelcomeGUI.Guest"));
		guestButton.setBounds(96, 192, 252, 45);
		//contentPane.add(guestButton);
		
		titleLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("WelcomeGUI.Welcome"));
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		titleLabel.setBounds(10, 11, 274, 28);
		//contentPane.add(titleLabel);
		
		comboBoxLanguages = new JComboBox<String>();
		comboBoxLanguages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale((String) comboBoxLanguages.getSelectedItem()));
				System.out.println("Locale: "+Locale.getDefault());
				paintAgain();
			}
		});
		comboBoxLanguages.setModel(hizkuntzak);
		contentPane.add(comboBoxLanguages);
		hizkuntzak.addElement("en");
		hizkuntzak.addElement("es");
		hizkuntzak.addElement("eus");
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6,1));
		panel.add(contentPane);
		panel.add(titleLabel);
		panel.add(registerButton);
		panel.add(loginButton);
		panel.add(guestButton);
		
		setContentPane(panel);
	}
	
	private void paintAgain() {
		lblNewLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("WelcomeGUI.Languages"));
		registerButton.setText(ResourceBundle.getBundle("Etiquetas").getString("WelcomeGUI.Register"));
		loginButton.setText(ResourceBundle.getBundle("Etiquetas").getString("WelcomeGUI.Login"));
		guestButton.setText(ResourceBundle.getBundle("Etiquetas").getString("WelcomeGUI.Guest"));
		titleLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("WelcomeGUI.Welcome"));
	}
}
