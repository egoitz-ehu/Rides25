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
import java.awt.event.ActionEvent;

public class DiruaAtera extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	
	private BLFacade bussinessLogic;

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
	
	public void setBussinessLogic(BLFacade logic) {
    	this.bussinessLogic=logic;
    }
	
	public DiruaAtera(User u) {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(4, 0, 0, 0));
		
		JLabel lblNewLabel = new JLabel("New label");
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					double kop = Double.parseDouble(textField.getText());
					if(kop<0) throw new NumberFormatException();
					boolean b = bussinessLogic.diruaAtera(u, kop);
					if(b) {
						lblNewLabel_1.setText(kop+"€ atera dira zure kontutik.");
					} else {
						lblNewLabel_1.setText("Zure kontuan ez dago nahiko diru.");
					}
				} catch(NumberFormatException e1) {
					lblNewLabel_1.setText("Zenbaki positibo bat sartu behar da.");
				}
			}
		});
		contentPane.add(btnNewButton);
	}

}
