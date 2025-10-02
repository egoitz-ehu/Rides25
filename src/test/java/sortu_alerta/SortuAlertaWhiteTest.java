package sortu_alerta;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

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

public class SortuAlertaWhiteTest {
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
		
		String driverEmail="driver@gmail.com";
		String driverName="Driver1";
		
		String from = "Bilbo";
		String to = "Gasteiz";
		
		Date rideDate = new Date();
		
		testDA.open();
		Ride r = testDA.addDriverWithRide(driverEmail, driverName, from, to, rideDate, 4, 10,2);
		int rideNumber = r.getRideNumber();
		double dirua = 100;
		Traveler t = testDA.createTraveler(travelerEmail, travelerName,dirua);
		testDA.addErreserbaToTraveler(t, rideNumber, 1, from, to, 10);
		testDA.close();
		
		sut.open();
		try {
			sut.sortuErreserba(t, rideNumber, rideNumber, from, to);
			sut.sortuAlerta(travelerEmail, from, to, rideDate);
			sut.close();
			fail();
		} catch (BadagoRideException | DatuakNullException | DiruaEzDaukaException | EserlekurikLibreEzException | AlertaAlreadyExistsException e) {
			sut.close();
			fail();
		} catch (ErreserbaAlreadyExistsException e) {
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
	/*
	@Test
	// Traveler datu basean dago, ez dauka ez alerta ez erreserbarik from,to,date datuekin, baina existitzen da bidaia bat datu berdinekin.
	//Ondorioz BadagoRideException altxatuko du.
	public void test3() {
		String travelerEmail = "traveler@gmail.com";
		String travelerName = "Traveler1";
		
		Traveler t = new Traveler(travelerEmail, null, travelerName, null); // Datu basean ez dagoen Traveler bat
		

		Driver d1 = new Driver("driver@gmail.com", "pass", "Driver1", "Driver1Surname");
		
		String from = "Bilbo";
		String to = "Gasteiz";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		Car c = new Car("1234ABC", 5, "txuria", "Mercedes", d1);
		
		testDA.open();
		testDA.createTraveler(travelerEmail, travelerName, 100);
		List<String> hiria = new ArrayList<String>();
		hiria.add(from);
		hiria.add(to);
		testDA.close();
		
		sut.open();
		try {
			sut.createRide(hiria, new ArrayList<Double>(), rideDate, c, d1.getEmail());
			System.out.println(rideDate + " ASKDPAJPODKASKDPO");
			sut.sortuAlerta(t.getEmail(), from, to, rideDate);
			sut.close();
			fail();
		} catch (AlertaAlreadyExistsException | ErreserbaAlreadyExistsException | RideMustBeLaterThanTodayException |  RideAlreadyExistException e) {
			sut.close();
			fail();
		} catch (BadagoRideException e) {
			sut.close();
			assertTrue(true);
		} finally {
			testDA.open();
			testDA.removeUser(travelerEmail);
			testDA.removeUser(d1.getEmail());
			testDA.close();
		}
	}
	*/
	
	@Test
	// Traveler ez dago datu basean, beraz NullPointerException altxatzen da.
	// Ondorioz, dena ondo joango da eta ez du salbuespenik altxako.
	public void test4() {
		String travelerEmail = "traveler@gmail.com";
				
		String from = "Bilbo";
		String to = "Gasteiz";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		sut.open();
		try {
			sut.sortuAlerta(travelerEmail, from, to, rideDate);
			sut.close();
			assertTrue(true);
		} catch (NullPointerException e) {
			sut.close();
			assertTrue(true);
		} catch(BadagoRideException | ErreserbaAlreadyExistsException | AlertaAlreadyExistsException e) {
			sut.close();
			fail();
		} finally {
			testDA.open();
			testDA.removeUser(travelerEmail);
			testDA.close();
		}
	}
	
	@Test
	// Traveler datu basean dago, ez du alerta ez erreserbarik datu berdinekin eta ez dago bidairik datu berdinekin.
	// Ondorioz, dena ondo joango da eta ez du salbuespenik altxako.
	public void test5() {
		String travelerEmail = "traveler@gmail.com";
		
		String driverEmail="driver@gmail.com";
		
		String from = "Bilbo";
		String to = "Gasteiz";
		
		Date rideDate = new Date(System.currentTimeMillis()+1000000);
		
		testDA.open();
		testDA.createTraveler(travelerEmail, "Traveler", 100);
		
		sut.open();
		try {
			sut.sortuAlerta(travelerEmail, from, to, rideDate);
			sut.close();
			assertTrue(true);
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
