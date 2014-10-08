package com.github.ldeitos.validators;

import static java.util.Arrays.asList;
import static org.apache.commons.collections15.CollectionUtils.exists;

import java.lang.annotation.Annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.collections15.Predicate;

public abstract class MultiTargetValidator<A extends Annotation> implements ConstraintValidator<A, Object> {
	
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		boolean ret = true;

		if(value != null) {
			ret = validate(value);
		}
		
		return ret;
	}

	private boolean validate(Object value) {
		final Class<?> valueClass = value.getClass();
		boolean ret = true;
		Predicate<Class<?>> assignableFrom = new Predicate<Class<?>>() {
			public boolean evaluate(Class<?> arg0) {
				return arg0.isAssignableFrom(valueClass);
			}
		};
		
		if(exists(asList(getTargetClasses()), assignableFrom)){
			ret = doValidation(value);
		}		
		
		return ret;
	}

	protected abstract boolean doValidation(Object value);

	protected abstract Class<?>[] getTargetClasses();
}
