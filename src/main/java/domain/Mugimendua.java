package domain;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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
}
