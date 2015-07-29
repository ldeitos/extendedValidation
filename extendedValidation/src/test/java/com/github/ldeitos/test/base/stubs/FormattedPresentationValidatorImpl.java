package com.github.ldeitos.test.base.stubs;

import com.github.ldeitos.validators.AbstractExtendedValidator;

public class FormattedPresentationValidatorImpl extends
AbstractExtendedValidator<FormattedPresentationConstraint, FormattedTest> {
	@Override
	public void initialize(FormattedPresentationConstraint constraintAnnotation) {
	}

	@Override
	public void doValidation(FormattedTest value) {
		addViolationWithDefaultTemplate();
		addViolation("{KEYB}");
	}
}