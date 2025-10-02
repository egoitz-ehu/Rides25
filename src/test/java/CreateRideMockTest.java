import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import data_access.DataAccess;
import domain.Car;
import domain.Driver;
import domain.Geldialdia;
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

public class CreateRideMockTest {
	
	static DataAccess sut;
	
	protected MockedStatic <Persistence> persistenceMock;

	@Mock
	protected  EntityManagerFactory entityManagerFactory;
	@Mock
	protected  EntityManager db;
	@Mock
    protected  EntityTransaction  et;
	

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
	
	
	Driver driver;
	
	@Test
	//sut.createRide:  The Driver("iker driver", "driver1@gmail.com") HAS one ride "from" "to" in that "date". 
	public void test1() {


        
		String driverEmail="driver1@gmail.com";
		String driverName="Aitor Fernandez";

		String rideFrom="Donostia";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=new Date();
		try {
			rideDate = sdf.parse("05/10/2025");
		} catch (ParseException e) {
			e.printStackTrace();
		}	
		try {
					
			 driver=new Driver(driverEmail,null,driverName,null);
			 driver.addRide(Arrays.asList(rideFrom,rideTo), Arrays.asList(2.2), rideDate, 2, new Car("123",2,"gorria","seat",driver));
			 
			//configure the state through mocks 
	        Mockito.when(db.find(Driver.class, driver.getEmail())).thenReturn(driver);
	        Mockito.when(db.find(Car.class, "123")).thenReturn(new Car("123",2,"gorria","seat",driver));
		
			
			//invoke System Under Test (sut)  
			sut.open();
		    sut.createRide(Arrays.asList(rideFrom,rideTo), Arrays.asList(2.2), rideDate, new Car("123",2,"gorria","seat",driver), driverEmail);
			sut.close();
			
			fail();
			
		   } catch (RideAlreadyExistException e) {
			 //verify the results
				sut.close();
				assertTrue(true);
			} catch (RideMustBeLaterThanTodayException e) {
			fail();
		} 
	} 
	@Test
	//sut.createRide:  The Driver("Aitor Fernandez", "driver1@gmail.com") HAS NOT one ride "from" "to" in that "date". 
	public void test2() {
		//define parameters
		String driverName="Aitor Fernandez";
		String driverEmail="driver1@gmail.com";

		String rideFrom="Donostia";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("05/10/2025");
		} catch (ParseException e) {
			e.printStackTrace();
		}	
		
		try {
			Driver driver1=new Driver(driverEmail,null,driverName,null);

			//configure the state through mocks 
	        Mockito.when(db.find(Driver.class, driver1.getEmail())).thenReturn(driver1);
	        Mockito.when(db.find(Car.class, "123")).thenReturn(new Car("123",2,"gorria","seat",driver));
					
			//invoke System Under Test (sut)  
			sut.open();
			Ride ride = sut.createRide(Arrays.asList(rideFrom,rideTo), Arrays.asList(2.2), rideDate, new Car("123",2,"gorria","seat",driver), driverEmail);
			sut.close();
			//verify the results
			assertNotNull(ride);
			List<Geldialdia> hiriList=ride.getGeldialdiak();
			assertEquals(hiriList.get(0).getHiria(),rideFrom);
			assertEquals(hiriList.get(1).getHiria(),rideTo);
			assertEquals(ride.getDate(),rideDate);
			
			
		   } catch (RideAlreadyExistException | RideMustBeLaterThanTodayException e) {
			// if the program goes to this point fail  
			fail();
			
			}  
	} 
	@Test
	//sut.createRide:  The Driver is null. The test must return null. If  an Exception is returned the createRide method is not well implemented.
		public void test3() {
			try {
				
				//define parameters
				driver=null;

				String rideFrom="Donostia";
				String rideTo="Zarautz";
				
				String driverEmail=null;

				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date rideDate=null;
				try {
					rideDate = sdf.parse("05/10/2025");
				} catch (ParseException e) {
					e.printStackTrace();
				}	
				
				
				Mockito.when(db.find(Driver.class, null)).thenThrow(NullPointerException.class);
				Mockito.when(db.find(Car.class, "123")).thenReturn(new Car("123",2,"gorria","seat",driver));
				
				//invoke System Under Test (sut)  
				sut.open();
				Ride ride = sut.createRide(Arrays.asList(rideFrom,rideTo), Arrays.asList(2.2), rideDate, new Car("123",2,"gorria","seat",driver), driverEmail);
				System.out.println("ride "+ride);

				//verify the results
				assertNull(ride);
				
				
			   } catch (RideAlreadyExistException | RideMustBeLaterThanTodayException e) {
				// if the program goes to this point fail  
				fail();

				}   finally {
					sut.close();
				}
			
			   } 
	@Test
	//sut.createRide:  The ride "from" is null. The test must return null. If  an Exception is returned the createRide method is not well implemented.

	//This method detects a fail in createRide method because the method does not check if the parameters are null, and the ride is created.
	
	public void test4() {
		String driverName="Aitor Fernandez";

		String driverEmail="driver1@gmail.com";
		String rideFrom=null;
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("05/10/2025");
		} catch (ParseException e) {
			e.printStackTrace();
		}	
		Ride ride=null;
		try {
			//configure the state through mocks 

			driver=new Driver(driverEmail,null,driverName,null);
	        Mockito.when(db.find(Driver.class, driver.getEmail())).thenReturn(driver);
	        Mockito.when(db.find(Car.class, "123")).thenReturn(new Car("123",2,"gorria","seat",driver));
			
	        //invoke System Under Test (sut)  
			sut.open();
			ride = sut.createRide(Arrays.asList(rideFrom,rideTo), Arrays.asList(2.2), rideDate, new Car("123",2,"gorria","seat",driver), driverEmail);
			sut.close();			
			//verify the results
			assertNull(ride);
			
		   } catch (Exception e) {
			   fail();
		   }
	}

}
