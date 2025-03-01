package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Driver;
import domain.Traveler;
import domain.User;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.Insets;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegisterGUI extends JFrame {

    private JPanel contentPane;
    private JTextField textFieldName;
    private JTextField textFieldSurname;
    private JTextField textFieldMail;
    private JPasswordField passwordField;
    
    private JLabel labelTitle;
    private JLabel labelName;
    private JLabel labelSurname;
    private JLabel labelMail;
    private JLabel labelPassword;
    private JLabel labelUserType;
    private JLabel errorLabel;
    
    private JRadioButton radioButtonDriver;
    private JRadioButton radioButtonPassenger;
    private ButtonGroup rbGroup;
    
    private JButton buttonSend;
    
    private BLFacade bussinessLogic;
/*
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    RegisterGUI frame = new RegisterGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
   */ 
    public void setBussinessLogic(BLFacade logic) {
    	this.bussinessLogic=logic;
    }

    public RegisterGUI() {
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Register.Title"));
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_contentPane.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        
        // Define weights to allow resizing
        gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 1.0}; // Ensures last row takes up space
        contentPane.setLayout(gbl_contentPane);
        
        // Label Title
        labelTitle = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Title"));
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.gridwidth = 12;
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 0;
        gbc_lblNewLabel.fill=GridBagConstraints.BOTH;
        contentPane.add(labelTitle, gbc_lblNewLabel);
        
        // Label and TextField for Name
        labelName = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Name"));
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.gridwidth = 7;
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_1.gridx = 0;
        gbc_lblNewLabel_1.gridy = 1;
        contentPane.add(labelName, gbc_lblNewLabel_1);
        
        textFieldName = new JTextField();
        GridBagConstraints gbc_textField = new GridBagConstraints();
        gbc_textField.gridwidth = 7;
        gbc_textField.insets = new Insets(0, 0, 5, 5);
        gbc_textField.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField.gridx = 0;
        gbc_textField.gridy = 2;
        gbc_textField.weightx = 1.0;  // Make it expand horizontally
        gbc_textField.weighty = 0.1;  // Control the vertical expansion
        contentPane.add(textFieldName, gbc_textField);
        textFieldName.setColumns(10);
        
        // Label and TextField for Surname
        labelSurname = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Surname"));
        GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
        gbc_lblNewLabel_2.gridwidth = 7;
        gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_2.gridx = 0;
        gbc_lblNewLabel_2.gridy = 3;
        contentPane.add(labelSurname, gbc_lblNewLabel_2);
        
        textFieldSurname = new JTextField();
        GridBagConstraints gbc_textFieldSurname = new GridBagConstraints();
        gbc_textFieldSurname.gridwidth = 7;
        gbc_textFieldSurname.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldSurname.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldSurname.gridx = 0;
        gbc_textFieldSurname.gridy = 4;
        gbc_textFieldSurname.weightx = 1.0; // Allow expansion in horizontal direction
        gbc_textFieldSurname.weighty = 0.1;
        contentPane.add(textFieldSurname, gbc_textFieldSurname);
        
        // Label and TextField for Mail
        labelMail = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Mail"));
        GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
        gbc_lblNewLabel_3.gridwidth = 7;
        gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_3.gridx = 0;
        gbc_lblNewLabel_3.gridy = 5;
        contentPane.add(labelMail, gbc_lblNewLabel_3);
        
        textFieldMail = new JTextField();
        GridBagConstraints gbc_textFieldMail = new GridBagConstraints();
        gbc_textFieldMail.gridwidth = 7;
        gbc_textFieldMail.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldMail.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldMail.gridx = 0;
        gbc_textFieldMail.gridy = 6;
        gbc_textFieldMail.weightx = 1.0;
        gbc_textFieldMail.weighty = 0.1;
        contentPane.add(textFieldMail, gbc_textFieldMail);
        
        errorLabel = new JLabel(); //$NON-NLS-1$ //$NON-NLS-2$
        GridBagConstraints gbc_errorLabel = new GridBagConstraints();
        gbc_lblNewLabel.gridwidth = 3;
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.gridx = 8;
        gbc_lblNewLabel.gridy = 6;
        contentPane.add(errorLabel, gbc_errorLabel);
        
        // Label and Password Field
        labelPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Password"));
        GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
        gbc_lblNewLabel_4.gridwidth = 7;
        gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_4.gridx = 0;
        gbc_lblNewLabel_4.gridy = 7;
        contentPane.add(labelPassword, gbc_lblNewLabel_4);
        
        passwordField = new JPasswordField();
        GridBagConstraints gbc_passwordField = new GridBagConstraints();
        gbc_passwordField.gridwidth = 7;
        gbc_passwordField.insets = new Insets(0, 0, 0, 5);
        gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
        gbc_passwordField.gridx = 0;
        gbc_passwordField.gridy = 8;
        gbc_passwordField.weightx = 1.0;
        gbc_passwordField.weighty = 0.1;
        contentPane.add(passwordField, gbc_passwordField);
        
        // User Type selection
        labelUserType = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.UserType"));
        GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
        gbc_lblNewLabel_5.gridwidth = 3;
        gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_5.gridx = 8;
        gbc_lblNewLabel_5.gridy = 2;
        contentPane.add(labelUserType, gbc_lblNewLabel_5);
        
        ActionListener actionRadioButton = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonSend.setEnabled(true);
			}
        };
        
        radioButtonDriver = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Driver"));
        radioButtonDriver.addActionListener(actionRadioButton);
        GridBagConstraints gbc_rdbtnDriver = new GridBagConstraints();
        gbc_rdbtnDriver.insets = new Insets(0, 0, 5, 5);
        gbc_rdbtnDriver.gridx = 9;
        gbc_rdbtnDriver.gridy = 3;
        gbc_rdbtnDriver.fill = GridBagConstraints.BOTH;
        contentPane.add(radioButtonDriver, gbc_rdbtnDriver);
        
        radioButtonPassenger = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Passenger"));
        radioButtonPassenger.addActionListener(actionRadioButton);
        GridBagConstraints gbc_rdbtnPassenger = new GridBagConstraints();
        gbc_rdbtnPassenger.insets = new Insets(0, 0, 5, 5);
        gbc_rdbtnPassenger.gridx = 9;
        gbc_rdbtnPassenger.gridy = 4;
        gbc_rdbtnPassenger.fill = GridBagConstraints.BOTH;
        contentPane.add(radioButtonPassenger, gbc_rdbtnPassenger);
        
        buttonSend = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Send"));
        buttonSend.setEnabled(false);
        buttonSend.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String mail = textFieldMail.getText();
        		String pass =  new String(passwordField.getPassword());
        		String name = textFieldName.getText();
        		String surname = textFieldSurname.getText();
        		String type;
        		if(radioButtonDriver.isSelected()) {
        			type="driver";
        		}
        		else {
        			type="traveler";
        		}
        
        		if(mail.isBlank() || pass.isBlank() || name.isBlank() || surname.isBlank())
        		{
        			JOptionPane.showMessageDialog(contentPane, ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.ErrorDataBlank"), "Error", JOptionPane.ERROR_MESSAGE);
        		}
        		else {
            		boolean b =bussinessLogic.register(mail, name, surname, pass, type);
            		if(!b) {
            			//errorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.ErrorUserAlreadyExists"));
            			JOptionPane.showMessageDialog(contentPane, ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.ErrorUserAlreadyExists"), "Error", JOptionPane.ERROR_MESSAGE);
            		} else {
            			JOptionPane.showMessageDialog(contentPane, ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Registered"), ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.RegisterTitle"), JOptionPane.INFORMATION_MESSAGE);
            		}
        		}
        	}
        });
        GridBagConstraints gbc_btnSend = new GridBagConstraints();
        gbc_btnSend.insets = new Insets(0, 0, 0, 5);
        gbc_btnSend.gridx = 9;
        gbc_btnSend.gridy = 8;
        gbc_btnSend.fill = GridBagConstraints.BOTH;
        contentPane.add(buttonSend, gbc_btnSend);
        
        // Group the radio buttons
        rbGroup = new ButtonGroup();
        rbGroup.add(radioButtonDriver);
        rbGroup.add(radioButtonPassenger);
    }
}
