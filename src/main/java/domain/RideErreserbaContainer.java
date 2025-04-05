package domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class RideErreserbaContainer implements Serializable{
	private Ride ride;
	private Erreserba erreserba;
	
	public RideErreserbaContainer() {
		super();
	}
	
	public RideErreserbaContainer(Ride r, Erreserba e) {
		this.ride=r;
		this.erreserba=e;
	}

	public Ride getRide() {
		return ride;
	}

	public void setRide(Ride ride) {
		this.ride = ride;
	}

	public Erreserba getErreserba() {
		return erreserba;
	}

	public void setErreserba(Erreserba erreserba) {
		this.erreserba = erreserba;
	}
}
