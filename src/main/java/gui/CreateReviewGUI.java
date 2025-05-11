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
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreateReviewGUI extends JFrame {

	private JPanel contentPane;
	
	private int puntuazioa;
	
	private Erreserba selectedErreserba;
	
	private DefaultComboBoxModel<Erreserba> model = new DefaultComboBoxModel<Erreserba>();

	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateReviewGUI frame = new CreateReviewGUI();
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
	public CreateReviewGUI(User u) {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
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
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 7;
		gbc_comboBox.gridy = 0;
		contentPane.add(comboBox, gbc_comboBox);
		
		JPanel panelStars = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridheight = 3;
		gbc_panel.gridwidth = 8;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		contentPane.add(panelStars, gbc_panel);
		
		panelStars.setLayout(new GridLayout(1, 5, 5, 5));

		JLabel[] starLabels = new JLabel[5];

		for (int i = 0; i < 5; i++) {
		    final int index = i;
		    starLabels[i] = new JLabel("☆");
		    starLabels[i].setFont(starLabels[i].getFont().deriveFont(32f));
		    starLabels[i].setForeground(java.awt.Color.GRAY);
		    starLabels[i].setHorizontalAlignment(JLabel.CENTER);

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
		GridBagConstraints gbc_lblMezua = new GridBagConstraints();
		gbc_lblMezua.insets = new Insets(0, 0, 5, 5);
		gbc_lblMezua.gridx = 0;
		gbc_lblMezua.gridy = 5;
		contentPane.add(lblMezua, gbc_lblMezua);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.gridwidth = 8;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 6;
		contentPane.add(scrollPane, gbc_scrollPane);
		
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
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridwidth = 8;
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 9;
		contentPane.add(btnNewButton, gbc_btnNewButton);
		
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
