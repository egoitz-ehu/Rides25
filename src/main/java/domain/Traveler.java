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
	private List<Ride> bookedRides = new Vector<Ride>();
	
	public Traveler (String email, String pass, String name, String surname) {
		super(email, pass, name, surname);
	}

	public List<Ride> getBookedRides() {
		return bookedRides;
	}
	
	
	//bookRides
	
	//addBalance
	
	//withdrawBalance
	
	//
}
