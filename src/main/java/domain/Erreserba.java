package domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Entity
public class Erreserba {
	@Id
	@GeneratedValue
	private int eskaeraNum;
	private int plazaKop;
	
	@Enumerated(EnumType.STRING)
	private ErreserbaEgoera egoera;
	
	private Date erreserbaData;
	@ManyToOne(fetch=FetchType.EAGER)
	private Traveler bidaiaria;
	@ManyToOne(fetch=FetchType.EAGER)
	private Ride ride;
	
	
	public ErreserbaEgoera getEgoera() {
		return egoera;
	}

	public void setEgoera(ErreserbaEgoera egoera) {
		this.egoera = egoera;
	}

	public int getEskaeraNum() {
		return eskaeraNum;
	}

	public void setEskaeraNum(int eskaeraNum) {
		this.eskaeraNum = eskaeraNum;
	}

	public int getPlazaKop() {
		return plazaKop;
	}

	public void setPlazaKop(int plazaKop) {
		this.plazaKop = plazaKop;
	}

	public Date getErreserbaData() {
		return erreserbaData;
	}

	public void setErreserbaData(Date erreserbaData) {
		this.erreserbaData = erreserbaData;
	}

	public Traveler getBidaiaria() {
		return bidaiaria;
	}

	public void setBidaiaria(Traveler bidaiaria) {
		this.bidaiaria = bidaiaria;
	}

	public Ride getRide() {
		return ride;
	}

	public void setRide(Ride ride) {
		this.ride = ride;
	}

	public Erreserba(int kop, Traveler bidaiaria, Ride ride) {
		this.plazaKop=kop;
		this.bidaiaria=bidaiaria;
		this.ride=ride;
		this.erreserbaData= new Date();
		this.egoera=ErreserbaEgoera.ZAIN;
	}
	
	public boolean containsRide(Ride r) {
		return (this.ride.equals(r));
	}
	
	public double prezioaKalkulatu() {
		return (this.plazaKop*this.ride.getPrice());
	}
	
	@Override
	public String toString() {
		return "Num:"+eskaeraNum + ";Plaza kop:" + plazaKop + ";Egoera:" + egoera + ";Data:" + erreserbaData + ";Bidaiaria mail:" + bidaiaria.getEmail();
	}
}
