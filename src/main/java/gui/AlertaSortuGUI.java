package gui;

import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JCalendar;
import configuration.UtilDate;
import domain.Traveler;
import exceptions.AlertaAlreadyExistsException;
import exceptions.BadagoRideException;
import exceptions.ErreserbaAlreadyExistsException;

public class AlertaSortuGUI extends JFrame {

    private JPanel contentPane;
    private JTextField textFrom;
    private JTextField textField;
    private JCalendar jCalendar1 = new JCalendar();

    public AlertaSortuGUI(Traveler t) {
        setBounds(100, 100, 450, 317);
        initContentPane();
        initLabelsAndFields();
        initCalendar();
        initButton(t);
    }

    private void initContentPane() {
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
    }

    private void initLabelsAndFields() {
        JLabel lblFrom = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AlertaSortuGUI.From"));
        lblFrom.setBounds(10, 11, 124, 14);
        contentPane.add(lblFrom);

        textFrom = new JTextField();
        textFrom.setBounds(185, 8, 186, 20);
        contentPane.add(textFrom);
        textFrom.setColumns(10);

        JLabel lblTo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AlertaSortuGUI.To"));
        lblTo.setBounds(10, 46, 124, 14);
        contentPane.add(lblTo);

        textField = new JTextField();
        textField.setBounds(185, 43, 186, 20);
        contentPane.add(textField);
        textField.setColumns(10);
    }

    private void initCalendar() {
        jCalendar1.setBounds(new Rectangle(90, 80, 225, 150));
        jCalendar1.addPropertyChangeListener(evt -> handleCalendarChange(evt));
        this.getContentPane().add(jCalendar1, null);
    }

    private void handleCalendarChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "locale":
                jCalendar1.setLocale((Locale) evt.getNewValue());
                break;
            case "calendar":
                updateCalendar((Calendar) evt.getOldValue(), (Calendar) evt.getNewValue());
                break;
            default:
                break;
        }
    }

    private void updateCalendar(Calendar oldCal, Calendar newCal) {
    	Calendar calendarAnt;
    	Calendar  calendarAct;
        calendarAnt = oldCal;
        calendarAct = newCal;

        int monthAnt = calendarAnt.get(Calendar.MONTH);
        int monthAct = calendarAct.get(Calendar.MONTH);

        if (monthAct != monthAnt) {
            if (monthAct == monthAnt + 2) {
                // Caso especial: 30 enero → 2 marzo, corregir a 1 febrero
                newCal.set(Calendar.MONTH, monthAnt + 1);
                newCal.set(Calendar.DAY_OF_MONTH, 1);
            }
            jCalendar1.setCalendar(newCal);
        }
    }

    private void initButton(Traveler t) {
        JButton btnSortu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AlertaSortuGUI.Crear"));
        btnSortu.setBounds(168, 244, 89, 23);
        btnSortu.addActionListener(e -> handleCreateAlert(t));
        contentPane.add(btnSortu);
    }

    private void handleCreateAlert(Traveler t) {
        try {
            Date d = jCalendar1.getDate();
            if (d == null || textFrom.getText().isEmpty() || textField.getText().isEmpty()) {
                showMessage("Alerta.Datuak");
            } else {
                WelcomeGUI.getBusinessLogic().sortuAlerta(
                        t.getEmail(),
                        textFrom.getText(),
                        textField.getText(),
                        UtilDate.trim(d)
                );
                showMessage("Alerta.Sortu");
            }
        } catch (BadagoRideException e1) {
            showMessage("Alerta.BadagoRide");
        } catch (ErreserbaAlreadyExistsException e1) {
            showMessage("Alerta.ErreserbaExists");
        } catch (AlertaAlreadyExistsException e1) {
            showMessage("Alerta.AlertaExists");
        }
    }

    private void showMessage(String key) {
        JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString(key));
    }
}
