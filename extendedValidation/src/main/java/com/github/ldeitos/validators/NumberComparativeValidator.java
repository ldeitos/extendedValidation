package com.github.ldeitos.validators;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.math.BigInteger;

abstract class NumberComparativeValidator<A extends Annotation> extends MultiTargetValidator<A> {
	private final Class<?>[] targetClasses = {Integer.class, Long.class, Short.class, 
			Byte.class, BigInteger.class, BigDecimal.class}; 

	protected boolean doValidation(Object value) {
		Number n = (Number) value;
		return compareValid(n);
	}
	
	@Override
	protected Class<?>[] getTargetClasses() {
		return targetClasses;
	}

	protected abstract boolean compareValid(Number n);
}
