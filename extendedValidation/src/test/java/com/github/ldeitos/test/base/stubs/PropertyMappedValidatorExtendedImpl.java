package com.github.ldeitos.test.base.stubs;

import java.util.Map;

import com.github.ldeitos.validators.AbstractExtendedValidator;

public class PropertyMappedValidatorExtendedImpl extends
AbstractExtendedValidator<PropertyMappedConstraintExtended, Map<String, Address>> {
	@Override
	public void initialize(PropertyMappedConstraintExtended constraintAnnotation) {
	}

	@Override
	public void doValidation(Map<String, Address> value) {
		addViolation(atKey("home"), "erro de valida��o");
	}
}