package gui;

import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.text.DateFormat;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Car;
import domain.Driver;
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

public class CreateRideGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	
	private Driver driver;
	private JTextField fieldOrigin=new JTextField();
	
	private JLabel jLabelOrigin = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.LeavingFrom"));
	private JLabel jLabelSeats = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.SelectCar"));
	private JLabel jLabRideDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideDate"));
	private JLabel jLabelPrice = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.Price"));

	
	
	private JComboBox<Car> jTextFieldSeats = new JComboBox<Car>();
	private DefaultComboBoxModel<Car> carModel = new DefaultComboBoxModel<Car>();
	
	private JTextField jTextFieldPrice = new JTextField();

	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;

	private JScrollPane scrollPaneEvents = new JScrollPane();

	private JButton jButtonCreate = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.CreateRide"));
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JLabel jLabelMsg = new JLabel();
	private JLabel jLabelError = new JLabel();
	
	private List<Date> datesWithEventsCurrentMonth;
	
	private JTable table;
	
	private String[] columnNamesRides = new String[] {
			"Pos","city"
	};
	
	private DefaultTableModel tableModel;
	
	private int lastPos=0;
	
	private Car selectedCar;


	public CreateRideGUI(Driver driver) {

		this.driver=driver;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.CreateRide"));

		jLabelOrigin.setBounds(new Rectangle(6, 56, 92, 20));
		jLabelSeats.setBounds(new Rectangle(6, 200, 173, 20));
		jTextFieldSeats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedCar=(Car) carModel.getSelectedItem();
			}
		});
		jTextFieldSeats.setBounds(new Rectangle(139, 200, 60, 20));
		jTextFieldSeats.setModel(carModel);
		
		carModel.addAll(WelcomeGUI.getBusinessLogic().getDriverCars(driver.getEmail()));
		
		jLabelPrice.setBounds(new Rectangle(6, 233, 173, 20));
		jTextFieldPrice.setBounds(new Rectangle(139, 233, 60, 20));

		jCalendar.setBounds(new Rectangle(300, 50, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));

		jButtonCreate.setBounds(new Rectangle(100, 263, 130, 30));

		jButtonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonCreate_actionPerformed(e);
			}
		});
		jButtonClose.setBounds(new Rectangle(275, 263, 130, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});

		jLabelMsg.setBounds(new Rectangle(275, 214, 305, 20));
		jLabelMsg.setForeground(Color.red);

		jLabelError.setBounds(new Rectangle(260, 233, 320, 20));
		jLabelError.setForeground(Color.red);

		this.getContentPane().add(jLabelMsg, null);
		this.getContentPane().add(jLabelError, null);

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jButtonCreate, null);
		this.getContentPane().add(jTextFieldSeats, null);

		this.getContentPane().add(jLabelSeats, null);
		this.getContentPane().add(jLabelOrigin, null);
		

		

		this.getContentPane().add(jCalendar, null);
		
		this.getContentPane().add(jLabelPrice, null);
		this.getContentPane().add(jTextFieldPrice, null);

		
		
		
		BLFacade facade = WelcomeGUI.getBusinessLogic();
		datesWithEventsCurrentMonth=facade.getThisMonthDatesWithRides("a","b",jCalendar.getDate());		
		
		jLabRideDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabRideDate.setBounds(298, 16, 140, 25);
		getContentPane().add(jLabRideDate);
		
		
		fieldOrigin.setBounds(83, 53, 130, 26);
		getContentPane().add(fieldOrigin);
		fieldOrigin.setColumns(10);
		
		 //Code for JCalendar
		this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
