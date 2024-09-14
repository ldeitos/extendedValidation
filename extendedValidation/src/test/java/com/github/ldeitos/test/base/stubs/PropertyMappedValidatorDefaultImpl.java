package com.github.ldeitos.test.base.stubs;

import java.util.Map;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PropertyMappedValidatorDefaultImpl implements
ConstraintValidator<PropertyMappedConstraintDefault, Map<String, Address>> {
	@Override
	public void initialize(PropertyMappedConstraintDefault constraintAnnotation) {
	}

	@Override
	public boolean isValid(Map<String, Address> value, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate("erro de valida��o").addBeanNode().inIterable()
		.atKey("home").addConstraintViolation();
		return false;
	}
}