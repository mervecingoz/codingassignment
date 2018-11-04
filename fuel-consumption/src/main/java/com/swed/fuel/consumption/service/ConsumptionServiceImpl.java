package com.swed.fuel.consumption.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swed.fuel.consumption.entity.Consumption;
import com.swed.fuel.consumption.repository.ConsumptionRepository;

@Service
public class ConsumptionServiceImpl implements ConsumptionService {

	@Autowired
	private ConsumptionRepository consumptionRepository;

	public ConsumptionServiceImpl() {
	}

	public List<Consumption> findAllConsumptions() {
		return consumptionRepository.findAll();
	}

	public List<Consumption> findConsumptionByDriverId(long driverId) {
		return consumptionRepository.findConsumptionByDriverId(driverId);
	}

	public void saveConsumption(Consumption consumption) {
		consumptionRepository.save(consumption);
	}

	public void saveBulkConsumption(List<Consumption> consumptionList) {
		consumptionRepository.saveAll(consumptionList);
	}

	public void deleteConsumption(long id) {
		consumptionRepository.deleteById(id);
	}

	public List<Map<?,?>> getTotalConsumptionAmountGroupByMonth(long driverId) {
		if (driverId > 0) {
			return consumptionRepository.getTotalConsumptionAmountByDriverIdGroupByMonth(driverId);
		}
		return consumptionRepository.getTotalConsumptionAmountGroupByMonth();
	}

	public List<Map<?,?>> getConsumptionByMonth(long driverId, Date date) {
		if (driverId > 0) {
			return consumptionRepository.getConsumptionByDriverIdAndMonth(driverId, date);
		}
		return consumptionRepository.getConsumptionByMonth(date);
	}

	public List<Map<?,?>> getConsumptionMonthlyStatistics(long driverId) {
		if (driverId > 0) {
			return consumptionRepository.getConsumptionMonthlyStatisticsByDriverId(driverId);
		}
		return consumptionRepository.getConsumptionMonthlyStatistics();
	}

}
