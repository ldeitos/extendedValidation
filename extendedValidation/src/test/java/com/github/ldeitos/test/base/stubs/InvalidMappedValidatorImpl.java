package com.github.ldeitos.test.base.stubs;

import com.github.ldeitos.validators.AbstractExtendedValidator;
import com.github.ldeitos.validators.util.Path;
import com.github.ldeitos.validators.util.PathBuilder;

public class InvalidMappedValidatorImpl extends AbstractExtendedValidator<InvalidMappedConstraint, User> {
	@Override
	public void initialize(InvalidMappedConstraint constraintAnnotation) {
	}

	@Override
	public void doValidation(User value) {
		PathBuilder pathBuilder = buildPath("addresses[home.teste].country.name");
		Path path = pathBuilder.getPath();
		addViolation(path, "erro de valida��o");
	}
}