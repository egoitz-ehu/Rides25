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
import domain.Ride;
import domain.Traveler;
import exceptions.DatuakNullException;
import exceptions.DiruaEzDaukaException;
import exceptions.ErreserbaAlreadyExistsException;
import exceptions.EserlekurikLibreEzException;

public class SortuErreserbaMockBlackTest {
	
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
	// Erreserba arrakastaz sortu da
	public void test1() {
		String travelerEmail = "traveler1@gmail.com";
		Traveler t = new Traveler(travelerEmail, "123", "Pepe", "Lopez");
		double dirua = 100.0;
		t.diruaSartu(dirua);
		
		int erosiEserlekuak = 1;
		
		int rideNumber = 100;
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		Ride r = new Ride(Arrays.asList("Donostia","Bilbo"),Arrays.asList(1.0),rideDate,3,null,null);
		
		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		Mockito.doReturn(r).when(db).find(Ride.class, rideNumber);
		
		sut.open();
		try {
			boolean res = sut.sortuErreserba(t, rideNumber, erosiEserlekuak, "Donostia", "Bilbo");
			assertTrue(res);
			double prezioa = r.prezioaKalkulatu("Donostia","Bilbo")*erosiEserlekuak;
			assertEquals(dirua-prezioa, t.getCash(), 0.01);
		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
		}
	}
	
	@Test
	// Traveler null da
	public void test2() {
		int rideNumber = 100;
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		Ride r = new Ride(Arrays.asList("Donostia","Bilbo"),Arrays.asList(1.0),rideDate,3,null,null);
		
		Mockito.doReturn(r).when(db).find(Ride.class, rideNumber);
		
		sut.open();
		try {
			sut.sortuErreserba(null, rideNumber, 2, "Donostia", "Bilbo");
			fail();
		} catch(DatuakNullException e) {
			assertTrue(true);
		} catch (Exception e) {
			System.out.println(e.getClass());
			fail();
		} finally {
			sut.close();
		}
	}
	
	
	@Test
	// Ride zenbakia <= 0 da
	public void test3() {
		String travelerEmail = "traveler1@gmail.com";
		Traveler t = new Traveler(travelerEmail, "123", "Pepe", "Lopez");
		double dirua = 100.0;
		t.diruaSartu(dirua);
		
		int rideNumber = -1;
		
		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		Mockito.doReturn(null).when(db).find(Ride.class, rideNumber);
		
		sut.open();
		try {
			sut.sortuErreserba(t, rideNumber, 2, "Donostia", "Bilbo");
			fail();
		} catch(DatuakNullException e) {
			assertTrue(true);
			assertEquals(dirua, t.getCash(), 0.01);
		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
		}
	}
	
	@Test
	// Eskatutako eserleku kopurua <= 0 da
	public void test4() {
		String travelerEmail = "traveler1@gmail.com";
		Traveler t = new Traveler(travelerEmail, "123", "Pepe", "Lopez");
		double dirua = 100.0;
		t.diruaSartu(dirua);
		
		int rideNumber = 100;
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		Ride r = new Ride(Arrays.asList("Donostia","Bilbo"),Arrays.asList(1.0),rideDate,3,null,null);
		
		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		Mockito.doReturn(r).when(db).find(Ride.class, rideNumber);
		
		sut.open();
		try {
			boolean res = sut.sortuErreserba(t, rideNumber, 0, "Donostia", "Bilbo");
			assertFalse(res);
			assertEquals(dirua, t.getCash(), 0.01);
		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
		}
	}
	
	@Test
	// From null da
	public void test5() {
		String travelerEmail = "traveler1@gmail.com";
		Traveler t = new Traveler(travelerEmail, "123", "Pepe", "Lopez");
		double dirua = 100.0;
		t.diruaSartu(dirua);
		
		int rideNumber = 100;
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		Ride r = new Ride(Arrays.asList("Donostia","Bilbo"),Arrays.asList(1.0),rideDate,3,null,null);
		
		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		Mockito.doReturn(r).when(db).find(Ride.class, rideNumber);
		
		sut.open();
		try {
			boolean res = sut.sortuErreserba(t, rideNumber, 2, null, "Bilbo");
			assertFalse(res);
			assertEquals(dirua, t.getCash(), 0.01);
		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
		}
	}
	
	@Test
	// To null da
	public void test6() {
		String travelerEmail = "traveler1@gmail.com";
		Traveler t = new Traveler(travelerEmail, "123", "Pepe", "Lopez");
		double dirua = 100.0;
		t.diruaSartu(dirua);
		
		int rideNumber = 100;
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		Ride r = new Ride(Arrays.asList("Donostia","Bilbo"),Arrays.asList(1.0),rideDate,3,null,null);
		
		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		Mockito.doReturn(r).when(db).find(Ride.class, rideNumber);
		
		sut.open();
		try {
			boolean res = sut.sortuErreserba(t, rideNumber, 2, "Donostia", null);
			assertFalse(res);
			assertEquals(dirua, t.getCash(), 0.01);
		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
		}
	}
	
