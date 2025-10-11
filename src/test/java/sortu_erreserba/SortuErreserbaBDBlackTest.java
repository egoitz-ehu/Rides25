package sortu_erreserba;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import data_access.DataAccess;
import domain.ErreserbaData;
import domain.Ride;
import domain.Traveler;
import exceptions.DatuakNullException;
import exceptions.DiruaEzDaukaException;
import exceptions.ErreserbaAlreadyExistsException;
import exceptions.EserlekurikLibreEzException;
import testOperations.TestDataAccess;

public class SortuErreserbaBDBlackTest {
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
	// Erreserba arrakastaz sortu da
	public void test1() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		String driverEmail="driver@gmail.com";
		String driverName="Driver1";
		
		String from = "Donostia";
		String to = "Bilbo";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		testDA.open();
		Ride r = testDA.addDriverWithRide(driverEmail, driverName, from, to, rideDate, 4, 10,2);
		int rideNumber = r.getRideNumber();
		double dirua = 100.0;
		Traveler t = testDA.createTraveler(travelerEmail, travelerName,dirua);
		testDA.close();
		
		int eserlekuKop = 1;
		
		sut.open();
		try {
			boolean res = sut.sortuErreserba(t, new ErreserbaData(rideNumber,"Donostia","Bilbo",eserlekuKop));
			assertTrue(res);
			double prezioa = r.prezioaKalkulatu(from, to)*eserlekuKop;
			testDA.open();
			assertEquals(dirua - prezioa, testDA.getTravelerCash(travelerEmail), 0.01);
			testDA.close();
		} catch (Exception e) {
			fail();
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
	// Traveler null da
	public void test2() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		String driverEmail="driver@gmail.com";
		String driverName="Driver1";
		
		String from = "Donostia";
		String to = "Bilbo";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		testDA.open();
		Ride r = testDA.addDriverWithRide(driverEmail, driverName, from, to, rideDate, 4, 10,2);
		int rideNumber = r.getRideNumber();
		testDA.close();
		
		Traveler t = null;
		
		sut.open();
		try {
			sut.sortuErreserba(t, new ErreserbaData(rideNumber,"Donostia","Bilbo",2));
			fail();
		} catch(DatuakNullException e) {
			assertTrue(true);
		} catch (Exception e) {
			fail();
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
	// Ride zenbakia <=0 da
	public void test3() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		String driverEmail="driver@gmail.com";
		String driverName="Driver1";
		
		String from = "Donostia";
		String to = "Bilbo";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		testDA.open();
		double dirua = 100.0;
		Traveler t = testDA.createTraveler(travelerEmail, travelerName,dirua);
		testDA.close();
		
		int rideNumber = -1;
		
		sut.open();
		try {
			sut.sortuErreserba(t, new ErreserbaData(rideNumber,"Donostia","Bilbo",2));
		} catch(DatuakNullException e) {
			assertTrue(true);
			testDA.open();
			assertEquals(dirua, testDA.getTravelerCash(travelerEmail), 0.01);
			testDA.close();
		} catch (Exception e) {
			fail();
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
	// Eskatutako eserleku kopurua <=0 da
	public void test4() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		String driverEmail="driver@gmail.com";
		String driverName="Driver1";
		
		String from = "Donostia";
		String to = "Bilbo";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		testDA.open();
		Ride r = testDA.addDriverWithRide(driverEmail, driverName, from, to, rideDate, 4, 10,2);
		int rideNumber = r.getRideNumber();
		double dirua = 100.0;
		Traveler t = testDA.createTraveler(travelerEmail, travelerName,dirua);
		testDA.close();
		
		sut.open();
		try {
			boolean res = sut.sortuErreserba(t,  new ErreserbaData(rideNumber,"Donostia","Bilbo",0));
			assertFalse(res);
			testDA.open();
			assertEquals(dirua, testDA.getTravelerCash(travelerEmail), 0.01);
			testDA.close();
		} catch (Exception e) {
			fail();
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
	// From null da
	public void test5() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		String driverEmail="driver@gmail.com";
		String driverName="Driver1";
		
		String from = "Donostia";
		String to = "Bilbo";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		testDA.open();
		Ride r = testDA.addDriverWithRide(driverEmail, driverName, from, to, rideDate, 4, 10,2);
		int rideNumber = r.getRideNumber();
		double dirua = 100.0;
		Traveler t = testDA.createTraveler(travelerEmail, travelerName,dirua);
		testDA.close();
		
		sut.open();
		try {
			boolean res = sut.sortuErreserba(t, new ErreserbaData(rideNumber,null,"Bilbo",2));
			assertFalse(res);
			testDA.open();
			assertEquals(dirua, testDA.getTravelerCash(travelerEmail), 0.01);
			testDA.close();
		} catch (Exception e) {
			fail();
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
	// to null da
	public void test6() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		String driverEmail="driver@gmail.com";
		String driverName="Driver1";
		
		String from = "Donostia";
		String to = "Bilbo";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		testDA.open();
		Ride r = testDA.addDriverWithRide(driverEmail, driverName, from, to, rideDate, 4, 10,2);
		int rideNumber = r.getRideNumber();
		double dirua = 100.0;
		Traveler t = testDA.createTraveler(travelerEmail, travelerName,dirua);
		testDA.close();
		
		sut.open();
		try {
			boolean res = sut.sortuErreserba(t, new ErreserbaData(rideNumber,"Donostia",null,2));
			assertFalse(res);
			testDA.open();
			assertEquals(dirua, testDA.getTravelerCash(travelerEmail), 0.01);
			testDA.close();
		} catch (Exception e) {
			fail();
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
	// Ride ez dago datu basean
	public void test7() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		String driverEmail="driver@gmail.com";
		String driverName="Driver1";
		
		String from = "Donostia";
		String to = "Bilbo";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		testDA.open();
		double dirua = 100.0;
		Traveler t = testDA.createTraveler(travelerEmail, travelerName,dirua);
		testDA.close();
		
		int rideNumber = 2;
		
		sut.open();
		try {
			sut.sortuErreserba(t, new ErreserbaData(rideNumber,"Donostia","Bilbo",2));
			fail();
		} catch(DatuakNullException e) {
			assertTrue(true);
			testDA.open();
			assertEquals(dirua, testDA.getTravelerCash(travelerEmail), 0.01);
			testDA.close();
		} catch (Exception e) {
			fail();
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
	// t ez dago datu basean
	public void test8() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		String driverEmail="driver@gmail.com";
		String driverName="Driver1";
		
		String from = "Donostia";
		String to = "Bilbo";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		testDA.open();
		Ride r = testDA.addDriverWithRide(driverEmail, driverName, from, to, rideDate, 4, 10,2);
		int rideNumber = r.getRideNumber();
		testDA.close();
		
		Traveler t = new Traveler(travelerEmail, null, travelerName, null);
		t.diruaSartu(100);
		
		sut.open();
		try {
			sut.sortuErreserba(t,  new ErreserbaData(rideNumber,"Donostia","Bilbo",2));
			fail();
		} catch(DatuakNullException e) {
			assertTrue(true);
		} catch (Exception e) {
			fail();
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
	// Ez dago nahiko eserlekurik libre
	public void test9() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		String driverEmail="driver@gmail.com";
		String driverName="Driver1";
		
		String from = "Donostia";
		String to = "Bilbo";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		testDA.open();
		Ride r = testDA.addDriverWithRide(driverEmail, driverName, from, to, rideDate, 4, 10,0);
		int rideNumber = r.getRideNumber();
		double dirua = 100.0;
		Traveler t = testDA.createTraveler(travelerEmail, travelerName,dirua);
		testDA.close();
		
		sut.open();
		try {
			sut.sortuErreserba(t,  new ErreserbaData(rideNumber,"Donostia","Bilbo",2));
			fail();
		} catch(EserlekurikLibreEzException e) {
			assertTrue(true);
			testDA.open();
			assertEquals(dirua, testDA.getTravelerCash(travelerEmail), 0.01);
			testDA.close();
		} catch (Exception e) {
			fail();
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
	// From ez dago ride-ko geldialdi-zerrendan
	public void test10() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		String driverEmail="driver@gmail.com";
		String driverName="Driver1";
		
		String from = "Donostia";
		String to = "Bilbo";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		testDA.open();
		Ride r = testDA.addDriverWithRide(driverEmail, driverName, from, to, rideDate, 4, 10,2);
		int rideNumber = r.getRideNumber();
		double dirua = 100.0;
		Traveler t = testDA.createTraveler(travelerEmail, travelerName,dirua);
		testDA.close();
		
		sut.open();
		try {
			boolean res = sut.sortuErreserba(t, new ErreserbaData(rideNumber,"Zarautz","Bilbo",2));
			assertFalse(res);
			testDA.open();
			assertEquals(dirua, testDA.getTravelerCash(travelerEmail), 0.01);
			testDA.close();
		} catch (Exception e) {
			fail();
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
	// From ez dago ride-ko geldialdi-zerrendan
	public void tes11() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		String driverEmail="driver@gmail.com";
		String driverName="Driver1";
		
		String from = "Donostia";
		String to = "Bilbo";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		testDA.open();
		Ride r = testDA.addDriverWithRide(driverEmail, driverName, from, to, rideDate, 4, 10,2);
		int rideNumber = r.getRideNumber();
		double dirua = 100.0;
		Traveler t = testDA.createTraveler(travelerEmail, travelerName,dirua);
		testDA.close();
		
		sut.open();
		try {
			boolean res = sut.sortuErreserba(t,  new ErreserbaData(rideNumber,"Bilbo","Zarautz",2));
			assertFalse(res);
			testDA.open();
			assertEquals(dirua, testDA.getTravelerCash(travelerEmail), 0.01);
			testDA.close();
		} catch (Exception e) {
			fail();
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
	// to from baiona baino lehenago dago
	public void test12() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		String driverEmail="driver@gmail.com";
		String driverName="Driver1";
		
		String from = "Donostia";
		String to = "Bilbo";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		testDA.open();
		Ride r = testDA.addDriverWithRide(driverEmail, driverName, from, to, rideDate, 4, 10,2);
		int rideNumber = r.getRideNumber();
		double dirua = 100.0;
		Traveler t = testDA.createTraveler(travelerEmail, travelerName,dirua);
		testDA.close();
		
		sut.open();
		try {
			boolean res = sut.sortuErreserba(t,  new ErreserbaData(rideNumber,"Bilbo","Donostia",2));
			assertFalse(res);
			testDA.open();
			assertEquals(dirua, testDA.getTravelerCash(travelerEmail), 0.01);
			testDA.close();
		} catch (Exception e) {
			fail();
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
	// Traveler ez dauka nahiko dirurik
	public void test13() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		String driverEmail="driver@gmail.com";
		String driverName="Driver1";
		
		String from = "Donostia";
		String to = "Bilbo";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		testDA.open();
		Ride r = testDA.addDriverWithRide(driverEmail, driverName, from, to, rideDate, 4, 10,2);
		int rideNumber = r.getRideNumber();
		double dirua = 0;
		Traveler t = testDA.createTraveler(travelerEmail, travelerName,dirua);
		testDA.close();
		
		sut.open();
		try {
			sut.sortuErreserba(t, new ErreserbaData(rideNumber,"Donostia","Bilbo",2));
			fail();
		} catch(DiruaEzDaukaException e) {
			assertTrue(true);
			testDA.open();
			assertEquals(dirua, testDA.getTravelerCash(travelerEmail), 0.01);
			testDA.close();
		} catch (Exception e) {
			fail();
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
	// Traveler dagoeneko dauka erreserba berdin bat
	public void test14() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		String driverEmail="driver@gmail.com";
		String driverName="Driver1";
		
		String from = "Donostia";
		String to = "Bilbo";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		testDA.open();
		Ride r = testDA.addDriverWithRide(driverEmail, driverName, from, to, rideDate, 4, 10,2);
		int rideNumber = r.getRideNumber();
		double dirua = 100.0;
		Traveler t = testDA.createTraveler(travelerEmail, travelerName,dirua);
		testDA.addErreserbaToTraveler(t, rideNumber, 1, from, to, 1);
		testDA.close();
		
		sut.open();
		try {
			sut.sortuErreserba(t, new ErreserbaData(rideNumber,"Donostia","Bilbo",2));
			fail();
		} catch(ErreserbaAlreadyExistsException e) {
			assertTrue(true);
			testDA.open();
			assertEquals(dirua-1, testDA.getTravelerCash(travelerEmail), 0.01); // dirua-1, lehenengo erreserbarengatik
			testDA.close();
		} catch (Exception e) {
			fail();
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
	// kop parametroarne muga balioaren proba, beheko muga+1 izanik kop
	public void test15() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		String driverEmail="driver@gmail.com";
		String driverName="Driver1";
		
		String from = "Donostia";
		String to = "Bilbo";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		int freeSeats = 4;
		
		testDA.open();
		Ride r = testDA.addDriverWithRide(driverEmail, driverName, from, to, rideDate, freeSeats, 10,freeSeats);
		int rideNumber = r.getRideNumber();
		double dirua = 100.0;
		Traveler t = testDA.createTraveler(travelerEmail, travelerName,dirua);
		testDA.close();
		
		int eserlekuKop = 2;
		
		sut.open();
		try {
			boolean res = sut.sortuErreserba(t, new ErreserbaData(rideNumber,"Donostia","Bilbo",eserlekuKop));
			assertTrue(res);
			double prezioa = r.prezioaKalkulatu(from, to)*eserlekuKop;
			testDA.open();
			assertEquals(dirua - prezioa, testDA.getTravelerCash(travelerEmail), 0.01);
			testDA.close();
		} catch (Exception e) {
			fail();
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
	// kop parametroarne muga balioaren proba, goiko muga-1 izanik kop. Hau da, eskuragarri dauden eseleku kopurua baino bat gutxiago
	public void test16() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		String driverEmail="driver@gmail.com";
		String driverName="Driver1";
		
		String from = "Donostia";
		String to = "Bilbo";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		int freeSeats = 4;
		
		testDA.open();
		Ride r = testDA.addDriverWithRide(driverEmail, driverName, from, to, rideDate, freeSeats, 10,freeSeats);
		int rideNumber = r.getRideNumber();
		double dirua = 100.0;
		Traveler t = testDA.createTraveler(travelerEmail, travelerName,dirua);
		testDA.close();
		
		int eserlekuKop = freeSeats - 1;
		
		sut.open();
		try {
			boolean res = sut.sortuErreserba(t, new ErreserbaData(rideNumber,"Donostia","Bilbo",eserlekuKop));
			assertTrue(res);
			double prezioa = r.prezioaKalkulatu(from, to)*eserlekuKop;
			testDA.open();
			assertEquals(dirua - prezioa, testDA.getTravelerCash(travelerEmail), 0.01);
			testDA.close();
		} catch (Exception e) {
			fail();
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
	// kop parametroarne muga balioaren proba, goiko mugaren berdina izanik kop. Hau da, eskuragarri dauden eseleku kopurua
	public void test17() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		String driverEmail="driver@gmail.com";
		String driverName="Driver1";
		
		String from = "Donostia";
		String to = "Bilbo";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		int freeSeats = 4;
		
		testDA.open();
		Ride r = testDA.addDriverWithRide(driverEmail, driverName, from, to, rideDate, freeSeats, 10,freeSeats);
		int rideNumber = r.getRideNumber();
		double dirua = 100.0;
		Traveler t = testDA.createTraveler(travelerEmail, travelerName,dirua);
		testDA.close();
		
		int eserlekuKop = freeSeats;
		
		sut.open();
		try {
			boolean res = sut.sortuErreserba(t, new ErreserbaData(rideNumber,"Donostia","Bilbo",eserlekuKop));
			assertTrue(res);
			double prezioa = r.prezioaKalkulatu(from, to)*eserlekuKop;
			testDA.open();
			assertEquals(dirua - prezioa, testDA.getTravelerCash(travelerEmail), 0.01);
			testDA.close();
		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
			testDA.open();
			testDA.removeRide(rideNumber);
			testDA.removeUser(travelerEmail);
			testDA.removeUser(driverEmail);
			testDA.close();
		}
	}
}
