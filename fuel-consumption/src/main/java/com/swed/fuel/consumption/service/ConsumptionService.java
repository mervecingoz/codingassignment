package com.swed.fuel.consumption.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.swed.fuel.consumption.entity.Consumption;

public interface ConsumptionService {

	public List<Consumption> findAllConsumptions();

	public List<Consumption> findConsumptionByDriverId(long driverId);

	public void saveConsumption(Consumption consumption);

	public void saveBulkConsumption(List<Consumption> consumptionList);

	public void deleteConsumption(long id);

	public List<Map<?,?>> getTotalConsumptionAmountGroupByMonth(long driverId);

	public List<Map<?,?>> getConsumptionByMonth(long driverId, Date date);

	public List<Map<?,?>> getConsumptionMonthlyStatistics(long driverId);

}
