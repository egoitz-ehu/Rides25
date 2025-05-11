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
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("BalorazioakIkusiGUI.Select"));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 3;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
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
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 3;
		gbc_comboBox.gridy = 0;
		contentPane.add(comboBox, gbc_comboBox);
		comboBox.setModel(model);
		
		lblMedia = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.gridwidth = 2;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		contentPane.add(lblMedia, gbc_lblNewLabel_1);
		
		panelBalorazioak = new JScrollPane();
		GridBagConstraints gbc_panelBalorazioak = new GridBagConstraints();
		gbc_panelBalorazioak.gridwidth = 3;
		gbc_panelBalorazioak.insets = new Insets(0, 0, 0, 5);
		gbc_panelBalorazioak.fill = GridBagConstraints.BOTH;
		gbc_panelBalorazioak.gridx = 0;
		gbc_panelBalorazioak.gridy = 2;
		contentPane.add(panelBalorazioak, gbc_panelBalorazioak);
		
		listaPanel = new JPanel();
		listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
		listaPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelBalorazioak.setViewportView(listaPanel);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 3;
		gbc_scrollPane.gridy = 2;
		contentPane.add(scrollPane, gbc_scrollPane);
		
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
			System.out.println(b);
		    JPanel item = sortuItem(b.getNorkIzena(), b.getPuntuazioa(), index);
		    System.out.println(item);
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
