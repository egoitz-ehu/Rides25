package domain;

import javax.persistence.Embeddable;
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
@Embeddable
public class Alerta {
	 @Enumerated(EnumType.STRING)
	 private AlertaEgoera amaituta;
	 
	 @XmlIDREF
	 @OneToOne
	 private Traveler traveler;
	 
	 @OneToOne
	 private City from;
	 
	 @OneToOne
	 private City to;
	 
	 public Alerta() {
		 super();
	 }
	 
	 public Alerta(Traveler traveler, City from, City to) {
		 this.amaituta=AlertaEgoera.ZAIN;
		 this.traveler=traveler;
		 this.from=from;
		 this.to=to;
	 }
}
