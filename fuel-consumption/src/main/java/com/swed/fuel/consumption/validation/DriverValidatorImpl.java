package com.swed.fuel.consumption.validation;

import static com.swed.fuel.consumption.validation.ValidationUtil.VALIDATION_ERROR_EMPTY_PARAMETER;
import static com.swed.fuel.consumption.validation.ValidationUtil.VALIDATION_ERROR_STRING_PARAMETER_MAX_LENGTH;
import static com.swed.fuel.consumption.validation.ValidationUtil.VALIDATION_ERROR_STRING_PARAMETER_MIN_LENGTH;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.swed.fuel.consumption.entity.Driver;

public class DriverValidatorImpl implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Driver.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Driver driver = (Driver) target;

		if (driver.getFirstName() == null) {
			errors.rejectValue("firstName", String.format(VALIDATION_ERROR_EMPTY_PARAMETER, "firstName"));
		}
		if (driver.getLastName() == null) {
			errors.rejectValue("lastName", String.format(VALIDATION_ERROR_EMPTY_PARAMETER, "lastName"));
		}
		if (driver.getFirstName().length() < 2) {
			errors.rejectValue("lastName", String.format(VALIDATION_ERROR_STRING_PARAMETER_MIN_LENGTH, "firstName", 2));
		}
		if (driver.getLastName().length() < 2) {
			errors.rejectValue("lastName", String.format(VALIDATION_ERROR_STRING_PARAMETER_MIN_LENGTH, "firstName", 2));
		}
		if (driver.getFirstName().length() > 50) {
			errors.rejectValue("firstName",
					String.format(VALIDATION_ERROR_STRING_PARAMETER_MAX_LENGTH, "firstName", 50));
		}
		if (driver.getLastName().length() > 50) {
			errors.rejectValue("lastName", String.format(VALIDATION_ERROR_STRING_PARAMETER_MAX_LENGTH, "lastName", 50));
		}

	}

}
