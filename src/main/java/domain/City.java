package domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class City {
	@XmlID
	@Id
	private String izena;
	
	public City() {
		super();
	}
	
	public City(String i) {
		this.izena = i;
	}
}
