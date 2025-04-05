package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;


@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Traveler extends User implements Serializable{

	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER)
	private List<Erreserba> bookedRides = new Vector<Erreserba>();
	
	public Traveler (String email, String pass, String name, String surname) {
		super(email, pass, name, surname);
	}
	
	public Traveler() {
		super();
	}

	public List<Erreserba> getBookedRides() {
		return bookedRides;
	}
	
	
	public boolean existBook(Ride r) {
		for(Erreserba erre: bookedRides) {
			if(erre.containsRide(r)) return true;
		}
		return false;
	}
	
	public boolean diruaDauka(Ride r, int eserKop) {
		return (this.getCash()>=r.getPrice()*eserKop);
	}
	
	public Erreserba sortuErreserba(Ride r, int eserKop) {
		double prezioa = r.getPrice()*eserKop;
		this.diruaAtera(prezioa);
		this.addFrozenMoney(prezioa);
		Erreserba erre = new Erreserba(eserKop, this, r);
		bookedRides.add(erre);
		return erre;
	}
	
	public void ezabatuErreserba(Erreserba e) {
		bookedRides.remove(e);
	}
}
