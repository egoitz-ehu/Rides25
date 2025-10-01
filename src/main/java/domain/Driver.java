package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Driver extends User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Ride> rides=new Vector<Ride>();
	
	@OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.PERSIST)
	private List<Car> carList = new Vector<Car>();
	

	public Driver(String email, String pass, String name, String surname) {
		super(email, pass, name, surname);
	}
	
	public Driver() {
		super();
	}
	
	public List<Ride> getRides() {
		return rides;
	}

	public void setRides(List<Ride> rides) {
		this.rides = rides;
	}

	public String toString(){
		return super.toString()+";"+rides;
	}

	public Ride addRide(List<String> hiriList, List<Double> prezioList, Date date, int nPlaces, Car c)  {
        Ride ride=new Ride(hiriList,prezioList,date,nPlaces,this,c);
        rides.add(ride);
        return ride;
	}

	/**
	 * This method checks if the ride already exists for that driver
	 * 
	 * @param from the origin location 
	 * @param to the destination location 
	 * @param date the date of the ride 
	 * @return true if the ride exists and false in other case
	 */
	public boolean doesRideExists(List<String> hiriList, Date date)  {
		for (Ride r:rides)
			if (r.haveSameStops(hiriList) && (java.util.Objects.equals(r.getDate(),date)) )
			 return true;
		
		return false;
	}
	/*
	public Ride removeRide(String from, String to, Date date) {
		boolean found=false;
		int index=0;
		Ride r=null;
		while (!found && index<=rides.size()) {
			r=rides.get(++index);
			if ( (java.util.Objects.equals(r.getFrom(),from)) && (java.util.Objects.equals(r.getTo(),to)) && (java.util.Objects.equals(r.getDate(),date)) )
			found=true;
		}
			
		if (found) {
			rides.remove(index);
			return r;
		} else return null;
	}*/
	
	public Car addCar(String matrikula, int eserKop, String kolorea, String modeloa) {
		Car c = new Car(matrikula,eserKop,kolorea,modeloa,this);
		this.carList.add(c);
		return c;
	}
	
	public List<Car> getCars(){
		return this.carList;
	}
}
