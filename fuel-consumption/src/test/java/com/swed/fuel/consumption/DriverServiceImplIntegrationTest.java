package com.swed.fuel.consumption;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.swed.fuel.consumption.entity.Driver;
import com.swed.fuel.consumption.repository.DriverRepository;
import com.swed.fuel.consumption.service.DriverService;
import com.swed.fuel.consumption.service.DriverServiceImpl;

@RunWith(SpringRunner.class)
public class DriverServiceImplIntegrationTest {

	@TestConfiguration
	static class DriverServiceImplTestContextConfiguration {

		@Bean
		public DriverService driverService() {
			return new DriverServiceImpl();
		}
	}

	@Autowired
	private DriverService driverService;

	@MockBean
	private DriverRepository driverRepository;

	@Before
	public void setUp() {
		Driver driver1 = new Driver(0, "harry", "potter");
		Driver driver2 = new Driver(1, "ron", "granger");
		Driver driver3 = new Driver(2, "hermione", "granger");
		Driver driver4 = new Driver(3, "hermione", "voldemort");
		List<Driver> drivers = new ArrayList<>();
		Mockito.when(driverRepository.findAll()).thenReturn(drivers);
		
		Mockito.when(driverRepository.findDriverByFirstName("harry")).thenReturn(Arrays.asList(driver1));
		Mockito.when(driverRepository.findDriverByFirstName("ron")).thenReturn(Arrays.asList(driver2));
		Mockito.when(driverRepository.findDriverByFirstName("hermione")).thenReturn(Arrays.asList(driver3, driver4));
		Mockito.when(driverRepository.findDriverByLastName("potter")).thenReturn(Arrays.asList(driver1));
		Mockito.when(driverRepository.findDriverByLastName("granger")).thenReturn(Arrays.asList(driver2, driver3));
		Mockito.when(driverRepository.findDriverByLastName("voldemort")).thenReturn(Arrays.asList(driver4));
		Mockito.when(driverRepository.findDriverById(0)).thenReturn(driver1);
		Mockito.when(driverRepository.findDriverById(1)).thenReturn(driver2);
		Mockito.when(driverRepository.findDriverById(2)).thenReturn(driver3);
		Mockito.when(driverRepository.findDriverById(3)).thenReturn(driver4);
	}

	@Test
	public void whenValidId_thenDriverShouldBeFound() {
		long id = 0;
		Driver foundDriver = driverService.findDriverById(id);
		assertTrue(foundDriver != null);
	}

	@Test
	public void whenNotValidId_thenDriverShouldNotBeFound() {
		long id = 100000;
		Driver foundDriver = driverService.findDriverById(id);
		assertTrue(foundDriver == null);
	}

	@Test
	public void whenValidFirstName_thenDriverShouldBeFound() {
		String name = "harry";
		List<Driver> foundDrivers = driverService.findDriverByFirstName(name);
		assertTrue(!foundDrivers.isEmpty());
	}

	@Test
	public void whenNotValidFirstName_thenDriverShouldNotBeFound() {
		String name = "harryY";
		List<Driver> foundDrivers = driverService.findDriverByFirstName(name);
		assertTrue(foundDrivers.isEmpty());
	}

	@Test
	public void whenValidLastName_thenDriverShouldBeFound() {
		String name = "potter";
		List<Driver> foundDrivers = driverService.findDriverByLastName(name);
		assertTrue(!foundDrivers.isEmpty());
	}

	@Test
	public void whenNotValidLastName_thenDriverShouldNotBeFound() {
		String name = "potterR";
		List<Driver> foundDrivers = driverService.findDriverByLastName(name);
		assertTrue(foundDrivers.isEmpty());
	}

}
