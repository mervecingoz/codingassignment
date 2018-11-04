package com.swed.fuel.consumption;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.swed.fuel.consumption.controller.ConsumptionRestController;
import com.swed.fuel.consumption.entity.Consumption;
import com.swed.fuel.consumption.entity.Driver;
import com.swed.fuel.consumption.enumaration.FuelType;
import com.swed.fuel.consumption.service.ConsumptionService;
import com.swed.fuel.consumption.service.DriverService;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(ConsumptionRestController.class)
public class ConsumptionRestControllerIntegrationTest {

	private InputStream is;

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private ConsumptionService consumptionService;

	@Mock
	private DriverService driverService;

	@Spy
	@InjectMocks
	private ConsumptionRestController consumptionRestController = new ConsumptionRestController();

	@Before
	public void init() {
		
		mockMvc = MockMvcBuilders.standaloneSetup(consumptionRestController).build();
		is = consumptionRestController.getClass().getClassLoader().getResourceAsStream("BulkConsumptions.txt");
	}

	@Test
	public void givenConsumtions_whenfindAllConsumptions_thenReturnJsonArray() throws Exception {

		Driver driver = new Driver(0, "harry", "potter");
		Consumption consumption = new Consumption(driver, FuelType.FUEL_95, new BigDecimal(10), new BigDecimal(20));
		List<Consumption> consumptions = Arrays.asList(consumption);

		given(consumptionService.findAllConsumptions()).willReturn(consumptions);

		mockMvc.perform(get("/consumptions")
				.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("[0].id").value(consumption.getId()));

	}
	
	@Test
	public void givenConsumtions_whenfindConsumptionByDriverId_thenReturnJsonArray() throws Exception {

		Driver driver = new Driver(3, "harry", "potter");
		Consumption consumption = new Consumption(driver, FuelType.FUEL_95, new BigDecimal(10), new BigDecimal(20));
		List<Consumption> consumptions = Arrays.asList(consumption);

		given(consumptionService.findConsumptionByDriverId(driver.getId())).willReturn(consumptions);

		mockMvc.perform(get("/consumptions/{driverId}", driver.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("[0].id").value(consumption.getId()));	
	}

	@Test
	public void whensSveConsumption_thenReturnResponseEntity() throws Exception {
		
		mockMvc.perform(post("/consumptions/saveConsumption")
				.content("{\"driver\":{\"id\":1},\"fueltype\":\"DIESEL\",\"price\":5,\"volume\":56,\"date\":\"01.01.2018\"}")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		        .andExpect(status().isOk());
	}

	@Test
	public void whenSaveBulkConsumption_thenReturnResponseEntity() throws Exception {
		
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "BulkConsumptions.txt", "multipart/form-data", is);
		
		mockMvc.perform(MockMvcRequestBuilders.multipart("/consumptions/saveBulkConsumption")
				.file(mockMultipartFile)
				.contentType(MediaType.MULTIPART_FORM_DATA))
		        .andExpect(MockMvcResultMatchers.status().is(200))
				.andReturn();
		;
	}

}
