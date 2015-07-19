package com.github.ldeitos.test.base.stubs;

import com.github.ldeitos.validators.AbstractExtendedValidator;
import com.github.ldeitos.validators.util.Path;
import com.github.ldeitos.validators.util.PathBuilder;

public class InvalidListValidatorImpl extends AbstractExtendedValidator<InvalidListConstraint, User> {
	@Override
	public void initialize(InvalidListConstraint constraintAnnotation) {
	}

	@Override
	public void doValidation(User value) {
		PathBuilder pathBuilder = buildPath("addresses(home).country.name");
		Path path = pathBuilder.getPath();
		addViolation(path, "erro de valida��o");
	}
}