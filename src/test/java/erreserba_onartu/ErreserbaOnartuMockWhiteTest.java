package erreserba_onartu;

import static org.junit.Assert.*;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
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

public class ErreserbaOnartuMockWhiteTest {
	
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
	// Sartutako identifikatzailea duen erreserbarik datu basean
	public void test1() {
		String travelerEmail = "traveler1@gmail.com";
		String travelerPassword = "traveler1";
		
		String driverEmail = "hola@gmail.com";
		String driverPassword = "driver1";
		
		int rideNumber = 10;
		int seatNumber = 2;
		
		int eskaeraNum = 10;
		
		String from = "Bilbo";
		String to = "Gasteiz";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		Traveler t = new Traveler(travelerEmail, travelerPassword, "Traveler1", "666666666");
		
		Driver d = new Driver(driverEmail, driverPassword, "Driver1", "777777777");

		Ride r = d.addRide(Arrays.asList(from,to), Arrays.asList(1.2), rideDate, seatNumber, null);

		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		Mockito.doReturn(r).when(db).find(Ride.class, rideNumber);
		Mockito.doReturn(d).when(db).find(Driver.class, driverEmail);
		Mockito.doReturn(null).when(db).find(Erreserba.class, eskaeraNum);
		
		sut.open();
		try {
			sut.erreserbaOnartu(eskaeraNum, driverEmail);
			fail();
		} catch(DatuakNullException e1) {
			assertTrue(true);
			assertEquals(Collections.emptyList(),t.getMugimenduak());
			assertEquals(Collections.emptyList(),d.getMugimenduak());
		} catch (Exception e1) {
			fail();
		} finally {
			sut.close();
		}
	}
	
	@Test
	// Erreserba dagoeneko onartuta dago
	public void test2() {
		String travelerEmail = "traveler1@gmail.com";
		String travelerPassword = "traveler1";
		
		String driverEmail = "hola@gmail.com";
		String driverPassword = "driver1";
		
		int rideNumber = 10;
		int seatNumber = 2;
		
		int eskaeraNum = 10;
		
		String from = "Bilbo";
		String to = "Gasteiz";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		Traveler t = new Traveler(travelerEmail, travelerPassword, "Traveler1", "666666666");
		
		Driver d = new Driver(driverEmail, driverPassword, "Driver1", "777777777");

		Ride r = d.addRide(Arrays.asList(from,to), Arrays.asList(1.2), rideDate, seatNumber, null);
		
		Erreserba e = t.sortuErreserba(r, 1, from, to, r.prezioaKalkulatu(from, to));
		e.setEskaeraNum(eskaeraNum);
		e.setEgoera(ErreserbaEgoera.ONARTUA);
		
		r.gehituErreserba(e);
		
		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		Mockito.doReturn(r).when(db).find(Ride.class, rideNumber);
		Mockito.doReturn(d).when(db).find(Driver.class, driverEmail);
		Mockito.doReturn(e).when(db).find(Erreserba.class, eskaeraNum);
		
		sut.open();
		try {
			sut.erreserbaOnartu(eskaeraNum, driverEmail);
			fail();
		} catch(DagoenekoOnartuaException e1) {
			assertTrue(true);
			assertEquals(r.prezioaKalkulatu(from, to), t.getFrozenMoney(),0.01);
			assertEquals(Collections.emptyList(),t.getMugimenduak());
			assertEquals(Collections.emptyList(),d.getMugimenduak());
		} catch (Exception e1) {
			fail();
		} finally {
			sut.close();
		}
	}
	
	@Test
	// Driver ez dago datu basean
	public void test3() {
		String travelerEmail = "traveler1@gmail.com";
		String travelerPassword = "traveler1";
		
		String driverEmail = "hola@gmail.com";
		String driverPassword = "driver1";
		
		int rideNumber = 10;
		int seatNumber = 2;
		
		int eskaeraNum = 10;
		
		String from = "Bilbo";
		String to = "Gasteiz";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		Traveler t = new Traveler(travelerEmail, travelerPassword, "Traveler1", "666666666");
		
		Driver d = new Driver(driverEmail, driverPassword, "Driver1", "777777777");

		Ride r = d.addRide(Arrays.asList(from,to), Arrays.asList(1.2), rideDate, seatNumber, null);
		
		Erreserba e = t.sortuErreserba(r, 1, from, to, r.prezioaKalkulatu(from, to));
		e.setEskaeraNum(eskaeraNum);
		
		r.gehituErreserba(e);
		
		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		Mockito.doReturn(r).when(db).find(Ride.class, rideNumber);
		Mockito.doReturn(null).when(db).find(Driver.class, driverEmail);
		Mockito.doReturn(e).when(db).find(Erreserba.class, eskaeraNum);
		
		sut.open();
		try {
			sut.erreserbaOnartu(eskaeraNum, driverEmail);
			fail();
		} catch(DatuakNullException e1) {
			assertTrue(true);
			assertEquals(r.prezioaKalkulatu(from, to), t.getFrozenMoney(),0.01);
			assertEquals(Collections.emptyList(),t.getMugimenduak());
		} catch (Exception e1) {
			fail();
		} finally {
			sut.close();
		}
	}
	
	@Test
	// Erreserba ondo onartu
	public void test4() {
		String travelerEmail = "traveler1@gmail.com";
		String travelerPassword = "traveler1";
		
		String driverEmail = "hola@gmail.com";
		String driverPassword = "driver1";
		
		int rideNumber = 10;
		int seatNumber = 2;
		
		int eskaeraNum = 10;
		
		String from = "Bilbo";
		String to = "Gasteiz";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		Traveler t = new Traveler(travelerEmail, travelerPassword, "Traveler1", "666666666");
		
		Driver d = new Driver(driverEmail, driverPassword, "Driver1", "777777777");

		Ride r = d.addRide(Arrays.asList(from,to), Arrays.asList(1.2), rideDate, seatNumber, null);
		
		Erreserba e = t.sortuErreserba(r, 1, from, to, r.prezioaKalkulatu(from, to));
		e.setEskaeraNum(eskaeraNum);
		
		r.gehituErreserba(e);
		
		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		Mockito.doReturn(r).when(db).find(Ride.class, rideNumber);
		Mockito.doReturn(d).when(db).find(Driver.class, driverEmail);
		Mockito.doReturn(e).when(db).find(Erreserba.class, eskaeraNum);
		
		sut.open();
		try {
			sut.erreserbaOnartu(eskaeraNum, driverEmail);
			assertEquals(r.prezioaKalkulatu(from, to), d.getFrozenMoney(),0.01);
			assertEquals(0.0, t.getFrozenMoney(),0.01);
			assertEquals(ErreserbaEgoera.ONARTUA, e.getEgoera());
			assertEquals(1, d.getMugimenduak().size());
			assertEquals(MugimenduMota.ERRESERBA_ONARTU_GIDARI, d.getMugimenduak().get(0).getType());
			assertEquals(1, t.getMugimenduak().size());
			assertEquals(MugimenduMota.ERRESERBA_ONARTU_BIDAIARI, t.getMugimenduak().get(0).getType());
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			fail();
		} finally {
			sut.close();
		}
	}
}
