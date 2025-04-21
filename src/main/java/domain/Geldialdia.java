package domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Geldialdia {
	@XmlID
	@Id
	private int pos;
	
	@XmlIDREF
	@OneToOne(fetch=FetchType.EAGER)
	private City hiria;
	
	@XmlIDREF
	@OneToOne(fetch=FetchType.EAGER)
	private Ride bidaia;
	
	public Geldialdia() {
		
	}
	
	public Geldialdia(int pos, City hiria, Ride bidaia) {
		this.pos=pos;
		this.hiria=hiria;
		this.bidaia=bidaia;
	}
}
