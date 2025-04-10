package businessLogic;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
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

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	DataAccess dbManager;

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		
		
		    dbManager=new DataAccess();
		    
		//dbManager.close();

		
	}
	
    public BLFacadeImplementation(DataAccess da)  {
		
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c=ConfigXML.getInstance();
		
		dbManager=da;		
	}
    
    
    /**
     * {@inheritDoc}
     */
    @WebMethod public List<String> getDepartCities(){
    	dbManager.open();	
		
		 List<String> departLocations=dbManager.getDepartCities();		

		dbManager.close();
		
		return departLocations;
    	
    }
    /**
     * {@inheritDoc}
     */
	@WebMethod public List<String> getDestinationCities(String from){
		dbManager.open();	
		
		 List<String> targetCities=dbManager.getArrivalCities(from);		

		dbManager.close();
		
		return targetCities;
	}

	/**
	 * {@inheritDoc}
	 */
   @WebMethod
   public Ride createRide( String from, String to, Date date, Car c, float price, String driverEmail ) throws RideMustBeLaterThanTodayException, RideAlreadyExistException{
	   
		dbManager.open();
		Ride ride=dbManager.createRide(from, to, date, c, price, driverEmail);		
		dbManager.close();
		return ride;
   };
	
   /**
    * {@inheritDoc}
    */
	@WebMethod 
	public List<Ride> getRides(String from, String to, Date date){
		dbManager.open();
		List<Ride>  rides=dbManager.getRides(from, to, date);
		dbManager.close();
		return rides;
	}

    
	/**
	 * {@inheritDoc}
	 */
	@WebMethod 
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date){
		dbManager.open();
		List<Date>  dates=dbManager.getThisMonthDatesWithRides(from, to, date);
		dbManager.close();
		return dates;
	}
	
	
	public void close() {
		DataAccess dB4oManager=new DataAccess();

		dB4oManager.close();

	}

	/**
	 * {@inheritDoc}
	 */
    @WebMethod	
	 public void initializeBD(){
    	dbManager.open();
		dbManager.initializeDB();
		dbManager.close();
	}

    @WebMethod
	public boolean register(String email, String name, String surname, String password, String type) {
		dbManager.open();
		boolean b = dbManager.register(email, name, surname, password, type);
		dbManager.close();
		return b;
	}

    @WebMethod
	public User login(String email, String password) {
    	dbManager.open();
    	User u = dbManager.login(email, password);
    	dbManager.close();
    	return u;
	}

	@WebMethod
	public boolean diruaAtera(String u, double kop) {
		boolean b;
		dbManager.open();
		b=dbManager.diruaAtera(u, kop);
		dbManager.close();
		return b;
	}

	@WebMethod
	public boolean diruaSartu(String t, double kop) {
		dbManager.open();
		boolean b = dbManager.diruaSartu(t, kop);
		dbManager.close();
		return b;
	}

	@WebMethod
	public boolean sortuErreserba(Traveler t, int rNumber, int kop) throws EserlekurikLibreEzException, ErreserbaAlreadyExistsException, DiruaEzDaukaException {
		dbManager.open();
		boolean b = dbManager.sortuErreserba(t, rNumber, kop);
		dbManager.close();
		return b;
	}

	@WebMethod
	public List<Integer> getAllRidesNumber(String ema) {
		dbManager.open();
		List<Integer> numberList = dbManager.getAllRidesNumber(ema);
		dbManager.close();
		return numberList;
	}

	@WebMethod
	public List<Erreserba> lortuErreserbak(int rideNumber) {
		dbManager.open();
		List<Erreserba> erreserbaList = dbManager.lortuErreserbak(rideNumber);
		dbManager.close();
		return erreserbaList;
	}

	@WebMethod
	public void onartuErreserba(int erreserbaNum, Driver d) {
		dbManager.open();
		dbManager.erreserbaOnartu(erreserbaNum, d);
		dbManager.close();
	}

	@WebMethod
	public void ukatuErreserba(int erreserbaNum, Ride r) {
		dbManager.open();
		dbManager.erreserbaUkatu(erreserbaNum, r);
		dbManager.close();
	}
	
	@WebMethod
	public List<Ride> getDriverAllRides(String driverEmail) {
		dbManager.open();
		List<Ride> rideList = dbManager.getDriverAllRides(driverEmail);
		dbManager.close();
		return rideList;
	}

	@WebMethod
	public boolean sortuKotxea(String matrikula, int eserKop, String kolorea, String mota, Driver d) {
		dbManager.open();
		boolean b = dbManager.sortuKotxea(matrikula,eserKop,kolorea, mota,d);
		dbManager.close();
		return b;
	}

	@WebMethod
	public void erreserbaBaieztatu(RideErreserbaContainer e) {
		dbManager.open();
		dbManager.erreserbaBaieztatu(e);
		dbManager.close();
	}

	@WebMethod
	public List<Erreserba> erreserbakLortu(Traveler t) {
		dbManager.open();
		List<Erreserba> lista = dbManager.erreserbakLortu(t);
		dbManager.close();
		return lista;
	}

	@WebMethod
	public void erreserbaEzeztatu(RideErreserbaContainer e, Traveler t) {
		dbManager.open();
		dbManager.erreserbaEzeztatu(e,t);
		dbManager.close();
	}

	@WebMethod
	public void kantzelatuBidaia(Ride r, String d) {
		dbManager.open();
		dbManager.kantzelatuBidaia(r, d);
		dbManager.close();
		System.out.println("Bidaia kantzelatu da.");
	}

	@WebMethod
	public List<Ride> getRidesDriver(Driver d) {
		dbManager.open();
		List<Ride> rideList = dbManager.getRidesDriver(d);
		dbManager.close();
		return rideList;
	}

	@WebMethod
	public List<Car> getDriverCars(String dEmail) {
		dbManager.open();
		List<Car> carList = dbManager.getDriverCars(dEmail);
		dbManager.close();
		return carList;
	}

	@WebMethod
	public double getUserMoney(String email) {
		dbManager.open();
		double kop = dbManager.getUserMoney(email);
		dbManager.close();
		return kop;
	}

	@WebMethod
	public double getUserFrozenMoney(String email) {
		dbManager.open();
		double kop = dbManager.getUserFrozenMoney(email);
		dbManager.close();
		return kop;
	}

	@WebMethod
	public List<Mugimendua> getUserMovements(String email) {
		dbManager.open();
		List<Mugimendua> list = dbManager.getUserMovements(email);
		dbManager.close();
		return list;
	}

	@WebMethod
	public List<TravelerErreserbaConatainer> getErreserbaTravelerContainers(Ride r) {
		dbManager.open();
		List<TravelerErreserbaConatainer> list = dbManager.getErresebraTravelerContainers(r);
		dbManager.close();
		return list;
	}

	@WebMethod
	public List<RideErreserbaContainer> getRideErreserbaContainers(Traveler t) {
		dbManager.open();
		List<RideErreserbaContainer> list = dbManager.getRideErreserbaContainers(t);
		dbManager.close();
		return list;
	}
	
	

}

