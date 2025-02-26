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
import domain.Driver;
import domain.Erreserba;
import domain.ErreserbaEgoera;
import domain.Ride;
import domain.Traveler;
import domain.User;
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
			
			//Create rides
			driver1.addRide("Donostia", "Bilbo", UtilDate.newDate(year,month,15), 4, 7);
			driver1.addRide("Donostia", "Gazteiz", UtilDate.newDate(year,month,6), 4, 8);
			driver1.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,25), 4, 4);

			driver1.addRide("Donostia", "Iruña", UtilDate.newDate(year,month,7), 4, 8);
			
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
		boolean b = u.diruaAtera(diruKop);
		if(b) {
			db.getTransaction().begin();
			db.merge(u);
			db.getTransaction().commit();
		}
		return b;
	}
	
	public boolean diruaSartu(User t, double kop) {
		boolean b =t.diruaSartu(kop);
		if(b) {
			db.getTransaction().begin();
			db.merge(t);
			db.getTransaction().commit();
		}
		return b;
	}
	
	public boolean sortuErreserba(Traveler t, Ride r, int kop) throws EserlekurikLibreEzException, ErreserbaAlreadyExistsException {
		if(kop>0) {
			if(!t.existBook(r)) {
				if(t.diruaDauka(r, kop)) {
					if(r.eserlekuakLibre(kop)) {
						//Erreserba e = new Erreserba(kop,t, r);
						Erreserba erreserbaBerria = t.sortuErreserba(r, kop);
						r.gehituErreserba(erreserbaBerria);						db.getTransaction().begin();
						db.persist(erreserbaBerria);
						db.merge(t);
						db.merge(r);
						db.getTransaction().commit();
						return true;	
					} else {
						throw new EserlekurikLibreEzException("Ez dago nahiko eserlekurik libre");
					}
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
		Traveler t = e.getBidaiaria();
		Driver d = db.find(Driver.class, dMail);
		double prezioa = e.prezioaKalkulatu();
		t.removeFrozenMoney(prezioa);
		d.addFrozenMoney(prezioa);
		e.setEgoera(ErreserbaEgoera.ONARTUA);
		db.persist(e);
		db.getTransaction().commit();
	}
	
	public void erreserbaUkatu(int erreserbaNum) {
		db.getTransaction().begin();
		Erreserba e = db.find(Erreserba.class, erreserbaNum);
		Traveler t = e.getBidaiaria();
		Ride r = e.getRide();
		double kop = e.prezioaKalkulatu();
		int eserKop = e.getPlazaKop();
		t.removeFrozenMoney(kop);
		t.diruaSartu(kop);
		t.ezbatuErreserba(e);
		r.itzuliEserlekuak(eserKop);
		e.setEgoera(ErreserbaEgoera.UKATUA);
		db.persist(e);
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
	public Ride createRide(String from, String to, Date date, int nPlaces, float price, String driverEmail) throws  RideAlreadyExistException, RideMustBeLaterThanTodayException {
		System.out.println(">> DataAccess: createRide=> from= "+from+" to= "+to+" driver="+driverEmail+" date "+date);
		try {
			if(new Date().compareTo(date)>0) {
				throw new RideMustBeLaterThanTodayException(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorRideMustBeLaterThanToday"));
			}
			db.getTransaction().begin();
			
			Driver driver = db.find(Driver.class, driverEmail);
			if (driver.doesRideExists(from, to, date)) {
				db.getTransaction().commit();
				throw new RideAlreadyExistException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.RideAlreadyExist"));
			}
			Ride ride = driver.addRide(from, to, date, nPlaces, price);
			//next instruction can be obviated
			db.persist(driver); 
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
