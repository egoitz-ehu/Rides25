package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JSeparator;

public class Register extends JFrame {

	private JPanel contentPane;
	private JTextField izena;
	private JTextField abizena;
	private JTextField mail;
	private JTextField pasahitza;
	private ButtonGroup buttongroup=new ButtonGroup();
	private JRadioButton Gidariardbtn;
	private JRadioButton Bidaiariardbtn;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register frame = new Register();
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
	public Register() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("REGISTER");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel.setBounds(40, 0, 131, 39);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Sartu zure izena:");
		lblNewLabel_1.setBounds(38, 39, 116, 14);
		contentPane.add(lblNewLabel_1);
		
		izena = new JTextField();
		izena.setBounds(40, 64, 131, 20);
		contentPane.add(izena);
		izena.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Sartu zure abizena:");
		lblNewLabel_1_1.setBounds(40, 95, 114, 14);
		contentPane.add(lblNewLabel_1_1);
		
		abizena = new JTextField();
		abizena.setColumns(10);
		abizena.setBounds(40, 120, 131, 20);
		contentPane.add(abizena);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Sartu zure mail-a:");
		lblNewLabel_1_1_1.setBounds(40, 151, 131, 14);
		contentPane.add(lblNewLabel_1_1_1);
		
		mail = new JTextField();
		mail.setBounds(40, 176, 130, 20);
		contentPane.add(mail);
		mail.setColumns(10);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Sartu zure pasahitza:");
		lblNewLabel_1_1_1_1.setBounds(40, 207, 131, 14);
		contentPane.add(lblNewLabel_1_1_1_1);
		
		pasahitza = new JTextField();
		pasahitza.setColumns(10);
		pasahitza.setBounds(40, 232, 130, 20);
		contentPane.add(pasahitza);
		
		JLabel lblNewLabel_2 = new JLabel("Aukeratu zure erabiltzaile mota:");
		lblNewLabel_2.setBounds(226, 50, 198, 17);
		contentPane.add(lblNewLabel_2);
		
		JRadioButton Gidariardbtn= new JRadioButton("Gidaria");
		Bidaiariardbtn.setBounds(250, 74, 109, 23);
		buttongroup.add(Gidariardbtn);
		contentPane.add(Gidariardbtn);
		
		JRadioButton Bidaiariardbtn = new JRadioButton("Bidaiaria");
		Bidaiariardbtn.setBounds(250, 112, 109, 23);
		buttongroup.add(Bidaiariardbtn);
		contentPane.add(Bidaiariardbtn);
		
	}
}
