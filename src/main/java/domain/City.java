package domain;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class City implements Serializable{
	@XmlID
	@Id
	private String name;
	
	@OneToMany(fetch=FetchType.EAGER)
	private List<Geldialdia> geldialdiLista=new Vector<Geldialdia>();
	
	public City() {
		
	}
	
	public City(String name) {
		this.name=name;
	}
	
	public void addGeldialdia(Geldialdia g) {
		this.geldialdiLista.add(g);
	}

	public String getName(){
		return name;
	}
}
