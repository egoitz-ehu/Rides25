package data_access;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import exceptions.DagoenekoOnartuaException;
import exceptions.DatuakNullException;
import exceptions.DiruaEzDaukaException;
import exceptions.ErreserbaAlreadyExistsException;
import exceptions.ErreserbaEgoeraEzDaZainException;
import exceptions.EserlekurikLibreEzException;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess  {
	private  EntityManager  db;

	ConfigXML c=ConfigXML.getInstance();

	public DataAccess() throws IOException  {
	    // ObjectDB 10 entitate baino gehiago
		// System.setProperty("objectdb.conf", "lib//objectdb.conf");
	    if (c.isDatabaseInitialized()) {
	        String fileName = c.getDbFilename();
	        Path fileToDelete = Paths.get(fileName);
	        Path fileToDeleteTemp = Paths.get(fileName + "$");

	        Files.deleteIfExists(fileToDelete);
	        Files.deleteIfExists(fileToDeleteTemp);

	        System.out.println("File deleted");
	    }
	    open();
	    if (c.isDatabaseInitialized()) initializeDB();

	    System.out.println("DataAccess created => isDatabaseLocal: " + c.isDatabaseLocal() + " isDatabaseInitialized: " + c.isDatabaseInitialized());

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
			Driver driver2 = new Driver("1","1","1","1");
			
			//Create traveler
			Traveler traveler1 = new Traveler("2","2","2","2");
			Traveler traveler2 = new Traveler("3","3","3","3");
			
			//Create Car
			driver2.addCar("1234ABC", 5, "urdina", "Audi");
			driver2.addCar("1234DEF", 3, "gorria", "Audi");
			
			String hiriIzena = "Bilbo";
			
			List<String> hiriak = new ArrayList<String>();
			hiriak.add(hiriIzena);
			hiriak.add("Donostia");
			List<Double> prezioak = new ArrayList<Double>();
			prezioak.add(10.0);
			prezioak.add(0.0);
			Date data = new Date();
			Calendar cal = Calendar.getInstance();
			cal.set(2025, 5, 20);
			data = cal.getTime();
			Ride r = driver2.addRide(hiriak, prezioak, UtilDate.trim(data), driver2.getCars().get(0).getEserKop(), driver2.getCars().get(0));
			driver2.getCars().get(0).gehituBidaia(r);
			
			traveler1.setCash(100);
			Erreserba e = traveler1.sortuErreserba(r, 5, hiriIzena, "Donostia", r.prezioaKalkulatu(hiriIzena, "Donostia"));
			
			r.gehituErreserba(e);
			
			db.persist(driver1);
			db.persist(driver2);
			db.persist(traveler1);
			db.persist(traveler2);
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
	
	public boolean sortuErreserba(Traveler t, int rNumber, int kop, String from, String to) throws EserlekurikLibreEzException, ErreserbaAlreadyExistsException,
	DiruaEzDaukaException, DatuakNullException {
		if(kop>0) {
			db.getTransaction().begin();
			Ride r = db.find(Ride.class, rNumber);
			Traveler tr = db.find(Traveler.class, t.getEmail());
			if(r==null || tr==null) {
				db.getTransaction().commit();
				throw new DatuakNullException("Datuak null dira");
			}
			double kostua = r.prezioaKalkulatu(from, to);
			if(!tr.existBook(r)) {
				if(tr.diruaDauka(kostua)) {
					if(r.eserlekuakLibre(kop)) {
						Erreserba erreserbaBerria = tr.sortuErreserba(r, kop, from, to, kostua);
						r.gehituErreserba(erreserbaBerria);
						tr.addMugimendua(kostua,MugimenduMota.ERRESERBA_SORTU);
						db.persist(r);
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
	
	public void erreserbaOnartu(int erreserbaNum, String dMail) throws DatuakNullException,DagoenekoOnartuaException {
		db.getTransaction().begin();
		Erreserba e = db.find(Erreserba.class, erreserbaNum);
		if(e==null) {
			db.getTransaction().commit();
			throw new DatuakNullException("Datuak null dira");
		}
		Traveler t = db.find(Traveler.class, e.getBidaiariaEmail());
		if(e.getEgoera()!=ErreserbaEgoera.ZAIN){
			db.getTransaction().commit();
			throw new DagoenekoOnartuaException("Dagoeneko onartua dago");
		}
		Driver d = db.find(Driver.class, dMail);
		if(d==null) {
			db.getTransaction().commit();
			throw new DatuakNullException("Datuak null dira");
		}
		double prezioa = e.getPrezioa();
		e.setEgoera(ErreserbaEgoera.ONARTUA);
		t.removeFrozenMoney(prezioa);
		d.addFrozenMoney(prezioa);
		d.addMugimendua(prezioa, MugimenduMota.ERRESERBA_ONARTU_GIDARI);
		t.addMugimendua(prezioa, MugimenduMota.ERRESERBA_ONARTU_BIDAIARI);
		db.getTransaction().commit(); 
	}
	
	public void erreserbaUkatu(int erreserbaNum, int rNumber) throws DatuakNullException, ErreserbaEgoeraEzDaZainException {
	    db.getTransaction().begin();
	    Erreserba e = db.find(Erreserba.class, erreserbaNum);
	    if(e == null) {
	        db.getTransaction().commit();
	        throw new DatuakNullException("Erreserba null da");
	    }
	    if(e.getEgoera() != ErreserbaEgoera.ZAIN) {
	        db.getTransaction().commit();
	        throw new ErreserbaEgoeraEzDaZainException();
	    }
	    Traveler t = db.find(Traveler.class, e.getBidaiariaEmail());
	    if(t == null) {
	        db.getTransaction().commit();
	        throw new DatuakNullException("Bidaiaria null da");
	    }
	    Ride r = db.find(Ride.class, rNumber);
	    if(r == null) {
	        db.getTransaction().commit();
	        throw new DatuakNullException("Ride null da");
	    }
	    double kop = e.getPrezioa();
	    int eserKop = e.getPlazaKop();

	    e.setEgoera(ErreserbaEgoera.UKATUA);
	    t.removeFrozenMoney(kop);
	    t.diruaSartu(kop);
	    r.itzuliEserlekuak(eserKop);
	    t.addMugimendua(kop, MugimenduMota.ERRESERBA_UKATU);

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
		double prezioa = e.getPrezioa();
		Driver d = db.find(Driver.class, c.getRide().getDriver().getEmail());
		d.removeFrozenMoney(prezioa);
		d.diruaSartu(prezioa);
		d.addMugimendua(prezioa, MugimenduMota.ERRESERBA_BAIEZTATU);
		db.getTransaction().commit();;
	}
	
	public void erreserbaEzeztatu(RideErreserbaContainer c, String tMail) {
		db.getTransaction().begin();
		Erreserba e = db.find(Erreserba.class,c.getErreserba().getEskaeraNum());
		e.setEgoera(ErreserbaEgoera.EZEZTATUA);
		Driver d = db.find(Driver.class, c.getRide().getDriver().getEmail());
		Traveler t = db.find(Traveler.class, tMail);
		Erreklamazioa err = t.sortuErreklamazioa(d, "ERRESERBA EZEZTATU",e.getPrezioa());
		d.addJasotakoErreklamazioa(err);
		db.getTransaction().commit();
	}
	
	public void kantzelatuBidaia(Ride r, String dMail) {
		db.getTransaction().begin();
		List<Erreserba> erreserbaList = r.getErreserbak();
		r.setEgoera(RideEgoera.KANTZELATUTA);
		Driver  d = db.find(Driver.class, dMail);
		for(Erreserba err:erreserbaList) {
			kantzelatuErreserba(d, err);
		}
		db.merge(r);
		db.persist(d);
		db.getTransaction().commit();
	}

	private void kantzelatuErreserba(Driver d, Erreserba err) {
		Erreserba e = db.find(Erreserba.class, err.getEskaeraNum());
		ErreserbaEgoera egoera = e.getEgoera();
		e.setEgoera(ErreserbaEgoera.KANTZELATUA);
		e.setKantzelatuData(new Date());
		Traveler t = e.getBidaiaria();
		double prezioa = e.getPrezioa();
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
			List<Geldialdia> geldialdiak = r.getGeldialdiak();
			for(Geldialdia g:geldialdiak) {
				if(!geldialdiak.get(geldialdiak.size()-1).equals(g) && !cityList.contains(g.getHiria())) {
					cityList.add(g.getHiria());
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
			List<Geldialdia> gList = r.getGeldialdiak();
			List<String> geldialdiak = new LinkedList<String>();
			for(Geldialdia g:gList) {
				geldialdiak.add(g.getHiria());
			}
			if(geldialdiak.contains(from)) {
				int i = geldialdiak.indexOf(from);
				for(int j=i+1;j<geldialdiak.size();j++) {
					cities.add(geldialdiak.get(j));
				}
			}
		}
		return cities;
	}

	public Ride createRide(List<String> hiriakLista, List<Double> prezioList, Date date, Car c, String driverEmail) throws  RideAlreadyExistException, RideMustBeLaterThanTodayException {
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
			Ride ride = driver.addRide(hiriakLista, prezioList, date,ci.getEserKop(),ci);

			ci.gehituBidaia(ride);
			db.persist(ci);
			db.getTransaction().commit();

			return ride;
		} catch (NullPointerException e) {
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
	    System.out.println(">> DataAccess: getRides=> from= " + from + " to= " + to + " date " + date);

	    TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r WHERE r.date = ?1", Ride.class);   
	    query.setParameter(1, date);
	    List<Ride> rides = query.getResultList();
	    List<Ride> filtradas = rides.stream()
	        .filter(r -> {
	            List<Geldialdia> geldialdiak = r.getGeldialdiak();
	            List<String> nombres = geldialdiak.stream()
	                                               .map(Geldialdia::getHiria)
	                                               .collect(Collectors.toList());
	            int i1 = nombres.indexOf(from);
	            int i2 = nombres.indexOf(to);
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

	    Date firstDayMonthDate = UtilDate.firstDayMonth(date);
	    Date lastDayMonthDate = UtilDate.lastDayMonth(date);

	    TypedQuery<Ride> query = db.createQuery(
	        "SELECT r FROM Ride r " +
	        "WHERE r.egoera = :egoera AND r.date BETWEEN :first AND :last AND r.eserLibre<>0", Ride.class);
	    query.setParameter("egoera", RideEgoera.MARTXAN);
	    query.setParameter("first", firstDayMonthDate);
	    query.setParameter("last", lastDayMonthDate);

	    List<Ride> rides = query.getResultList();
	    for (Ride r : rides) {
	        List<Geldialdia> geldialdiak = r.getGeldialdiak();
	        List<String> nombres = geldialdiak.stream()
	                                          .map(Geldialdia::getHiria)
	                                          .collect(Collectors.toList());
	        int i1 = nombres.indexOf(from);
	        int i2 = nombres.indexOf(to);
	        if (i1 != -1 && i2 != -1 && i1 <= i2) {
	            res.add(r.getDate());
	        }
	    }
	    return res;
	}

	

	public void open(){
		EntityManagerFactory emf;
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
	
	public void alertaAurkitu(List<String> hiriak, Date date) {
		db.getTransaction().begin();
		TypedQuery<Alerta> query = db.createQuery("SELECT a FROM Alerta a WHERE a.date=?1 AND a.egoera=?2", Alerta.class);
		query.setParameter(1, date);
		query.setParameter(2, AlertaEgoera.ZAIN);
		List<Alerta> aList = query.getResultList();
		for(Alerta a: aList) {
			int indexFrom = hiriak.indexOf(a.getFrom());
			int indexTo = hiriak.indexOf(a.getTo());
			if(indexFrom!=-1 && indexTo!=-1 && indexFrom<=indexTo) {
				a.setEgoera(AlertaEgoera.AURKITUA);
			}
		}
		db.getTransaction().commit();
	}
	
	public List<Erreklamazioa> lortuErreklamazioak(String email) {
		TypedQuery<Erreklamazioa> query = null;
		query= db.createQuery("SELECT e FROM Erreklamazioa e WHERE (e.nork.email=?1 OR e.nori.email=?2) AND e.egoera=?3", Erreklamazioa.class);
		query.setParameter(1, email);
		query.setParameter(2, email);
		query.setParameter(3, ErreklamazioaEgoera.BURUTZEN);
		return query.getResultList();
	}
	
	public List<Erreklamazioa> lortuErreklamazioakGuztiak() {
		TypedQuery<Erreklamazioa> query = db.createQuery("SELECT e FROM Erreklamazioa e WHERE e.egoera=?1", Erreklamazioa.class);
		query.setParameter(1, ErreklamazioaEgoera.BURUTZEN);
		return query.getResultList();
	}
	
	public List<String> lortuErabiltzaileEmailGuztiak () {
		TypedQuery<String> query = db.createQuery("SELECT u.email FROM User u", String.class);
		return query.getResultList();
	}
	
	public void sortuErreklamazioa(String email1, String email2, double kop, String mezua) {
		db.getTransaction().begin();
		User nork = db.find(User.class, email1);
		User nori = db.find(User.class, email2);
		Erreklamazioa e = nork.sortuErreklamazioa(nori, mezua, kop);
		nori.addJasotakoErreklamazioa(e);
		nori.setCash(nori.getCash()-kop);
		nori.addFrozenMoney(kop);
		nori.addMugimendua(kop, MugimenduMota.ERREKLAMAZIOA_SORTU);
		db.getTransaction().commit();
	}
	
	public List<Mezua> lortuErreklamazioMezuak(int id){
		Erreklamazioa e = db.find(Erreklamazioa.class, id);
		return e.getMezuList();
	}
	
	public void bidaliMezua(String email, String text, int id) {
		db.getTransaction().begin();
		User u = db.find(User.class, email);
		Erreklamazioa e = db.find(Erreklamazioa.class, id);
		if(u!=null) {
			e.sortuMezua(text, u);
		} else {
			e.sortuMezua(text, null);
		}
		db.getTransaction().commit();
	}
	
	public void onartuErreklamazioa(int id) {
		db.getTransaction().begin();
		Erreklamazioa e = db.find(Erreklamazioa.class, id);
		e.setEgoera(ErreklamazioaEgoera.ONARTUA);
		double kop = e.getKopurua();
		User uNork = e.getNork();
		User uNori = e.getNori();
		uNork.diruaSartu(kop);
		uNori.removeFrozenMoney(kop);
		uNork.addMugimendua(kop, MugimenduMota.ERREKLAMAZIOA_IRABAZI);
		uNori.addMugimendua(kop, MugimenduMota.ERREKLAMAZIOA_GALDU);
		db.getTransaction().commit();
	}
	
	public void ukatuErreklamazioa(int id) {
		db.getTransaction().begin();
		Erreklamazioa e = db.find(Erreklamazioa.class, id);
		e.setEgoera(ErreklamazioaEgoera.EZEZTATUA);
		double kop = e.getKopurua();
		User uNori = e.getNori();
		uNori.removeFrozenMoney(kop);
		uNori.diruaSartu(kop);
		uNori.addMugimendua(kop, MugimenduMota.ERREKLAMAZIOA_IRABAZI);
		db.getTransaction().commit();
	}
}
