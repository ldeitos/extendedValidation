package com.github.ldeitos.validator.stubs;

import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PropertyMappedValidatorDefaultImpl implements
ConstraintValidator<PropertyMappedConstraintDefault, Map<String, Address>> {
	@Override
	public void initialize(PropertyMappedConstraintDefault constraintAnnotation) {
	}

	@Override
	public boolean isValid(Map<String, Address> value, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate("erro de validação").addBeanNode().inIterable()
		.atKey("home").addConstraintViolation();
		return false;
	}
}