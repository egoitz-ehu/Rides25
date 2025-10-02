package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;

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
	private JButton btnRecharge;

	/**
	 * Create the frame.
	 */
	public ErreklamazioaGestionatuGUI(User u) {
		setBounds(100, 100, 678, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gblContentPane = new GridBagLayout();
		gblContentPane.columnWidths = new int[]{55, 31, 45, 87, 49, 72, 87, 213, 0};
		gblContentPane.rowHeights = new int[]{21, 30, 0, 0, 13, 159, 0, 0, 0};
		gblContentPane.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gblContentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gblContentPane);
		
		JLabel lblAukeratu = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioaGestionatu.Aukeratu"));
		GridBagConstraints gbcLblAukeratu = new GridBagConstraints();
		gbcLblAukeratu.gridwidth = 3;
		gbcLblAukeratu.anchor = GridBagConstraints.WEST;
		gbcLblAukeratu.insets = new Insets(0, 0, 5, 5);
		gbcLblAukeratu.gridx = 0;
		gbcLblAukeratu.gridy = 0;
		contentPane.add(lblAukeratu, gbcLblAukeratu);
		
		JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mezuakKargatu();
				btnRecharge.setEnabled(true);
			}
		});

		GridBagConstraints gbcComboBox = new GridBagConstraints();
		gbcComboBox.anchor = GridBagConstraints.NORTH;
		gbcComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbcComboBox.insets = new Insets(0, 0, 5, 5);
		gbcComboBox.gridwidth = 3;
		gbcComboBox.gridx = 5;
		gbcComboBox.gridy = 0;
		contentPane.add(comboBox, gbcComboBox);
		
		modelErreklamazioa = new DefaultComboBoxModel<Erreklamazioa>();
		comboBox.setModel(modelErreklamazioa);
		
		JLabel lblNewLabel1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioaGestionatu.Gaia")); //$NON-NLS-1$ //$NON-NLS-2$
		GridBagConstraints gbclblNewLabel1 = new GridBagConstraints();
		gbclblNewLabel1.anchor = GridBagConstraints.WEST;
		gbclblNewLabel1.gridwidth = 3;
		gbclblNewLabel1.insets = new Insets(0, 0, 5, 5);
		gbclblNewLabel1.gridx = 0;
		gbclblNewLabel1.gridy = 1;
		contentPane.add(lblNewLabel1, gbclblNewLabel1);
		
		lblGaia = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		GridBagConstraints gbclblNewLabel2 = new GridBagConstraints();
		gbclblNewLabel2.anchor = GridBagConstraints.NORTHWEST;
		gbclblNewLabel2.gridheight = 2;
		gbclblNewLabel2.gridwidth = 7;
		gbclblNewLabel2.insets = new Insets(0, 0, 5, 5);
		gbclblNewLabel2.gridx = 0;
		gbclblNewLabel2.gridy = 2;
		contentPane.add(lblGaia, gbclblNewLabel2);
		
		btnRecharge = new JButton("\u21BB");
		btnRecharge.setFont(btnRecharge.getFont().deriveFont(16f));
		btnRecharge.setEnabled(false);
		btnRecharge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mezuakKargatu();
			}
		});
		GridBagConstraints gbcbtnRecharge = new GridBagConstraints();
		gbcbtnRecharge.insets = new Insets(0, 0, 5, 5);
		gbcbtnRecharge.gridx = 7;
		gbcbtnRecharge.gridy = 3;
		contentPane.add(btnRecharge, gbcbtnRecharge);
		lblNorkMezuak = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioaGestionatu.Mezuak"));
		GridBagConstraints gbclblNorkMezuak = new GridBagConstraints();
		gbclblNorkMezuak.gridwidth = 4;
		gbclblNorkMezuak.anchor = GridBagConstraints.NORTHWEST;
		gbclblNorkMezuak.insets = new Insets(0, 0, 5, 5);
		gbclblNorkMezuak.gridx = 0;
		gbclblNorkMezuak.gridy = 4;
		contentPane.add(lblNorkMezuak, gbclblNorkMezuak);
		
		lblNoriMezuak = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioaGestionatu.Mezuak"));
		GridBagConstraints gbclblNoriMezuak = new GridBagConstraints();
		gbclblNoriMezuak.gridwidth = 3;
		gbclblNoriMezuak.anchor = GridBagConstraints.NORTHWEST;
		gbclblNoriMezuak.insets = new Insets(0, 0, 5, 5);
		gbclblNoriMezuak.gridx = 4;
		gbclblNoriMezuak.gridy = 4;
		contentPane.add(lblNoriMezuak, gbclblNoriMezuak);
		
		lblAdminMezuak = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioaGestionatu.Mezuak") + " Admin");
		GridBagConstraints gbclblAdminMezuak = new GridBagConstraints();
		gbclblAdminMezuak.anchor = GridBagConstraints.NORTHWEST;
		gbclblAdminMezuak.insets = new Insets(0, 0, 5, 5);
		gbclblAdminMezuak.gridx = 7;
		gbclblAdminMezuak.gridy = 4;
		contentPane.add(lblAdminMezuak, gbclblAdminMezuak);
		
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
		
		GridBagConstraints gbcpanelScrolls = new GridBagConstraints();
		gbcpanelScrolls.insets = new Insets(0, 0, 5, 0);
		gbcpanelScrolls.fill = GridBagConstraints.BOTH;
		gbcpanelScrolls.gridx = 0;
		gbcpanelScrolls.gridy = 5;
		gbcpanelScrolls.gridwidth = GridBagConstraints.REMAINDER;
		gbcpanelScrolls.weightx = 1.0;
		gbcpanelScrolls.weighty = 1.0;
		contentPane.add(panelMezuak, gbcpanelScrolls);
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioaGestionatu.Berria"));
		GridBagConstraints gbclblNewLabel = new GridBagConstraints();
		gbclblNewLabel.anchor = GridBagConstraints.WEST;
		gbclblNewLabel.gridwidth = 7;
		gbclblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbclblNewLabel.gridx = 0;
		gbclblNewLabel.gridy = 6;
		contentPane.add(lblNewLabel, gbclblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbcscrollPane = new GridBagConstraints();
		gbcscrollPane.gridwidth = 7;
		gbcscrollPane.insets = new Insets(0, 0, 0, 5);
		gbcscrollPane.fill = GridBagConstraints.BOTH;
		gbcscrollPane.gridx = 0;
		gbcscrollPane.gridy = 7;
		contentPane.add(scrollPane, gbcscrollPane);
		
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
		GridBagConstraints gbcbtnNewButton = new GridBagConstraints();
		gbcbtnNewButton.insets = new Insets(0, 0, 0, 5);
		gbcbtnNewButton.gridx = 7;
		gbcbtnNewButton.gridy = 7;
		contentPane.add(btnNewButton, gbcbtnNewButton);
		
		
		
		List<Erreklamazioa> erreklamaioList = WelcomeGUI.getBusinessLogic().lortuErreklamazioak(u.getEmail());
		modelErreklamazioa.addAll(erreklamaioList);
	}
	
	private JTextArea sortuMezua(String mezua) {
	    JTextArea textArea = new JTextArea(mezua);
	    textArea.setLineWrap(true);
	    textArea.setWrapStyleWord(true);
	    textArea.setEditable(false);
	    textArea.setOpaque(true);
	    textArea.setBackground(new Color(230, 240, 255));
	    textArea.setBorder(BorderFactory.createCompoundBorder(
	        BorderFactory.createLineBorder(Color.GRAY, 1),
	        BorderFactory.createEmptyBorder(5, 10, 5, 10)
	    ));
	    return textArea;
	}
	
	private void mezuakKargatu() {
		panelNorkMezuak.removeAll();
		panelNoriMezuak.removeAll();
		panelAdminMezuak.removeAll();
		Erreklamazioa err = (Erreklamazioa) modelErreklamazioa.getSelectedItem();
		List<Mezua> mezuList = WelcomeGUI.getBusinessLogic().lortuErreklamazioMezuak(err.getId());
		for(Mezua m:mezuList) {
			if(m.getNork()==null) {
				panelAdminMezuak.add(sortuMezua(m.getText()));
			}else if(m.getNork().equals(err.getNork())) {
				panelNorkMezuak.add(sortuMezua(m.getText()));
			} else {
				panelNoriMezuak.add(sortuMezua(m.getText()));
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
