package com.swed.fuel.consumption.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swed.fuel.consumption.entity.Driver;
import com.swed.fuel.consumption.repository.DriverRepository;

@Service
public class DriverServiceImpl implements DriverService{

	@Autowired
	private DriverRepository driverRepository;

	public DriverServiceImpl() {
	}

	public List<Driver> findAllDrivers() {
		return driverRepository.findAll();
	}
	
	public Driver findDriverById(long id) {
		return driverRepository.findDriverById(id);
	}

	public List<Driver> findDriverByFirstName(String firstName) {
		 return driverRepository.findDriverByFirstName(firstName);
	}

	public List<Driver> findDriverByLastName(String lastName) {
		return driverRepository.findDriverByLastName(lastName);
	}

	public void saveDriver(Driver driver) {
		driverRepository.save(driver);
	}

	public void deleteDriver(long id) {
		driverRepository.deleteById(id);
	}

}
