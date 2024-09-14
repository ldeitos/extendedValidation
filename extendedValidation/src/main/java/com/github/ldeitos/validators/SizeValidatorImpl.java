package com.github.ldeitos.validators;

import static java.lang.String.format;
import static org.apache.commons.lang3.ArrayUtils.getLength;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

import com.github.ldeitos.constraint.Size;

public class SizeValidatorImpl extends MultiTargetValidator<Size> implements SizeValidator {

	private static final Class<?>[] targetClasses = { Collection.class, Map.class, CharSequence.class };

	private int min;

	private int max;

	@Override
	public void initialize(Size constraintAnnotation) {
		min = constraintAnnotation.min();
		max = constraintAnnotation.max();

		validateParameters();
	}

	private void validateParameters() {
		doValidation(min, 0, "'min'", "zero");
		doValidation(max, 0, "'max'", "zero");
		doValidation(max, min, "'max'", "'min'");
	}

	private void doValidation(int val, int valReference, String fieldName, String valueReference) {
		if (val < valReference) {
			String msg = "Attribute %s must be greater than %s.";
			throw new InvalidParameterException(format(msg, fieldName, valueReference));
		}
	}

	@Override
	protected boolean doValidation(Object value) {
		Class<?> valueClass = value.getClass();
		int size;

		if (CharSequence.class.isAssignableFrom(valueClass)) {
			size = CharSequence.class.cast(value).length();
		} else if (Collection.class.isAssignableFrom(valueClass)) {
			size = Collection.class.cast(value).size();
		} else if (Map.class.isAssignableFrom(valueClass)) {
			size = Map.class.cast(value).size();
		} else {
			size = getLength(value);
		}

		return minValid(size) && maxValid(size);
	}

	private boolean maxValid(int size) {
		return size <= max;
	}

	private boolean minValid(int size) {
		return size >= min;
	}

	@Override
	protected Class<?>[] getTargetClasses() {
		return targetClasses;
	}

	@Override
	protected Predicate<Class<?>> getMustValidatePredicate(final Class<?> valueClass) {
		Predicate<Class<?>> assignableFrom = new Predicate<Class<?>>() {
			@Override
			public boolean test(Class<?> arg0) {
				return valueClass.getName().startsWith("[") || arg0.isAssignableFrom(valueClass);
			}
		};
		return assignableFrom;
	}

}
