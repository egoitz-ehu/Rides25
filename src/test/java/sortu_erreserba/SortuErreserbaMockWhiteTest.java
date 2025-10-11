package sortu_erreserba;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Date;

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
import domain.ErreserbaData;
import domain.Ride;
import domain.Traveler;
import exceptions.DatuakNullException;
import exceptions.DiruaEzDaukaException;
import exceptions.ErreserbaAlreadyExistsException;
import exceptions.EserlekurikLibreEzException;

public class SortuErreserbaMockWhiteTest {

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
	// Eserleku kopurua ez da 1 edo gehiago
	public void test1() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		int rideNumber = 1;
		
		String from = "Bilbo";
		String to = "Gasteiz";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		Traveler t = new Traveler(travelerEmail, null, travelerName, null);
		Ride r = new Ride(Arrays.asList(from,to), Arrays.asList(1.2), rideDate, 2, null, null);
		
		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		Mockito.doReturn(r).when(db).find(Ride.class, rideNumber);
		
		sut.open();
		try {
			boolean res = sut.sortuErreserba(t, new ErreserbaData(rideNumber, from, to, 0));
			assertFalse(res);
			assertEquals(0,t.getCash(),0.01);
		} catch (EserlekurikLibreEzException | ErreserbaAlreadyExistsException | DiruaEzDaukaException | DatuakNullException e) {
			fail();
		}
	}
	
	@Test
	// Traveler datu basean dago, baina dagoeneko badu erreserba berdin bat. Ondorioz ErreserbaAlreadyExistsException jaurtiko du
	public void test2() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		int rideNumber = 1;
		
		String from = "Bilbo";
		String to = "Gasteiz";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		Traveler t = new Traveler(travelerEmail, null, travelerName, null);
		Ride r = new Ride(Arrays.asList(from,to), Arrays.asList(1.2), rideDate, 2, null, null);
		
		t.sortuErreserba(r, 1, from, to, 1.2);
		
		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		Mockito.doReturn(r).when(db).find(Ride.class, rideNumber);
		
		sut.open();
		try {
			sut.sortuErreserba(t, new ErreserbaData(rideNumber, from, to, 1));
			fail();
		} catch (EserlekurikLibreEzException | DiruaEzDaukaException | DatuakNullException e) {
			fail();
		} catch (ErreserbaAlreadyExistsException e) {
			assertTrue(true);
			assertEquals(0,t.getCash(),0.01);
		} 
	}
	
	@Test
	// Traveler datu basean dago eta ez dauka dirurik. Ondorioz DiruaEzDaukaException jaurtiko du
	public void test3() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		int rideNumber = 1;
		
		String from = "Bilbo";
		String to = "Gasteiz";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		Traveler t = new Traveler(travelerEmail, null, travelerName, null);
		Ride r = new Ride(Arrays.asList(from,to), Arrays.asList(1.2), rideDate, 2, null, null);
		
		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		Mockito.doReturn(r).when(db).find(Ride.class, rideNumber);
		
		sut.open();
		try {
			sut.sortuErreserba(t,  new ErreserbaData(rideNumber, from, to, 1));
			fail();
		} catch (EserlekurikLibreEzException | ErreserbaAlreadyExistsException | DatuakNullException e) {
			fail();
		} catch (DiruaEzDaukaException e) {
			assertTrue(true);
			assertEquals(0,t.getCash(),0.01);
		} 
	}
	
	@Test
	// Traveler datu basean dago, ez dauka erreserbarik eta dirua dauka. Eserleku kopurua 1 edo gehiago da.
	// Baina bidaiaren eserlekuak ez daude libre. Ondorioz EserlekurikLibreEzException jaurtiko du
	public void test4() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		int rideNumber = 1;
		
		String from = "Bilbo";
		String to = "Gasteiz";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		Traveler t = new Traveler(travelerEmail, null, travelerName, null);
		double dirua = 1000;
		t.diruaSartu(dirua);
		
		Ride r = new Ride(Arrays.asList(from,to), Arrays.asList(1.2), rideDate, 2, null, null);
		r.setEserLibre(0);
		
		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		Mockito.doReturn(r).when(db).find(Ride.class, rideNumber);
		
		sut.open();
		try {
			sut.sortuErreserba(t,  new ErreserbaData(rideNumber, from, to, 1));
			fail();
		} catch (DiruaEzDaukaException | ErreserbaAlreadyExistsException | DatuakNullException e) {
			fail();
		} catch (EserlekurikLibreEzException e) {
			assertTrue(true);
			assertEquals(dirua,t.getCash(),0.01);
		} 
	}
	
	@Test
	// Traveler ez dago datu basean
	public void test5() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		int rideNumber = 1;
		
		String from = "Bilbo";
		String to = "Gasteiz";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		Traveler t = new Traveler(travelerEmail, null, travelerName, null);
		
		Ride r = new Ride(Arrays.asList(from,to), Arrays.asList(1.2), rideDate, 2, null, null);
		
		Mockito.doReturn(null).when(db).find(Traveler.class, travelerEmail); // t ez dago datu basean
		Mockito.doReturn(r).when(db).find(Ride.class, rideNumber);
		
		sut.open();
		try {
			sut.sortuErreserba(t,  new ErreserbaData(rideNumber, from, to, 1));
			fail();
		} catch (DiruaEzDaukaException | EserlekurikLibreEzException | ErreserbaAlreadyExistsException e) {
			fail();
		} catch (DatuakNullException e) {
			assertTrue(true);
			assertEquals(0,t.getCash(),0.01);
		}
	}
	@Test
	// Traveler datu basean dago, ez dauka erreserbarik eta dirua dauka.
	// Eserleku kopurua 1 edo gehiago da. Erreserba ondo sortuko da
	public void test6() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		int rideNumber = 1;
		
		String from = "Bilbo";
		String to = "Gasteiz";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		Traveler t = new Traveler(travelerEmail, null, travelerName, null);
		double dirua = 1000;
		t.diruaSartu(dirua);
		
		Ride r = new Ride(Arrays.asList(from,to), Arrays.asList(1.2), rideDate, 2, null, null);
		
		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		Mockito.doReturn(r).when(db).find(Ride.class, rideNumber);
		
		sut.open();
		try {
			boolean res = sut.sortuErreserba(t,  new ErreserbaData(rideNumber, from, to, 1));
			assertTrue(res);
			double prezioa = r.prezioaKalkulatu(from, to);
			assertEquals(dirua-prezioa,t.getCash(),0.01);
		} catch (DiruaEzDaukaException | EserlekurikLibreEzException | ErreserbaAlreadyExistsException | DatuakNullException e) {
			fail();
		}
	}
}
