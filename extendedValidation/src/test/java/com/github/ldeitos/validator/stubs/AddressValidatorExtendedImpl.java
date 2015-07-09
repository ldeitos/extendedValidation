package com.github.ldeitos.validator.stubs;

import com.github.ldeitos.validators.AbstractExtendedValidator;

public class AddressValidatorExtendedImpl extends
AbstractExtendedValidator<AddressConstraintExtended, Address> {
	@Override
	public void initialize(AddressConstraintExtended constraintAnnotation) {
	}

	@Override
	public void doValidation(Address value) {
		addViolation(buildPath("street").getPath(), "erro de validação");
	}
}