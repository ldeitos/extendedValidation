package com.github.ldeitos.validator.stubs;

import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PropertyValidatorDefaultImpl implements
    ConstraintValidator<PropertyConstraintDefault, Map<String, Address>> {
	@Override
	public void initialize(PropertyConstraintDefault constraintAnnotation) {
	}

	@Override
	public boolean isValid(Map<String, Address> value, ConstraintValidatorContext context) {
		context.buildConstraintViolationWithTemplate("erro de validação").addConstraintViolation();
		return false;
	}
}