	@Test
	// Ride ez dago datu basean
	public void test7() {
		String travelerEmail = "traveler1@gmail.com";
		Traveler t = new Traveler(travelerEmail, "123", "Pepe", "Lopez");
		double dirua = 100.0;
		t.diruaSartu(dirua);
		
		int rideNumber = 100;
		
		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		Mockito.doReturn(null).when(db).find(Ride.class, rideNumber);
		
		sut.open();
		try {
			sut.sortuErreserba(t, rideNumber, 2, "Donostia", "Bilbo");
			fail();
		} catch(DatuakNullException e) {
			assertTrue(true);
			assertEquals(dirua, t.getCash(), 0.01);
		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
		}
	}
	
	@Test
	// t ez dago datu basean
	public void test8() {
		String travelerEmail = "traveler1@gmail.com";
		Traveler t = new Traveler(travelerEmail, "123", "Pepe", "Lopez");
		double dirua = 100.0;
		t.diruaSartu(dirua);
		
		int rideNumber = 100;
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		Ride r = new Ride(Arrays.asList("Donostia","Bilbo"),Arrays.asList(1.0),rideDate,3,null,null);
		
		Mockito.doReturn(null).when(db).find(Traveler.class, travelerEmail);
		Mockito.doReturn(r).when(db).find(Ride.class, rideNumber);
		
		sut.open();
		try {
			sut.sortuErreserba(t, rideNumber, 2, "Donostia", "Bilbo");
			fail();
		} catch(DatuakNullException e) {
			assertTrue(true);
			assertEquals(dirua, t.getCash(), 0.01);
		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
		}
	}
	
	@Test
	// Ez dago nahiko eserlekurik libre
	public void test9() {
		String travelerEmail = "traveler1@gmail.com";
		Traveler t = new Traveler(travelerEmail, "123", "Pepe", "Lopez");
		double dirua = 100.0;
		t.diruaSartu(dirua);
		
		int rideNumber = 100;
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		Ride r = new Ride(Arrays.asList("Donostia","Bilbo"),Arrays.asList(1.0),rideDate,1,null,null);
		
		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		Mockito.doReturn(r).when(db).find(Ride.class, rideNumber);
		
		sut.open();
		try {
			sut.sortuErreserba(t, rideNumber, 2, "Donostia", "Bilbo");
			fail();
		} catch(EserlekurikLibreEzException e) {
			assertTrue(true);
			assertEquals(dirua, t.getCash(), 0.01);
		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
		}
	}
	
	@Test
	// From ez dago ride-ko geldialdien artean
	public void test10() {
		String travelerEmail = "traveler1@gmail.com";
		Traveler t = new Traveler(travelerEmail, "123", "Pepe", "Lopez");
		double dirua = 100.0;
		t.diruaSartu(dirua);
		
		int rideNumber = 100;
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		Ride r = new Ride(Arrays.asList("Donostia","Bilbo"),Arrays.asList(1.0),rideDate,3,null,null);
		
		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		Mockito.doReturn(r).when(db).find(Ride.class, rideNumber);
		
		sut.open();
		try {
			boolean res = sut.sortuErreserba(t, rideNumber, 2, "Zarautz", "Bilbo");
			assertFalse(res);
			assertEquals(dirua, t.getCash(), 0.01);
		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
		}
	}
	
	@Test
	// To ez dago ride-ko geldialdien artean
	public void test11() {
		String travelerEmail = "traveler1@gmail.com";
		Traveler t = new Traveler(travelerEmail, "123", "Pepe", "Lopez");
		double dirua = 100.0;
		t.diruaSartu(dirua);
		
		int rideNumber = 100;
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		Ride r = new Ride(Arrays.asList("Donostia","Bilbo"),Arrays.asList(1.0),rideDate,3,null,null);
		
		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		Mockito.doReturn(r).when(db).find(Ride.class, rideNumber);
		
		sut.open();
		try {
			boolean res = sut.sortuErreserba(t, rideNumber, 2, "Bilbo", "Zarautz");
			assertFalse(res);
			assertEquals(dirua, t.getCash(), 0.01);
		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
		}
	}
	
	@Test
	// to from baino lehenago agertatzen da geldialdien artean
	public void test12() {
		String travelerEmail = "traveler1@gmail.com";
		Traveler t = new Traveler(travelerEmail, "123", "Pepe", "Lopez");
		double dirua = 100.0;
		t.diruaSartu(dirua);
		
		int rideNumber = 100;
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		Ride r = new Ride(Arrays.asList("Donostia","Bilbo"),Arrays.asList(1.0),rideDate,3,null,null);
		
		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		Mockito.doReturn(r).when(db).find(Ride.class, rideNumber);
		
		sut.open();
		try {
			boolean res = sut.sortuErreserba(t, rideNumber, 2, "Bilbo", "Donostia");
			assertFalse(res);
			assertEquals(dirua, t.getCash(), 0.01);
		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
		}
	}
	
