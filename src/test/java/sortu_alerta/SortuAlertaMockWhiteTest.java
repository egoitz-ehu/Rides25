package sortu_alerta;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import data_access.DataAccess;
import domain.Erreserba;
import domain.Ride;
import domain.Traveler;
import exceptions.AlertaAlreadyExistsException;
import exceptions.ErreserbaAlreadyExistsException;
import exceptions.BadagoRideException;

public class SortuAlertaMockWhiteTest {

	static DataAccess sut;
	
	protected MockedStatic <Persistence> persistenceMock;
	
	@Mock
	protected EntityManagerFactory entityManagerFactory;
	@Mock
	protected EntityManager db;
	@Mock
	protected EntityTransaction  et;
	
	@Mock
	protected TypedQuery<Ride> typedQueryRide;
	
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
	// Bidaiariak badauka alerta bat lehenagotik pasatako from,to,date datuekin
	//Ondorioz, AlertaAlreadyExistsException altxatu behar du
	public void test1() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
				
		String from = "Bilbo";
		String to = "Gasteiz";
		
		Date date = new Date(System.currentTimeMillis()+1000000);
		
		Traveler t = new Traveler(travelerEmail, null, travelerName, null);
		t.sortuAlerta(from, to, date);
		
		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		
		sut.open();
		try {
			sut.sortuAlerta(travelerEmail, from, to, date);
			sut.close();
			fail();
		} catch (ErreserbaAlreadyExistsException | BadagoRideException | NullPointerException e) {
			sut.close();
			fail();
		} catch(AlertaAlreadyExistsException e) {
			sut.close();
			assertTrue(true);
		}
	}
	
	@Test
	// Traveler datu basean dago, ez dauka alertarik from,to,date datuekin baina bai erreserba.
	// Ondorioz ErreserbaAlreadyExistsException altxatuko du
	public void test2() {
		String travelerEmail = "traveler@gmail.com";
		Traveler t = new Traveler(travelerEmail, "123", "Pepe", "Lopez");
		
		String from = "Bilbo";
		String to = "Donosti";
		
		Date rideDate = new Date();
		Ride r = new Ride(Arrays.asList("Donostia","Bilbo"),Arrays.asList(1.0),rideDate,3,null,null);

		Erreserba e = t.sortuErreserba(r, 1, from, to, 10);
		System.out.println(e);
		
		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		Mockito.doReturn(r).when(db).find(Traveler.class, r.getRideNumber());

		
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
		}
	}
	
	@Test
	// Ez dauka alerta berdina, ezta erreserbarik, baian existitzen da bidaia bat baldintzak betetzen dituena
	public void test3() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		int rideNumber = 1;
		
		String from = "Bilbo";
		String to = "Gasteiz";
		
		Date rideDate = new Date();
		
		Traveler t = new Traveler(travelerEmail, null, travelerName, null);
		Ride r = new Ride(Arrays.asList(from,to), Arrays.asList(1.2), rideDate, 2, null, null);
		
		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		Mockito.doReturn(r).when(db).find(Ride.class, rideNumber);
		Mockito.when(db.createQuery("SELECT r FROM Ride r WHERE r.egoera = :egoera AND r.date BETWEEN :first AND :last AND r.eserLibre<>0", Ride.class)).thenReturn(typedQueryRide);
		Mockito.when(typedQueryRide.getResultList()).thenReturn(Arrays.asList(r));
		
		sut.open();
		try {
			sut.sortuAlerta(travelerEmail, from, to, rideDate);
			fail();
			sut.close();
		}  catch (AlertaAlreadyExistsException | ErreserbaAlreadyExistsException | NullPointerException e) {
			System.out.println(e.getMessage());
			sut.close();
			fail();
		} catch(BadagoRideException e) {
			sut.close();
			assertTrue(true);
		}
	}

	
	@Test
	// Traveler datu basean dago, ez du alerta ez erreserbarik datu berdinekin eta ez dago bidairik datu berdinekin.
	// Ondorioz, dena ondo joango da eta ez du salbuespenik altxako.
	public void test4() {
		String travelerEmail = "traveler@gmail.com";
		
		
		String from = "Bilbo";
		String to = "Gasteiz";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
				
		Traveler t = new Traveler(travelerEmail, null, "traveler", null);

		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		Mockito.when(db.createQuery("SELECT r FROM Ride r WHERE r.egoera = :egoera AND r.date BETWEEN :first AND :last AND r.eserLibre<>0", Ride.class)).thenReturn(typedQueryRide);
		Mockito.when(typedQueryRide.getResultList()).thenReturn(Arrays.asList());
		
		sut.open();
		try {
			sut.sortuAlerta(t.getEmail(), from, to, rideDate);
			sut.close();
			assertEquals(1, t.getAlertaList().size());
			assertEquals(from, t.getAlertaList().get(0).getFrom());
			assertEquals(to, t.getAlertaList().get(0).getTo());
			assertEquals(rideDate, t.getAlertaList().get(0).getDate());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}
}
