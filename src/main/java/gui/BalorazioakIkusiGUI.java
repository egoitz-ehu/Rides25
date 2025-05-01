package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
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

public class BalorazioakIkusiGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	
	private List<Balorazioa> balorazioList;
	private JPanel listaPanel;
	private JScrollPane panelBalorazioak;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	
	private int selectedIndex;

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
		gbl_contentPane.rowHeights = new int[]{0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("BalorazioakIkusiGUI.Select"));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 3;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 0;
		contentPane.add(textField, gbc_textField);
		textField.setColumns(10);
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BalorazioakIkusiGUI.Find"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				balorazioList = WelcomeGUI.getBusinessLogic().lortuBalorazioak(textField.getText());
				gehitu();
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 4;
		gbc_btnNewButton.gridy = 0;
		contentPane.add(btnNewButton, gbc_btnNewButton);
		
		panelBalorazioak = new JScrollPane();
		GridBagConstraints gbc_panelBalorazioak = new GridBagConstraints();
		gbc_panelBalorazioak.gridwidth = 3;
		gbc_panelBalorazioak.insets = new Insets(0, 0, 0, 5);
		gbc_panelBalorazioak.fill = GridBagConstraints.BOTH;
		gbc_panelBalorazioak.gridx = 0;
		gbc_panelBalorazioak.gridy = 1;
		contentPane.add(panelBalorazioak, gbc_panelBalorazioak);
		
		listaPanel = new JPanel();
		listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
		listaPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelBalorazioak.setViewportView(listaPanel);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 3;
		gbc_scrollPane.gridy = 1;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
	}
	
	private JPanel sortuItem(String izena, int puntu) {
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
            	selectedIndex = listaPanel.getComponentZOrder(panel);
            	textArea.setText(balorazioList.get(selectedIndex).getMezua());
            }
        });

        return panel;
    }
	
	private void gehitu() {
		listaPanel.removeAll();
		for (Balorazioa b : balorazioList) {
			System.out.println(b);
		    JPanel item = sortuItem(b.getNorkIzena(), b.getPuntuazioa());
		    System.out.println(item);
		    listaPanel.add(item);
		    listaPanel.add(Box.createVerticalStrut(10));
		}
		panelBalorazioak.revalidate();
		panelBalorazioak.repaint();
	}

}
