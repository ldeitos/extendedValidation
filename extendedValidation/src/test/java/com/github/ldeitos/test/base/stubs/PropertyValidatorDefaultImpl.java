package com.github.ldeitos.test.base.stubs;

import java.util.Map;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PropertyValidatorDefaultImpl implements
    ConstraintValidator<PropertyConstraintDefault, Map<String, Address>> {
	@Override
	public void initialize(PropertyConstraintDefault constraintAnnotation) {
	}

	@Override
	public boolean isValid(Map<String, Address> value, ConstraintValidatorContext context) {
		context.buildConstraintViolationWithTemplate("erro de valida��o").addConstraintViolation();
		return false;
	}
}