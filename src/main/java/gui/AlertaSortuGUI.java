package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JCalendar;

import business_logic.BLFacade;
import configuration.UtilDate;
import domain.RideEgoera;
import domain.Traveler;
import exceptions.AlertaAlreadyExistsException;
import exceptions.BadagoRideException;
import exceptions.ErreserbaAlreadyExistsException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AlertaSortuGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textFrom;
	private JTextField textField;
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;

	/**
	 * Create the frame.
	 */
	public AlertaSortuGUI(Traveler t) {
		setBounds(100, 100, 450, 317);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblFrom = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AlertaSortuGUI.From"));
		lblFrom.setBounds(10, 11, 124, 14);
		contentPane.add(lblFrom);
		
		textFrom = new JTextField();
		textFrom.setBounds(185, 8, 186, 20);
		contentPane.add(textFrom);
		textFrom.setColumns(10);
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AlertaSortuGUI.To"));
		lblNewLabel.setBounds(10, 46, 124, 14);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(185, 43, 186, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		jCalendar1.setBounds(new Rectangle(90, 80, 225, 150));


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
						System.out.println(UtilDate.trim(jCalendar1.getDate()));
					} catch (Exception e1) {

						e1.printStackTrace();
					}

				}
			} 
			
		});

		this.getContentPane().add(jCalendar1, null);
		
		JButton btnSortu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AlertaSortuGUI.Crear"));
		btnSortu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Date d =jCalendar1.getDate();
					if(d==null || textFrom.getText().equals("") || textField.getText().equals("")) {
						JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("Alerta.Datuak"));
					}else {
						WelcomeGUI.getBusinessLogic().sortuAlerta(t.getEmail(),textFrom.getText(),textField.getText(), UtilDate.trim(d));
						JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("Alerta.Sortu"));
					}
				} catch (BadagoRideException e1) {
					JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("Alerta.BadagoRide"));
				} catch (ErreserbaAlreadyExistsException e1) {
					JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("Alerta.ErreserbaExists"));
				} catch (AlertaAlreadyExistsException e1) {
					JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("Alerta.AlertaExists"));
				}
			}
		});
		
		btnSortu.setBounds(168, 244, 89, 23);
		contentPane.add(btnSortu);
	}
}
