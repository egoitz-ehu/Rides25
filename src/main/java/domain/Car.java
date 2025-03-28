package domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Car {
	@Id
	private String matrikula;
	private int eserKop;
	private String kolorea;
	private String modeloa;
	
	private Driver jabea;
	
	@OneToMany
	private List<Ride> rideList;
	
	public Car(String matrikula, int eserKop, String kolorea, String modeloa, Driver jabea) {
		this.matrikula=matrikula;
		this.eserKop=eserKop;
		this.kolorea=kolorea;
		this.modeloa=modeloa;
		this.jabea=jabea;
	}
	
	public void gehituBidaia(Ride r) {
		this.rideList.add(r);
	}
}
