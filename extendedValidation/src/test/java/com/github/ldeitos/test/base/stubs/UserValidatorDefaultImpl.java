package com.github.ldeitos.test.base.stubs;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserValidatorDefaultImpl implements ConstraintValidator<UserConstraintDefault, User> {
	@Override
	public void initialize(UserConstraintDefault constraintAnnotation) {
	}

	@Override
	public boolean isValid(User value, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate("erro de valida��o").addNode("addresses")
		    .addNode("country").inIterable().atKey("home").addNode("name").addConstraintViolation();
		return false;
	}
}