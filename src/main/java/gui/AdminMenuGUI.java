package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Admin;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ErreklamazioaBurutuGUI eB = new ErreklamazioaBurutuGUI(a);
				eB.setVisible(true);
			}
		});
		contentPane.add(btnNewButton);
	}

}
