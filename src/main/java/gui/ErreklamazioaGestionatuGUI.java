package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Erreklamazioa;
import domain.Mezua;
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
import java.util.ResourceBundle;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ErreklamazioaGestionatuGUI extends JFrame {

	private JPanel contentPane;
	
	private DefaultComboBoxModel<Erreklamazioa> modelErreklamazioa;
	
	private JPanel panelNorkMezuak;
	private JPanel panelNoriMezuak;
	private JPanel panelAdminMezuak;
	private JLabel lblNorkMezuak;
	private JLabel lblNoriMezuak;
	private JLabel lblAdminMezuak;
	private JLabel lblGaia;

	/**
	 * Create the frame.
	 */
	public ErreklamazioaGestionatuGUI(User u) {
		setBounds(100, 100, 678, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{55, 31, 45, 87, 49, 72, 87, 213, 0};
		gbl_contentPane.rowHeights = new int[]{21, 30, 0, 0, 13, 159, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblAukeratu = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioaGestionatu.Aukeratu"));
		GridBagConstraints gbc_lblAukeratu = new GridBagConstraints();
		gbc_lblAukeratu.gridwidth = 3;
		gbc_lblAukeratu.anchor = GridBagConstraints.WEST;
		gbc_lblAukeratu.insets = new Insets(0, 0, 5, 5);
		gbc_lblAukeratu.gridx = 0;
		gbc_lblAukeratu.gridy = 0;
		contentPane.add(lblAukeratu, gbc_lblAukeratu);
		
		JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mezuakKargatu();
			}
		});

		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.anchor = GridBagConstraints.NORTH;
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridwidth = 3;
		gbc_comboBox.gridx = 5;
		gbc_comboBox.gridy = 0;
		contentPane.add(comboBox, gbc_comboBox);
		
		modelErreklamazioa = new DefaultComboBoxModel<Erreklamazioa>();
		comboBox.setModel(modelErreklamazioa);
		
		JLabel lblNewLabel_1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioaGestionatu.Gaia")); //$NON-NLS-1$ //$NON-NLS-2$
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.gridwidth = 3;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		lblGaia = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewLabel_2.gridheight = 2;
		gbc_lblNewLabel_2.gridwidth = 7;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 2;
		contentPane.add(lblGaia, gbc_lblNewLabel_2);
		lblNorkMezuak = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioaGestionatu.Mezuak"));
		GridBagConstraints gbc_lblNorkMezuak = new GridBagConstraints();
		gbc_lblNorkMezuak.gridwidth = 4;
		gbc_lblNorkMezuak.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNorkMezuak.insets = new Insets(0, 0, 5, 5);
		gbc_lblNorkMezuak.gridx = 0;
		gbc_lblNorkMezuak.gridy = 4;
		contentPane.add(lblNorkMezuak, gbc_lblNorkMezuak);
		
		lblNoriMezuak = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioaGestionatu.Mezuak"));
		GridBagConstraints gbc_lblNoriMezuak = new GridBagConstraints();
		gbc_lblNoriMezuak.gridwidth = 3;
		gbc_lblNoriMezuak.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNoriMezuak.insets = new Insets(0, 0, 5, 5);
		gbc_lblNoriMezuak.gridx = 4;
		gbc_lblNoriMezuak.gridy = 4;
		contentPane.add(lblNoriMezuak, gbc_lblNoriMezuak);
		
		lblAdminMezuak = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioaGestionatu.Mezuak") + " Admin");
		GridBagConstraints gbc_lblAdminMezuak = new GridBagConstraints();
		gbc_lblAdminMezuak.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblAdminMezuak.insets = new Insets(0, 0, 5, 5);
		gbc_lblAdminMezuak.gridx = 7;
		gbc_lblAdminMezuak.gridy = 4;
		contentPane.add(lblAdminMezuak, gbc_lblAdminMezuak);
		
		panelNorkMezuak = new JPanel();
		panelNorkMezuak.setLayout(new GridLayout(0, 1, 0, 5));
		panelNorkMezuak.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JScrollPane scrollPaneNork = new JScrollPane();
		scrollPaneNork.setViewportView(panelNorkMezuak);
		
		panelNoriMezuak = new JPanel();
		panelNoriMezuak.setLayout(new GridLayout(0, 1, 0, 5));
		panelNoriMezuak.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		
		JScrollPane scrollPaneNori = new JScrollPane();
		scrollPaneNori.setViewportView(panelNoriMezuak);
		
		panelAdminMezuak = new JPanel();
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
		gbc_panelScrolls.gridy = 5;
		gbc_panelScrolls.gridwidth = GridBagConstraints.REMAINDER;
		gbc_panelScrolls.weightx = 1.0;
		gbc_panelScrolls.weighty = 1.0;
		contentPane.add(panelMezuak, gbc_panelScrolls);
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioaGestionatu.Berria"));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.gridwidth = 7;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 6;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 7;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 7;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioaGestionatu.Bidali"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textArea.getText();
				Erreklamazioa err =(Erreklamazioa) modelErreklamazioa.getSelectedItem();
				if(!(text.isEmpty() || text.isBlank()) && err!=null)  {
					WelcomeGUI.getBusinessLogic().bidaliMezua(u.getEmail(), text, err.getId());
				}
				mezuakKargatu();
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 7;
		gbc_btnNewButton.gridy = 7;
		contentPane.add(btnNewButton, gbc_btnNewButton);
		
		
		
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
		    int preferredHeight = textArea.getPreferredSize().height;
		    textArea.setBackground(new Color(230, 240, 255));
		    textArea.setBorder(BorderFactory.createCompoundBorder(
		        BorderFactory.createLineBorder(Color.GRAY, 1),
		        BorderFactory.createEmptyBorder(5, 10, 5, 10)
		    ));
		    textArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		    textArea.setAlignmentX(LEFT_ALIGNMENT);
		    return textArea;
	}
	
	private void mezuakKargatu() {
		panelNorkMezuak.removeAll();
		panelNoriMezuak.removeAll();
		panelAdminMezuak.removeAll();
		Erreklamazioa err = (Erreklamazioa) modelErreklamazioa.getSelectedItem();
		List<Mezua> mezuList = WelcomeGUI.getBusinessLogic().lortuErreklamazioMezuak(err.getId());
		for(Mezua m:mezuList) {
			if(m.getNork().equals(err.getNork())) {
				panelNorkMezuak.add(sortuMezua(m.getText()));
			} else if (m.getNork().equals(err.getNori())) {
				panelNoriMezuak.add(sortuMezua(m.getText()));
			} else {
				panelAdminMezuak.add(sortuMezua(m.getText()));
			}
		}
		lblNorkMezuak.setText(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioaGestionatu.Mezuak") + " " + err.getNork().getName());
		lblNoriMezuak.setText(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioaGestionatu.Mezuak") + " " + err.getNori().getName());
		lblGaia.setText(err.getGaia());
		
		panelNorkMezuak.revalidate();
		panelNorkMezuak.repaint();

		panelNoriMezuak.revalidate();
		panelNoriMezuak.repaint();

		panelAdminMezuak.revalidate();
		panelAdminMezuak.repaint();
	}
}
