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

    @OneToOne(fetch=FetchType.EAGER)
    private User nori;

    @OneToOne(fetch=FetchType.EAGER)
    private User nork;

    @OneToOne
    private Erreserba erreserba;
}
