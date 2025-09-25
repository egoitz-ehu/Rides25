package testOperations;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import configuration.ConfigXML;
import domain.Driver;
import domain.Ride;
import domain.Traveler;


public class TestDataAccess {
	protected  EntityManager  db;
	protected  EntityManagerFactory emf;

	ConfigXML  c=ConfigXML.getInstance();


	public TestDataAccess()  {
		
		System.out.println("TestDataAccess created");

		//open();
		
	}

	
	public void open(){
		

		String fileName=c.getDbFilename();
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
		System.out.println("TestDataAccess opened");

		
	}
	public void close(){
		db.close();
		System.out.println("TestDataAccess closed");
	}

	public boolean removeDriver(String driverEmail) {
		System.out.println(">> TestDataAccess: removeRide");
		Driver d = db.find(Driver.class, driverEmail);
		if (d!=null) {
			db.getTransaction().begin();
			db.remove(d);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }
	public Driver createDriver(String email, String name) {
		System.out.println(">> TestDataAccess: addDriver");
		Driver driver=null;
			db.getTransaction().begin();
			try {
			    driver=new Driver(email,null,name,null);
				db.persist(driver);
				db.getTransaction().commit();
			}
			catch (Exception e){
				e.printStackTrace();
			}
			return driver;
    }
	public boolean existDriver(String email) {
		 return  db.find(Driver.class, email)!=null;
		 

	}
		
		public Ride addDriverWithRide(String email, String name, String from, String to, Date date, int nPlaces, float price, int freePlaces) {
		    System.out.println(">> TestDataAccess: addDriverWithRide");
		    Driver driver = null;
		    Ride r = null;
		    db.getTransaction().begin();
		    try {
		        driver = db.find(Driver.class, email);
		        if (driver == null) {
		            driver = new Driver(email, null, name, null);
		            db.persist(driver);
		        }
		        
		        r = driver.addRide(Arrays.asList(from, to), Arrays.asList((double) price), date, nPlaces, null);
		        r.setEserLibre(freePlaces);
		        
		        db.persist(r);
		        
		        db.getTransaction().commit();
		    } catch (Exception e) {
		        e.printStackTrace();
		        if (db.getTransaction().isActive()) {
		            db.getTransaction().rollback();
		        }
		    }
		    return r;
		}
		
		public Traveler createTraveler(String email, String name, double cash) {
			System.out.println(">> TestDataAccess: addTraveler");
			Traveler traveler=null;
				db.getTransaction().begin();
				try {
				    traveler=new Traveler(email,null,name,null);
				    traveler.setCash(cash);
					db.persist(traveler);
					db.getTransaction().commit();
				}
				catch (Exception e){
					e.printStackTrace();
				}
				return traveler;
	    }
		
		public void addErreserbaToTraveler(Traveler t, int rideNumber, int seatNumber, String from, String to, double price) {
			System.out.println(">> TestDataAccess: addErreserbaToTraveler");
			db.getTransaction().begin();
			try {
				Ride r = db.find(Ride.class, rideNumber);
				System.out.println("Ride:"+r);
				t.sortuErreserba(r, seatNumber, from, to, price);
				db.getTransaction().commit();
			}
			catch (Exception e){
				e.printStackTrace();
			}
	    }
		
		public void removeRide(int rideNumber) {
			System.out.println(">> TestDataAccess: removeRide");
			Ride r = db.find(Ride.class, rideNumber);
			if (r!=null) {
				db.getTransaction().begin();
				db.remove(r);
				db.getTransaction().commit();
			}
		}
		
		public void removeUser(String email) {
			System.out.println(">> TestDataAccess: removeUser");
			Object u = db.find(Driver.class, email);
			if (u==null)
				u = db.find(Traveler.class, email);
			if (u!=null) {
				db.getTransaction().begin();
				db.remove(u);
				db.getTransaction().commit();
			}
		}

		
}


