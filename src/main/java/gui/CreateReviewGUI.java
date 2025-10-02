package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Erreserba;
import domain.TravelerErreserbaContainer;
import domain.User;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreateReviewGUI extends JFrame {

	private JPanel contentPane;
	
	private int puntuazioa;
	
	private Erreserba selectedErreserba;
	
	private DefaultComboBoxModel<Erreserba> model = new DefaultComboBoxModel<Erreserba>();

	/**
	 * Create the frame.
	 */
	public CreateReviewGUI(User u) {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gblContentPane = new GridBagLayout();
		gblContentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gblContentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gblContentPane.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gblContentPane.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gblContentPane);
		
		JLabel lblErreserba = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateReviewGUI.erreserba"));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 3;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		contentPane.add(lblErreserba, gbc_lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedErreserba=(Erreserba) model.getSelectedItem();
			}
		});
		comboBox.setModel(model);
		GridBagConstraints gbcComboBox = new GridBagConstraints();
		gbcComboBox.insets = new Insets(0, 0, 5, 0);
		gbcComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbcComboBox.gridx = 7;
		gbcComboBox.gridy = 0;
		contentPane.add(comboBox, gbcComboBox);
		
		JPanel panelStars = new JPanel();
		GridBagConstraints gbcPanel = new GridBagConstraints();
		gbcPanel.gridheight = 3;
		gbcPanel.gridwidth = 8;
		gbcPanel.insets = new Insets(0, 0, 5, 0);
		gbcPanel.fill = GridBagConstraints.BOTH;
		gbcPanel.gridx = 0;
		gbcPanel.gridy = 2;
		contentPane.add(panelStars, gbcPanel);
		
		panelStars.setLayout(new GridLayout(1, 5, 5, 5));

		JLabel[] starLabels = new JLabel[5];

		for (int i = 0; i < 5; i++) {
		    final int index = i;
		    starLabels[i] = new JLabel("☆");
		    starLabels[i].setFont(starLabels[i].getFont().deriveFont(32f));
		    starLabels[i].setForeground(java.awt.Color.GRAY);
		    starLabels[i].setHorizontalAlignment(SwingConstants.CENTER);

		    starLabels[i].addMouseListener(new java.awt.event.MouseAdapter() {
		        @Override
		        public void mouseClicked(java.awt.event.MouseEvent e) {
		            for (int j = 0; j < 5; j++) {
		                if (j <= index) {
		                    starLabels[j].setText("★");
		                    starLabels[j].setForeground(java.awt.Color.YELLOW);
		                } else {
		                    starLabels[j].setText("☆");
		                    starLabels[j].setForeground(java.awt.Color.GRAY);
		                }
		            }
		            puntuazioa=index+1;
		        }
		    });

		    panelStars.add(starLabels[i]);
		}
		
		JLabel lblMezua = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateReviewGUI.sartuMezua"));
		GridBagConstraints gbcLblMezua = new GridBagConstraints();
		gbcLblMezua.insets = new Insets(0, 0, 5, 5);
		gbcLblMezua.gridx = 0;
		gbcLblMezua.gridy = 5;
		contentPane.add(lblMezua, gbcLblMezua);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbcScrollPane = new GridBagConstraints();
		gbcScrollPane.gridheight = 2;
		gbcScrollPane.gridwidth = 8;
		gbcScrollPane.insets = new Insets(0, 0, 5, 0);
		gbcScrollPane.fill = GridBagConstraints.BOTH;
		gbcScrollPane.gridx = 0;
		gbcScrollPane.gridy = 6;
		contentPane.add(scrollPane, gbcScrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateReviewGUI.bidali"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedErreserba!=null && puntuazioa!=0 && textArea.getText().length()>0) {
					WelcomeGUI.getBusinessLogic().sortuBalorazioa(u.getEmail(), selectedErreserba.getEskaeraNum(), puntuazioa, textArea.getText());
					model.removeAllElements();
					lortuErreserbak(u);	
				}
			}
		});
		GridBagConstraints gbcBtnNewButton = new GridBagConstraints();
		gbcBtnNewButton.gridwidth = 8;
		gbcBtnNewButton.gridx = 0;
		gbcBtnNewButton.gridy = 9;
		contentPane.add(btnNewButton, gbcBtnNewButton);
		
		lortuErreserbak(u);
	}
	
	private void lortuErreserbak(User u) {
		List<TravelerErreserbaContainer> eContainerList = WelcomeGUI.getBusinessLogic().lortuBalorazioErreserbak(u);
		List<Erreserba> eList = new LinkedList<Erreserba>();
		for(TravelerErreserbaContainer x:eContainerList) {
			Erreserba e = x.getErreserba();
			e.setBidaiaria(x.getTraveler());
			eList.add(e);
		}
		model.addAll(eList);
	}

}
