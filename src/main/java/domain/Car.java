package domain;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Car implements Serializable{
	@XmlID
	@Id
	private String matrikula;
	private int eserKop;
	private String kolorea;
	private String modeloa;
	
	@XmlIDREF
	@OneToOne(fetch=FetchType.EAGER)
	private Driver jabea;
	
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER)
	private List<Ride> rideList=new LinkedList<Ride>();
	
	public Car(String matrikula, int eserKop, String kolorea, String modeloa, Driver jabea) {
		this.matrikula=matrikula;
		this.eserKop=eserKop;
		this.kolorea=kolorea;
		this.modeloa=modeloa;
		this.jabea=jabea;

	}
	
	public Car() {
		super();
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
}
