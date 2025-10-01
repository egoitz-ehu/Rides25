package domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Admin  extends User implements Serializable{
	
	public Admin (String email, String pass, String name, String surname) {
		super(email, pass, name, surname);
	}
	
	public Admin() {
		super();
	}
}
