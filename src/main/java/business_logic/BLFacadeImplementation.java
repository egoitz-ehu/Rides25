package business_logic;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import data_access.DataAccess;
import domain.Ride;
import domain.RideErreserbaContainer;
import domain.Traveler;
import domain.TravelerErreserbaContainer;
import domain.User;
import domain.UserData;
import domain.Alerta;
import domain.Balorazioa;
import domain.Car;
import domain.Driver;
import domain.Erreklamazioa;
import domain.Erreserba;
import domain.ErreserbaData;
import domain.ExtendedIterator;
import domain.ExtendedIteratorImplementation;
import domain.Mezua;
import domain.Mugimendua;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.AlertaAlreadyExistsException;
import exceptions.BadagoRideException;
import exceptions.DagoenekoOnartuaException;
import exceptions.DatuakNullException;
import exceptions.DiruaEzDaukaException;
import exceptions.ErreserbaAlreadyExistsException;
import exceptions.ErreserbaEgoeraEzDaZainException;
import exceptions.EserlekurikLibreEzException;
import exceptions.RideAlreadyExistException;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	DataAccess dbManager;
	
	Logger logger = Logger.getLogger(getClass().getName());

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		
		
		    try {
				dbManager=new DataAccess();
			} catch (IOException e) {
				e.printStackTrace();
			}

		
	}
	
    public BLFacadeImplementation(DataAccess da)  {
		
		logger.info("Creating BLFacadeImplementation instance with DataAccess parameter");
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
   public Ride createRide( List<String> geldiList, List<Double> prezioList, Date date, Car c, String driverEmail ) throws RideMustBeLaterThanTodayException, RideAlreadyExistException{
	   
		dbManager.open();
		Ride ride=dbManager.createRide(geldiList, prezioList, date, c, driverEmail);
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
		DataAccess dB4oManager;
		try {
			dB4oManager = new DataAccess();
			dB4oManager.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

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
	public boolean register(UserData u, String type) {
		dbManager.open();
		boolean b = dbManager.register(u, type);
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
	public boolean sortuErreserba(Traveler t, ErreserbaData eData) throws
	EserlekurikLibreEzException, ErreserbaAlreadyExistsException, DiruaEzDaukaException, DatuakNullException {
		dbManager.open();
		boolean b = dbManager.sortuErreserba(t, eData);
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
	public void onartuErreserba(int erreserbaNum, String d) throws DatuakNullException, DagoenekoOnartuaException {
		dbManager.open();
		dbManager.erreserbaOnartu(erreserbaNum, d);
		dbManager.close();
	}

	@WebMethod
	public void ukatuErreserba(int erreserbaNum, int r) throws DatuakNullException, ErreserbaEgoeraEzDaZainException {
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
	public boolean sortuKotxea(Car kotxeBerria) {
		dbManager.open();
		boolean b = dbManager.sortuKotxea(kotxeBerria);
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
	public void erreserbaEzeztatu(RideErreserbaContainer e, String t) {
		dbManager.open();
		dbManager.erreserbaEzeztatu(e,t);
		dbManager.close();
	}

	@WebMethod
	public void kantzelatuBidaia(Ride r, String d) {
		dbManager.open();
		dbManager.kantzelatuBidaia(r, d);
		dbManager.close();
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
	public List<TravelerErreserbaContainer> getErreserbaTravelerContainers(Ride r) {
		dbManager.open();
		List<TravelerErreserbaContainer> list = dbManager.getErresebraTravelerContainers(r);
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

	@WebMethod
	public List<String> getStopCitiesNames() {
		dbManager.open();
		List<String> hList = dbManager.getStopCitiesNames();
		dbManager.close();
		return hList;
	}

	@WebMethod
	public List<TravelerErreserbaContainer> lortuBalorazioErreserbak(User u) {
		dbManager.open();
		List<TravelerErreserbaContainer> eList = dbManager.lortuBalorazioErreserbak(u);
		dbManager.close();
		return eList;
	}

	@WebMethod
	public void sortuBalorazioa(String u, int er, int puntuzioa, String mezua) {
		dbManager.open();
		dbManager.sortuBalorazioa(u, er, puntuzioa, mezua);
		dbManager.close();
	}

	@WebMethod
	public List<Balorazioa> lortuBalorazioak(String email) {
		dbManager.open();
		List<Balorazioa> list = dbManager.lortuBaloraizoak(email);
		dbManager.close();
		return list;
	}

	@WebMethod
	public void sortuAlerta(String email, String from, String to, Date date) throws BadagoRideException, ErreserbaAlreadyExistsException, AlertaAlreadyExistsException {
		dbManager.open();
		dbManager.sortuAlerta(email, from, to, date);
		dbManager.close();
	}

	@WebMethod
	public List<Alerta> lortuAlertak(String email) {
		dbManager.open();
		List<Alerta> list = dbManager.lortuAlertak(email);
		dbManager.close();
		return list;
	}
	
	@WebMethod
	public void ezabatuAlerta(int id) {
		dbManager.open();
		dbManager.ezabatuAlerta(id);
		dbManager.close();
	}

	@WebMethod
	public void alertaAurkitua(List<String> hiriak, Date d) {
		dbManager.open();
		dbManager.alertaAurkitu(hiriak, d);
		dbManager.close();
	}

	@WebMethod
	public List<Erreklamazioa> lortuErreklamazioak(String email) {
		dbManager.open();
		List<Erreklamazioa> list = dbManager.lortuErreklamazioak(email);
		dbManager.close();
		return list;
	}
	
	@WebMethod
	public List<Erreklamazioa> lortuErreklamazioakGuztiak() {
		dbManager.open();
		List<Erreklamazioa> list = dbManager.lortuErreklamazioakGuztiak();
		dbManager.close();
		return list;
	}

	@WebMethod
	public List<String> lortuErabiltzaileEmailGuztiak() {
		dbManager.open();
		List<String> list = dbManager.lortuErabiltzaileEmailGuztiak();
		dbManager.close();
		return list;
	}

	@WebMethod
	public void sortuErreklamazioa(String email1, String email2, double kop, String mezua) {
		dbManager.open();
		dbManager.sortuErreklamazioa(email1, email2, kop, mezua);
		dbManager.close();
	}

	@WebMethod
	public List<Mezua> lortuErreklamazioMezuak(int id) {
		dbManager.open();
		List<Mezua> l = dbManager.lortuErreklamazioMezuak(id);
		dbManager.close();
		return l;
	}

	@WebMethod
	public void bidaliMezua(String email, String text, int id) {
		dbManager.open();
		dbManager.bidaliMezua(email, text, id);
		dbManager.close();
	}

	@WebMethod
	public void onartuErreklamazioa(int id) {
		dbManager.open();
		dbManager.onartuErreklamazioa(id);
		dbManager.close();
	}

	@WebMethod
	public void ukatuErreklamazioa(int id) {
		dbManager.open();
		dbManager.ukatuErreklamazioa(id);
		dbManager.close();
	}

	@Override
	public ExtendedIterator<String> getDepartingCitiesIterator() {
		return new ExtendedIteratorImplementation<String>(this.getStopCitiesNames());
	}
}

