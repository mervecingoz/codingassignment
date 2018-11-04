package com.swed.fuel.consumption.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.google.gson.annotations.SerializedName;

@Entity
public class Driver {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SerializedName("id")
	private long id;

	@NotNull
	@Size(min=2, message=" firstName length must be grater than 2 characters")
	@Size(max=50, message=" firstName length must be less than 50 characters")
	@Column(name = "first_name")
	@SerializedName("firstName")
	private String firstName;

	@NotNull
	@Size(min=2, message=" lastName length must be grater than 2 characters")
	@Size(max=50, message=" lastName length must be less than 50 characters")
	@Column(name = "last_name")
	@SerializedName("lastName")
	private String lastName;

	protected Driver() {
	}

	public Driver(long id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public Driver(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Driver [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}

}
