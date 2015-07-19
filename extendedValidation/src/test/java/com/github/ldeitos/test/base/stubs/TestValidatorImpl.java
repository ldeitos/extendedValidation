package com.github.ldeitos.test.base.stubs;

import com.github.ldeitos.validators.AbstractExtendedValidator;

public class TestValidatorImpl extends AbstractExtendedValidator<TestConstraint, Bean> {
	@Override
	public void initialize(TestConstraint constraintAnnotation) {
	}

	@Override
	public void doValidation(Bean value) {
		if (value.getStringField() == null) {
			addViolationWithDefaultTemplate("par=defaultConstraintParameter");
		}

		if (value.isBooleanField()) {
			addViolation("{validation.test.msg}", "par2=parameter2", "par1=parameter1");
		}

		if (value.isOtherBooleanField()) {
			addViolation("{validation.test.indexed}", "Test", "param2", "param3");
		}
	}
}