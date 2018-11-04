package com.swed.fuel.consumption;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.swed.fuel.consumption.entity.Consumption;
import com.swed.fuel.consumption.entity.Driver;
import com.swed.fuel.consumption.enumaration.FuelType;
import com.swed.fuel.consumption.repository.ConsumptionRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ConsumptionRepositoryIntegrationTest {
 
    @Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private ConsumptionRepository consumptionRepository;
    
    @Test
	public void whenFindConsumptionByDriver_thenReturnConsumptions() {
		Driver driver = new Driver("harry", "potter");
		entityManager.persist(driver);
	    entityManager.flush();
	    
		Consumption consumption1 = new Consumption(driver, FuelType.FUEL_95, new BigDecimal(10), new BigDecimal(6));
		Consumption consumption2 = new Consumption(driver, FuelType.FUEL_98, new BigDecimal(13), new BigDecimal(5));
	    
	    List<Consumption> persistedConsumptions = new ArrayList<Consumption>();
	    persistedConsumptions.add(consumption1);
	    persistedConsumptions.add(consumption2);
	    entityManager.persist(consumption1);
	    entityManager.persist(consumption2);
	    entityManager.flush();
	    
	    List<Consumption> foundConsumptions = consumptionRepository.findConsumptionByDriverId(driver.getId());
	    
	    assertThat(foundConsumptions, is(persistedConsumptions));
	}    
 
}