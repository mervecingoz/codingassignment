package com.swed.fuel.consumption;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.swed.fuel.consumption.entity.Driver;
import com.swed.fuel.consumption.repository.DriverRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DriverRepositoryIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private DriverRepository driverRepository;
	
	@Test
	public void whenFindDriverById_thenReturnDriver() {
		Driver driver = new Driver("harry", "potter");
		Driver persisted = entityManager.persist(driver);
	    entityManager.flush();
	    
	    Driver foundDriver = driverRepository.findDriverById(persisted.getId());
	    
	    assertThat(foundDriver, is(persisted));
	}
	
	@Test
	public void whenFindDriverByFirstName_thenReturnDriver() {
		
	    Driver driver1 = new Driver("t_harry", "potter");
	    Driver driver2 = new Driver("t_harry", "weasley");
	    Driver driver3 = new Driver("hermione", "granger");
	    entityManager.persist(driver1);
	    entityManager.persist(driver2);
	    entityManager.persist(driver3);
	    entityManager.flush();
	    
	    List<Driver> expectedList = new ArrayList<Driver>();
	    expectedList.add(driver1);
	    expectedList.add(driver2);
	 
	    List<Driver> foundDriverList = driverRepository.findDriverByFirstName("t_harry");
	 
	    assertThat(foundDriverList, is(expectedList));
	}
	
	@Test
	public void whenFindDriverByLastName_thenReturnDriver() {
		
	    Driver driver1 = new Driver("harry", "potter");
	    Driver driver2 = new Driver("ron", "t_weasley");
	    Driver driver3 = new Driver("hermione", "t_weasley");
	    entityManager.persist(driver1);
	    entityManager.persist(driver2);
	    entityManager.persist(driver3);
	    entityManager.flush();
	    
	    List<Driver> expectedList = new ArrayList<Driver>();
	    expectedList.add(driver2);
	    expectedList.add(driver3);
	 
	    List<Driver> foundDriverList = driverRepository.findDriverByLastName("t_weasley");
	 
	    assertThat(foundDriverList, is(expectedList));
	}

}
