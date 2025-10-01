package erreserba_onartu;

import static org.junit.Assert.*;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import data_access.DataAccess;
import domain.Driver;
import domain.Erreserba;
import domain.ErreserbaEgoera;
import domain.MugimenduMota;
import domain.Ride;
import domain.Traveler;
import exceptions.DagoenekoOnartuaException;
import exceptions.DatuakNullException;

public class ErreserbaOnartuMockBlack {

	static DataAccess sut;
	
	protected MockedStatic <Persistence> persistenceMock;
	
	@Mock
	protected EntityManagerFactory entityManagerFactory;
	
	@Mock
	protected EntityManager db;
	
	@Mock
	protected EntityTransaction  et;

	@Before
    public  void init() {
        MockitoAnnotations.openMocks(this);
        persistenceMock = Mockito.mockStatic(Persistence.class);
		persistenceMock.when(() -> Persistence.createEntityManagerFactory(Mockito.any()))
        .thenReturn(entityManagerFactory);
        
        Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
		Mockito.doReturn(et).when(db).getTransaction();
	    sut=new DataAccess(db);
    }
	
	@After
    public  void tearDown() {
		persistenceMock.close();
    }
	
	@Test
	// Erreserba onartu: Erreserba zenbakia eta erabiltzailearen emaila baliozkoak dira
	public void test1() {
	    String dMail = "proba@gmail.com";
	    String tMail = "proba2@gmail.com";
	    
	    Driver d = new Driver(dMail, "1234", null, null);
	    Ride r = d.addRide(Arrays.asList("Donostia","Bilbo"), Arrays.asList(1.0),
	            null, 2, null);
	    
	    double prezioa = r.prezioaKalkulatu("Donostia", "Bilbo");
	    
	    Traveler t = new Traveler(tMail, "1234", null, null);
	    t.setCash(100);
	    
	    int eNum = 10;
	    Erreserba e =t.sortuErreserba(r, 2, "Donostia", "Bilbo", prezioa);
	    e.setEskaeraNum(eNum);
	    
	    Mockito.doReturn(d).when(db).find(Driver.class, dMail);
	    Mockito.doReturn(e).when(db).find(Erreserba.class, e.getEskaeraNum());
	    Mockito.doReturn(t).when(db).find(Traveler.class, tMail);
	    
	    sut.open();
	    try {
	        sut.erreserbaOnartu(eNum, dMail);

	        Mockito.verify(et).begin();
	        assertEquals(ErreserbaEgoera.ONARTUA, e.getEgoera());
	        assertEquals(0,t.getFrozenMoney(),0.01);
	        assertEquals(prezioa*2, d.getFrozenMoney(), 0.01);
	        assertEquals(1, d.getMugimenduak().size());
	        assertEquals(prezioa*2, d.getMugimenduak().get(0).getKopurua(), 0.01);
	        assertEquals(MugimenduMota.ERRESERBA_ONARTU_GIDARI, d.getMugimenduak().get(0).getType());
	        assertEquals(1, t.getMugimenduak().size());
	        assertEquals(prezioa*2, t.getMugimenduak().get(0).getKopurua(), 0.01);
	        assertEquals(MugimenduMota.ERRESERBA_ONARTU_BIDAIARI, t.getMugimenduak().get(0).getType());
	        Mockito.verify(et).commit();
	    } catch (Exception ex) {
	        System.out.println(ex.getMessage());
	        fail("Ez da espero exceptionik");
	    } finally {
	        sut.close();
	    }
	}
	
	@Test
	// Erreserba ez dago datu basean
	public void test2() {
		String dMail = "proba@gmail.com";
	    String tMail = "proba2@gmail.com";
	    
	    Driver d = new Driver(dMail, "1234", null, null);
	    Ride r = d.addRide(Arrays.asList("Donostia","Bilbo"), Arrays.asList(1.0),
	            null, 2, null);
	    
	    double prezioa = r.prezioaKalkulatu("Donostia", "Bilbo");
	    
	    Traveler t = new Traveler(tMail, "1234", null, null);
	    t.setCash(100);
	    
	    int eNum = 10;
	    
	    Mockito.doReturn(d).when(db).find(Driver.class, dMail);
	    Mockito.doReturn(null).when(db).find(Erreserba.class, eNum);
	    Mockito.doReturn(t).when(db).find(Traveler.class, tMail);
	    
	    sut.open();
	    try {
	        sut.erreserbaOnartu(eNum, dMail);
	        fail();
	    } catch (DatuakNullException e) {
	    	 assertEquals(0, d.getMugimenduak().size());
	    	 assertEquals(0, t.getMugimenduak().size());
	    } catch (Exception ex) {
	        System.out.println(ex.getMessage());
	        fail("Ez da espero exceptionik");
	    } finally {
	        sut.close();
	    }
	}
	
