package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Geldialdia {
	@XmlID
    @Id
    @GeneratedValue
    @XmlJavaTypeAdapter(IntegerAdapter.class)
    private Integer id;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public double getPrezioa() {
		return prezioa;
	}

	public void setPrezioa(double prezioa) {
		this.prezioa = prezioa;
	}

	public boolean isHelmugaDa() {
		return helmugaDa;
	}

	public void setHelmugaDa(boolean helmugaDa) {
		this.helmugaDa = helmugaDa;
	}

	public String getHiria() {
		return hiria;
	}

	public void setHiria(String hiria) {
		this.hiria = hiria;
	}

	private int pos;
	
	private double prezioa;
	
	private boolean helmugaDa;
	
	private String hiria;
	
	public Geldialdia() {
		super();
	}
	
	public Geldialdia(int pos, double prezioa, boolean helmugaDa, String hiria) {
		this.pos=pos;
		this.prezioa=prezioa;
		this.helmugaDa=helmugaDa;
		this.hiria=hiria;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o==null) return false;
		if(!(o instanceof Geldialdia)) return false;
		Geldialdia g = (Geldialdia) o;
		return g.hiria.equals(this.hiria);
	}
}
