package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;
import domain.Ride;
import domain.RideEgoera;
import domain.Traveler;
import exceptions.AlertaAlreadyExistsException;
import exceptions.BadagoRideException;
import exceptions.DiruaEzDaukaException;
import exceptions.ErreserbaAlreadyExistsException;
import exceptions.EserlekurikLibreEzException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;
import java.util.List;

import javax.swing.table.DefaultTableModel;


public class ErreserbaEskaeraGUI extends JFrame {
	private static final long serialVersionUID = 1L;


	private JComboBox<String> jComboBoxOrigin = new JComboBox<String>();
	DefaultComboBoxModel<String> originLocations = new DefaultComboBoxModel<String>();

	private JComboBox<String> jComboBoxDestination = new JComboBox<String>();
	DefaultComboBoxModel<String> destinationCities = new DefaultComboBoxModel<String>();

	private JLabel jLabelOrigin = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.LeavingFrom"));
	private JLabel jLabelDestination = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.GoingTo"));
	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideDate"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.Rides")); 

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();

	private List<Date> datesWithRidesCurrentMonth = new Vector<Date>();

	private JTable tableRides= new JTable();

	private DefaultTableModel tableModelRides;
	
	private JLabel labelEserleku;
	private JButton buttonErreserba;

	private String[] columnNamesRides = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.Driver"), 
			ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.NPlaces"), 
			ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.Price"),
			ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.Aveilable")
	};
	private JTextField eserKop;
	
	private Ride selectedRide;
	
	private JLabel erreserbaMessageLabel;
	
	JLabel lblEz;
	JButton btnAlerta;

	public ErreserbaEskaeraGUI(Traveler t, String from, String to, Date date)
	{

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("ErreserbaEskaera.Title"));

		jLabelEventDate.setBounds(new Rectangle(457, 6, 140, 25));
		jLabelEvents.setBounds(58, 229, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelEvents);

		jButtonClose.setBounds(new Rectangle(274, 419, 130, 30));

		jButtonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jButton2_actionPerformed(e);
			}
		});
		BLFacade facade = WelcomeGUI.getBusinessLogic();
		List<String> origins=facade.getStopCitiesNames();
		originLocations.addElement("");
		for(String location:origins) originLocations.addElement(location);
		
		jLabelOrigin.setBounds(new Rectangle(6, 56, 92, 20));
		jLabelDestination.setBounds(6, 81, 61, 16);
		getContentPane().add(jLabelOrigin);

		getContentPane().add(jLabelDestination);

		jComboBoxOrigin.setModel(originLocations);
		jComboBoxOrigin.setBounds(new Rectangle(103, 50, 172, 20));
		

		//List<String> aCities=facade.getDestinationCities((String)jComboBoxOrigin.getSelectedItem());
		//for(String aciti:aCities) {
			//destinationCities.addElement(aciti);
		//}
		
		jComboBoxOrigin.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				destinationCities.removeAllElements();
				BLFacade facade = WelcomeGUI.getBusinessLogic();

				List<String> aCities=facade.getDestinationCities((String)jComboBoxOrigin.getSelectedItem());
				for(String aciti:aCities) {
					destinationCities.addElement(aciti);
				}
				tableModelRides.getDataVector().removeAllElements();
				tableModelRides.fireTableDataChanged();
			}
		});


		jComboBoxDestination.setModel(destinationCities);
		jComboBoxDestination.setBounds(new Rectangle(103, 80, 172, 20));
		jComboBoxDestination.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				paintDaysWithEvents(jCalendar1,datesWithRidesCurrentMonth,	new Color(210,228,238));

				BLFacade facade = WelcomeGUI.getBusinessLogic();

				datesWithRidesCurrentMonth=facade.getThisMonthDatesWithRides((String)jComboBoxOrigin.getSelectedItem(),(String)jComboBoxDestination.getSelectedItem(),jCalendar1.getDate());
				paintDaysWithEvents(jCalendar1,datesWithRidesCurrentMonth,Color.CYAN);
			}
		});

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jComboBoxOrigin, null);

		this.getContentPane().add(jComboBoxDestination, null);


		jCalendar1.setBounds(new Rectangle(300, 50, 225, 150));


		// Code for JCalendar
		jCalendar1.addPropertyChangeListener(new PropertyChangeListener()
		{
			public void propertyChange(PropertyChangeEvent propertychangeevent)
			{

				if (propertychangeevent.getPropertyName().equals("locale"))
				{
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				}
				else if (propertychangeevent.getPropertyName().equals("calendar"))
				{
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					

					
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());

					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);

					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) {
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}						

						jCalendar1.setCalendar(calendarAct);

					}
					
					try {
						tableModelRides.setDataVector(null, columnNamesRides);
						tableModelRides.setColumnCount(5); // another column added to allocate ride objects

						BLFacade facade = WelcomeGUI.getBusinessLogic();
						List<domain.Ride> rides=facade.getRides((String)jComboBoxOrigin.getSelectedItem(),(String)jComboBoxDestination.getSelectedItem(),UtilDate.trim(jCalendar1.getDate()));

						if (rides.isEmpty() ) jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.NoRides")+ ": "+dateformat1.format(calendarAct.getTime()));
						else jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.Rides")+ ": "+dateformat1.format(calendarAct.getTime()));
						for (domain.Ride ride:rides){
							if(ride.getEgoera().equals(RideEgoera.MARTXAN)) {
								Vector<Object> row = new Vector<Object>();
								row.add(ride.getDriver().getName());
								row.add(ride.getnPlaces());
								row.add(ride.prezioaKalkulatu((String) originLocations.getSelectedItem(), (String) destinationCities.getSelectedItem()));
								row.add(ride.getEserLibre());
								row.add(ride); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,3)
								tableModelRides.addRow(row);	
							}
						}
						Date d = jCalendar1.getDate();

						datesWithRidesCurrentMonth=facade.getThisMonthDatesWithRides((String)jComboBoxOrigin.getSelectedItem(),(String)jComboBoxDestination.getSelectedItem(), UtilDate.trim(d));
						if(datesWithRidesCurrentMonth.isEmpty() && !((String)jComboBoxOrigin.getSelectedItem()).equals("")) {
							btnAlerta.setEnabled(true);
							lblEz.setEnabled(true);
							lblEz.setText(ResourceBundle.getBundle("Etiquetas").getString("ErreserbaEskaeraGUI.BidairikEz"));
						} else {
							btnAlerta.setEnabled(false);
							lblEz.setEnabled(false);
							lblEz.setText("");
						}
						paintDaysWithEvents(jCalendar1,datesWithRidesCurrentMonth,Color.CYAN);


					} catch (Exception e1) {

						e1.printStackTrace();
					}
					tableRides.getColumnModel().getColumn(0).setPreferredWidth(170);
					tableRides.getColumnModel().getColumn(1).setPreferredWidth(30);
					tableRides.getColumnModel().getColumn(1).setPreferredWidth(30);
					tableRides.getColumnModel().removeColumn(tableRides.getColumnModel().getColumn(4)); // not shown in JTable
					erreserbaMessageLabel.setText("");
				}
			} 
			
		});

		this.getContentPane().add(jCalendar1, null);

		scrollPaneEvents.setBounds(new Rectangle(58, 256, 346, 150));
		
		tableRides.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        int selectedRow = tableRides.getSelectedRow();
		        System.out.println("Selected row:"+selectedRow);
		        if (selectedRow != -1) {
		            selectedRide = (Ride) tableModelRides.getValueAt(selectedRow, 4);
		            labelEserleku.setEnabled(true);
		            eserKop.setEnabled(true);
		            buttonErreserba.setEnabled(true);
		        }
		    }
		});


		scrollPaneEvents.setViewportView(tableRides);
		tableModelRides = new DefaultTableModel(null, columnNamesRides);

		tableRides.setModel(tableModelRides);

		tableModelRides.setDataVector(null, columnNamesRides);
		tableModelRides.setColumnCount(5); // another column added to allocate ride objects

		tableRides.getColumnModel().getColumn(0).setPreferredWidth(170);
		tableRides.getColumnModel().getColumn(1).setPreferredWidth(30);
		tableRides.getColumnModel().getColumn(2).setPreferredWidth(30);
		tableRides.getColumnModel().getColumn(3).setPreferredWidth(30);

		tableRides.getColumnModel().removeColumn(tableRides.getColumnModel().getColumn(3)); // not shown in JTable

		this.getContentPane().add(scrollPaneEvents, null);
		datesWithRidesCurrentMonth=facade.getThisMonthDatesWithRides((String)jComboBoxOrigin.getSelectedItem(),(String)jComboBoxDestination.getSelectedItem(),jCalendar1.getDate());
		paintDaysWithEvents(jCalendar1,datesWithRidesCurrentMonth,Color.CYAN);
		
		eserKop = new JTextField();
		eserKop.setText(""); //$NON-NLS-1$ //$NON-NLS-2$
		eserKop.setBounds(457, 301, 140, 20);
		getContentPane().add(eserKop);
		eserKop.setColumns(10);
		eserKop.setEnabled(false);
		
		labelEserleku = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErreserbaEskaeraGUI.Seats")); //$NON-NLS-1$ //$NON-NLS-2$
		labelEserleku.setEnabled(false);
		labelEserleku.setBounds(431, 276, 217, 14);
		getContentPane().add(labelEserleku);
		
		buttonErreserba = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ErreserbaEskaeraGUI.Egin")); //$NON-NLS-1$ //$NON-NLS-2$
		buttonErreserba.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int kop = Integer.parseInt(eserKop.getText());
					boolean b = WelcomeGUI.getBusinessLogic().sortuErreserba(t, selectedRide.getRideNumber(), kop, (String)jComboBoxOrigin.getSelectedItem(),(String)jComboBoxDestination.getSelectedItem());
					tableModelRides.setDataVector(null, columnNamesRides);
					//tableModelRides.setColumnCount(5);
					if(b) {
						erreserbaMessageLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("ErreserbaEskaeraGUI.Sortu"));
					} else {
						erreserbaMessageLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("ErreserbaEskaeraGUI.Arazoa"));
					}
				} catch (NumberFormatException e2) {
					erreserbaMessageLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("ErreserbaEskaeraGUI.EserFormat"));
				} catch (EserlekurikLibreEzException e3) {
					erreserbaMessageLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("ErreserbaEskaeraGUI.LekurikEz"));
				} catch (ErreserbaAlreadyExistsException e4) {
					erreserbaMessageLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("ErreserbaEskaeraGUI.ErreserbaDu"));
				} catch (DiruaEzDaukaException e1) {
					erreserbaMessageLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("ErreserbaEskaeraGUI.DirurikEz"));
				}
			}
		});
		buttonErreserba.setBounds(457, 372, 143, 23);
		buttonErreserba.setEnabled(false);
		getContentPane().add(buttonErreserba);
		
		erreserbaMessageLabel = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		erreserbaMessageLabel.setBounds(414, 406, 234, 43);
		erreserbaMessageLabel.setForeground(Color.red);
		getContentPane().add(erreserbaMessageLabel);
		
		lblEz = new JLabel(); //$NON-NLS-1$ //$NON-NLS-2$
		lblEz.setBounds(10, 145, 265, 14);
		getContentPane().add(lblEz);
		
		btnAlerta = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ErreserbaEskaeraGUI.AlertaSortu")); //$NON-NLS-1$ //$NON-NLS-2$
		btnAlerta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Date d =jCalendar1.getDate();
					WelcomeGUI.getBusinessLogic().sortuAlerta(t.getEmail(),(String)jComboBoxOrigin.getSelectedItem(),(String)jComboBoxDestination.getSelectedItem(), UtilDate.trim(d));
					JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("Alerta.Sortu"));
				} catch (BadagoRideException e1) {
					JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("Alerta.BadagoRide"));
				} catch (ErreserbaAlreadyExistsException e1) {
					JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("Alerta.ErreserbaExists"));
				} catch (AlertaAlreadyExistsException e1) {
					JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("Alerta.AlertaExists"));
				}
				btnAlerta.setEnabled(false);
				lblEz.setEnabled(false);
				lblEz.setText("");
			}
		});
		btnAlerta.setBounds(83, 177, 122, 23);
		getContentPane().add(btnAlerta);
		
		if (from != null) {
		    jComboBoxOrigin.setSelectedItem(from);

		    // Forzar actualización de destinos
		    List<String> aCities = facade.getDestinationCities(from);
		    destinationCities.removeAllElements();
		    for (String aciti : aCities) {
		        destinationCities.addElement(aciti);
		    }

		    if (to != null) {
		        jComboBoxDestination.setSelectedItem(to);
		    }

		    if (date != null) {
		        jCalendar1.setDate(date);

		        // Pintar días con rides
		        datesWithRidesCurrentMonth = facade.getThisMonthDatesWithRides(from, to, UtilDate.trim(date));
		        paintDaysWithEvents(jCalendar1, datesWithRidesCurrentMonth, Color.CYAN);

		        // Cargar rides automáticamente
		        List<Ride> rides = facade.getRides(from, to, UtilDate.trim(date));
		        tableModelRides.setDataVector(null, columnNamesRides);
		        tableModelRides.setColumnCount(5);
		        for (Ride ride : rides) {
		            if (ride.getEgoera().equals(RideEgoera.MARTXAN)) {
		                Vector<Object> row = new Vector<>();
		                row.add(ride.getDriver().getName());
		                row.add(ride.getnPlaces());
		                row.add(ride.prezioaKalkulatu((String) originLocations.getSelectedItem(), (String) destinationCities.getSelectedItem()));
		                row.add(ride.getEserLibre());
		                row.add(ride);
		                tableModelRides.addRow(row);
		            }
		        }
		        tableRides.getColumnModel().removeColumn(tableRides.getColumnModel().getColumn(4));
		    }
		}
		
		btnAlerta.setEnabled(false);
		lblEz.setEnabled(false);

	}
	public static void paintDaysWithEvents(JCalendar jCalendar,List<Date> datesWithEventsCurrentMonth, Color color) {
		//		// For each day with events in current month, the background color for that day is changed to cyan.


		Calendar calendar = jCalendar.getCalendar();

		int month = calendar.get(Calendar.MONTH);
		int today=calendar.get(Calendar.DAY_OF_MONTH);
		int year=calendar.get(Calendar.YEAR);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);

		/*if (Locale.getDefault().equals(new Locale("es")))
			offset += 4;
		else
			offset += 5;*/
		offset += 5;


		for (Date d:datesWithEventsCurrentMonth){

			calendar.setTime(d);


			// Obtain the component of the day in the panel of the DayChooser of the
			// JCalendar.
			// The component is located after the decorator buttons of "Sun", "Mon",... or
			// "Lun", "Mar"...,
			// the empty days before day 1 of month, and all the days previous to each day.
			// That number of components is calculated with "offset" and is different in
			// English and Spanish
			//			    		  Component o=(Component) jCalendar.getDayChooser().getDayPanel().getComponent(i+offset);; 
			Component o = (Component) jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(color);
		}

		calendar.set(Calendar.DAY_OF_MONTH, today);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);


	}
	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