	@Test
	// Traveler diru nahikorik ez dauka
	public void test13() {
		String travelerEmail = "traveler1@gmail.com";
		Traveler t = new Traveler(travelerEmail, "123", "Pepe", "Lopez");
		
		int rideNumber = 100;
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		Ride r = new Ride(Arrays.asList("Donostia","Bilbo"),Arrays.asList(1.0),rideDate,3,null,null);
		
		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		Mockito.doReturn(r).when(db).find(Ride.class, rideNumber);
		
		sut.open();
		try {
			sut.sortuErreserba(t, rideNumber, 2, "Donostia", "Bilbo");
			fail();
		} catch(DiruaEzDaukaException e) {
			assertTrue(true);
			assertEquals(0.0, t.getCash(), 0.01);
		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
		}
	}
	
	@Test
	// Travelerrak dagoeneko badauka erreserba bidaia horretarako
	public void test14() {
		String travelerEmail = "traveler1@gmail.com";
		Traveler t = new Traveler(travelerEmail, "123", "Pepe", "Lopez");
		double dirua = 100.0;
		t.diruaSartu(dirua);
		
		int rideNumber = 100;
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		Ride r = new Ride(Arrays.asList("Donostia","Bilbo"),Arrays.asList(1.0),rideDate,3,null,null);
		
		t.sortuErreserba(r, 1, "Donsotia", "Bilbo", 1);
		
		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		Mockito.doReturn(r).when(db).find(Ride.class, rideNumber);
		
		sut.open();
		try {
			sut.sortuErreserba(t, rideNumber, 2, "Donostia", "Bilbo");
			fail();
		} catch(ErreserbaAlreadyExistsException e) {
			assertTrue(true);
			assertEquals(dirua-1, t.getCash(), 0.01); // lehenengo erreserbarengatik dirua gutxitu egin zaio
		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
		}
	}
	
	@Test
	// kop parametroarne muga balioaren proba, beheko muga+1 izanik kop
	public void test15() {
		String travelerEmail = "traveler1@gmail.com";
		Traveler t = new Traveler(travelerEmail, "123", "Pepe", "Lopez");
		double dirua = 100.0;
		t.diruaSartu(dirua);
		
		int erosiEserlekuak = 2;
		
		int rideNumber = 100;
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		Ride r = new Ride(Arrays.asList("Donostia","Bilbo"),Arrays.asList(1.0),rideDate,5,null,null);
		
		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		Mockito.doReturn(r).when(db).find(Ride.class, rideNumber);
		
		sut.open();
		try {
			boolean res = sut.sortuErreserba(t, rideNumber, erosiEserlekuak, "Donostia", "Bilbo");
			assertTrue(res);
			double prezioa = r.prezioaKalkulatu("Donostia","Bilbo")*erosiEserlekuak;
			assertEquals(dirua-prezioa, t.getCash(), 0.01);
		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
		}
	}
	
	@Test
	// kop parametroarne muga balioaren proba, goiko muga-1 izanik kop. Hau da, eskuragarri dauden eseleku kopurua baino bat gutxiago
	public void test16() {
		String travelerEmail = "traveler1@gmail.com";
		Traveler t = new Traveler(travelerEmail, "123", "Pepe", "Lopez");
		double dirua = 100.0;
		t.diruaSartu(dirua);
		
		int libre = 5;
		
		int erosiEserlekuak = libre-1;
		
		int rideNumber = 100;
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		Ride r = new Ride(Arrays.asList("Donostia","Bilbo"),Arrays.asList(1.0),rideDate,libre,null,null);
		
		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		Mockito.doReturn(r).when(db).find(Ride.class, rideNumber);
		
		sut.open();
		try {
			boolean res = sut.sortuErreserba(t, rideNumber, erosiEserlekuak, "Donostia", "Bilbo");
			assertTrue(res);
			double prezioa = r.prezioaKalkulatu("Donostia","Bilbo")*erosiEserlekuak;
			assertEquals(dirua-prezioa, t.getCash(), 0.01);
		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
		}
	}
	
	@Test
	// kop parametroarne muga balioaren proba, goiko mugaren berdina izanik kop. Hau da, eskuragarri dauden eseleku kopurua
	public void test17() {
		String travelerEmail = "traveler1@gmail.com";
		Traveler t = new Traveler(travelerEmail, "123", "Pepe", "Lopez");
		double dirua = 100.0;
		t.diruaSartu(dirua);
		
		int libre = 5;
		
		int erosiEserlekuak = libre;
		
		int rideNumber = 100;
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		Ride r = new Ride(Arrays.asList("Donostia","Bilbo"),Arrays.asList(1.0),rideDate,libre,null,null);
		
		Mockito.doReturn(t).when(db).find(Traveler.class, travelerEmail);
		Mockito.doReturn(r).when(db).find(Ride.class, rideNumber);
		
		sut.open();
		try {
			boolean res = sut.sortuErreserba(t, rideNumber, erosiEserlekuak, "Donostia", "Bilbo");
			assertTrue(res);
			double prezioa = r.prezioaKalkulatu("Donostia","Bilbo")*erosiEserlekuak;
			assertEquals(dirua-prezioa, t.getCash(), 0.01);
		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
		}
	}
}
