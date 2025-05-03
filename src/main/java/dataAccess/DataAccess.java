package dataAccess;

import java.awt.Container;
import java.io.File;
import java.net.NoRouteToHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.*;
import exceptions.AlertaAlreadyExistsException;
import exceptions.BadagoRideException;
import exceptions.DiruaEzDaukaException;
import exceptions.ErreserbaAlreadyExistsException;
import exceptions.EserlekurikLibreEzException;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess  {
	private  EntityManager  db;
	private  EntityManagerFactory emf;


	ConfigXML c=ConfigXML.getInstance();

     public DataAccess()  {
		if (c.isDatabaseInitialized()) {
			String fileName=c.getDbFilename();

			File fileToDelete= new File(fileName);
			if(fileToDelete.delete()){
				File fileToDeleteTemp= new File(fileName+"$");
				fileToDeleteTemp.delete();

				  System.out.println("File deleted");
				} else {
				  System.out.println("Operation failed");
				}
		}
		open();
		if  (c.isDatabaseInitialized())initializeDB();
		
		System.out.println("DataAccess created => isDatabaseLocal: "+c.isDatabaseLocal()+" isDatabaseInitialized: "+c.isDatabaseInitialized());

		close();

	}
     
    public DataAccess(EntityManager db) {
    	this.db=db;
    }

	
	
	/**
	 * This is the data access method that initializes the database with some events and questions.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){
		
		db.getTransaction().begin();

		try {

		   Calendar today = Calendar.getInstance();
		   
		   int month=today.get(Calendar.MONTH);
		   int year=today.get(Calendar.YEAR);
		   if (month==12) { month=1; year+=1;}  
	    
		   
		    //Create drivers 
			Driver driver1=new Driver("driver1@gmail.com","abc123", "Ane", "Gaztañaga");
			//Car c1 = driver1.addCar("123", 4, "Green", "Seat");
			
			//Create rides
			//driver1.addRide("Donostia", "Bilbo", UtilDate.newDate(year,month,15), 4, 7,c1);
			//driver1.addRide("Donostia", "Gazteiz", UtilDate.newDate(year,month,6), 4, 8,c1);
			//driver1.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,25), 4, 4,c1);

			//driver1.addRide("Donostia", "Iruña", UtilDate.newDate(year,month,7), 4, 8,c1);
			
			//driver2.addRide("Donostia", "Bilbo", UtilDate.newDate(year,month,15), 3, 3);
			//driver2.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,25), 2, 5);
			//driver2.addRide("Eibar", "Gasteiz", UtilDate.newDate(year,month,6), 2, 5);

			//driver3.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,14), 1, 3);
			
			Driver driver2 = new Driver("1","1","1","1");
			Traveler traveler1 = new Traveler("2","2","2","2");

			
						
			db.persist(driver1);
			db.persist(driver2);
			db.persist(traveler1);

	
			db.getTransaction().commit();
			System.out.println("Db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean existInDB(User u) {
		TypedQuery<User> query = db.createQuery("SELECT u FROM User u WHERE u.email=?1", User.class);
		query.setParameter(1, u.getEmail());
		
		List<User> l = query.getResultList();
		return !l.isEmpty();
	}
	
	public boolean register(String email, String name, String surname, String password, String type) {
		User u=db.find(User.class, email);
		if (u==null) {
			User user;
			if(type.equals("driver")) {
				user = new Driver(email,password,name,surname);
			} else {
				user = new Traveler(email,password,name,surname);
			}
			db.getTransaction().begin();
			db.persist(user);
			db.getTransaction().commit();
			System.out.println("Erabiltzaile berri bat sortu da");
			return true;
		} else {
			System.out.println("Dagoeneko badago erabiltzaile bat email berdinarekin");
			return false;
		}
	}
	
	public User login(String email, String password) {
		TypedQuery<User> query = db.createQuery("SELECT u FROM User u WHERE u.email=?1 AND u.password=?2", User.class);
		query.setParameter(1, email);
		query.setParameter(2, password);
		List<User> l = query.getResultList();
		if(l.isEmpty()) return null;
		else return l.get(0);
	}
	
	public boolean diruaAtera(String email, double diruKop) {
		db.getTransaction().begin();
		User u = db.find(User.class, email);
		boolean b = u.diruaAtera(diruKop);
		if(b) {
			u.addMugimendua(diruKop,MugimenduMota.DIRU_IRTEERA);
			//db.merge(u);
			db.getTransaction().commit();
		}
		return b;
	}
	
	public boolean diruaSartu(String email, double kop) {

			db.getTransaction().begin();
			User u = db.find(User.class, email);
			boolean b =u.diruaSartu(kop);
			if(b) {
				u.addMugimendua(kop,MugimenduMota.DIRU_SARRERA);
				//db.merge(t);
				db.getTransaction().commit();
			}
		return b;
	}
	
	public boolean sortuErreserba(Traveler t, int rNumber, int kop, String from, String to) throws EserlekurikLibreEzException, ErreserbaAlreadyExistsException, DiruaEzDaukaException {
		if(kop>0) {
			db.getTransaction().begin();
			Ride r = db.find(Ride.class, rNumber);
			double kostua = r.prezioaKalkulatu(kop);
			Traveler tr = db.find(Traveler.class, t.getEmail());
			if(!tr.existBook(r)) {
				if(tr.diruaDauka(r, kop)) {
					if(r.eserlekuakLibre(kop)) {
						//Erreserba e = new Erreserba(kop,t, r);
						Erreserba erreserbaBerria = tr.sortuErreserba(r, kop, from, to);
						r.gehituErreserba(erreserbaBerria);
						tr.addMugimendua(kostua,MugimenduMota.ERRESERBA_SORTU);
						//db.persist(erreserbaBerria);
						db.persist(r);
						//db.persist(erreserbaBerria);
						//db.merge(t);
						db.getTransaction().commit();
						return true;	
					} else {
						throw new EserlekurikLibreEzException("Ez dago nahiko eserlekurik libre");
					}
				} else {
					throw new DiruaEzDaukaException("Ez dauka dirurik");
				}
			} else {
				throw new ErreserbaAlreadyExistsException("Dagoeneko erreserba bat du erabiltzaile honek bidaia honetan");
			}
		}
		return false;
	}
	
	public List<Integer> getAllRidesNumber(String  ema) {
		TypedQuery<Integer> query = db.createQuery("SELECT r.rideNumber FROM Ride r WHERE driver.email=?1", Integer.class);
		query.setParameter(1, ema);
		return query.getResultList();
	}
	
	public List<Ride> getDriverAllRides(String driverEmail) {
		TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r WHERE driver.email=?1",Ride.class);
		query.setParameter(1, driverEmail);
		return query.getResultList();
	}
	
	public List<Erreserba> lortuErreserbak(int rideNumber) {
		TypedQuery<Erreserba> query = db.createQuery("SELECT e FROM Erreserba e WHERE e.ride.rideNumber=?1 AND egoera=?2", Erreserba.class);
		query.setParameter(1, rideNumber);
		query.setParameter(2, ErreserbaEgoera.ZAIN);
		return query.getResultList();
	}
	
	public void erreserbaOnartu(int erreserbaNum, String dMail) {
		db.getTransaction().begin();
		Erreserba e = db.find(Erreserba.class, erreserbaNum);
		Traveler t = db.find(Traveler.class, e.getBidaiariaEmail());
		Driver d = db.find(Driver.class, dMail);
		double prezioa = e.prezioaKalkulatu();
		e.setEgoera(ErreserbaEgoera.ONARTUA);
		t.removeFrozenMoney(prezioa);
		d.addFrozenMoney(prezioa);
		d.addMugimendua(prezioa, MugimenduMota.ERRESERBA_ONARTU_GIDARI);
		t.addMugimendua(prezioa, MugimenduMota.ERRESERBA_ONARTU_BIDAIARI);
		db.getTransaction().commit(); 
	}
	
	public void erreserbaUkatu(int erreserbaNum, Ride r) {
		db.getTransaction().begin();
		Erreserba e = db.find(Erreserba.class, erreserbaNum);
		Traveler t = e.getBidaiaria();
		double kop = e.prezioaKalkulatu();
		int eserKop = e.getPlazaKop();
		e.setEgoera(ErreserbaEgoera.UKATUA);
		t.removeFrozenMoney(kop);
		t.diruaSartu(kop);
		r.itzuliEserlekuak(eserKop);
		t.addMugimendua(kop, MugimenduMota.ERRESERBA_UKATU);
		db.merge(r);
		db.getTransaction().commit();
	}
	
	public boolean sortuKotxea(String matrikula, int eserKop, String kolorea, String mota, Driver d) {
		Car kotxea = db.find(Car.class, matrikula);
		if(kotxea!=null) return false;
		db.getTransaction().begin();
		//Driver di = db.find(Driver.class, d.getEmail());
		Car c=d.addCar(matrikula,eserKop,kolorea,mota);
		db.persist(c);
		db.merge(d);
		db.getTransaction().commit();
		return true;
	}
	
	public List<Erreserba> erreserbakLortu(Traveler t) {
		TypedQuery<Erreserba> query = db.createQuery("SELECT e FROM Erreserba e WHERE e.bidaiaria=?1", Erreserba.class);
		query.setParameter(1, t);
		List<Erreserba> erreserbak = query.getResultList();
		return erreserbak;
	}
	
	public void erreserbaBaieztatu(RideErreserbaContainer c) {
		db.getTransaction().begin();
		Erreserba e = db.find(Erreserba.class, c.getErreserba().getEskaeraNum()); // Bestela arazoa eguneratzean web zerbitzu bat denean
		e.setEgoera(ErreserbaEgoera.BAIEZTATUA);
		Ride r = c.getRide();
		double prezioa = e.getPlazaKop()*r.getPrice();
		Driver d = db.find(Driver.class, c.getRide().getDriver().getEmail());
		d.removeFrozenMoney(prezioa);
		d.diruaSartu(prezioa);
		d.addMugimendua(prezioa, MugimenduMota.ERRESERBA_BAIEZTATU);
		db.merge(e);
		db.merge(r);
		db.getTransaction().commit();;
	}
	
	public void erreserbaEzeztatu(RideErreserbaContainer c, String tMail) {
		db.getTransaction().begin();
		Erreserba e = c.getErreserba();
		Ride r = c.getRide();
		e.setEgoera(ErreserbaEgoera.EZEZTATUA);
		double prezioa = e.getPlazaKop()*r.getPrice();
		Driver d = db.find(Driver.class, c.getRide().getDriver().getEmail());
		Traveler t = db.find(Traveler.class, tMail);
		d.removeFrozenMoney(prezioa);
		d.addMugimendua(prezioa, MugimenduMota.ERRESERBA_EZEZTATU_GIDARI);
		t.diruaSartu(prezioa);
		t.addMugimendua(prezioa, MugimenduMota.ERRESERBA_EZEZTATU_BIDAIARI);
		db.merge(e);
		db.merge(t);
		db.getTransaction().commit();
	}
	
	public void kantzelatuBidaia(Ride r, String dMail) {
		db.getTransaction().begin();
		List<Erreserba> erreserbaList = r.getErreserbak();
		r.setEgoera(RideEgoera.KANTZELATUTA);
		Driver  d = db.find(Driver.class, dMail);
		for(Erreserba err:erreserbaList) {
			Erreserba e = db.find(Erreserba.class, err.getEskaeraNum());
			ErreserbaEgoera egoera = e.getEgoera();
			e.setEgoera(ErreserbaEgoera.KANTZELATUA);
			e.setKantzelatuData(new Date());
			Traveler t = e.getBidaiaria();
			double prezioa = e.prezioaKalkulatu();
			if(egoera.equals(ErreserbaEgoera.ONARTUA)) {
				d.removeFrozenMoney(prezioa);
				t.diruaSartu(prezioa);
				d.addMugimendua(prezioa, MugimenduMota.BIDAIA_KANTZELATU_GIDARI);
				t.addMugimendua(prezioa, MugimenduMota.BIDAIA_KANTZELATU_BIDAIARI);
			} else if(egoera.equals(ErreserbaEgoera.ZAIN)) {
				t.removeFrozenMoney(prezioa);
				t.diruaSartu(prezioa);
				t.addMugimendua(prezioa, MugimenduMota.BIDAIA_KANTZELATU_BIDAIARI);
			}
		}
		db.merge(r);
		db.persist(d);
		db.getTransaction().commit();
	}
	
	public List<RideErreserbaContainer> getRideErreserbaContainers(Traveler t) {
		List<RideErreserbaContainer> containerList = new LinkedList<RideErreserbaContainer>();
		TypedQuery<Erreserba> query = db.createQuery("SELECT e FROM Erreserba e WHERE bidaiaria=?1", Erreserba.class);
		query.setParameter(1, t);
		List<Erreserba> erreserbaList = query.getResultList();
		for(Erreserba e: erreserbaList) {
			containerList.add(new RideErreserbaContainer(e.getRide(),e));
		}
		return containerList;
	}
	
	public List<TravelerErreserbaContainer> getErresebraTravelerContainers(Ride r){
		List<TravelerErreserbaContainer> containerList = new LinkedList<TravelerErreserbaContainer>();
		TypedQuery<Erreserba> query = db.createQuery("SELECT e FROM Erreserba e WHERE ride=?1",Erreserba.class);
		query.setParameter(1, r);
		List<Erreserba> erreserbaList = query.getResultList();
		for(Erreserba e:erreserbaList) {
			containerList.add(new TravelerErreserbaContainer(e,e.getBidaiaria()));
		}
		return containerList;
	}
	
	public List<Car> getDriverCars(String dMail){
		TypedQuery<Car> query = db.createQuery("SELECT c FROM Car c WHERE jabea.email=?1",Car.class);
		query.setParameter(1, dMail);
		return query.getResultList();
	}
	
	public List<Ride> getRidesDriver(Driver d){
		TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r WHERE driver=?1",Ride.class);
		query.setParameter(1, d);
		return query.getResultList();
	}
	
	public double getUserMoney(String email) {
		TypedQuery<Double> query = db.createQuery("SELECT u.cash FROM User u WHERE email=?1", Double.class);
		query.setParameter(1, email);
		List<Double> res = query.getResultList();
		return res.get(0);
	}
	
	public double getUserFrozenMoney(String email) {
		TypedQuery<Double> query = db.createQuery("SELECT u.frozenMoney FROM User u WHERE email=?1", Double.class);
		query.setParameter(1, email);
		List<Double> res = query.getResultList();
		return res.get(0);
	}
	
	public List<Mugimendua> getUserMovements(String email) {
		TypedQuery<Mugimendua> query = db.createQuery("SELECT m FROM Mugimendua m WHERE user.email=?1", Mugimendua.class);
		query.setParameter(1, email);
		return query.getResultList();
	}

	public List<String> getStopCitiesNames(){
		TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r",Ride.class);
		List<Ride> rides = query.getResultList();
		List<String> cityList = new LinkedList<String>();
		for(Ride r:rides) {
			List<String> geldialdiak = r.getGeldialdiak();
			for(String g:geldialdiak) {
				if(!geldialdiak.get(geldialdiak.size()-1).equals(g) && !cityList.contains(g)) {
					cityList.add(g);
				}
			}
		}
		return cityList;
	}
	
	/**
	 * This method returns all the cities where rides depart 
	 * @return collection of cities
	 */
	public List<String> getDepartCities(){
			TypedQuery<String> query = db.createQuery("SELECT DISTINCT r.from FROM Ride r ORDER BY r.from", String.class);
			List<String> cities = query.getResultList();
			return cities;
		
	}
	/**
	 * This method returns all the arrival destinations, from all rides that depart from a given city  
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	public List<String> getArrivalCities(String from){
		TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r",Ride.class);
		List<Ride> rides = query.getResultList();
		List<String> cities = new LinkedList<String>();
		for(Ride r:rides) {
			List<String> geldialdiak = r.getGeldialdiak();
			if(geldialdiak.contains(from)) {
				int i = geldialdiak.indexOf(from);
				for(int j=i+1;j<geldialdiak.size();j++) {
					cities.add(geldialdiak.get(j));
				}
			}
		}
		return cities;
	}

	public Ride createRide(List<String> hiriakLista,Date date, Car c, float price, String driverEmail) throws  RideAlreadyExistException, RideMustBeLaterThanTodayException {
		//System.out.println(">> DataAccess: createRide=> from= "+from+" to= "+to+" driver="+driverEmail+" date "+date);
		try {
			if(new Date().compareTo(date)>0) {
				throw new RideMustBeLaterThanTodayException(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorRideMustBeLaterThanToday"));
			}
			db.getTransaction().begin();
			
			Driver driver = db.find(Driver.class, driverEmail);
			Car ci = db.find(Car.class, c.getMatrikula());

			if (driver.doesRideExists(hiriakLista, date)) {
				db.getTransaction().commit();
				throw new RideAlreadyExistException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.RideAlreadyExist"));
			}
			Ride ride = driver.addRide(hiriakLista, date,ci.getEserKop(), price,ci);

			//next instruction can be obviated
			//db.persist(driver);
			ci.gehituBidaia(ride);
			db.persist(ci);
			db.getTransaction().commit();

			return ride;
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			db.getTransaction().commit();
			return null;
		}
		
		
	}
	
	/**
	 * This method retrieves the rides from two locations on a given date 
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @return collection of rides
	 */
	public List<Ride> getRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getRides=> from= "+from+" to= "+to+" date "+date);

		List<Ride> res = new ArrayList<>();	
		TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r WHERE r.date = ?1",Ride.class);   
		query.setParameter(1, date);
		List<Ride> rides = query.getResultList();
		List<Ride> filtradas = rides.stream()
			    .filter(r -> {
			        List<String> g = r.getGeldialdiak();
			        int i1 = g.indexOf(from);
			        int i2 = g.indexOf(to);
			        return i1 != -1 && i2 != -1 && i1 < i2;
			    })
			    .collect(Collectors.toList());
	 	return filtradas;
	}
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride 
	 * @param date of the month for which days with rides want to be retrieved 
	 * @return collection of rides
	 */
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		List<Date> res = new ArrayList<>();	
		
		Date firstDayMonthDate= UtilDate.firstDayMonth(date);
		Date lastDayMonthDate= UtilDate.lastDayMonth(date);
				
		
		TypedQuery<Ride> query = db.createQuery(
			    "SELECT r FROM Ride r " +
			    "WHERE r.egoera = :egoera AND r.date BETWEEN :first AND :last AND r.eserLibre<>0", Ride.class);
		query.setParameter("egoera", RideEgoera.MARTXAN);
		query.setParameter("first", firstDayMonthDate);
		query.setParameter("last", lastDayMonthDate);

		List<Ride> rides = query.getResultList();
		for (Ride r : rides) {
			List<String> stops = r.getGeldialdiak();
		    int i1 = stops.indexOf(from);
		    int i2 = stops.indexOf(to);
		    if (i1 != -1 && i2 != -1 && i1 <= i2) {
		    	res.add(r.getDate());
		    }
		}
	 	return res;
	}
	

	public void open(){
		
		String fileName=c.getDbFilename();
		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);
			  db = emf.createEntityManager();
    	   }
		System.out.println("DataAccess opened => isDatabaseLocal: "+c.isDatabaseLocal());

		
	}

	public void close(){
		db.close();
		System.out.println("DataAcess closed");
	}
	
	public List<TravelerErreserbaContainer> lortuBalorazioErreserbak(User u){
		List<Erreserba> erreserbak;
		if(u instanceof Traveler) {
			TypedQuery<Erreserba> q1 = db.createQuery(
				    "SELECT e FROM Erreserba e WHERE e.bidaiaria = :u AND (e.egoera=:j OR e.egoera=:k)", Erreserba.class);
			q1.setParameter("u", u);
			q1.setParameter("j", ErreserbaEgoera.BAIEZTATUA);
			q1.setParameter("k", ErreserbaEgoera.EZEZTATUA);
			erreserbak = q1.getResultList();

		} else {
			TypedQuery<Erreserba> q1 = db.createQuery("SELECT e FROM Erreserba e JOIN e.ride r WHERE r.driver=:u AND (e.egoera=:j OR e.egoera=:k)",Erreserba.class);
			q1.setParameter("u", u);
			q1.setParameter("j", ErreserbaEgoera.BAIEZTATUA);
			q1.setParameter("k", ErreserbaEgoera.EZEZTATUA);
			erreserbak = q1.getResultList();
		}
		TypedQuery<Erreserba> q2 = db.createQuery(
				   "SELECT b.erreserba FROM Balorazioa b WHERE b.nork = :u", Erreserba.class);
		q2.setParameter("u", u);
		List<Erreserba> baloratuak = q2.getResultList();
		List<Erreserba> gabe = new ArrayList<>();
		for (Erreserba e : erreserbak) {
		    if (!baloratuak.contains(e)) {
		        gabe.add(e);
		    }
		}
		List<TravelerErreserbaContainer> containerList = new LinkedList<TravelerErreserbaContainer>();
		for(Erreserba e:gabe) {
			containerList.add(new TravelerErreserbaContainer(e,e.getBidaiaria()));
		}
		return containerList;
	}
	
	public void sortuBalorazioa(String eMail, int eNum, int puntuzioa, String mezua) {
		db.getTransaction().begin();
		User u;
		User e = db.find(User.class, eMail);
		Erreserba er = db.find(Erreserba.class, eNum);
		if(e instanceof Traveler) {
			TypedQuery<User> query = db.createQuery("SELECT r.driver FROM Ride r WHERE :er MEMBER OF r.erreserbak", User.class);
			query.setParameter("er", er);
			u = query.getSingleResult();
		} else {
			u = er.getBidaiaria();
		}
		Balorazioa ba = e.sortuBalorazioa(er, puntuzioa, mezua, u);
		u.gehituJasotakoBalorazioa(ba);
		er.gehituBalorazioa(ba);
		db.persist(e);
		db.getTransaction().commit();
		System.out.println("Balorazioa sortu da");
	}
	
	public List<Balorazioa> lortuBaloraizoak(String email){
		TypedQuery<Balorazioa> query = db.createQuery("SELECT b FROM Balorazioa b WHERE b.nori.email=?1", Balorazioa.class);
		query.setParameter(1, email);
		return query.getResultList();
	}
	
	public void sortuAlerta(String email, String from, String to, Date d) throws BadagoRideException, ErreserbaAlreadyExistsException, AlertaAlreadyExistsException {
		db.getTransaction().begin();
		Traveler t = db.find(Traveler.class, email);
		if(!t.baduAlertaBerdina(from, to, d)) {
			if(!t.baduErreserba(from, to, d)) {
				if(getThisMonthDatesWithRides(from,to,d).isEmpty()) {
					t.sortuAlerta(from, to, d);
				} else {
					db.getTransaction().commit();
					throw new BadagoRideException();
				}
			} else {
				db.getTransaction().commit();
				throw new ErreserbaAlreadyExistsException();
			}
		} else {
			db.getTransaction().commit();
			throw new AlertaAlreadyExistsException();
		}
		db.getTransaction().commit();
	}
	
	public List<Alerta> lortuAlertak(String email){
		TypedQuery<Alerta> query = db.createQuery("SELECT a FROM Alerta a WHERE a.traveler.email=?1",Alerta.class);
		query.setParameter(1, email);
		return query.getResultList();
	}
	
	public void ezabatuAlerta(int id) {
		db.getTransaction().begin();
		Alerta a = db.find(Alerta.class, id);
		Traveler t = a.getTraveler();
		t.kenduAlerta(a);
		db.remove(a);
		db.getTransaction().commit();;
		System.out.println("Alerta ezabatu da");
	}
	
}
