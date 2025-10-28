package domain;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class DriverAdapter extends AbstractTableModel {
	List<Ride> rideList;
	
	public DriverAdapter(Driver d) {
		this.rideList = d.getRides();
	}
	
	@Override
	public int getRowCount() {
		return rideList.size();
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(columnIndex==0) return this.rideList.get(rowIndex).getGeldialdiak().get(0).getHiria();
		else if(columnIndex==1) return this.rideList.get(rowIndex).getGeldialdiak().get(1).getHiria();
		else if(columnIndex==2) return this.rideList.get(rowIndex).getDate();
		else if(columnIndex==3) return this.rideList.get(rowIndex).getnPlaces();
		else  return this.rideList.get(rowIndex).prezioaKalkulatu(this.rideList.get(rowIndex).getGeldialdiak().get(0).getHiria(), this.rideList.get(rowIndex).getGeldialdiak().get(1).getHiria());
	}

}
