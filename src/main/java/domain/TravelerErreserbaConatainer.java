package domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class TravelerErreserbaConatainer implements Serializable{
	private Erreserba erreserba;
	private Traveler traveler;
	
	public TravelerErreserbaConatainer() {
		super();
	}
	
	public TravelerErreserbaConatainer(Erreserba e, Traveler t) {
		this.erreserba=e;
		this.traveler=t;
	}

	public Erreserba getErreserba() {
		return erreserba;
	}

	public void setErreserba(Erreserba erreserba) {
		this.erreserba = erreserba;
	}

	public Traveler getTraveler() {
		return traveler;
	}

	public void setTraveler(Traveler traveler) {
		this.traveler = traveler;
	}
}
