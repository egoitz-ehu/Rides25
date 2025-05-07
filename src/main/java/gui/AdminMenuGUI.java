package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Admin;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import javax.swing.JLabel;

public class AdminMenuGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public AdminMenuGUI(Admin a) {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AdminMenuGUI.btn"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ErreklamazioaBurutuGUI eB = new ErreklamazioaBurutuGUI(a);
				eB.setVisible(true);
			}
		});
		contentPane.setLayout(new GridLayout(3, 1, 0, 0));
		
		JLabel label = new JLabel("");
		contentPane.add(label);
		contentPane.add(btnNewButton);
	}

}
