package com.swed.fuel.consumption.service;

import java.util.List;

import com.swed.fuel.consumption.entity.Driver;

public interface DriverService {

	public List<Driver> findAllDrivers();

	public Driver findDriverById(long id);

	public List<Driver> findDriverByFirstName(String firstName);

	public List<Driver> findDriverByLastName(String lastName);

	public void saveDriver(Driver driver);

	public void deleteDriver(long id);

}
