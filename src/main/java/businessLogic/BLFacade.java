package businessLogic;

import java.util.Date;
import java.util.List;

//import domain.Booking;
import domain.Ride;
import domain.RideErreserbaContainer;
import domain.Traveler;
import domain.TravelerErreserbaConatainer;
import domain.User;
import domain.Car;
import domain.Driver;
import domain.Erreserba;
import domain.Mugimendua;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.DiruaEzDaukaException;
import exceptions.ErreserbaAlreadyExistsException;
import exceptions.EserlekurikLibreEzException;
import exceptions.RideAlreadyExistException;

import javax.jws.WebMethod;
import javax.jws.WebService;
 
/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	  
	/**
	 * This method returns all the cities where rides depart 
	 * @return collection of cities
	 */
	@WebMethod public List<String> getDepartCities();
	
	/**
	 * This method returns all the arrival destinations, from all rides that depart from a given city  
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	@WebMethod public List<String> getDestinationCities(String from);


	/**
	 * This method creates a ride for a driver
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @param nPlaces available seats
	 * @param driver to which ride is added
	 * 
	 * @return the created ride, or null, or an exception
	 * @throws RideMustBeLaterThanTodayException if the ride date is before today 
 	 * @throws RideAlreadyExistException if the same ride already exists for the driver
	 */
   @WebMethod
   public Ride createRide( String from, String to, Date date, Car c, float price, String driverEmail) throws RideMustBeLaterThanTodayException, RideAlreadyExistException;
	
	
	/**
	 * This method retrieves the rides from two locations on a given date 
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @return collection of rides
	 */
	@WebMethod public List<Ride> getRides(String from, String to, Date date);
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride 
	 * @param date of the month for which days with rides want to be retrieved 
	 * @return collection of rides
	 */
	@WebMethod public List<Date> getThisMonthDatesWithRides(String from, String to, Date date);
	
	/**
	 * This method calls the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();
	
	@WebMethod public boolean register(String email, String name, String surname, String password, String type);
	
	@WebMethod public User login(String email, String password);

	@WebMethod public boolean diruaAtera(String u, double kop);
	
	@WebMethod public boolean diruaSartu(String t, double kop);
	
	@WebMethod public boolean sortuErreserba(Traveler t, int rNumber, int kop) throws EserlekurikLibreEzException, ErreserbaAlreadyExistsException, DiruaEzDaukaException;
	
	@WebMethod public List<Integer> getAllRidesNumber(String email);
	
	@WebMethod public List<Erreserba> lortuErreserbak(int rideNumber);
	
	@WebMethod public void onartuErreserba(int erreserbaNum, Driver d);
	
	@WebMethod public void ukatuErreserba(int erreserbaNum, Ride r);
	
	@WebMethod public List<Ride> getDriverAllRides(String driverEmail);
	
	@WebMethod public boolean sortuKotxea(String matrikula,int eserKop,String kolorea, String mota, Driver d);
	
	@WebMethod public void erreserbaBaieztatu(RideErreserbaContainer e);
	
	@WebMethod public void erreserbaEzeztatu(RideErreserbaContainer e, Traveler t);
	
	@WebMethod public List<Erreserba> erreserbakLortu(Traveler t);
	
	@WebMethod public void kantzelatuBidaia(Ride r, String d);
	
	@WebMethod public List<Ride> getRidesDriver(Driver d);
	
	@WebMethod public List<Car> getDriverCars(String dEmail);
	
	@WebMethod public double getUserMoney(String email);
	
	@WebMethod public double getUserFrozenMoney(String email);
	
	@WebMethod public List<Mugimendua> getUserMovements(String email);
	
	@WebMethod public List<TravelerErreserbaConatainer> getErreserbaTravelerContainers(Ride r);
	
	@WebMethod public List<RideErreserbaContainer> getRideErreserbaContainers(Traveler t);
}
