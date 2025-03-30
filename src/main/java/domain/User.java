package domain;

import java.util.List;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

@MappedSuperclass
public abstract class User {
	@Id
	private String email;
	private String password;
	private String name;
	private String surname;
	private double cash;
	private double frozenMoney;
	@OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.MERGE)
	private List<Mugimendua> mugimenduList = new Vector<Mugimendua>();

	public User(String email, String pass, String name, String surname) {
		this.email = email;
		this.password = pass;
		this.name = name;
		this.surname = surname;
		this.cash = 0;
		this.setFrozenMoney(0);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public double getCash() {
		return cash;
	}
	public void setCash(double cash) {
		this.cash = cash;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String pass) {
		this.password = pass;
	}
	
	public String toString(){
		return email+";"+name+";"+"surname";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Driver other = (Driver) obj;
		if (getEmail() != other.getEmail())
			return false;
		return true;
	}
	
	public boolean diruaAtera(double kop) {
		if(this.cash-kop>=0.0) {
			this.cash=this.cash-kop;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean diruaSartu(double kop) {
		if(kop>=0.0) {
			this.cash=this.cash+kop;
			return true;
		} else {
			return false;
		}
	}

	public double getFrozenMoney() {
		return frozenMoney;
	}

	public void setFrozenMoney(double frozenMoney) {
		this.frozenMoney = frozenMoney;
	}
	
	public void addFrozenMoney(double kop) {
		if(kop>0.0) this.frozenMoney+=kop;
	}
	
	public void removeFrozenMoney(double kop) {
		if(kop>0.0) this.frozenMoney-=kop;
	}
	
	public List<Mugimendua> getMugimenduak(){
		return this.mugimenduList;
	}
	
	public void addMugimendua(double kop, MugimenduMota mota) {
		Mugimendua m = new Mugimendua(kop,mota,this);
		this.mugimenduList.add(m);
	}
}
