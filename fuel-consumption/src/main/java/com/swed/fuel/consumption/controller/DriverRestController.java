package com.swed.fuel.consumption.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swed.fuel.consumption.entity.Driver;
import com.swed.fuel.consumption.service.DriverService;
import com.swed.fuel.consumption.validation.DriverValidatorImpl;

@RestController
public class DriverRestController {
	
	@Autowired
	private DriverService driverService;
	
	@InitBinder("driver")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new DriverValidatorImpl());
    }

	@GetMapping("/drivers")
	public List<Driver> findAllDrivers() {
		return driverService.findAllDrivers();
	}
	
	@GetMapping("/drivers/{id}")
	public Driver findDriverById(@PathVariable long id) {
		 return driverService.findDriverById(id);
	}

	@RequestMapping("drivers/findDriverByFirstName")
	public List<Driver> findDriverByFirstName(@RequestParam("firstName") String firstName) {
		return driverService.findDriverByFirstName(firstName);
	}

	@RequestMapping("drivers/findDriverByLastName")
	public List<Driver> findDriverByLastName(@RequestParam("lastName") String lastName) {
		return driverService.findDriverByLastName(lastName);
	}

	@RequestMapping("/drivers/save")
	public ResponseEntity<String> saveDriver(@Valid @RequestBody Driver driver) {
		driverService.saveDriver(driver);			
		return new ResponseEntity<>("Driver is saved!", HttpStatus.OK);
	}

	@DeleteMapping("/drivers/delete/{id}")
	public ResponseEntity<String> deleteDriver(@PathVariable long id) {
		driverService.deleteDriver(id);
		return new ResponseEntity<>("Driver is deleted!", HttpStatus.OK);
	}

}
