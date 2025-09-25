package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Erreserba implements Serializable{
	@XmlID
	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer eskaeraNum;
	private int plazaKop;
	
	@Enumerated(EnumType.STRING)
	private ErreserbaEgoera egoera;
	private Date kantzelatuData;
	
	public Date getKantzelatuData() {
		return kantzelatuData;
	}

	public double getPrezioa() {
		return prezioa;
	}

	public void setPrezioa(double prezioa) {
		this.prezioa = prezioa;
	}

	public void setKantzelatuData(Date kantzelatuData) {
		this.kantzelatuData = kantzelatuData;
	}

	private Date erreserbaData;
	@XmlIDREF
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	private Traveler bidaiaria;
	@XmlIDREF
	@OneToOne(fetch=FetchType.EAGER)
	private Ride ride;

	@XmlIDREF
	@OneToMany
	private List<Balorazioa> balorazioak = new Vector<Balorazioa>();
	
	private String from;
	
	private String to;
	
	private double prezioa;
	
	
	public ErreserbaEgoera getEgoera() {
		return egoera;
	}

	public void setEgoera(ErreserbaEgoera egoera) {
		this.egoera = egoera;
	}

	public int getEskaeraNum() {
		return eskaeraNum;
	}

	public void setEskaeraNum(int eskaeraNum) {
		this.eskaeraNum = eskaeraNum;
	}

	public int getPlazaKop() {
		return plazaKop;
	}

	public void setPlazaKop(int plazaKop) {
		this.plazaKop = plazaKop;
	}

	public Date getErreserbaData() {
		return erreserbaData;
	}

	public void setErreserbaData(Date erreserbaData) {
		this.erreserbaData = erreserbaData;
	}

	public Traveler getBidaiaria() {
		return bidaiaria;
	}
	
	public String getBidaiariaEmail() {
		return this.bidaiaria.getEmail();
	}

	public void setBidaiaria(Traveler bidaiaria) {
		this.bidaiaria = bidaiaria;
	}

	public Ride getRide() {
		return ride;
	}

	public void setRide(Ride ride) {
		this.ride = ride;
	}

	public Erreserba(int kop, Traveler bidaiaria, Ride ride, String from, String to, double prezioa) {
		this.plazaKop=kop;
		this.bidaiaria=bidaiaria;
		this.ride=ride;
		this.erreserbaData= new Date();
		this.egoera=ErreserbaEgoera.ZAIN;
		this.from=from;
		this.to=to;
		this.prezioa=prezioa;
	}
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Erreserba() {
		super();
	}
	
	public boolean containsRide(Ride r) {
		return (this.ride.equals(r));
	}
	
	@Override
	public String toString() {
		return eskaeraNum + " " + plazaKop + " " + egoera + " " + erreserbaData + " " + bidaiaria.getEmail();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Erreserba other = (Erreserba) obj;
		return this.eskaeraNum==other.eskaeraNum;

	}
	
	public void gehituBalorazioa(Balorazioa ba) {
		this.balorazioak.add(ba);
	}
	
	public Date getRideDate() {
		return this.ride.getDate();
	}
}
