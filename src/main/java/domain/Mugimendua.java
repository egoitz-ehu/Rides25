package domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
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
public class Mugimendua implements Serializable{
	@XmlID
	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer id;
	private Date data;
	private double kopurua;
	@Enumerated(EnumType.STRING)
	private MugimenduMota type;
	@XmlIDREF
	@OneToOne
	private User user;
	
	public Mugimendua(double kopurua, MugimenduMota type, User u) {
		this.kopurua=kopurua;
		this.type=type;
		this.user=u;
		this.data= new Date();
	}
	
	public Mugimendua() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public double getKopurua() {
		return kopurua;
	}

	public void setKopurua(double kopurua) {
		this.kopurua = kopurua;
	}

	public MugimenduMota getType() {
		return type;
	}

	public void setType(MugimenduMota type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
