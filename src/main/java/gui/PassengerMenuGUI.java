package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class PassengerMenuGUI extends JFrame {

	private JPanel contentPane;
	
	private JMenuBar menuBar;
	private JMenu moneyMenu;
	private JMenuItem itemWithdraw;
	private JMenuItem itemDeposit;
	
	private JLabel title;
	
	private JButton buttonQuery;

	/**
	 * Launch the application.
	 */
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
	}

	/**
	 * Create the frame.
	 */
	public PassengerMenuGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		moneyMenu = new JMenu(ResourceBundle.getBundle("Etiquetas").getString("PassengerMenuGUI.Money")); //$NON-NLS-1$ //$NON-NLS-2$
		menuBar.add(moneyMenu);
		
		itemWithdraw = new JMenuItem(ResourceBundle.getBundle("Etiquetas").getString("PassengerMenuGUI.Withdraw")); //$NON-NLS-1$ //$NON-NLS-2$
		moneyMenu.add(itemWithdraw);
		
		itemDeposit = new JMenuItem(ResourceBundle.getBundle("Etiquetas").getString("PassengerMenuGUI.Deposit")); //$NON-NLS-1$ //$NON-NLS-2$
		moneyMenu.add(itemDeposit);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(3, 1, 0, 0));
		
		title = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("PassengerMenuGUI.Welcome"));
		contentPane.add(title);
		
		buttonQuery = new JButton(ResourceBundle.getBundle("Etiquetas").getString("PassengerMenuGUI.Query"));
		contentPane.add(buttonQuery);
	}

}
