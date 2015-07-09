package com.github.ldeitos.validator.stubs;

import java.util.Map;

import com.github.ldeitos.validators.AbstractExtendedValidator;

public class PropertyValidatorExtendedImpl extends
AbstractExtendedValidator<PropertyConstraintExtended, Map<String, Address>> {
	@Override
	public void initialize(PropertyConstraintExtended constraintAnnotation) {
	}

	@Override
	public void doValidation(Map<String, Address> value) {
		addViolation("erro de validação");
	}
}