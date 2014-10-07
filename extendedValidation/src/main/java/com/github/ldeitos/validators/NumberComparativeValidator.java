package com.github.ldeitos.validators;

import static org.apache.commons.lang.ArrayUtils.contains;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public abstract class NumberComparativeValidator<A extends Annotation> implements ConstraintValidator<A, Object> {
	private Class<?>[] validatedClasses = {Integer.class, Long.class, Short.class, 
			Byte.class, BigInteger.class, BigDecimal.class}; 
	
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		boolean ret = true;

		if(value != null) {
			ret = validate(value);
		}
		
		return ret;
	}

	private boolean validate(Object value) {
		boolean ret = true;
		Class<?> valueClass = value.getClass();
		
		if(contains(validatedClasses, valueClass)){
			Number n = (Number) value;
			ret = compareValid(n);
		}		
		
		return ret;
	}

	protected abstract boolean compareValid(Number n);
}
