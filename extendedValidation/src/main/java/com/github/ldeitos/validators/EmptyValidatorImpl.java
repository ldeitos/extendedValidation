package com.github.ldeitos.validators;

import static org.apache.commons.lang3.ArrayUtils.getLength;

import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

import com.github.ldeitos.constraint.Empty;

import jakarta.enterprise.context.Dependent;

@Dependent
public class EmptyValidatorImpl extends MultiTargetValidator<Empty> implements EmptyValidator {

	private static final Class<?>[] targetClasses = { Collection.class, Map.class, CharSequence.class };

	@Override
	public void initialize(Empty constraintAnnotation) {
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

		return size == 0;
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
