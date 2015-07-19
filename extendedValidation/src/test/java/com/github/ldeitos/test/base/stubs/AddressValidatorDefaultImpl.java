package com.github.ldeitos.test.base.stubs;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AddressValidatorDefaultImpl implements ConstraintValidator<AddressConstraintDefault, Address> {
	@Override
	public void initialize(AddressConstraintDefault constraintAnnotation) {
	}

	@Override
	public boolean isValid(Address value, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate("erro de valida��o").addPropertyNode("street")
		.addConstraintViolation();
		return false;
	}
}