package com.github.ldeitos.test.base.stubs;

import com.github.ldeitos.validators.AbstractExtendedValidator;

public class AddressValidatorExtendedImpl extends
AbstractExtendedValidator<AddressConstraintExtended, Address> {
	@Override
	public void initialize(AddressConstraintExtended constraintAnnotation) {
	}

	@Override
	public void doValidation(Address value) {
		addViolation(buildPath("street").getPath(), "erro de valida��o");
	}
}