//			
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar.getLocale());
					
					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) { 
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolverá 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}
						
						jCalendar.setCalendar(calendarAct);						
	
					}
					jCalendar.setCalendar(calendarAct);
					int offset = jCalendar.getCalendar().get(Calendar.DAY_OF_WEEK);
					
						if (Locale.getDefault().equals(new Locale("es")))
							offset += 4;
						else
							offset += 5;
				Component o = (Component) jCalendar.getDayChooser().getDayPanel().getComponent(jCalendar.getCalendar().get(Calendar.DAY_OF_MONTH) + offset);
				}}});
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(16, 88, 250, 78);

		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
			},columnNamesRides
			
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(90);
		tableModel = new DefaultTableModel(null, columnNamesRides);
		scrollPane.setViewportView(table);
		table.setModel(tableModel);

		table.setDragEnabled(true);
		table.setDropMode(DropMode.INSERT_ROWS);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setTransferHandler(new TableRowTransferHandler(table));

		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem deleteItem = new JMenuItem("Delete");
		JMenuItem upItem = new JMenuItem("Move up");
		JMenuItem downItem = new JMenuItem("Move down");
		popupMenu.add(deleteItem);
		popupMenu.add(upItem);
		popupMenu.add(downItem);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.isPopupTrigger()) showPopup(e);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if(e.isPopupTrigger()) showPopup(e);
			}

			private void showPopup(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				if(row>=0 && row<table.getRowCount()) {
					table.setRowSelectionInterval(row,row);
					popupMenu.show(e.getComponent(),e.getX(),e.getY());
				}
			}

		});

		deleteItem.addActionListener(e->{
			int selectedRow = table.getSelectedRow();
			if(selectedRow!=-1) {
				tableModel.removeRow(selectedRow);
				eguneratuPos(tableModel);
			}
		});

		upItem.addActionListener(e->{
			int selectedRow = table.getSelectedRow();
			if(selectedRow>0){
				Object city = table.getValueAt(selectedRow,1);
				tableModel.removeRow(selectedRow);
				tableModel.insertRow(selectedRow-1,new Object[]{null,city});
				eguneratuPos(tableModel);
			}
		});

		downItem.addActionListener(e->{
			int selectedRow = table.getSelectedRow();
			if(selectedRow>=0 && selectedRow<table.getRowCount()-1){
				Object city = table.getValueAt(selectedRow,1);
				tableModel.removeRow(selectedRow);
				tableModel.insertRow(selectedRow+1,new Object[]{null,city});
				eguneratuPos(tableModel);
			}
		});
		
		JButton btnNewButton = new JButton("Add"); //$NON-NLS-1$ //$NON-NLS-2$
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!fieldOrigin.getText().isEmpty() && !dagoenekoBadago(fieldOrigin.getText())) {
					lastPos++;
					Vector<Object> v = new Vector<Object>();
					v.add(lastPos);
					v.add(fieldOrigin.getText());
					tableModel.addRow(v);
				}
			}
		});
		btnNewButton.setBounds(223, 55, 65, 23);
		getContentPane().add(btnNewButton);
	}
	
	private boolean dagoenekoBadago(String name) {
		Vector<Vector> datuak = tableModel.getDataVector();
		for(int i=0;i<lastPos;i++){
			if(datuak.elementAt(i).elementAt(1).equals(name)) return true;
		}
		return false;
	}
	
	private void jButtonCreate_actionPerformed(ActionEvent e) {
		jLabelMsg.setText("");
		String error=field_Errors();
		if (error!=null) 
			jLabelMsg.setText(error);
		else
			try {
				BLFacade facade = WelcomeGUI.getBusinessLogic();
				float price = Float.parseFloat(jTextFieldPrice.getText());
				List<String> hiriak = new ArrayList<String>();
				for(int i = 0; i<tableModel.getRowCount();i++){
					String h = (String) tableModel.getValueAt(i,1);
					hiriak.add(h);
				}
				Ride r=facade.createRide(hiriak,UtilDate.trim(jCalendar.getDate()), selectedCar, price, driver.getEmail());
				jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideCreated"));

			} catch (RideMustBeLaterThanTodayException e1) {
				// TODO Auto-generated catch block
				jLabelMsg.setText(e1.getMessage());
			} catch (RideAlreadyExistException e1) {
				// TODO Auto-generated catch block
				jLabelMsg.setText(e1.getMessage());
			}

		}
	

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
	private String field_Errors() {
		
		try {
			if ((fieldOrigin.getText().length()==0) || (selectedCar == null) || (jTextFieldPrice.getText().length()==0))
				return ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorQuery");
			else {

				if (selectedCar == null) {
					return ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.NotCar");
				}
				else {
					float price = Float.parseFloat(jTextFieldPrice.getText());
					if (price <= 0) 
						return ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.PriceMustBeGreaterThan0");
					
					else 
						return null;
						
				}
			}
		} catch (java.lang.NumberFormatException e1) {

			return  ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorNumber");		
		} catch (Exception e1) {

			e1.printStackTrace();
			return null;

		}
	}

	class TableRowTransferHandler extends TransferHandler {
		private final JTable table;
		private int[] rows = null;

		public TableRowTransferHandler(JTable table) {
			this.table=table;
		}

		@Override
		protected Transferable createTransferable(JComponent c) {
			rows = table.getSelectedRows();
			return new StringSelection("");
		}

		@Override
		public int getSourceActions(JComponent c) {
			return MOVE;
		}
		@Override
		public boolean canImport(TransferHandler.TransferSupport support) {
			return support.isDrop();
		}

		@Override
		public boolean importData(TransferHandler.TransferSupport support) {
			if(!canImport(support)) return false;
			JTable.DropLocation d1 = (JTable.DropLocation) support.getDropLocation();
			int dropRow = d1.getRow();
			if(rows==null || rows.length==0) return false;
			DefaultTableModel  model = (DefaultTableModel) table.getModel();
			List<Object[]> rowData = new ArrayList<>();
			for(int row:rows) {
				int colCount = model.getColumnCount();
				Object[] rowValues = new Object[colCount];
				for(int col = 0; col<colCount; col++) {
					rowValues[col]=model.getValueAt(row,col);
				}
				rowData.add(rowValues);
			}
			for(int i = rows.length-1;i>=0;i--) {
				model.removeRow(rows[i]);
				if(rows[i]<dropRow) dropRow--;
			}
			for(Object[] row:rowData) {
				model.insertRow(dropRow++,row);
			}
			eguneratuPos(model);
			return true;
		}

	}

	private void eguneratuPos(DefaultTableModel model){
		for(int i = 0; i < model.getRowCount(); i++) {
			model.setValueAt(i+1,i,0);
		}
	}
}
