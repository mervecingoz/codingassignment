package com.swed.fuel.consumption;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.swed.fuel.consumption.entity.Consumption;
import com.swed.fuel.consumption.entity.Driver;
import com.swed.fuel.consumption.enumaration.FuelType;
import com.swed.fuel.consumption.repository.ConsumptionRepository;
import com.swed.fuel.consumption.service.ConsumptionService;
import com.swed.fuel.consumption.service.ConsumptionServiceImpl;

@RunWith(SpringRunner.class)
public class ConsumptionServiceImplIntegrationTest {
	
	Driver driver = new Driver(0, "harry", "potter");
	@TestConfiguration
	static class ConsumptionServiceImplTestContextConfiguration {

		@Bean
		public ConsumptionService consumptionService() {
			return new ConsumptionServiceImpl();
		}
	}

	@Autowired
	private ConsumptionService consumptionService;

	@MockBean
	private ConsumptionRepository consumptionRepository;

	@Before
	public void setUp() {
		
		Consumption consumption1 = new Consumption(driver, FuelType.FUEL_95, new BigDecimal(10), new BigDecimal(6));
		Consumption consumption2 = new Consumption(driver, FuelType.DIESEL, new BigDecimal(16), new BigDecimal(9));

		List<Consumption> consumptions = new ArrayList<>();
		consumptions.add(consumption1);
		consumptions.add(consumption2);

		Mockito.when(consumptionRepository.findConsumptionByDriverId(driver.getId())).thenReturn(consumptions);
	}
	
	@Test
	public void whenNotValidDriver_thenConsumptionsShouldNotBeFound() {
		Driver driver = new Driver(1, "ron", "weasley");
		List<Consumption> foundConsumptions = consumptionService.findConsumptionByDriverId(driver.getId());
		assertTrue(foundConsumptions.isEmpty());
	}

	@Test
	public void whenValidDriver_thenConsumptionsShouldBeFound() {
		List<Consumption> foundConsumptions = consumptionService.findConsumptionByDriverId(driver.getId());
		assertTrue(!foundConsumptions.isEmpty());
	}

}
