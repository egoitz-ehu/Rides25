package erreserba_onartu;

import static org.junit.Assert.*;

import java.io.IOException;

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

public class ErreserbaOnartuBlack {
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
	// Erreserba onartu: Erreserba zenbakia eta erabiltzailearen emaila baliozkoak dira
	public void test1() {
		String dMail = "proba@gmail.com";
	    String tMail = "proba2@gmail.com";
	    String psswd = "1234";
	    
	    String from = "Bilbo";
	    String to = "Gasteiz";
	    
	    int eserKop = 3;
	    
	    testDA.open();
	    Ride r = testDA.addDriverWithRide(dMail, psswd, from, to, null, eserKop, 1, eserKop);
	    Driver d = testDA.getDriver(dMail);
	    Traveler t = testDA.createTravelerWithErreserba(tMail, r.getRideNumber(), eserKop, from, to, r.prezioaKalkulatu(from, to));
	    Erreserba e = t.getBookedRides().get(0);
	    testDA.close();
	    
	    sut.open();
	    try {
	    	double prezioa = r.prezioaKalkulatu(from, to);
	    	sut.erreserbaOnartu(e.getEskaeraNum(), dMail);
	    	sut.close();
	    	testDA.open();
	    	t = testDA.getTraveler(tMail);
	    	e = t.getBookedRides().get(0);
	    	d = testDA.getDriver(dMail);
	    	assertEquals(ErreserbaEgoera.ONARTUA, e.getEgoera());
	        assertEquals(0,t.getFrozenMoney(),0.01);
	        assertEquals(prezioa*eserKop, d.getFrozenMoney(), 0.01);
	        assertEquals(1, d.getMugimenduak().size());
	        assertEquals(prezioa*eserKop, d.getMugimenduak().get(0).getKopurua(), 0.01);
	        assertEquals(MugimenduMota.ERRESERBA_ONARTU_GIDARI, d.getMugimenduak().get(0).getType());
	        assertEquals(1, t.getMugimenduak().size());
	        assertEquals(prezioa*eserKop, t.getMugimenduak().get(0).getKopurua(), 0.01);
	        assertEquals(MugimenduMota.ERRESERBA_ONARTU_BIDAIARI, t.getMugimenduak().get(0).getType());
	        testDA.close();
	    } catch (Exception e2) {
	    	fail("Ez da espero exceptionik");
	    	sut.close();
	    } finally {
	    	testDA.open();
	    	testDA.removeRide(r.getRideNumber());
	    	testDA.removeUser(dMail);
	    	testDA.removeUser(tMail);
	    	testDA.close();
	    }
	}
	
	@Test
	// Erreserba ez dago datu basean
	public void test2() {
		
		String dMail = "proba@gmail.com";
	    String tMail = "proba2@gmail.com";
	    String psswd = "1234";
	    
	    String from = "Bilbo";
	    String to = "Gasteiz";
	    
	    int eserKop = 3;
	    
	    testDA.open();
	    Ride r = testDA.addDriverWithRide(dMail, psswd, from, to, null, eserKop, 1, eserKop);
	    Driver d = testDA.getDriver(dMail);
	    Traveler t = testDA.createTraveler(tMail, psswd, 10);
	    testDA.close();
	    
	    sut.open();
	    try {
	    	sut.erreserbaOnartu(10, dMail);
	    	sut.close();
	    	fail();
	    } catch(DatuakNullException e1) {
	    	testDA.open();
		    t = testDA.getTraveler(tMail);
		    d = testDA.getDriver(dMail);
		    testDA.close();
	    	assertEquals(0, d.getMugimenduak().size());
	    	assertEquals(0, t.getMugimenduak().size());
	    } catch (Exception e2) {
	    	fail("Ez da espero exceptionik");
	    	sut.close();
	    } finally {
	    	testDA.open();
	    	testDA.removeRide(r.getRideNumber());
	    	testDA.removeUser(dMail);
	    	testDA.removeUser(tMail);
	    	testDA.close();
	    }
	}
	
