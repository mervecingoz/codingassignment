package com.swed.fuel.consumption.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.swed.fuel.consumption.entity.Consumption;

@Repository
@Service
public interface ConsumptionRepository extends JpaRepository<Consumption, Long> {

	List<Consumption> findConsumptionByDriverId(long driverId);
	
	@Query(value= "SELECT to_char(date, 'mm.yyyy') as month, sum(price) as total_amount FROM Consumption GROUP BY to_char(date, 'mm.yyyy')", nativeQuery = true)
	public List<Map<?,?>> getTotalConsumptionAmountGroupByMonth();
	
	@Query(value= "SELECT to_char(date, 'mm.yyyy') as month, sum(price) as total_amount FROM Consumption where driver_id = :driverId GROUP BY to_char(date, 'mm.yyyy')", nativeQuery = true)
	public List<Map<?,?>> getTotalConsumptionAmountByDriverIdGroupByMonth(@Param("driverId")long driverId);
	
	@Query(value= "SELECT * FROM Consumption WHERE to_char(date, 'mm.yyyy') = to_char(:date, 'mm.yyyy')", nativeQuery = true)
	public List<Map<?,?>> getConsumptionByMonth(@Param("date") Date date);
	
	@Query(value= "SELECT * FROM Consumption WHERE driver_id = :driverId and to_char(date, 'mm.yyyy') = to_char(:date, 'mm.yyyy')", nativeQuery = true)
	public List<Map<?,?>> getConsumptionByDriverIdAndMonth(@Param("driverId")long driverId, @Param("date") Date date);

	@Query(value= "SELECT to_char(date, 'mm.yyyy') as month, fueltype, sum(volume) as total_volume, avg(price) as avarage_price FROM Consumption GROUP BY to_char(date, 'mm.yyyy'), fueltype order by  to_char(date, 'mm.yyyy'), fueltype", nativeQuery = true)
	public List<Map<?,?>> getConsumptionMonthlyStatistics();
	
	@Query(value= "SELECT to_char(date, 'mm.yyyy') as month, fueltype, sum(volume) as total_volume, avg(price) as avarage_price FROM Consumption where driver_id = :driverId GROUP BY to_char(date, 'mm.yyyy'), fueltype order by  to_char(date, 'mm.yyyy'), fueltype", nativeQuery = true)
	public List<Map<?,?>> getConsumptionMonthlyStatisticsByDriverId(@Param("driverId")long driverId);
}
