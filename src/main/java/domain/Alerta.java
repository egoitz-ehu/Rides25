package domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Alerta implements Serializable{
	@XmlID
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer id;
	
	 @Enumerated(EnumType.STRING)
	 private AlertaEgoera egoera;
	 
	 @XmlIDREF
	 @OneToOne
	 private Traveler traveler;
	 
	 private String from;
	 
	 private String to;
	 
	 private Date date;
	 
	 public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AlertaEgoera getEgoera() {
		return egoera;
	}

	public void setEgoera(AlertaEgoera amaituta) {
		this.egoera = amaituta;
	}

	public Traveler getTraveler() {
		return traveler;
	}

	public void setTraveler(Traveler traveler) {
		this.traveler = traveler;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Alerta() {
		 super();
	 }
	 
	 public Alerta(Traveler traveler, String from, String to, Date d) {
		 this.egoera=AlertaEgoera.ZAIN;
		 this.traveler=traveler;
		 this.from=from;
		 this.to=to;
		 this.date=d;
	 }
}
