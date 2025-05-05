package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Erreklamazioa;
import domain.User;

import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.List;

import javax.swing.JButton;

public class ErreklamazioaGestionatuGUI extends JFrame {

	private JPanel contentPane;
	
	private DefaultComboBoxModel<Erreklamazioa> modelErreklamazioa;

	/**
	 * Create the frame.
	 */
	public ErreklamazioaGestionatuGUI(User u) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 678, 407);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{55, 31, 45, 87, 49, 72, 87, 213, 0};
		gbl_contentPane.rowHeights = new int[]{21, 30, 13, 159, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblAukeratu = new JLabel("New label");
		GridBagConstraints gbc_lblAukeratu = new GridBagConstraints();
		gbc_lblAukeratu.gridwidth = 3;
		gbc_lblAukeratu.anchor = GridBagConstraints.WEST;
		gbc_lblAukeratu.insets = new Insets(0, 0, 5, 5);
		gbc_lblAukeratu.gridx = 0;
		gbc_lblAukeratu.gridy = 0;
		contentPane.add(lblAukeratu, gbc_lblAukeratu);
		
		JComboBox comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.anchor = GridBagConstraints.NORTH;
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridwidth = 3;
		gbc_comboBox.gridx = 5;
		gbc_comboBox.gridy = 0;
		contentPane.add(comboBox, gbc_comboBox);
		
		modelErreklamazioa = new DefaultComboBoxModel<Erreklamazioa>();
		
		JLabel lblNorkMezuak = new JLabel("New label");
		GridBagConstraints gbc_lblNorkMezuak = new GridBagConstraints();
		gbc_lblNorkMezuak.gridwidth = 2;
		gbc_lblNorkMezuak.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblNorkMezuak.insets = new Insets(0, 0, 5, 5);
		gbc_lblNorkMezuak.gridx = 0;
		gbc_lblNorkMezuak.gridy = 2;
		contentPane.add(lblNorkMezuak, gbc_lblNorkMezuak);
		
		JLabel lblNoriMezuak = new JLabel("New label");
		GridBagConstraints gbc_lblNoriMezuak = new GridBagConstraints();
		gbc_lblNoriMezuak.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblNoriMezuak.insets = new Insets(0, 0, 5, 5);
		gbc_lblNoriMezuak.gridx = 4;
		gbc_lblNoriMezuak.gridy = 2;
		contentPane.add(lblNoriMezuak, gbc_lblNoriMezuak);
		
		JLabel lblAdminMezuak = new JLabel("New label");
		GridBagConstraints gbc_lblAdminMezuak = new GridBagConstraints();
		gbc_lblAdminMezuak.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblAdminMezuak.insets = new Insets(0, 0, 5, 5);
		gbc_lblAdminMezuak.gridx = 7;
		gbc_lblAdminMezuak.gridy = 2;
		contentPane.add(lblAdminMezuak, gbc_lblAdminMezuak);
		
		JPanel panelNorkMezuak = new JPanel();
		panelNorkMezuak.setLayout(new GridLayout(0, 1, 0, 5));
		panelNorkMezuak.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JScrollPane scrollPaneNork = new JScrollPane();
		scrollPaneNork.setViewportView(panelNorkMezuak);
		
		JPanel panelNoriMezuak = new JPanel();
		panelNoriMezuak.setLayout(new GridLayout(0, 1, 0, 5));
		panelNoriMezuak.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		
		JScrollPane scrollPaneNori = new JScrollPane();
		scrollPaneNori.setViewportView(panelNoriMezuak);
		
		JPanel panelAdminMezuak = new JPanel();
		panelAdminMezuak.setLayout(new GridLayout(0, 1, 0, 5));
		panelAdminMezuak.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JScrollPane scrollPaneAdmin = new JScrollPane();
		scrollPaneAdmin.setViewportView(panelAdminMezuak);
		

		JPanel panelMezuak = new JPanel(new GridLayout(1,3,10,0));
		panelMezuak.add(scrollPaneNork);
		panelMezuak.add(scrollPaneNori);
		panelMezuak.add(scrollPaneAdmin);
		
		GridBagConstraints gbc_panelScrolls = new GridBagConstraints();
		gbc_panelScrolls.insets = new Insets(0, 0, 5, 0);
		gbc_panelScrolls.fill = GridBagConstraints.BOTH;
		gbc_panelScrolls.gridx = 0;
		gbc_panelScrolls.gridy = 3;
		gbc_panelScrolls.gridwidth = GridBagConstraints.REMAINDER;
		gbc_panelScrolls.weightx = 1.0;
		gbc_panelScrolls.weighty = 1.0;
		contentPane.add(panelMezuak, gbc_panelScrolls);
		
		JLabel lblNewLabel = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 4;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 4;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 7;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 5;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		JButton btnNewButton = new JButton("New button");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 7;
		gbc_btnNewButton.gridy = 5;
		contentPane.add(btnNewButton, gbc_btnNewButton);
		
		for (int i = 1; i <= 20; i++) {
			panelNorkMezuak.add(sortuMezua("Mensaje largo número " + i + "...\nCon varias líneas de ejemplo para comprobar que se ajusta correctamente."));
			panelNoriMezuak.add(sortuMezua("Mensaje largo número " + i + "...\nCon varias líneas de ejemplo para comprobar que se ajusta correctamente."));
			panelAdminMezuak.add(sortuMezua("Mensaje largo número " + i + "...\nCon varias líneas de ejemplo para comprobar que se ajusta correctamente."));
		}
		
		List<Erreklamazioa> erreklamaioList = WelcomeGUI.getBusinessLogic().lortuErreklamazioak(u.getEmail());
		modelErreklamazioa.addAll(erreklamaioList);
	}
	
	private JTextArea sortuMezua(String mezua) {
		 	JTextArea textArea = new JTextArea(mezua);
		    textArea.setLineWrap(true);              
		    textArea.setLineWrap(true);
		    textArea.setWrapStyleWord(true);           
		    textArea.setEditable(false);               
		    textArea.setOpaque(true);
		    textArea.setSize(500, Short.MAX_VALUE);
		    int preferredHeight = textArea.getPreferredSize().height;
		    textArea.setBackground(new Color(230, 240, 255));
		    textArea.setBorder(BorderFactory.createCompoundBorder(
		        BorderFactory.createLineBorder(Color.GRAY, 1),
		        BorderFactory.createEmptyBorder(5, 10, 5, 10)
		    ));
		    textArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, preferredHeight));
		    return textArea;
	}
}
