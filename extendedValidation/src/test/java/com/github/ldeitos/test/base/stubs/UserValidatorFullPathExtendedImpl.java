package com.github.ldeitos.test.base.stubs;

import com.github.ldeitos.validators.AbstractExtendedValidator;
import com.github.ldeitos.validators.util.PathBuilder;

public class UserValidatorFullPathExtendedImpl extends
AbstractExtendedValidator<UserConstraintFullPathExtended, User> {
	@Override
	public void initialize(UserConstraintFullPathExtended constraintAnnotation) {
	}

	@Override
	public void doValidation(User value) {
		PathBuilder pathBuilder = buildPath("addresses[home].country.name");
		addViolation(pathBuilder, "erro de valida��o");
	}
}