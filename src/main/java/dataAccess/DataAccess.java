package dataAccess;

import java.io.File;
import java.net.NoRouteToHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Car;
import domain.Driver;
import domain.Erreserba;
import domain.ErreserbaEgoera;
import domain.MugimenduMota;
import domain.Mugimendua;
import domain.Ride;
import domain.Traveler;
import domain.User;
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
			Car c1 = driver1.addCar("123", 4, "Green", "Seat");
			
			//Create rides
			driver1.addRide("Donostia", "Bilbo", UtilDate.newDate(year,month,15), 4, 7,c1);
			driver1.addRide("Donostia", "Gazteiz", UtilDate.newDate(year,month,6), 4, 8,c1);
			driver1.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,25), 4, 4,c1);

			driver1.addRide("Donostia", "Iruña", UtilDate.newDate(year,month,7), 4, 8,c1);
			
			//driver2.addRide("Donostia", "Bilbo", UtilDate.newDate(year,month,15), 3, 3);
			//driver2.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,25), 2, 5);
			//driver2.addRide("Eibar", "Gasteiz", UtilDate.newDate(year,month,6), 2, 5);

			//driver3.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,14), 1, 3);

			
						
			db.persist(driver1);
			//db.persist(driver2);
			//db.persist(driver3);

	
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
	
	public boolean diruaAtera(User u, double diruKop) {
		db.getTransaction().begin();
		boolean b = u.diruaAtera(diruKop);
		if(b) {
			u.addMugimendua(diruKop,MugimenduMota.DIRU_IRTEERA);
			db.merge(u);
			db.getTransaction().commit();
		}
		return b;
	}
	
	public boolean diruaSartu(User t, double kop) {

			db.getTransaction().begin();
			//User u = db.find(User.class, t.getEmail());
			boolean b =t.diruaSartu(kop);
			if(b) {
				t.addMugimendua(kop,MugimenduMota.DIRU_SARRERA);
				db.merge(t);
				db.getTransaction().commit();
			}
		return b;
	}
	
	public boolean sortuErreserba(Traveler t, int rNumber, int kop) throws EserlekurikLibreEzException, ErreserbaAlreadyExistsException, DiruaEzDaukaException {
		if(kop>0) {
			db.getTransaction().begin();
			Ride r = db.find(Ride.class, rNumber);
			//Traveler t = db.find(Traveler.class, tEmail);
			double kostua = r.prezioaKalkulatu(kop);
			Traveler tr = db.find(Traveler.class, t.getEmail());
			if(!tr.existBook(r)) {
				if(tr.diruaDauka(r, kop)) {
					if(r.eserlekuakLibre(kop)) {
						//Erreserba e = new Erreserba(kop,t, r);
						Erreserba erreserbaBerria = tr.sortuErreserba(r, kop);
						r.gehituErreserba(erreserbaBerria);
						tr.addMugimendua(kostua,MugimenduMota.ERRESERBA_SORTU);
						db.persist(erreserbaBerria);
						//db.persist(r);
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
	
	public void erreserbaOnartu(int erreserbaNum, Driver d) {
		db.getTransaction().begin();
		Erreserba e = db.find(Erreserba.class, erreserbaNum);
		Traveler t = db.find(Traveler.class, e.getBidaiariaEmail());
		double prezioa = e.prezioaKalkulatu();
		e.setEgoera(ErreserbaEgoera.ONARTUA);
		t.removeFrozenMoney(prezioa);
		d.addFrozenMoney(prezioa);
		d.addMugimendua(prezioa, MugimenduMota.ERRESERBA_ONARTU_GIDARI);
		t.addMugimendua(prezioa, MugimenduMota.ERRESERBA_ONARTU_BIDAIARI);
		//db.persist(e);
		db.merge(d);
		db.persist(t);
		db.getTransaction().commit();
	}
	
	public void erreserbaUkatu(int erreserbaNum, Ride r) {
		db.getTransaction().begin();
		Erreserba e = db.find(Erreserba.class, erreserbaNum);
		Traveler t = db.find(Traveler.class, e.getBidaiariaEmail());
		//Ride r = e.getRide();
		double kop = e.prezioaKalkulatu();
		int eserKop = e.getPlazaKop();
		e.setEgoera(ErreserbaEgoera.UKATUA);
		t.removeFrozenMoney(kop);
		t.diruaSartu(kop);
		t.ezabatuErreserba(e);
		r.itzuliEserlekuak(eserKop);
		t.addMugimendua(kop, MugimenduMota.ERRESERBA_UKATU);
		//db.persist(e);
		db.merge(r);
		db.merge(t);
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
	
	public void erreserbaBaieztatu(Erreserba e) {
		db.getTransaction().begin();
		e.setEgoera(ErreserbaEgoera.BAIEZTATUA);
		double prezioa = e.prezioaKalkulatu();
		Ride r = e.getRide();
		Driver d = r.getDriver();
		d.removeFrozenMoney(prezioa);
		d.diruaSartu(prezioa);
		d.addMugimendua(prezioa, MugimenduMota.ERRESERBA_BAIEZTATU);
		db.merge(e);
		db.getTransaction().commit();;
	}
	
	public void erreserbaEzeztatu(Erreserba e, Traveler t) {
		db.getTransaction().begin();
		e.setEgoera(ErreserbaEgoera.EZEZTATUA);
		double prezioa = e.prezioaKalkulatu();
		Ride r = e.getRide();
		Driver d = r.getDriver();
		d.removeFrozenMoney(prezioa);
		d.addMugimendua(prezioa, MugimenduMota.ERRESERBA_EZEZTATU_GIDARI);
		t.diruaSartu(prezioa);
		t.addMugimendua(prezioa, MugimenduMota.ERRESERBA_EZEZTATU_BIDAIARI);
		db.merge(e);
		db.merge(t);
		db.getTransaction().commit();
	}
	
	public void kantzelatuBidaia(Ride r, Driver d) {
		db.getTransaction().begin();
		List<Erreserba> erreserbaList = r.getErreserbak();
		for(Erreserba e:erreserbaList) {
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
		db.merge(d);
		db.getTransaction().commit();
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
		TypedQuery<String> query = db.createQuery("SELECT DISTINCT r.to FROM Ride r WHERE r.from=?1 ORDER BY r.to",String.class);
		query.setParameter(1, from);
		List<String> arrivingCities = query.getResultList(); 
		return arrivingCities;
		
	}
	/**
	 * This method creates a ride for a driver
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @param nPlaces available seats
	 * @param driverEmail to which ride is added
	 * 
	 * @return the created ride, or null, or an exception
	 * @throws RideMustBeLaterThanTodayException if the ride date is before today 
 	 * @throws RideAlreadyExistException if the same ride already exists for the driver
	 */
	public Ride createRide(String from, String to, Date date, Car c, float price, String driverEmail) throws  RideAlreadyExistException, RideMustBeLaterThanTodayException {
		System.out.println(">> DataAccess: createRide=> from= "+from+" to= "+to+" driver="+driverEmail+" date "+date);
		try {
			if(new Date().compareTo(date)>0) {
				throw new RideMustBeLaterThanTodayException(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorRideMustBeLaterThanToday"));
			}
			db.getTransaction().begin();
			
			Driver driver = db.find(Driver.class, driverEmail);
			Car ci = db.find(Car.class, c.getMatrikula());
			if (driver.doesRideExists(from, to, date)) {
				db.getTransaction().commit();
				throw new RideAlreadyExistException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.RideAlreadyExist"));
			}
			Ride ride = driver.addRide(from, to, date,ci.getEserKop(), price,ci);

			//next instruction can be obviated
			//db.persist(driver);
			ci.addRide(ride);
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
		TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date=?3",Ride.class);   
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, date);
		List<Ride> rides = query.getResultList();
	 	 for (Ride ride:rides){
		   res.add(ride);
		  }
	 	return res;
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
				
		
		TypedQuery<Date> query = db.createQuery("SELECT DISTINCT r.date FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date BETWEEN ?3 and ?4",Date.class);   
		
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, firstDayMonthDate);
		query.setParameter(4, lastDayMonthDate);
		List<Date> dates = query.getResultList();
	 	 for (Date d:dates){
		   res.add(d);
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
	
}
