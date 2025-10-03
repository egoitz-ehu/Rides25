package sortu_alerta;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import data_access.DataAccess;
import domain.Erreserba;
import domain.Ride;
import domain.Traveler;
import exceptions.AlertaAlreadyExistsException;
import exceptions.BadagoRideException;
import exceptions.ErreserbaAlreadyExistsException;
import testOperations.TestDataAccess;

public class SortuAlertaBDBlackTest {
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
	// Alerta arrakastaz sortu da
	public void test1() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		String from = "Donostia";
		String to = "Bilbo";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		testDA.open();
		Traveler t = testDA.createTraveler(travelerEmail, travelerName,10);
		testDA.close();
		
		
		sut.open();
		try {
			sut.sortuAlerta(travelerEmail, from, to, rideDate);
			assertTrue(true);
		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
			testDA.open();
			testDA.removeUser(travelerEmail);
			testDA.close();
		}
	}
	
	@Test
	// travelerEmail null da
	public void test2() {
		
		String from = "Donostia";
		String to = "Bilbo";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
				
		sut.open();
		try {
			sut.sortuAlerta(null, from, to, rideDate);
			fail();
		} catch(IllegalArgumentException e) {
			assertTrue(true);
		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
		}
	}
	
	@Test
	// from null da
	public void test3() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		String from = null;
		String to = "Bilbo";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		testDA.open();
		testDA.createTraveler(travelerEmail, travelerName,10);
		testDA.close();
			
		sut.open();
		try {
			sut.sortuAlerta(travelerEmail, from, to, rideDate);
			fail();
		} catch (Exception e) {
			assertTrue(true);
		} finally {
			sut.close();
			testDA.open();
			testDA.removeUser(travelerEmail);
			testDA.close();
		}
	}
	
	
	@Test
	// to null da
	public void test4() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		String from = "Donosti";
		String to = null;
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		testDA.open();
		testDA.createTraveler(travelerEmail, travelerName,10);
		testDA.close();
			
		sut.open();
		try {
			sut.sortuAlerta(travelerEmail, from, to, rideDate);
			fail();
		} catch (Exception e) {
			assertTrue(true);
		} finally {
			sut.close();
			testDA.open();
			testDA.removeUser(travelerEmail);
			testDA.close();
		
	}
}

	
	@Test
	// date null da
	public void test5() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		String from = "Donostia";
		String to = "Bilbo";
		
		Date rideDate = null;
		
		testDA.open();
		testDA.createTraveler(travelerEmail, travelerName, 100);
		testDA.close();
		
		sut.open();
		try {
			sut.sortuAlerta(travelerEmail, from, to, rideDate);
			fail();
		} catch (NullPointerException e) {
			assertTrue(true);
		} catch(Exception e){
			fail();
		}finally {
			sut.close();
			testDA.open();
			testDA.removeUser(travelerEmail);
			testDA.close();
		}
	}
	
	@Test
	// traveler ez dago datu basean
	public void test6() {
		String travelerEmail = "traveler@gmail.com";
		
		String from = "Donostia";
		String to = "Bilbo";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);

		
		sut.open();
		try {
			sut.sortuAlerta(travelerEmail, from, to, rideDate);
			fail();
		} catch (NullPointerException e) {
			assertTrue(true);
		} catch(Exception e2) {
			fail();
		}finally {
			sut.close();
			testDA.open();
			testDA.removeUser(travelerEmail);
			testDA.close();
		}
	}
	
	@Test
	// Travelerrak badu alerta bat from,to,date datu berdinekin.
	public void test7() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		String from = "Donostia";
		String to = "Bilbo";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		testDA.open();
		Traveler t = testDA.createTraveler(travelerEmail, travelerName, 100);
		testDA.addAlertaToTraveler(t, from, to, rideDate);
		testDA.close();
				
		sut.open();
		try {
			sut.sortuAlerta(travelerEmail, from, to, rideDate);
			fail();
		} catch(AlertaAlreadyExistsException e) {
			assertTrue(true);
		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
			testDA.open();
			testDA.removeUser(travelerEmail);
			testDA.close();
		}
	}
	
	@Test
	// Travelerrak badu erreserba eginda from,to,date datuekin
	public void test8() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		String driverEmail="driver@gmail.com";
		String driverName="Driver1";
		
		String from = "Bilbo";
		String to = "Donosti";
		
		Date rideDate = new Date();
		
		testDA.open();
		Ride r = testDA.addDriverWithRide(driverEmail, driverName, from, to, rideDate, 4, 10,2);
		int rideNumber = r.getRideNumber();
		double dirua = 100;
		Traveler t = testDA.createTraveler(travelerEmail, travelerName,dirua);
		Erreserba e = t.sortuErreserba(r, 1, from, to, 10);
		testDA.addErreserbaToTraveler(t, rideNumber, 1, from, to, 10);
		testDA.close();
		
		sut.open();
		try {
			sut.sortuAlerta(travelerEmail, from, to, e.getErreserbaData());
			fail();
		} catch (BadagoRideException | AlertaAlreadyExistsException  e1) {
			fail();
		} catch (ErreserbaAlreadyExistsException e2) {
			assertTrue(true);
		} finally {
			sut.close();
			testDA.open();
			testDA.removeRide(rideNumber);
			testDA.removeUser(travelerEmail);
			testDA.removeUser(driverEmail);
			testDA.close();
		}
	}
	
	
	@Test
	// Travelerrak sortu nahi duen alertak dituen datu berdineko bidaia iada existitzen da.
	public void test9() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		String driverEmail="driver@gmail.com";
		String driverName="Driver1";
		
		String from = "Bilbo";
		String to = "Gasteiz";
		
		Date rideDate = new Date();
		
		testDA.open();
		Ride r = testDA.addDriverWithRide(driverEmail, driverName, from, to, rideDate, 4, 10,2);
		int rideNumber = r.getRideNumber();
		double dirua = 100;
		testDA.createTraveler(travelerEmail, travelerName,dirua);
		testDA.close();
		
		sut.open();
		try {
			sut.sortuAlerta(travelerEmail, from, to, rideDate);
			fail();
			sut.close();
		}  catch (AlertaAlreadyExistsException | ErreserbaAlreadyExistsException | NullPointerException e) {
			sut.close();
			fail();
		} catch(BadagoRideException e) {
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
	
}
