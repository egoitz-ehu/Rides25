package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Driver;

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

public class DriverMenuGUI extends JFrame {

	private JPanel contentPane;
	
	private JMenuBar menuBar;
	private JMenu moneyMenu;
	private JMenuItem itemWithdraw;
	
	private JLabel title;
	
	private JButton buttonQuery;
	private JButton buttonCreate;
	private JButton buttonManage;

	private BLFacade bussinessLogic;

	/**
	 * Launch the application.
	 *//*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DriverMenuGUI frame = new DriverMenuGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/
	
	public void setBussinessLogic(BLFacade logic) {
    	this.bussinessLogic=logic;
    }

	/**
	 * Create the frame.
	 */
	public DriverMenuGUI(Driver driver) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		moneyMenu = new JMenu(ResourceBundle.getBundle("Etiquetas").getString("DriverMenuGUI.Money")); //$NON-NLS-1$ //$NON-NLS-2$
		menuBar.add(moneyMenu);
		
		itemWithdraw = new JMenuItem(ResourceBundle.getBundle("Etiquetas").getString("DriverMenuGUI.Withdraw")); //$NON-NLS-1$ //$NON-NLS-2$
		itemWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DiruaAtera dA = new DiruaAtera(driver);
				dA.setBussinessLogic(bussinessLogic);
				dA.setVisible(true);
			}
		});
		moneyMenu.add(itemWithdraw);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(4, 1, 0, 3));
		
		title = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DriverMenuGUI.Welcome"));
		contentPane.add(title);
		
		buttonQuery = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DriverMenuGUI.Query"));
		contentPane.add(buttonQuery);
		
		buttonCreate = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DriverMenuGUI.Create")); //$NON-NLS-1$ //$NON-NLS-2$
		contentPane.add(buttonCreate);
		
		buttonManage = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DriverMenuGUI.Manage")); //$NON-NLS-1$ //$NON-NLS-2$
		contentPane.add(buttonManage);
	}

}
