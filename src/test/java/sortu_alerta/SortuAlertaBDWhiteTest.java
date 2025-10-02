package sortu_alerta;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import data_access.DataAccess;
import domain.Car;
import domain.Driver;
import domain.Ride;
import domain.Traveler;
import exceptions.AlertaAlreadyExistsException;
import exceptions.BadagoRideException;
import exceptions.DatuakNullException;
import exceptions.DiruaEzDaukaException;
import exceptions.ErreserbaAlreadyExistsException;
import exceptions.EserlekurikLibreEzException;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.RideAlreadyExistException;

import testOperations.TestDataAccess;

public class SortuAlertaBDWhiteTest {
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
	// Bidaiariak badauka alerta bat lehenagotik pasatako from,to,date datuekin
	//Ondorioz, AlertaAlreadyExistsException altxatu behar du
	public void test1() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		String driverEmail="driver@gmail.com";
		
		String from = "Bilbo";
		String to = "Gasteiz";
		
		Date date = new Date(System.currentTimeMillis()+1000000);
		
		testDA.open();
		double dirua = 100;
		Traveler t = testDA.createTraveler(travelerEmail, travelerName,dirua);
		testDA.addAlertaToTraveler(t, from, to, date);
		testDA.close();
		
		sut.open();
		try {
			sut.sortuAlerta(travelerEmail, from, to, date);
			sut.close();
			fail();
		} catch (BadagoRideException | ErreserbaAlreadyExistsException e) {
			sut.close();
			fail();
		} catch (AlertaAlreadyExistsException e) {
			sut.close();
			assertTrue(true);
		} finally {
			testDA.open();
			testDA.removeUser(travelerEmail);
			testDA.removeUser(driverEmail);
			testDA.close();
		}
	}
	
	@Test
	// Traveler datu basean dago, ez dauka alertarik from,to,date datuekin baina bai erreserba.
	// Ondorioz ErreserbaAlreadyExistsException altxatuko du
	public void test2() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		String from = "Bilbo";
		String to = "Gasteiz";
		
		Date rideDate = new Date();
		
		testDA.open();
		double dirua = 100;
		Traveler t = testDA.createTraveler(travelerEmail, travelerName,dirua);
		testDA.addErreserbaToTraveler(t, 100, 1, from, to, 10);
		testDA.close();
		
		sut.open();
		try {
			sut.sortuAlerta(travelerEmail, from, to, rideDate);
			sut.close();
			fail();
		} catch (BadagoRideException | AlertaAlreadyExistsException e) {
			sut.close();
			fail();
		} catch (ErreserbaAlreadyExistsException e) {
			sut.close();
			assertTrue(true);
		} finally {
			testDA.open();
			testDA.removeUser(travelerEmail);
			testDA.close();
		}
	}
	
	@Test
	// Ez dauka alerta berdina, ezta erreserbarik, baian existitzen da bidaia bat baldintzak betetzen dituena
	public void test3() {
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
			System.out.println(e.getMessage());
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
	
	@Test
	// Traveler datu basean dago, ez du alerta ez erreserbarik datu berdinekin eta ez dago bidairik datu berdinekin.
	// Ondorioz, dena ondo joango da eta ez du salbuespenik altxako.
	public void test4() {
		String travelerEmail = "traveler@gmail.com";
		
		String driverEmail="driver@gmail.com";
		
		String from = "Bilbo";
		String to = "Gasteiz";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		testDA.open();
		testDA.createTraveler(travelerEmail, "Traveler", 100);
		testDA.close();
		sut.open();
		try {
			sut.sortuAlerta(travelerEmail, from, to, rideDate);
			sut.close();
			testDA.open();
			Traveler t = testDA.getTraveler(travelerEmail);
			testDA.close();
			assertEquals(1, t.getAlertaList().size());
			assertEquals(from, t.getAlertaList().get(0).getFrom());
			assertEquals(to, t.getAlertaList().get(0).getTo());
			assertEquals(rideDate, t.getAlertaList().get(0).getDate());
		} catch (Exception e) {
			sut.close();
			fail();
		} finally {
			testDA.open();
			testDA.removeUser(travelerEmail);
			testDA.removeUser(driverEmail);
			testDA.close();
		}
	}
}
