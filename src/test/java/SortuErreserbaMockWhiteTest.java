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
import domain.Ride;
import domain.Traveler;
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
			boolean res = sut.sortuErreserba(t, rideNumber, 0, from, to);
			assertFalse(res);
		} catch (EserlekurikLibreEzException | ErreserbaAlreadyExistsException | DiruaEzDaukaException e) {
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
			sut.sortuErreserba(t, rideNumber, 1, from, to);
			fail();
		} catch (EserlekurikLibreEzException | DiruaEzDaukaException e) {
			fail();
		} catch (ErreserbaAlreadyExistsException e) {
			assertTrue(true);
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
			sut.sortuErreserba(t, rideNumber, 1, from, to);
			fail();
		} catch (EserlekurikLibreEzException | ErreserbaAlreadyExistsException e) {
			fail();
		} catch (DiruaEzDaukaException e) {
			assertTrue(true);
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
		t.diruaSartu(1000);
		
		Ride r = new Ride(Arrays.asList(from,to), Arrays.asList(1.2), rideDate, 2, null, null);
		r.setEserLibre(0);
		
		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		Mockito.doReturn(r).when(db).find(Ride.class, rideNumber);
		
		sut.open();
		try {
			sut.sortuErreserba(t, rideNumber, 1, from, to);
			fail();
		} catch (DiruaEzDaukaException | ErreserbaAlreadyExistsException e) {
			fail();
		} catch (EserlekurikLibreEzException e) {
			assertTrue(true);
		} 
	}
	
	@Test
	// Traveler datu basean dago, ez dauka erreserbarik eta dirua dauka.
	// Eserleku kopurua 1 edo gehiago da. Erreserba ondo sortuko da
	public void test5() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		int rideNumber = 1;
		
		String from = "Bilbo";
		String to = "Gasteiz";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		Traveler t = new Traveler(travelerEmail, null, travelerName, null);
		t.diruaSartu(1000);
		
		Ride r = new Ride(Arrays.asList(from,to), Arrays.asList(1.2), rideDate, 2, null, null);
		
		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		Mockito.doReturn(r).when(db).find(Ride.class, rideNumber);
		
		sut.open();
		try {
			boolean res = sut.sortuErreserba(t, rideNumber, 1, from, to);
			assertTrue(res);
		} catch (DiruaEzDaukaException | EserlekurikLibreEzException | ErreserbaAlreadyExistsException e) {
			fail();
		}
	}
}
