package com.swed.fuel.consumption.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.swed.fuel.consumption.entity.Consumption;
import com.swed.fuel.consumption.service.ConsumptionService;
import com.swed.fuel.consumption.validation.ConsumptionValidatorImpl;

@RestController
public class ConsumptionRestController {

	private static final String DATE_FORMAT_DDMMYYY = "dd.MM.yyyy";
	private static final String DATE_FORMAT_MMYYY = "MM.yyyy";

	@Autowired
	private ConsumptionService consumptionService;

	@InitBinder("consumption")
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(new ConsumptionValidatorImpl());
	}

	@GetMapping("/consumptions")
	public List<Consumption> findAllConsumptions() {
		return consumptionService.findAllConsumptions();
	}
	
	@GetMapping("/consumptions/{driverId}")
	public List<Consumption> findConsumptionByDriverId(@PathVariable(value = "driverId") long driverId) {
		 return consumptionService.findConsumptionByDriverId(driverId);
	}

	@RequestMapping("/consumptions/saveConsumption")
	public ResponseEntity<String> saveConsumption(@Valid @RequestBody Consumption consumption) {
		consumptionService.saveConsumption(consumption);
		return new ResponseEntity<>("Consumption is saved!", HttpStatus.OK);
	}

	@PostMapping("/consumptions/saveBulkConsumption")
	public ResponseEntity<String> saveBulkConsumption(@Valid @RequestParam("file") MultipartFile file,RedirectAttributes redirectAttributes) throws IOException {
		String fileContent = new String(file.getBytes()).replaceAll("\\s", "");
		Type listType = new TypeToken<ArrayList<Consumption>>() {}.getType();
		Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT_DDMMYYY).create();
		List<Consumption> consumptionCollection = gson.fromJson(fileContent, listType);
		consumptionService.saveBulkConsumption(consumptionCollection);
		return new ResponseEntity<>("Consumption is saved!", HttpStatus.OK);
	}

	@PostMapping("/consumptions/getTotalConsumptionAmountGroupByMonth")
	public List<Map<?,?>> getTotalConsumptionAmountGroupByMonth(@RequestParam(value = "driverId", required = false) String driverId) {
		 List<Map<?,?>> totalConsumptionAmountGroupByMonth = consumptionService.getTotalConsumptionAmountGroupByMonth((driverId != null && !driverId.isEmpty()) ? Long.valueOf(driverId) : 0);
	return totalConsumptionAmountGroupByMonth;
	}

	@PostMapping("/consumptions/getConsumptionByMonth")
	public List<Map<?,?>> getConsumptionByMonth(@RequestParam(value = "driverId", required = false) String driverId, @RequestParam("date") @DateTimeFormat(pattern = DATE_FORMAT_MMYYY) Date date) {
		return consumptionService.getConsumptionByMonth((driverId != null && !driverId.isEmpty()) ? Long.valueOf(driverId) : 0, date);
	}

	@PostMapping("/consumptions/getConsumptionMonthlyStatistics")
	public List<Map<?,?>> getConsumptionByMonth(@RequestParam(value = "driverId", required = false) String driverId) {
		return consumptionService.getConsumptionMonthlyStatistics((driverId != null && !driverId.isEmpty()) ? Long.valueOf(driverId) : 0);
	}

}
