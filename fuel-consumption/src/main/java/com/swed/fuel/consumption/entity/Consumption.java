package com.swed.fuel.consumption.entity;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swed.fuel.consumption.enumaration.FuelType;

@Entity
public class Consumption {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "driver_id")
	private Driver driver;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private FuelType fueltype;
	
	@NotNull
	@Column(name = "price")
	private BigDecimal price;
	
	@NotNull
	@Column(name = "volume")
	private BigDecimal volume;
	
	@NotNull
	@Column(name = "date")
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	@JsonFormat(pattern = "dd.MM.yyyy")
	private Date date;

	protected Consumption() {
	}

	public Consumption(Driver driver, FuelType fuelType, BigDecimal price, BigDecimal volume) {
		super();
		this.driver = driver;
		this.fueltype = fuelType;
		this.price = price;
		this.volume = volume;
		this.date = Calendar.getInstance().getTime();
	}
	
	public Consumption(Driver driver, FuelType fuelType, BigDecimal price, BigDecimal volume, Date date) {
		super();
		this.driver = driver;
		this.fueltype = fuelType;
		this.price = price;
		this.volume = volume;
		this.date = date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	
	public FuelType getFueltype() {
		return fueltype;
	}

	public void setFueltype(FuelType fueltype) {
		this.fueltype = fueltype;
	}	

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Registration [id=" + id + ", driver=" + driver + ", price=" + price + ", volume=" + volume + ", date="
				+ date + "]";
	}

}
