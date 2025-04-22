package domain;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Geldialdia implements Serializable{
	
	@XmlID
	@Id
	@GeneratedValue
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	private Integer id;

	@XmlJavaTypeAdapter(IntegerAdapter.class)
	private Integer pos;
	
	@XmlIDREF
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private City hiria;
	
	@XmlIDREF
	@OneToOne(fetch=FetchType.EAGER)
	private Ride bidaia;

	private Boolean helmugaDa;
	
	public Geldialdia() {
		
	}
	
	public Geldialdia(int pos, City hiria, Ride bidaia, Boolean helmugaDa) {
		this.pos=pos;
		this.hiria=hiria;
		this.bidaia=bidaia;
		this.helmugaDa=helmugaDa;
	}

	public String getCityName(){
		return this.hiria.getName();
	}
}
