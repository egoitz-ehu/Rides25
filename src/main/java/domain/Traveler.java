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
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Erreserba> bookedRides = new Vector<Erreserba>();
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Alerta> alertaList = new Vector<Alerta>();
	
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
	
	public boolean diruaDauka(double kop) {
		return (this.getCash()>=kop);
	}
	
	public Erreserba sortuErreserba(Ride r, int eserKop, String from, String to, double prezioa) {
		this.diruaAtera(prezioa*eserKop);
		this.addFrozenMoney(prezioa*eserKop);
		Erreserba erre = new Erreserba(eserKop, this, r, from, to, prezioa*eserKop);
		bookedRides.add(erre);
		return erre;
	}
	
	public void sortuAlerta(String from, String to, Date d) {
		Alerta a = new Alerta(this,from,to,d);
		alertaList.add(a);
	}
	
	public boolean baduAlertaBerdina(String from, String to, Date date) {
		for(Alerta a:alertaList) {
			if(a.getFrom().equals(from)&&a.getTo().equals(to)&&a.getDate().equals(date)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean baduErreserba(String from, String to, Date date) {
		for(Erreserba e:bookedRides) {
			if(e.getFrom().equals(from)&&e.getTo().equals(to)&&e.getErreserbaData().equals(date)) {
				return true;
			}
		}
		return false;
	}
	
	public void kenduAlerta(Alerta a) {
		this.alertaList.remove(a);
	}

	public List<Alerta> getAlertaList() {
		return this.alertaList;
	}
}
