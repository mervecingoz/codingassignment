package com.swed.fuel.consumption;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.swed.fuel.consumption.controller.DriverRestController;
import com.swed.fuel.consumption.entity.Driver;
import com.swed.fuel.consumption.service.DriverService;

@RunWith(SpringRunner.class)
@WebMvcTest(DriverRestController.class)
public class DriverRestControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DriverService driverService;
	
	@Test
	public void givenDrivers_whenFindAllDrivers_thenReturnJsonArray() throws Exception {

		Driver driver = new Driver(0, "harry", "potter");
		List<Driver> drivers = Arrays.asList(driver);

		given(driverService.findAllDrivers()).willReturn(drivers);
		
		mockMvc.perform(get("/drivers")
				.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("[0].id").value(driver.getId()));	 
		 
		mockMvc.perform(get("/drivers")
				.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("[0].firstName").value(driver.getFirstName()));
		 
		mockMvc.perform(get("/drivers")
				.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("[0].lastName").value(driver.getLastName()));
	}


	@Test
	public void givenDrivers_whenFindDriverById_thenReturnJson() throws Exception {

		Driver driver = new Driver(5, "harry", "potter");
		given(driverService.findDriverById(driver.getId())).willReturn(driver);
		
		mockMvc.perform(get("/drivers/{id}", driver.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("id").value(driver.getId()));
		
	}
	
	@Test
	public void givenDrivers_whenFindDriverByFirstName_thenReturnJsonArray() throws Exception {

		Driver driver1 = new Driver(0, "harry", "potter");
		Driver driver2 = new Driver(1, "harry", "malfoy");
		List<Driver> drivers = Arrays.asList(driver1, driver2);

		given(driverService.findDriverByFirstName(driver1.getFirstName())).willReturn(drivers);

		mockMvc.perform(get("/drivers/findDriverByFirstName")
				.param("firstName", driver1.getFirstName())
				.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("[0].id").value(driver1.getId()))
				.andExpect(jsonPath("[1].id").value(driver2.getId()));	 
	}
	
	@Test
	public void givenDrivers_whenFindDriverByLastName_thenReturnJsonArray() throws Exception {

		Driver driver1 = new Driver(0, "harry", "potter");
		Driver driver2 = new Driver(1, "ron", "potter");
		List<Driver> drivers = Arrays.asList(driver1, driver2);

		given(driverService.findDriverByLastName(driver1.getLastName())).willReturn(drivers);
		
		mockMvc.perform(get("/drivers/findDriverByLastName")
				.param("lastName", driver1.getLastName())
				.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("[0].id").value(driver1.getId()))
				.andExpect(jsonPath("[1].id").value(driver2.getId()));
	}
	
	@Test
	public void whenSaveDriver_thenReturnResponseEntity() throws Exception {
		
		String jsonContent = "{\"firstName\":\"harry\", \"lastName\":\"potter\"}";
		
		mockMvc.perform(post("/drivers/save")
				.content(jsonContent)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		        .andExpect(status().isOk());
	}
	
	@Test
	public void whenDelete_thenReturnResponseEntity() throws Exception {
		Driver driver = new Driver(0, "harry", "potter");
		List<Driver> drivers = Arrays.asList(driver);

		given(driverService.findAllDrivers()).willReturn(drivers);
		
		mockMvc.perform(delete("/drivers/delete/{id}", driver.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

}
