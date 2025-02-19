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

	public List<Erreserba> getBookedRides() {
		return bookedRides;
	}
	
	
	public boolean existBook(Ride r) {
		return bookedRides.contains(r);
	}
	
	public boolean diruaDauka(Ride r, int eserKop) {
		return (this.getCash()>=r.getPrice()*eserKop);
	}
	
	public Erreserba sortuErreserba(Ride r, int eserKop) {
		Erreserba e = new Erreserba(eserKop,this, r);
		this.setCash(this.getCash()-r.getPrice()*eserKop);
		this.setFrozenMoney(this.getFrozenMoney()+r.getPrice()*eserKop);
		bookedRides.add(e);
		r.gehituErreserba(e);
		return e;
	}
}
