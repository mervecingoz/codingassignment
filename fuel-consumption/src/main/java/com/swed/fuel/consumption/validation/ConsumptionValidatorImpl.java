package com.swed.fuel.consumption.validation;

import static com.swed.fuel.consumption.validation.ValidationUtil.VALIDATION_ERROR_BIGDECIMAL_PARAMETER_MUST_BE_POSITIVE;
import static com.swed.fuel.consumption.validation.ValidationUtil.VALIDATION_ERROR_EMPTY_PARAMETER;

import java.math.BigDecimal;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.swed.fuel.consumption.entity.Consumption;


public class ConsumptionValidatorImpl implements Validator {
	

	@Override
	public boolean supports(Class<?> clazz) {
		return Consumption.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		Consumption consumption = (Consumption) target;
		
		if (consumption.getDriver() == null) {
			errors.rejectValue("driver", String.format(VALIDATION_ERROR_EMPTY_PARAMETER, "driver"));
		}		
		if (consumption.getDate() == null) {
			errors.rejectValue("date", String.format(VALIDATION_ERROR_EMPTY_PARAMETER, "date"));
		}
		if (consumption.getFueltype() == null) {
			errors.rejectValue("fuelType", String.format(VALIDATION_ERROR_EMPTY_PARAMETER, "fuelType"));
		}
		if (consumption.getPrice() == null) {
			errors.rejectValue("price", String.format(VALIDATION_ERROR_EMPTY_PARAMETER, "price"));
		}
		if (consumption.getVolume() == null) {
			errors.rejectValue("volume", String.format(VALIDATION_ERROR_EMPTY_PARAMETER, "volume"));
		}
		if (consumption.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
			errors.rejectValue("price", String.format(VALIDATION_ERROR_BIGDECIMAL_PARAMETER_MUST_BE_POSITIVE, "price"));
		}
		if (consumption.getVolume().compareTo(BigDecimal.ZERO) <= 0) {
			errors.rejectValue("volume",
					String.format(VALIDATION_ERROR_BIGDECIMAL_PARAMETER_MUST_BE_POSITIVE, "volume"));
		}

	}

}
