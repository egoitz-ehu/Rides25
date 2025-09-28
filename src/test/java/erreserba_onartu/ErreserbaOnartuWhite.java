package erreserba_onartu;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import data_access.DataAccess;
import domain.Driver;
import domain.Erreserba;
import domain.ErreserbaEgoera;
import domain.MugimenduMota;
import domain.Ride;
import domain.Traveler;
import exceptions.DagoenekoOnartuaException;
import exceptions.DatuakNullException;
import testOperations.TestDataAccess;

public class ErreserbaOnartuWhite {
	static DataAccess sut;

    @BeforeClass
    public static void setUpClass() {
        try {
			sut = new DataAccess();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	static TestDataAccess testDA=new TestDataAccess();
	
	@Test
	// Sartutako identifikatzailea duen ride-rik datu basean
	public void test1() {
		String travelerEmail = "traveler1@gmail.com";
		String travelerPassword = "traveler1";
		
		String driverEmail = "hola@gmail.com";
		String driverPassword = "driver1";
		
		int seatNumber = 2;
	
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		String from = "Bilbo";
		String to = "Gasteiz";
		
		testDA.open();
		Ride r =testDA.addDriverWithRide(driverEmail, driverPassword, from, to, rideDate, seatNumber, 1, seatNumber);
		Traveler t = testDA.createTraveler(travelerEmail, travelerPassword, 10);
		testDA.close();
		
		sut.open();
		try {
			sut.erreserbaOnartu(10, driverEmail);
			fail();
		} catch(DatuakNullException e1) {
			assertEquals(Collections.emptyList(),t.getMugimenduak());
			assertEquals(Collections.emptyList(),r.getDriver().getMugimenduak());
		} catch (Exception e1) {
			fail();
		} finally {
			sut.close();
			testDA.open();
			testDA.removeRide(r.getRideNumber());
			testDA.removeUser(driverEmail);
			testDA.removeUser(travelerEmail);
		}
	}
	
	@Test
	// Erreserba dagoeneko onartuta dago
	public void test2() {
		String travelerEmail = "traveler1@gmail.com";
		
		String driverEmail = "hola@gmail.com";
		String driverPassword = "driver1";
		
		int seatNumber = 2;
	
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		String from = "Bilbo";
		String to = "Gasteiz";
		
		testDA.open();
		Ride r =testDA.addDriverWithRide(driverEmail, driverPassword, from, to, rideDate, seatNumber, 1, seatNumber);
		Traveler t = testDA.createTravelerWithErreserba(travelerEmail, r.getRideNumber(), 1, from, to, r.prezioaKalkulatu(from, to));
		Erreserba e = t.getBookedRides().get(0);
		testDA.changeErreserbaStatus(e.getEskaeraNum(), ErreserbaEgoera.ONARTUA);
		testDA.close();
		
		sut.open();
		try {
			sut.erreserbaOnartu(e.getEskaeraNum(), driverEmail);
			fail();
		} catch(DagoenekoOnartuaException e1) {
			testDA.open();
			t = testDA.getTraveler(travelerEmail);
			r = testDA.getDriver(driverEmail).getRides().get(0);
			testDA.close();
			assertEquals(Collections.emptyList(),t.getMugimenduak());
			assertEquals(Collections.emptyList(),r.getDriver().getMugimenduak());
			assertEquals(r.prezioaKalkulatu(from, to),t.getFrozenMoney(),0.01);
		} catch (Exception e1) {
			fail();
		} finally {
			sut.close();
			testDA.open();
			testDA.removeRide(r.getRideNumber());
			testDA.removeUser(driverEmail);
			testDA.removeUser(travelerEmail);
		}
	}
	
	@Test
	// Driver ez dago datu basean
	public void test3() {
		String travelerEmail = "traveler1@gmail.com";
		
		String driverEmail = "hola@gmail.com";
		
		String from = "Bilbo";
		String to = "Gasteiz";
		
		testDA.open();
		double price = 10;
		Traveler t = testDA.createTravelerWithErreserba(travelerEmail, 10, 1, from, to, price);
		Erreserba e = t.getBookedRides().get(0);
		testDA.close();
		
		sut.open();
		try {
			sut.erreserbaOnartu(e.getEskaeraNum(), driverEmail);
			fail();
		} catch(DatuakNullException e1) {
			testDA.open();
			t = testDA.getTraveler(travelerEmail);
			testDA.close();
			assertEquals(Collections.emptyList(),t.getMugimenduak());
			assertEquals(price,t.getFrozenMoney(),0.01);
		} catch (Exception e1) {
			fail();
		} finally {
			sut.close();
			testDA.open();
			testDA.removeUser(travelerEmail);
		}
	}
	
	@Test
	// Erreserba ondo onartua
	public void test4() {
		String travelerEmail = "traveler1@gmail.com";
		
		String driverEmail = "hola@gmail.com";
		String driverPassword = "driver1";
		
		int seatNumber = 2;
	
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		String from = "Bilbo";
		String to = "Gasteiz";
		
		testDA.open();
		Ride r =testDA.addDriverWithRide(driverEmail, driverPassword, from, to, rideDate, seatNumber, 1, seatNumber);
		Traveler t = testDA.createTravelerWithErreserba(travelerEmail, r.getRideNumber(), 1, from, to, r.prezioaKalkulatu(from, to));
		Erreserba e = t.getBookedRides().get(0);
		Driver d = r.getDriver();
		testDA.close();
		
		sut.open();
		try {
			sut.erreserbaOnartu(e.getEskaeraNum(), driverEmail);
			testDA.open();
			t = testDA.getTraveler(travelerEmail);
			e = t.getBookedRides().get(0);
			d = testDA.getDriver(driverEmail);
			r = d.getRides().get(0);
			testDA.close();
			assertEquals(r.prezioaKalkulatu(from, to),d.getFrozenMoney(),0.01);
			assertEquals(0,t.getFrozenMoney(),0.01);
			assertEquals(ErreserbaEgoera.ONARTUA, e.getEgoera());
			assertEquals(1,d.getMugimenduak().size());
			assertEquals(MugimenduMota.ERRESERBA_ONARTU_GIDARI, d.getMugimenduak().get(0).getType());
			assertEquals(1, t.getMugimenduak().size());
			assertEquals(MugimenduMota.ERRESERBA_ONARTU_BIDAIARI, t.getMugimenduak().get(0).getType());
		} catch (Exception e1) {
			fail();
		} finally {
			sut.close();
			testDA.open();
			testDA.removeRide(r.getRideNumber());
			testDA.removeUser(driverEmail);
			testDA.removeUser(travelerEmail);
		}
	}
}
