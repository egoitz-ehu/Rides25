package domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Balorazioa implements Serializable {
    @XmlID
    @Id
    @GeneratedValue
    @XmlJavaTypeAdapter(IntegerAdapter.class)
    private Integer id;
    
    private int puntuazioa;

    private String mezua;

    private Date ezarritakoData;

    @OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
    private User nori;

    @OneToOne(fetch=FetchType.EAGER)
    private User nork;

    @OneToOne(cascade=CascadeType.PERSIST)
    private Erreserba erreserba;
    
    public Balorazioa(User nork, User nori, Erreserba er, int puntuazioa, String mezua) {
    	this.nork=nork;
    	this.nori=nori;
    	this.erreserba=er;
    	this.puntuazioa=puntuazioa;
    	this.mezua=mezua;
    	this.ezarritakoData=new Date();
    }
}
