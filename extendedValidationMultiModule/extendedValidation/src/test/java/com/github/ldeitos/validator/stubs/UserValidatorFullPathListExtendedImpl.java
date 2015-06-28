package com.github.ldeitos.validator.stubs;

import com.github.ldeitos.validators.AbstractExtendedValidator;
import com.github.ldeitos.validators.util.PathBuilder;

public class UserValidatorFullPathListExtendedImpl extends
AbstractExtendedValidator<UserConstraintFullPathListExtended, User> {
	@Override
	public void initialize(UserConstraintFullPathListExtended constraintAnnotation) {
	}

	@Override
	public void doValidation(User value) {
		PathBuilder pathBuilder = buildPath("addresses(0).country.name");
		addViolation(pathBuilder, "erro de validação");
	}
}