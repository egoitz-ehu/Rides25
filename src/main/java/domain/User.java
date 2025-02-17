package domain;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class User {
	@Id
	private String email;
	private String password;
	private String name;
	private String surname;
	private double cash;

	public User(String email, String pass, String name, String surname) {
		this.email = email;
		this.password = pass;
		this.name = name;
		this.surname = surname;
		this.cash = 0;
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
	public void setCash(int cash) {
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
}
