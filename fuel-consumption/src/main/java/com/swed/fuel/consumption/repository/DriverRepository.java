package com.swed.fuel.consumption.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.swed.fuel.consumption.entity.Driver;

@Repository
@Service
public interface DriverRepository extends JpaRepository<Driver, Long> {

	Driver findDriverById(long id);
	
	List<Driver> findDriverByFirstName(String firstName);

	List<Driver> findDriverByLastName(String lastName);

}