	@Test
	// Driver ez dago datu basean
	public void test3() {
		String dMail = "proba@gmail.com";
	    String tMail = "proba2@gmail.com";
	    
	    double prezioa = 1;
	    
	    Traveler t = new Traveler(tMail, "1234", null, null);
	    t.setCash(100);
	    
	    int eNum = 10;
	    Erreserba e =t.sortuErreserba(null, 2, "Donostia", "Bilbo", prezioa);
	    e.setEskaeraNum(eNum);
	    
	    Mockito.doReturn(null).when(db).find(Driver.class, dMail);
	    Mockito.doReturn(null).when(db).find(Erreserba.class, eNum);
	    Mockito.doReturn(t).when(db).find(Traveler.class, tMail);
	    
	    sut.open();
	    try {
	        sut.erreserbaOnartu(eNum, dMail);
	        fail();
	    } catch (DatuakNullException e2) {
	    	assertEquals(ErreserbaEgoera.ZAIN, e.getEgoera());
	    	assertEquals(0, t.getMugimenduak().size());
	    } catch (Exception ex) {
	        System.out.println(ex.getMessage());
	        fail("Ez da espero exceptionik");
	    } finally {
	        sut.close();
	    }
	}
	
	@Test
	// Driveraren emaila null da
	public void test4() {
	    String tMail = "proba2@gmail.com";
	    
	    double prezioa = 1;
	    
	    Traveler t = new Traveler(tMail, "1234", null, null);
	    t.setCash(100);
	    
	    int eNum = 10;
	    Erreserba e =t.sortuErreserba(null, 2, "Donostia", "Bilbo", prezioa);
	    e.setEskaeraNum(eNum);
	    
	    Mockito.doReturn(null).when(db).find(Erreserba.class, eNum);
	    Mockito.doReturn(t).when(db).find(Traveler.class, tMail);
	    
	    sut.open();
	    try {
	        sut.erreserbaOnartu(eNum, null);
	        assertEquals(ErreserbaEgoera.ZAIN, e.getEgoera());
	    	assertEquals(0, t.getMugimenduak().size());
	    } catch (Exception ex) {
	        System.out.println(ex.getMessage());
	        fail("Ez da espero exceptionik");
	    } finally {
	        sut.close();
	    }
	}
	
	@Test
	// Erreserba dagoeneko onartuta dago
	public void test5() {
		String dMail = "proba@gmail.com";
	    String tMail = "proba2@gmail.com";
	    
	    Driver d = new Driver(dMail, "1234", null, null);
	    Ride r = d.addRide(Arrays.asList("Donostia","Bilbo"), Arrays.asList(1.0),
	            null, 2, null);
	    
	    double prezioa = r.prezioaKalkulatu("Donostia", "Bilbo");
	    
	    Traveler t = new Traveler(tMail, "1234", null, null);
	    t.setCash(100);
	    
	    int eNum = 10;
	    Erreserba e =t.sortuErreserba(r, 2, "Donostia", "Bilbo", prezioa);
	    e.setEskaeraNum(eNum);
	    e.setEgoera(ErreserbaEgoera.ONARTUA);
	    
	    Mockito.doReturn(d).when(db).find(Driver.class, dMail);
	    Mockito.doReturn(e).when(db).find(Erreserba.class, e.getEskaeraNum());
	    Mockito.doReturn(t).when(db).find(Traveler.class, tMail);
	    
	    sut.open();
	    try {
	        sut.erreserbaOnartu(eNum, dMail);
	        fail();
	    } catch (DagoenekoOnartuaException e2) {
	    	assertEquals(ErreserbaEgoera.ONARTUA, e.getEgoera());
	    	assertEquals(0, t.getMugimenduak().size());
	    	assertEquals(0, d.getMugimenduak().size());
	    } catch (Exception ex) {
	        System.out.println(ex.getMessage());
	        fail("Ez da espero exceptionik");
	    } finally {
	        sut.close();
	    }
	}
}
