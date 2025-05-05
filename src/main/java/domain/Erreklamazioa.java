package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public class Erreklamazioa implements Serializable{
	@XmlID
    @Id
    @GeneratedValue
    @XmlJavaTypeAdapter(IntegerAdapter.class)
    private Integer id;
	
	@Enumerated(EnumType.STRING)
	private ErreklamazioaEgoera egoera;
	 
	 private String gaia;
	 
	 private Date date;
	 
	 @OneToOne
	 private User nork;
	 
	 @OneToOne
	 private User nori;
	 
	 @ElementCollection(fetch = FetchType.EAGER)
	 private List<String> norkMezuak = new Vector<>();
	 @ElementCollection(fetch = FetchType.EAGER)
	 private List<String> noriMezuak = new Vector<>();
	 @ElementCollection(fetch = FetchType.EAGER)
	 private List<String> adminMezuak = new Vector<>();
	 
	 public Erreklamazioa() {
		 super();
	 }
	 
	 public Erreklamazioa(User nork, User nori, String gaia) {
		 this.nori=nori;
		 this.nork=nork;
		 this.gaia=gaia;
		 this.date=new Date();
		 this.egoera=ErreklamazioaEgoera.BURUTZEN;
	 }
	 
	 @Override
	 public String toString() {
		 return id + ";" + nork.getName() + ";" + nori.getName();
	 }
}