	@Test
	// Driver ez dago datu basean
	public void test3() {
		
		String dMail = "proba@gmail.com";
	    String tMail = "proba2@gmail.com";
	    String psswd = "1234";
	    
	    String from = "Bilbo";
	    String to = "Gasteiz";
	    
	    int eserKop = 3;
	    
	    testDA.open();
	    Traveler t = testDA.createTravelerWithErreserba(tMail, 10, eserKop, from, to, 1);
	    Erreserba e = t.getBookedRides().get(0);
	    testDA.close();
	    
	    sut.open();
	    try {
	    	sut.erreserbaOnartu(e.getEskaeraNum(), dMail);
	    	sut.close();
	    	fail();
	    } catch(DatuakNullException e1) {
	    	testDA.open();
		    t = testDA.getTraveler(tMail);
		    e = t.getBookedRides().get(0);
		    testDA.close();
	    	assertEquals(0, t.getMugimenduak().size());
	    	assertEquals(ErreserbaEgoera.ZAIN, e.getEgoera());
	    } catch (Exception e2) {
	    	fail("Ez da espero exceptionik");
	    	sut.close();
	    } finally {
	    	testDA.open();
	    	testDA.removeUser(dMail);
	    	testDA.removeUser(tMail);
	    	testDA.close();
	    }
	}
	
	@Test
	// Driveraren emaila null da
	public void test4() {

		String dMail = "proba@gmail.com";
	    String tMail = "proba2@gmail.com";
	    String psswd = "1234";
	    
	    String from = "Bilbo";
	    String to = "Gasteiz";
	    
	    int eserKop = 3;
	    
	    testDA.open();
	    Traveler t = testDA.createTravelerWithErreserba(tMail, 10, eserKop, from, to, 1);
	    Erreserba e = t.getBookedRides().get(0);
	    testDA.close();
	    
	    sut.open();
	    try {
	    	sut.erreserbaOnartu(e.getEskaeraNum(), null);
	    	sut.close();
	    	testDA.open();
		    t = testDA.getTraveler(tMail);
		    e = t.getBookedRides().get(0);
		    testDA.close();
		    assertEquals(ErreserbaEgoera.ZAIN, e.getEgoera());
	    	assertEquals(0, t.getMugimenduak().size());
	    	fail();
	    } catch (Exception e2) {
	    	fail("Ez da espero exceptionik");
	    	sut.close();
	    } finally {
	    	testDA.open();
	    	testDA.removeUser(dMail);
	    	testDA.removeUser(tMail);
	    	testDA.close();
	    }
	}
	
	@Test
	// Erreserba dagoeneko onartuta dago
	public void test5() {
		String dMail = "proba@gmail.com";
	    String tMail = "proba2@gmail.com";
	    String psswd = "1234";
	    
	    String from = "Bilbo";
	    String to = "Gasteiz";
	    
	    int eserKop = 3;
	    
	    testDA.open();
	    Ride r = testDA.addDriverWithRide(dMail, psswd, from, to, null, eserKop, 1, eserKop);
	    Driver d = testDA.getDriver(dMail);
	    Traveler t = testDA.createTravelerWithErreserba(tMail, r.getRideNumber(), eserKop, from, to, r.prezioaKalkulatu(from, to));
	    Erreserba e = t.getBookedRides().get(0);
	    testDA.changeErreserbaStatus(e.getEskaeraNum(), ErreserbaEgoera.ONARTUA);
	    testDA.close();
	    
	    sut.open();
	    try {
	    	sut.erreserbaOnartu(e.getEskaeraNum(), dMail);
	    	sut.close();
	    	fail();
	    } catch(DagoenekoOnartuaException e2) {
	    	testDA.open();
	    	t = testDA.getTraveler(tMail);
	    	e = t.getBookedRides().get(0);
	    	d = testDA.getDriver(dMail);
	    	testDA.close();
	    	assertEquals(ErreserbaEgoera.ONARTUA, e.getEgoera());
	    	assertEquals(0, t.getMugimenduak().size());
	    	assertEquals(0, d.getMugimenduak().size());
	    } catch (Exception e2) {
	    	fail("Ez da espero exceptionik");
	    	sut.close();
	    } finally {
	    	testDA.open();
	    	testDA.removeRide(r.getRideNumber());
	    	testDA.removeUser(dMail);
	    	testDA.removeUser(tMail);
	    	testDA.close();
	    }
	}
}
