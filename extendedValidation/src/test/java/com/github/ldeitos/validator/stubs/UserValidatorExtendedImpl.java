package com.github.ldeitos.validator.stubs;

import com.github.ldeitos.validators.AbstractExtendedValidator;
import com.github.ldeitos.validators.util.Path;
import com.github.ldeitos.validators.util.PathBuilder;

public class UserValidatorExtendedImpl extends AbstractExtendedValidator<UserConstraintExtended, User> {
	@Override
	public void initialize(UserConstraintExtended constraintAnnotation) {
	}

	@Override
	public void doValidation(User value) {
		PathBuilder pathBuilder = buildPath("addresses", "home").add("country").add("name");
		Path path = pathBuilder.getPath();
		addViolation(path, "erro de validação");
	}
}