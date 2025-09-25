import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import data_access.DataAccess;
import domain.Ride;
import domain.Traveler;
import exceptions.DiruaEzDaukaException;
import exceptions.ErreserbaAlreadyExistsException;
import exceptions.EserlekurikLibreEzException;
import testOperations.TestDataAccess;

public class SortuErreserbaWhite {
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
	// Eserleku kopurua ez da 1 edo gehiago
	public void test1() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		String driverEmail="driver@gmail.com";
		String driverName="Driver1";
		
		int rideNumber = 1;
		
		String from = "Bilbo";
		String to = "Gasteiz";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		testDA.open();
		Ride r = testDA.addDriverWithRide(driverEmail, driverName, from, to, rideDate, 4, 10,2);
		Traveler t = testDA.createTraveler(travelerEmail, travelerName,100);
		testDA.close();
		
		sut.open();
		try {
			boolean res = sut.sortuErreserba(t, rideNumber, 0, from, to);
			sut.close();
			assertFalse(res);
		} catch (EserlekurikLibreEzException | DiruaEzDaukaException | ErreserbaAlreadyExistsException e) {
			sut.close();
			fail();
		} finally {
			testDA.open();
			testDA.removeRide(rideNumber);
			testDA.removeUser(travelerEmail);
			testDA.removeUser(driverEmail);
			testDA.close();
		}
	}
	
	@Test
	// Traveler datu basean dago, baina dagoeneko badu erreserba berdin bat.
	// Ondorioz ErreserbaAlreadyExistsException jaurtiko du
	public void test2() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		String driverEmail="driver@gmail.com";
		String driverName="Driver1";
		
		String from = "Bilbo";
		String to = "Gasteiz";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		testDA.open();
		Ride r = testDA.addDriverWithRide(driverEmail, driverName, from, to, rideDate, 4, 10,2);
		int rideNumber = r.getRideNumber();
		Traveler t = testDA.createTraveler(travelerEmail, travelerName,100);
		testDA.addErreserbaToTraveler(t, rideNumber, 1, from, to, 10);
		testDA.close();
		
		sut.open();
		try {
			sut.sortuErreserba(t, rideNumber, 1, from, to);
			sut.close();
			fail();
		} catch (EserlekurikLibreEzException | DiruaEzDaukaException e) {
			sut.close();
			fail();
		} catch (ErreserbaAlreadyExistsException e) {
			sut.close();
			assertTrue(true);
		} finally {
			testDA.open();
			testDA.removeRide(rideNumber);
			testDA.removeUser(travelerEmail);
			testDA.removeUser(driverEmail);
			testDA.close();
		}
	}
	
	@Test
	// Traveler datu basean dago eta ez dauka dirurik. Ondorioz DiruaEzDaukaException jaurtiko du
	public void test3() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		String driverEmail="driver@gmail.com";
		String driverName="Driver1";
		
		String from = "Bilbo";
		String to = "Gasteiz";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		testDA.open();
		Ride r = testDA.addDriverWithRide(driverEmail, driverName, from, to, rideDate, 4, 10,2);
		int rideNumber = r.getRideNumber();
		Traveler t = testDA.createTraveler(travelerEmail, travelerName,0);
		testDA.close();
		
		sut.open();
		try {
			sut.sortuErreserba(t, rideNumber, 1, from, to);
			sut.close();
			fail();
		} catch (EserlekurikLibreEzException | ErreserbaAlreadyExistsException e) {
			sut.close();
			fail();
		} catch (DiruaEzDaukaException e) {
			sut.close();
			assertTrue(true);
		} finally {
			testDA.open();
			testDA.removeRide(rideNumber);
			testDA.removeUser(travelerEmail);
			testDA.removeUser(driverEmail);
			testDA.close();
		}
	}
	
	@Test
	// Traveler datu basean dago, ez dauka erreserbarik eta dirua dauka.
	// Eserleku kopurua 1 edo gehiago da.
	// Baina bidaiaren eserlekuak ez daude libre. Ondorioz EserlekurikLibreEzException jaurtiko du
	public void test4() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		String driverEmail="driver@gmail.com";
		String driverName="Driver1";
		
		String from = "Bilbo";
		String to = "Gasteiz";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		testDA.open();
		Ride r = testDA.addDriverWithRide(driverEmail, driverName, from, to, rideDate, 4, 10,0);
		int rideNumber = r.getRideNumber();
		Traveler t = testDA.createTraveler(travelerEmail, travelerName,100);
		testDA.close();
		
		sut.open();
		try {
			sut.sortuErreserba(t, rideNumber, 1, from, to);
			sut.close();
			fail();
		} catch (DiruaEzDaukaException | ErreserbaAlreadyExistsException e) {
			sut.close();
			fail();
		} catch (EserlekurikLibreEzException e) {
			sut.close();
			assertTrue(true);
		} finally {
			testDA.open();
			testDA.removeRide(rideNumber);
			testDA.removeUser(travelerEmail);
			testDA.removeUser(driverEmail);
			testDA.close();
		}
	}
	
	@Test
	// Traveler datu basean dago, ez dauka erreserbarik eta dirua dauka.
	// Eserleku kopurua 1 edo gehiago da. Erreserba ondo sortuko da
	public void test5() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		String driverEmail="driver@gmail.com";
		String driverName="Driver1";
		
		String from = "Bilbo";
		String to = "Gasteiz";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		testDA.open();
		Ride r = testDA.addDriverWithRide(driverEmail, driverName, from, to, rideDate, 4, 10,2);
		int rideNumber = r.getRideNumber();
		Traveler t = testDA.createTraveler(travelerEmail, travelerName,100);
		testDA.close();
		
		sut.open();
		try {
			boolean res = sut.sortuErreserba(t, rideNumber, 1, from, to);
			sut.close();
			assertTrue(res);
		} catch (DiruaEzDaukaException | EserlekurikLibreEzException | ErreserbaAlreadyExistsException e) {
			sut.close();
			fail();
		} finally {
			testDA.open();
			testDA.removeRide(rideNumber);
			testDA.removeUser(travelerEmail);
			testDA.removeUser(driverEmail);
			testDA.close();
		}
	}
}
