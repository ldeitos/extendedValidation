package com.github.ldeitos.validators;

import java.lang.annotation.Annotation;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.github.ldeitos.validation.impl.interpolator.PreInterpolator;

public abstract class AbstractExtendedValidator<A extends Annotation, T> implements ConstraintValidator<A, T> {

	private boolean isValid = true;

	private ConstraintValidatorContext context;

	@Inject
	private PreInterpolator preInterpolator;

	@Override
	public final boolean isValid(T value, ConstraintValidatorContext context) {
		this.context = context;

		doValidation(value);

		return isValid;
	}

	public abstract void doValidation(T value);

	protected void addViolationWithDefaultTemplate(String... msgParameters) {
		String msg = context.getDefaultConstraintMessageTemplate();
		String msgInterpolated = preInterpolator.interpolate(msg, msgParameters);

		makeInvalid();

		context.buildConstraintViolationWithTemplate(msgInterpolated).addConstraintViolation();
	}

	protected void addViolation(String msgTemplate, String... msgParameters) {
		String msgInterpolated = preInterpolator.interpolate(msgTemplate, msgParameters);

		makeInvalid();

		context.buildConstraintViolationWithTemplate(msgInterpolated).addConstraintViolation();
	}

	private void makeInvalid() {
		if (isValid) {
			isValid = false;
			context.disableDefaultConstraintViolation();
		}
	}
}