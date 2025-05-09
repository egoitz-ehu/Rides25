package domain;

import java.io.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.ArrayList;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Ride implements Serializable {
	@XmlID
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer rideNumber;
	private int nPlaces;
	private int eserLibre;
	private Date date;
	
	@OneToOne
	private Car car;
	
	public List<Erreserba> getErreserbak() {
		return erreserbak;
	}

	public void setErreserbak(List<Erreserba> erreserbak) {
		this.erreserbak = erreserbak;
	}
	
	@OneToOne(fetch=FetchType.EAGER)
	private Driver driver;
	
	@OneToMany(fetch=FetchType.EAGER)
	private List<Erreserba> erreserbak=new Vector<Erreserba>();
	
	@Enumerated(EnumType.STRING)
	private RideEgoera egoera;
	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Geldialdia> geldialdiak = new ArrayList<Geldialdia>();
	
	
	public RideEgoera getEgoera() {
		return egoera;
	}

	public void setEgoera(RideEgoera egoera) {
		this.egoera = egoera;
	}

	public int getEserLibre() {
		return eserLibre;
	}

	public void setEserLibre(int eserLibre) {
		this.eserLibre = eserLibre;
	}

	public Ride(){
		super();
	}
	/*
	public Ride(Integer rideNumber, String from, String to, Date date, int nPlaces, float price, Driver driver) {
		super();
		this.rideNumber = rideNumber;
		this.from = from;
		this.to = to;
		this.nPlaces = nPlaces;
		this.date=date;
		this.price=price;
		this.driver = driver;
		this.eserLibre=nPlaces;
		this.egoera=RideEgoera.MARTXAN;
	}

	

	public Ride(String from, String to,  Date date, int nPlaces, float price, Driver driver, Car c) {
		super();
		this.from = from;
		this.to = to;
		this.nPlaces = nPlaces;
		this.date=date;
		this.price=price;
		this.driver = driver;
		this.eserLibre=nPlaces;
		this.car=c;
		this.egoera=RideEgoera.MARTXAN;
	}*/

	public Ride(List<String> hiriList, List<Double> prezioList, Date date, int nPlaces, Driver driver, Car c) {
		super();
		this.nPlaces = nPlaces;
		this.date=date;
		this.driver = driver;
		this.eserLibre=nPlaces;
		this.car=c;
		this.egoera=RideEgoera.MARTXAN;
		List<Geldialdia> geldialdiList = new LinkedList<Geldialdia>();
		for(int i=0;i<hiriList.size();i++) {
			Geldialdia g = null;
			if(i==hiriList.size()-1) {
				g = new Geldialdia(i+1,0,i==hiriList.size()-1,hiriList.get(i));
			} else {
				g = new Geldialdia(i+1,prezioList.get(i),i==hiriList.size()-1,hiriList.get(i));
			}
			geldialdiList.add(g);
		}
		this.geldialdiak=geldialdiList;
	}
	
	/**
	 * Get the  number of the ride
	 * 
	 * @return the ride number
	 */
	public Integer getRideNumber() {
		return rideNumber;
	}

	
	/**
	 * Set the ride number to a ride
	 * 
	 * @param ride Number to be set	 */
	
	public void setRideNumber(Integer rideNumber) {
		this.rideNumber = rideNumber;
	}

	/**
	 * Get the free places of the ride
	 * 
	 * @return the available places
	 */
	
	/**
	 * Get the date  of the ride
	 * 
	 * @return the ride date 
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * Set the date of the ride
	 * 
	 * @param date to be set
	 */	
	public void setDate(Date date) {
		this.date = date;
	}

	
	public float getnPlaces() {
		return nPlaces;
	}

	/**
	 * Set the free places of the ride
	 * 
	 * @param  nPlaces places to be set
	 */

	public void setBetMinimum(int nPlaces) {
		this.nPlaces = nPlaces;
	}

	/**
	 * Get the driver associated to the ride
	 * 
	 * @return the associated driver
	 */
	public Driver getDriver() {
		return driver;
	}

	/**
	 * Set the driver associated to the ride
	 * 
	 * @param driver to associate to the ride
	 */
	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	
	@Override
	public String toString() {
		return this.rideNumber+";"+this.date;
	}


	public void gehituErreserba(Erreserba e) {
		this.erreserbak.add(e);
		this.eserLibre-=e.getPlazaKop();
	}

	public boolean eserlekuakLibre(int kop) {
		if(kop<=this.eserLibre) return true;
		else return false;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ride other = (Ride) obj;
		if (this.rideNumber==other.rideNumber)
			return true;
		return false;
	}
	
	public void itzuliEserlekuak(int kop) {
		this.eserLibre+=kop;
	}

	public List<Geldialdia> getGeldialdiak(){
		return this.geldialdiak;
	}

	public boolean haveSameStops(List<String> hiriList) {
		return hiriList.equals(geldialdiak);
	}
	
	public double prezioaKalkulatu(String from, String to) {
		double kop = 0.0;
		boolean fromAurkitua=false;
		for(Geldialdia g:geldialdiak) {
			if(fromAurkitua) {
				if(g.getHiria().equals(to)) {
					break;
				}
				kop+=g.getPrezioa();
			} else if(g.getHiria().equals(from)) {
				fromAurkitua=true;
				kop+=g.getPrezioa();
			}
		}
		return kop;
	}
}
