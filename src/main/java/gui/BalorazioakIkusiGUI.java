package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import domain.Balorazioa;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JComboBox;

public class BalorazioakIkusiGUI extends JFrame {

	private JPanel contentPane;
	
	private List<Balorazioa> balorazioList;
	private JPanel listaPanel;
	private JScrollPane panelBalorazioak;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	
	private int selectedIndex;
	private JLabel lblMedia;
	private JComboBox comboBox;
	private DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();

	/**
	 * Create the frame.
	 */
	public BalorazioakIkusiGUI() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gblContentPane = new GridBagLayout();
		gblContentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gblContentPane.rowHeights = new int[]{0, 0, 0, 0};
		gblContentPane.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gblContentPane.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gblContentPane);
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("BalorazioakIkusiGUI.Select"));
		GridBagConstraints gbcLblNewLabel = new GridBagConstraints();
		gbcLblNewLabel.gridwidth = 3;
		gbcLblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbcLblNewLabel.gridx = 0;
		gbcLblNewLabel.gridy = 0;
		contentPane.add(lblNewLabel, gbcLblNewLabel);
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedIndex = comboBox.getSelectedIndex();
				if (selectedIndex != -1) {
					balorazioList = WelcomeGUI.getBusinessLogic().lortuBalorazioak(model.getElementAt(selectedIndex));
					gehitu();
				}
			}
		});
		GridBagConstraints gbcComboBox = new GridBagConstraints();
		gbcComboBox.insets = new Insets(0, 0, 5, 5);
		gbcComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbcComboBox.gridx = 3;
		gbcComboBox.gridy = 0;
		contentPane.add(comboBox, gbcComboBox);
		comboBox.setModel(model);
		
		lblMedia = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		GridBagConstraints gbcLblNewLabel1 = new GridBagConstraints();
		gbcLblNewLabel1.anchor = GridBagConstraints.WEST;
		gbcLblNewLabel1.gridwidth = 2;
		gbcLblNewLabel1.insets = new Insets(0, 0, 5, 5);
		gbcLblNewLabel1.gridx = 0;
		gbcLblNewLabel1.gridy = 1;
		contentPane.add(lblMedia, gbcLblNewLabel1);
		
		panelBalorazioak = new JScrollPane();
		GridBagConstraints gbcPanelBalorazioak = new GridBagConstraints();
		gbcPanelBalorazioak.gridwidth = 3;
		gbcPanelBalorazioak.insets = new Insets(0, 0, 0, 5);
		gbcPanelBalorazioak.fill = GridBagConstraints.BOTH;
		gbcPanelBalorazioak.gridx = 0;
		gbcPanelBalorazioak.gridy = 2;
		contentPane.add(panelBalorazioak, gbcPanelBalorazioak);
		
		listaPanel = new JPanel();
		listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
		listaPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelBalorazioak.setViewportView(listaPanel);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbcScrollPane = new GridBagConstraints();
		gbcScrollPane.gridwidth = 2;
		gbcScrollPane.fill = GridBagConstraints.BOTH;
		gbcScrollPane.gridx = 3;
		gbcScrollPane.gridy = 2;
		contentPane.add(scrollPane, gbcScrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
		
		model.addAll(WelcomeGUI.getBusinessLogic().lortuErabiltzaileEmailGuztiak());
	}
	
	private JPanel sortuItem(String izena, int puntu, int index) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(230, 240, 255));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel lblIzena = new JLabel(izena);
        lblIzena.setFont(new Font("SansSerif", Font.BOLD, 14));

        JLabel lblPuntu = new JLabel("<html>" + puntu + " <span style='color:yellow;'>★</span></html>");
        lblPuntu.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblPuntu.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(lblIzena, BorderLayout.WEST);
        panel.add(lblPuntu, BorderLayout.EAST);
        
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	textArea.setText(balorazioList.get(index).getMezua());
            }
        });

        return panel;
    }
	
	private void gehitu() {
		listaPanel.removeAll();
		double media = 0.0;
		int index = 0;
		for (Balorazioa b : balorazioList) {
		    JPanel item = sortuItem(b.getNorkIzena(), b.getPuntuazioa(), index);
		    listaPanel.add(item);
		    listaPanel.add(Box.createVerticalStrut(10));
		    media+=b.getPuntuazioa();
		    index++;
		}
		media = media / balorazioList.size();
		int mediaOsoa = (int) media;
		StringBuilder html = new StringBuilder("<html>");

		for (int i = 0; i < mediaOsoa; i++) {
			html.append("<span style='color:yellow;'>★</span>");
		}

		for (int i = mediaOsoa; i < 5; i++) {
			html.append("<span style='color:lightGray;'>★</span>");
		}

		html.append(" (" + mediaOsoa + ")</html>");

		lblMedia.setText(html.toString());
		panelBalorazioak.revalidate();
		panelBalorazioak.repaint();
	}

}
