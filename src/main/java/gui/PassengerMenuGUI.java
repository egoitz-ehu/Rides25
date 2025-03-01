package gui;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Traveler;

import java.awt.GridBagLayout;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PassengerMenuGUI extends JFrame {

	private JPanel contentPane;
	
	private JMenuBar menuBar;
	private JMenu moneyMenu;
	private JMenuItem itemWithdraw;
	private JMenuItem itemDeposit;
	
	private JLabel title;
	
	private JButton buttonQuery;
	private JMenuItem itemIkusi;

	/**
	 * Launch the application.
	 *//*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PassengerMenuGUI frame = new PassengerMenuGUI();
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
	public PassengerMenuGUI(Traveler traveler) {
		setBounds(100, 100, 450, 300);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("PassengerMenu.Title"));
		
		moneyMenu = new JMenu(ResourceBundle.getBundle("Etiquetas").getString("PassengerMenuGUI.Money")); //$NON-NLS-1$ //$NON-NLS-2$
		menuBar.add(moneyMenu);
		
		itemWithdraw = new JMenuItem(ResourceBundle.getBundle("Etiquetas").getString("PassengerMenuGUI.Withdraw")); //$NON-NLS-1$ //$NON-NLS-2$
		itemWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DiruaAteraGUI dA = new DiruaAteraGUI(traveler);
				dA.setVisible(true);
			}
		});
		moneyMenu.add(itemWithdraw);
		
		itemDeposit = new JMenuItem(ResourceBundle.getBundle("Etiquetas").getString("PassengerMenuGUI.Deposit")); //$NON-NLS-1$ //$NON-NLS-2$
		itemDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DiruaSartuGUI dS = new DiruaSartuGUI(traveler);
				dS.setVisible(true);
			}
		});
		moneyMenu.add(itemDeposit);
		
		itemIkusi = new JMenuItem(ResourceBundle.getBundle("Etiquetas").getString("PassengerMenuGUI.See")); //$NON-NLS-1$ //$NON-NLS-2$
		moneyMenu.add(itemIkusi);
		itemIkusi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DiruaIkusiGUI dI = new DiruaIkusiGUI(traveler);
				dI.setVisible(true);
			}
		});
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(3, 1, 0, 0));
		
		//title = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("PassengerMenuGUI.Welcome")+traveler.getName());
		title = new JLabel("<html><font color='black'>"+ResourceBundle.getBundle("Etiquetas").getString("PassengerMenuGUI.Welcome")+"</font> <font color='blue'>"+traveler.getName()+"</font></html>");
		title.setFont(new Font("Dialog", Font.BOLD, 32));
		contentPane.add(title);
		
		buttonQuery = new JButton(ResourceBundle.getBundle("Etiquetas").getString("PassengerMenuGUI.Query"));
		buttonQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ErreserbaEskaeraGUI eE = new ErreserbaEskaeraGUI(traveler);
				eE.setVisible(true);
			}
		});
		contentPane.add(buttonQuery);
	}

}
