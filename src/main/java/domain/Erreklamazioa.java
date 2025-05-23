package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Erreklamazioa implements Serializable{
	@XmlID
    @Id
    @GeneratedValue
    @XmlJavaTypeAdapter(IntegerAdapter.class)
    private Integer id;
	
	@Enumerated(EnumType.STRING)
	private ErreklamazioaEgoera egoera;
	 
	 public List<Mezua> getMezuList() {
		return mezuList;
	}

	public ErreklamazioaEgoera getEgoera() {
		return egoera;
	}

	public void setEgoera(ErreklamazioaEgoera egoera) {
		this.egoera = egoera;
	}

	public String getGaia() {
		return gaia;
	}

	public void setGaia(String gaia) {
		this.gaia = gaia;
	}

	public double getKopurua() {
		return kopurua;
	}

	public void setKopurua(double kopurua) {
		this.kopurua = kopurua;
	}

	public void setMezuList(List<Mezua> mezuList) {
		this.mezuList = mezuList;
	}

	public User getNork() {
		return nork;
	}

	public void setNork(User nork) {
		this.nork = nork;
	}

	public User getNori() {
		return nori;
	}

	public void setNori(User nori) {
		this.nori = nori;
	}

	private String gaia;
	 
	 private Date date;
	 
	 private double kopurua;
	 
	 @OneToOne
	 private User nork;
	 
	 @OneToOne
	 private User nori;
	 
	 @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	 private List<Mezua> mezuList = new Vector<Mezua>();
	 
	 public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Erreklamazioa() {
		 super();
	 }
	 
	 public Erreklamazioa(User nork, User nori, String gaia, double kopurua) {
		 this.nori=nori;
		 this.nork=nork;
		 this.gaia=gaia;
		 this.date=new Date();
		 this.egoera=ErreklamazioaEgoera.BURUTZEN;
		 this.kopurua=kopurua;
	 }
	 
	 @Override
	 public String toString() {
		 return id + ";" + nork.getName() + ";" + nori.getName();
	 }
	 
	 public Mezua sortuMezua(String text, User u) {
		 Mezua m = new Mezua(text,u,this);
		 gehituMezua(m);
		 return m;
	 }
	 
	 public void gehituMezua(Mezua m) {
		 this.mezuList.add(m);
	 }
}
