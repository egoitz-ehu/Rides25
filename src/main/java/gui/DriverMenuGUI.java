package gui;

import java.awt.EventQueue;
import java.awt.Font;

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
	private JButton buttonManage;
	private JMenuItem itemIkusi;
	private JButton btnCreateCar;
	private JMenu ridesMenu;
	private JMenuItem createRide;
	private JMenuItem cancelRide;
	private JMenuItem itemMugimenduak;
	private JMenu mnNewMenu;
	private JMenuItem mntmNewMenuItem;
	private JMenuItem mntmNewMenuItem_1;
	private JMenu erreklamazioaMenu;
	private JMenuItem itemErreklamazioakIkusi;
	private JMenuItem itemErreklamazioaSortu;

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
	/**
	 * Create the frame.
	 */
	public DriverMenuGUI(Driver driver) {
		setBounds(100, 100, 450, 300);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("DriverMenu.Title"));
		
		moneyMenu = new JMenu(ResourceBundle.getBundle("Etiquetas").getString("DriverMenuGUI.Money")); //$NON-NLS-1$ //$NON-NLS-2$
		menuBar.add(moneyMenu);
		
		itemWithdraw = new JMenuItem(ResourceBundle.getBundle("Etiquetas").getString("DriverMenuGUI.Withdraw")); //$NON-NLS-1$ //$NON-NLS-2$
		itemWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DiruaAteraGUI dA = new DiruaAteraGUI(driver);
				dA.setVisible(true);
			}
		});
		moneyMenu.add(itemWithdraw);
		
		itemIkusi = new JMenuItem(ResourceBundle.getBundle("Etiquetas").getString("DriverMenuGUI.See")); //$NON-NLS-1$ //$NON-NLS-2$
		itemIkusi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DiruaIkusiGUI dI = new DiruaIkusiGUI(driver);
				dI.setVisible(true);
			}
		});
		moneyMenu.add(itemIkusi);
		
		itemMugimenduak = new JMenuItem(ResourceBundle.getBundle("Etiquetas").getString("DriverMenuGUI.SeeMovements")); //$NON-NLS-1$ //$NON-NLS-2$
		itemMugimenduak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				QueryTransactionsGUI tr = new QueryTransactionsGUI(driver);
				tr.setVisible(true);
			}
		});
		moneyMenu.add(itemMugimenduak);
		
		ridesMenu = new JMenu(ResourceBundle.getBundle("Etiquetas").getString("DriverMenuGUI.Rides")); //$NON-NLS-1$ //$NON-NLS-2$
		menuBar.add(ridesMenu);
		
		createRide = new JMenuItem(ResourceBundle.getBundle("Etiquetas").getString("DriverMenuGUI.Create")); //$NON-NLS-1$ //$NON-NLS-2$
		createRide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateRideGUI cR = new CreateRideGUI(driver);
				cR.setVisible(true);
			}
		});
		ridesMenu.add(createRide);
		
		cancelRide = new JMenuItem(ResourceBundle.getBundle("Etiquetas").getString("DriverMenuGUI.CancelRide")); //$NON-NLS-1$ //$NON-NLS-2$
		cancelRide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BidaiaKantzelatuGUI bK = new BidaiaKantzelatuGUI(driver);
				bK.setVisible(true);
			}
		});
		ridesMenu.add(cancelRide);
		
		mnNewMenu = new JMenu(ResourceBundle.getBundle("Etiquetas").getString("MenuBalorazioa")); //$NON-NLS-1$ //$NON-NLS-2$
		menuBar.add(mnNewMenu);
		
		mntmNewMenuItem = new JMenuItem(ResourceBundle.getBundle("Etiquetas").getString("MenuBalorazioa.Create")); //$NON-NLS-1$ //$NON-NLS-2$
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateReviewGUI cR = new CreateReviewGUI(driver);
				cR.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		mntmNewMenuItem_1 = new JMenuItem(ResourceBundle.getBundle("Etiquetas").getString("MenuBalorazioa.See")); //$NON-NLS-1$ //$NON-NLS-2$
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BalorazioakIkusiGUI bI = new BalorazioakIkusiGUI();
				bI.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		erreklamazioaMenu = new JMenu(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioaMenu.Title")); //$NON-NLS-1$ //$NON-NLS-2$
		menuBar.add(erreklamazioaMenu);
		
		itemErreklamazioakIkusi = new JMenuItem(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioaMenu.Ikusi")); //$NON-NLS-1$ //$NON-NLS-2$
		itemErreklamazioakIkusi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ErreklamazioaGestionatuGUI eG = new ErreklamazioaGestionatuGUI(driver);
				eG.setVisible(true);
			}
		});
		erreklamazioaMenu.add(itemErreklamazioakIkusi);
		
		itemErreklamazioaSortu = new JMenuItem(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioaMenu.Sortu")); //$NON-NLS-1$ //$NON-NLS-2$
		itemErreklamazioaSortu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ErreklamazioaSortuGUI eS = new ErreklamazioaSortuGUI(driver);
				eS.setVisible(true);
			}
		});
		erreklamazioaMenu.add(itemErreklamazioaSortu);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(4, 1, 0, 3));
		
		title = new JLabel("<html><font color='black'>"+ResourceBundle.getBundle("Etiquetas").getString("PassengerMenuGUI.Welcome")+"</font> <font color='blue'>"+driver.getName()+"</font></html>");
		title.setFont(new Font("Dialog", Font.BOLD, 32));
		contentPane.add(title);
		
		buttonQuery = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DriverMenuGUI.Query"));
		buttonQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FindRidesGUI findRides = new FindRidesGUI();
				findRides.setVisible(true);
			}
		});
		contentPane.add(buttonQuery);
		
		buttonManage = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DriverMenuGUI.Manage")); //$NON-NLS-1$ //$NON-NLS-2$
		buttonManage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ErreserbakGestionatuGUI eG = new ErreserbakGestionatuGUI(driver);
				eG.setVisible(true);
			}
		});
		contentPane.add(buttonManage);
		
		btnCreateCar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DriverMenuGUI.CreateCar")); //$NON-NLS-1$ //$NON-NLS-2$
		btnCreateCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				KotxeaSortuGUI kS = new KotxeaSortuGUI(driver);
				kS.setVisible(true);
			}
		});
		contentPane.add(btnCreateCar);
	}

}
