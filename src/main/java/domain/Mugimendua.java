package domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Mugimendua {
	@Id
	@GeneratedValue
	private int id;
	private Date data;
	private double kopurua;
	@Enumerated(EnumType.STRING)
	private MugimenduMota type;
	@OneToOne
	private User user;
	
	public Mugimendua(double kopurua, MugimenduMota type, User u) {
		this.kopurua=kopurua;
		this.type=type;
		this.user=u;
		this.data= new Date();
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
