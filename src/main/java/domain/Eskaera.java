package domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Eskaera {
	@Id
	@GeneratedValue
	private int eskaeraNum;
	private int plazaKop;
	private boolean egoera;
	private Date eskaeraData;
	
	public Eskaera(int kop, Date data) {
		this.plazaKop=kop;
		this.eskaeraData=data;
		this.egoera=false;
	}
}
