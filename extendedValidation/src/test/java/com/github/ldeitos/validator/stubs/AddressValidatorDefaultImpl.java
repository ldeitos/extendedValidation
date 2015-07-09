package com.github.ldeitos.validator.stubs;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AddressValidatorDefaultImpl implements ConstraintValidator<AddressConstraintDefault, Address> {
	@Override
	public void initialize(AddressConstraintDefault constraintAnnotation) {
	}

	@Override
	public boolean isValid(Address value, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate("erro de validação").addPropertyNode("street")
		.addConstraintViolation();
		return false;
	}
}