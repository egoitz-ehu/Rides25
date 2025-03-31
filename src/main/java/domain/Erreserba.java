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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
@Entity
public class Erreserba {
	@Id
	@GeneratedValue
	private int eskaeraNum;
	private int plazaKop;
	
	@Enumerated(EnumType.STRING)
	private ErreserbaEgoera egoera;
	
	private Date erreserbaData;
	@OneToOne(fetch=FetchType.EAGER)
	private Traveler bidaiaria;
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
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
	
	public String getBidaiariaEmail() {
		return this.bidaiaria.getEmail();
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
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Erreserba other = (Erreserba) obj;
		if (this.eskaeraNum==other.eskaeraNum)
			return true;
		return false;
	}
}
