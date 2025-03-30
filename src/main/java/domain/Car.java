package domain;

import java.util.List;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	
	@OneToMany(fetch=FetchType.EAGER)
	private List<Ride> rideList=new Vector<Ride>();
	
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
	
	@Override
	public String toString() {
		return this.matrikula+";"+this.eserKop+";"+this.kolorea+";"+this.modeloa;
	}

	public String getMatrikula() {
		return matrikula;
	}

	public void setMatrikula(String matrikula) {
		this.matrikula = matrikula;
	}

	public int getEserKop() {
		return eserKop;
	}

	public void setEserKop(int eserKop) {
		this.eserKop = eserKop;
	}

	public String getKolorea() {
		return kolorea;
	}

	public void setKolorea(String kolorea) {
		this.kolorea = kolorea;
	}

	public String getModeloa() {
		return modeloa;
	}

	public void setModeloa(String modeloa) {
		this.modeloa = modeloa;
	}

	public Driver getJabea() {
		return jabea;
	}

	public void setJabea(Driver jabea) {
		this.jabea = jabea;
	}
	
	public void addRide(Ride r) {
		this.rideList.add(r);
	}
}
