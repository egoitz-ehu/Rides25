package domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class Mezua {
	@XmlID
	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer id;
	
	private String text;
	
	private Date data;
	
	@OneToOne(fetch=FetchType.EAGER)
	private User nork;
	
	@XmlIDREF
	@OneToOne
	private Erreklamazioa erreklamazioa;
	
	public Mezua() {
		super();
	}
	
	public Mezua(String text, User nork, Erreklamazioa e) {
		this.text=text;
		this.data=new Date();
		this.nork=nork;
		this.erreklamazioa=e;
	}
}
