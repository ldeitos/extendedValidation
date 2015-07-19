package com.github.ldeitos.test.base.stubs;

import java.util.Map;

import com.github.ldeitos.validators.AbstractExtendedValidator;

public class PropertyValidatorExtendedImpl extends
AbstractExtendedValidator<PropertyConstraintExtended, Map<String, Address>> {
	@Override
	public void initialize(PropertyConstraintExtended constraintAnnotation) {
	}

	@Override
	public void doValidation(Map<String, Address> value) {
		addViolation("erro de valida��o");
	}
}