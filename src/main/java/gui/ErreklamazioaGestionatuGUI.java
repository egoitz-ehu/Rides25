package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class ErreklamazioaGestionatuGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ErreklamazioaGestionatuGUI frame = new ErreklamazioaGestionatuGUI();
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
	public ErreklamazioaGestionatuGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 678, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{55, 31, 45, 87, 49, 72, 87, 213, 0};
		gbl_contentPane.rowHeights = new int[]{21, 30, 13, 159, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.gridwidth = 3;
		gbc_comboBox.gridx = 5;
		gbc_comboBox.gridy = 0;
		contentPane.add(comboBox, gbc_comboBox);
		
		JLabel lblNorkMezuak = new JLabel("New label");
		GridBagConstraints gbc_lblNorkMezuak = new GridBagConstraints();
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
		gbc_lblAdminMezuak.insets = new Insets(0, 0, 5, 0);
		gbc_lblAdminMezuak.gridx = 7;
		gbc_lblAdminMezuak.gridy = 2;
		contentPane.add(lblAdminMezuak, gbc_lblAdminMezuak);
		
		JPanel panelNorkMezuak = new JPanel();
		panelNorkMezuak.setLayout(new GridLayout(0, 1, 0, 5));
		panelNorkMezuak.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		
		JScrollPane scrollPaneNork = new JScrollPane();
		GridBagConstraints gbc_scrollPaneNork = new GridBagConstraints();
		gbc_scrollPaneNork.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneNork.weightx = 1.0;
		gbc_scrollPaneNork.weighty = 1.0;
		gbc_scrollPaneNork.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPaneNork.gridwidth = 4;
		gbc_scrollPaneNork.gridx = 0;
		gbc_scrollPaneNork.gridy = 3;
		contentPane.add(scrollPaneNork, gbc_scrollPaneNork);
		scrollPaneNork.setViewportView(panelNorkMezuak);
		
		JPanel panelNoriMezuak = new JPanel();
		GridBagConstraints gbc_panelNoriMezuak = new GridBagConstraints();
		gbc_panelNoriMezuak.fill = GridBagConstraints.BOTH;
		gbc_panelNoriMezuak.insets = new Insets(0, 0, 0, 5);
		gbc_panelNoriMezuak.gridwidth = 3;
		gbc_panelNoriMezuak.gridx = 4;
		gbc_panelNoriMezuak.gridy = 3;
		contentPane.add(panelNoriMezuak, gbc_panelNoriMezuak);
		
		JPanel panelAdminMezuak = new JPanel();
		GridBagConstraints gbc_panelAdminMezuak = new GridBagConstraints();
		gbc_panelAdminMezuak.fill = GridBagConstraints.BOTH;
		gbc_panelAdminMezuak.gridx = 7;
		gbc_panelAdminMezuak.gridy = 3;
		contentPane.add(panelAdminMezuak, gbc_panelAdminMezuak);
		
		for (int i = 1; i <= 20; i++) {
		    JTextArea textArea = new JTextArea("Mensaje largo número " + i + "...\nCon varias líneas de ejemplo para comprobar que se ajusta correctamente.");
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
		    panelNorkMezuak.add(textArea);
		}
	}
}
