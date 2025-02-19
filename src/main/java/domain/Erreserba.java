package domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Entity
public class Erreserba {
	@Id
	@GeneratedValue
	private int eskaeraNum;
	private int plazaKop;
	private boolean egoera;
	private Date erreserbaData;
	@ManyToOne(fetch=FetchType.EAGER)
	private Traveler bidaiaria;
	@ManyToOne(fetch=FetchType.EAGER)
	private Ride ride;
	
	public Erreserba(int kop, Traveler bidaiaria, Ride ride) {
		this.plazaKop=kop;
		this.bidaiaria=bidaiaria;
		this.ride=ride;
		this.erreserbaData= new Date();
		this.egoera=false;
	}
}
