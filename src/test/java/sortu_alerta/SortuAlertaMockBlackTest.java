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
import exceptions.BadagoRideException;
import exceptions.ErreserbaAlreadyExistsException;

public class SortuAlertaMockBlackTest {
	
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
	// Alerta arrakastaz sortu da
	public void test1() {
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
	
	@Test
	// Traveler null da
	public void test2() {
		String from = "Donostia";
		String to = "Bilbo";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
				
		sut.open();
		try {
			sut.sortuAlerta(null, from, to, rideDate);
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
		Traveler t = new Traveler(travelerEmail, null, "traveler", null);
		
		String from = null;
		String to = "Bilbo";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		
		sut.open();
		try {
			sut.sortuAlerta(travelerEmail, from, to, rideDate);
			assertEquals(0, t.getAlertaList().size());
		} catch(Exception e) {
			fail();
		} finally {
			sut.close();
		}
	}
	
	@Test
	// to null da
	public void test4() {
		String travelerEmail = "traveler@gmail.com";
		Traveler t = new Traveler(travelerEmail, null, "traveler", null);

		String from = "Donosti";
		String to = null;
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);

		sut.open();
		try {
			sut.sortuAlerta(travelerEmail, from, to, rideDate);
			assertEquals(0, t.getAlertaList().size());
		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
		}
	}
	
	@Test
	// date null da
	public void test5() {
		String travelerEmail = "traveler1@gmail.com";
		Traveler t = new Traveler(travelerEmail, "123", "Pepe", "Lopez");

		String from = "Donosti";
		String to = "Bilbo";
		
		Date rideDate = null;
		
		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		
		sut.open();
		try {
			sut.sortuAlerta(travelerEmail, from, to, rideDate);
			assertEquals(0, t.getAlertaList().size());
		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
		}
	}
	
	@Test
	// traveler ez dago datu basean
	public void test6() {
		String travelerEmail = "traveler@gmail.com";

		String from = "Donostia";
		String to = "Bilbo";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);

		Mockito.doReturn(null).when(db).find(Traveler.class, travelerEmail);

		
		sut.open();
		try {
			sut.sortuAlerta(travelerEmail, from, to, rideDate);
			assertTrue(true);
		} catch(Exception e2) {
			fail();
		}finally {
			sut.close();
		}
	}
	
	@Test
	// Travelerrak badu alerta bat from,to,date datu berdinekin.
	public void test7() {
		String travelerEmail = "traveler@gmail.com";
		Traveler t = new Traveler(travelerEmail, "123", "Pepe", "Lopez");
		
		String from = "Donostia";
		String to = "Bilbo";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		t.sortuAlerta(from, to, rideDate);
		
		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		
				
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
		}
	}
	
	@Test
	// Travelerrak badu erreserba eginda from,to,date datuekin
	public void test8() {
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
	// Travelerrak sortu nahi duen bidaia iada existitzen da.
	public void test9() {
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
	// Data iraganean da. Ez litzateke alerta sortu behar.
	public void test10() {
		String travelerEmail = "traveler@gmail.com";


		String from = "Bilbo";
		String to = "Gasteiz";

		Date rideDate = new Date(System.currentTimeMillis()-1000000);

		Traveler t = new Traveler(travelerEmail, null, "traveler", null);

		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		Mockito.when(db.createQuery("SELECT r FROM Ride r WHERE r.egoera = :egoera AND r.date BETWEEN :first AND :last AND r.eserLibre<>0", Ride.class)).thenReturn(typedQueryRide);
		Mockito.when(typedQueryRide.getResultList()).thenReturn(Arrays.asList());

		sut.open();
		try {
			sut.sortuAlerta(t.getEmail(), from, to, rideDate);
			sut.close();
			assertEquals(0, t.getAlertaList().size());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	// Data gaurkoa da. Alerta sortu behar da.
	public void test13() {
		String travelerEmail = "traveler@gmail.com";


		String from = "Bilbo";
		String to = "Gasteiz";

		Date rideDate = new Date(System.currentTimeMillis());

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
