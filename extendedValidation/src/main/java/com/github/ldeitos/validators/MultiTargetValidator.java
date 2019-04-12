package com.github.ldeitos.validators;

import static java.lang.String.format;
import static java.util.Arrays.asList;

import java.lang.annotation.Annotation;
import java.security.InvalidParameterException;
import java.util.function.Predicate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

abstract class MultiTargetValidator<A extends Annotation> implements ConstraintValidator<A, Object> {

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		boolean ret = true;

		if (value != null) {
			ret = validate(value);
		}

		return ret;
	}

	private boolean validate(Object value) {
		final Class<?> valueClass = value.getClass();
		boolean ret = true;
		Predicate<Class<?>> assignableFrom = getMustValidatePredicate(valueClass);

		if (asList(getTargetClasses()).stream().anyMatch(assignableFrom)) {
			ret = doValidation(value);
		} else {
			throw new InvalidParameterException(format(
			    "Invalid target type [%s] to be validated by [%s] validator.", valueClass.getName(), this
			        .getClass().getName()));
		}

		return ret;
	}

	protected Predicate<Class<?>> getMustValidatePredicate(final Class<?> valueClass) {
		Predicate<Class<?>> assignableFrom = new Predicate<Class<?>>() {
			@Override
			public boolean test(Class<?> t) {
				return t.isAssignableFrom(valueClass);
			}
		};
		return assignableFrom;
	}

	protected abstract boolean doValidation(Object value);

	protected abstract Class<?>[] getTargetClasses();
}
